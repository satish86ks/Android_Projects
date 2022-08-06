package holybible.religious.christianity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MusicPlayer extends AppCompatActivity implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, View.OnClickListener {

    ConnectivityManager cm;
    NetworkInfo activeNetwork;

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    TextView totalTime, currentTime;
    private int lengthOfAudio;
    String baseUrl = "http://gator4273.temp.domains/~saibaba/sai-song/assets/songs/";
    List<String> songList;
    private int currentSongIndex;
    ImageView play_pause, next, previous, shareSong;
    private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;
    File file;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title and go full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_music_player);

        currentSongIndex = getIntent().getIntExtra("SelectedSongIndex", 0);
        songList = getIntent().getStringArrayListExtra("SongList");

        // create a media player
        mediaPlayer = new MediaPlayer();
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        totalTime = (TextView) findViewById(R.id.totalTime);
        currentTime = (TextView) findViewById(R.id.currentTime);

        play_pause = (ImageView) findViewById(R.id.media_play);
        next = (ImageView) findViewById(R.id.next);
        previous = (ImageView) findViewById(R.id.previous);
        shareSong = (ImageView) findViewById(R.id.share_btn);

        play_pause.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        shareSong.setOnClickListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {

                //  Toast.makeText(MusicPlayer.this, "Error....", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        play();
        volumeControls();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playNext();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
        seekBar.setSecondaryProgress((lengthOfAudio / 100) * percent);

    }

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            if (mediaPlayer != null) {

                //set progress to current position
                int mCurrentPosition = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(mCurrentPosition);

                //update current time text view
                currentTime.setText(getTimeString(mCurrentPosition));

                //handle drag on seekbar
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (mediaPlayer != null && fromUser) {
                            mediaPlayer.seekTo(progress);
                        }
                    }
                });

            }

            //repeat above code every second
            mHandler.postDelayed(this, 10);
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void play() {

        file = new File(Environment.getExternalStorageDirectory(), "Sai Baba/" + songList.get(currentSongIndex).replace(".mp3", ""));

        if (file.exists()) {
            //  Toast.makeText(this, "File available", Toast.LENGTH_SHORT).show();

            try {
                mediaPlayer.setDataSource(String.valueOf(file));
                mediaPlayer.prepareAsync();
                play_Song();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            //  Toast.makeText(this, "File not available", Toast.LENGTH_SHORT).show();
            checkInternetConnection();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                try {
                    mediaPlayer.setDataSource(baseUrl + songList.get(currentSongIndex));
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                play_Song();

            } else {
                //showErrorDialog();
            }
        }
    }


    void play_Song() {
        // try to load data and play
        try {

            seekBar.setSecondaryProgress(0);

            // create a progress dialog (waiting media player preparation)
            final ProgressDialog dialog = new ProgressDialog(this);

            // set message of the dialog
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();

            // execute this code at the end of asynchronous media player preparation
            mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
                public void onPrepared(final MediaPlayer mp) {

                    //start media player
                    mp.start();

                    //set max value
                    lengthOfAudio = mediaPlayer.getDuration();
                    seekBar.setMax(lengthOfAudio);
                    totalTime.setText(getTimeString(lengthOfAudio));

                    //update seekbar
                    mRunnable.run();

                    //dismiss dialog
                    dialog.dismiss();
                    play_pause.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                }
            });

        } catch (Exception e) {
            Activity a = this;
            a.finish();
            Toast.makeText(this, "file_not_found", Toast.LENGTH_SHORT).show();
        }
    }


    public void play_Pause() {
        // check for already playing
        if (mediaPlayer.isPlaying()) {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
                // Changing button image to play button
                play_pause.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
            }
        } else {
            // Resume song
            if (mediaPlayer != null) {
                mediaPlayer.start();
                // Changing button image to pause button
                play_pause.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
            }
        }
    }


    public void seekForward(View view) {

        //set seek time
        int seekForwardTime = 5000;

        // get current song position
        int currentPosition = mediaPlayer.getCurrentPosition();
        // check if seekForward time is lesser than song duration
        if (currentPosition + seekForwardTime <= mediaPlayer.getDuration()) {
            // forward song
            mediaPlayer.seekTo(currentPosition + seekForwardTime);
        } else {
            // forward to end position
            mediaPlayer.seekTo(mediaPlayer.getDuration());
        }

    }

    public void seekBackward(View view) {

        //set seek time
        int seekBackwardTime = 5000;

        // get current song position
        int currentPosition = mediaPlayer.getCurrentPosition();
        // check if seekBackward time is greater than 0 sec
        if (currentPosition - seekBackwardTime >= 0) {
            // forward song
            mediaPlayer.seekTo(currentPosition - seekBackwardTime);
        } else {
            // backward to starting position
            mediaPlayer.seekTo(0);
        }

    }

    //skip to previous track
    public void playPrev() {
        if (currentSongIndex > 0) {
            mediaPlayer.reset();
            currentSongIndex = currentSongIndex - 1;
            play();
        } else {
            // play last song
            mediaPlayer.reset();
            currentSongIndex = songList.size() - 1;
            play();
        }
    }

    //skip to next
    public void playNext() {
        if (currentSongIndex == songList.size() - 1) {
            mediaPlayer.reset();
            currentSongIndex = 0;
            play();
        } else {
            // play first song
            mediaPlayer.reset();
            currentSongIndex = currentSongIndex + 1;
            play();
        }
    }

    private String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        //  long hours = millis / (1000 * 60 * 60);
        long minutes = (millis % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = ((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000;
        //  (String.format("%02d", hours))
        //         .append(":")
        buf.append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }

    private void volumeControls() {
        try {
            volumeSeekbar = (SeekBar) findViewById(R.id.volume_seekbar);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.media_play:
                play_Pause();

                return;
            case R.id.previous:
                playPrev();

                return;

            case R.id.next:
                playNext();

                return;

            case R.id.share_btn:
                shareCurrentSong();

                return;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        finish();
    }



    void checkInternetConnection() {
        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = cm.getActiveNetworkInfo();
    }

    private void shareCurrentSong() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = baseUrl + songList.get(currentSongIndex);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject/Title");
        intent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(intent, "Share Current Song"));
    }

}