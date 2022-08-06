package holybible.religious.christianity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.Arrays;
import java.util.List;

import holybible.religious.christianity.adapter.ReadJoke_Adapter;

public class QuotesActivity extends AppCompatActivity implements View.OnClickListener {

    List<String> jokes;
    TextView index_status;
    ImageView nextbtn, prevbtn, shareJoke, shareWithWhatsapp;
    ScrollView scrollView;
    RelativeLayout bottom_layout;

    ReadJoke_Adapter readJokeAdapter;

    ViewPager readJokePager;
    ReaderViewPagerTransformer viewPagerTransformer;
    int currentIndex, totalJokes;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    String selectedCategory, selectedChapter;
    DbHelper dbHelper;
    private final String TAG = QuotesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_quotes);

        init();

        //////////////////
        readJokeAdapter = new ReadJoke_Adapter(this, jokes);
        readJokePager.setAdapter(readJokeAdapter);
        readJokePager.setPageMargin(8);
        readJokePager.setCurrentItem(0);

        viewPagerTransformer = new ReaderViewPagerTransformer(ReaderViewPagerTransformer.TransformType.FLOW);
        readJokePager.setPageTransformer(true, viewPagerTransformer);

        readJokePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
                setbtnvisibility();

                setActionbar(currentIndex + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        InterstitialAd.load(this,getString(R.string.admob_interstitial_id), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.i(TAG, "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i(TAG, loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });



    }

    void setActionbar(int index) {

        index_status.setText(index + " / " + jokes.size());

    }

    void init() {

        selectedChapter = getIntent().getStringExtra("selected_chapter");
        selectedCategory = getIntent().getStringExtra("selected_category");

        dbHelper = new DbHelper(this, "christ.db");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        index_status = (TextView) findViewById(R.id.index_status);
        readJokePager = (ViewPager) findViewById(R.id.readJokePager);
        nextbtn = (ImageView) findViewById(R.id.nextbtn);
        prevbtn = (ImageView) findViewById(R.id.prevbtn);
        shareJoke = (ImageView) findViewById(R.id.share_current_joke);
        shareWithWhatsapp = (ImageView) findViewById(R.id.share_with_whatsapp);

        bottom_layout = (RelativeLayout) findViewById(R.id.bottom_layout);
        nextbtn.setOnClickListener(this);
        shareJoke.setOnClickListener(this);
        shareWithWhatsapp.setOnClickListener(this);
        prevbtn.setOnClickListener(this);

        if (selectedCategory != null) {
            getSupportActionBar().setTitle("Holy Bible");
            jokes = dbHelper.getBibleChapterDescription(selectedChapter);
            totalJokes = jokes.size();
        } else {
            getSupportActionBar().setTitle("Bible Quotes");
            jokes = Arrays.asList(getResources().getStringArray(R.array.qoutes));
            totalJokes = jokes.size();
        }

        setActionbar(1);
    }


    void nextButton() {

        currentIndex++;

        if (this.currentIndex <= totalJokes) {

            readJokePager.setCurrentItem(currentIndex);

        } else {
            currentIndex = totalJokes;
        }
    }

    void prevButton() {

        currentIndex--;

        if (this.currentIndex >= 0) {
            readJokePager.setCurrentItem(currentIndex);

        } else {
            this.currentIndex = 0;
        }

    }

    void setbtnvisibility() {
        if (currentIndex == 0) {
            prevbtn.setVisibility(View.GONE);
        } else if (currentIndex == totalJokes) {
            nextbtn.setVisibility(View.GONE);

        } else {
            prevbtn.setVisibility(View.VISIBLE);
            nextbtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.prevbtn:
                prevButton();
                return;

            case R.id.nextbtn:
                nextButton();
                return;

            case R.id.share_current_joke:
                shareCurrentJoke();
                return;

            case R.id.share_with_whatsapp:
                shareWithWhatsapp();
                return;


            default:
                return;
        }
    }

    private void shareApp() {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = "https://play.google.com/store/apps/details?id=" + getPackageName();
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(intent, "Choose sharing method"));
    }

    private void shareCurrentJoke() {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        if(jokes.size()>currentIndex) {
            String shareBodyText = jokes.get(currentIndex);
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(intent, "Share current joke"));
        }
    }

    private void shareWithWhatsapp() {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        if(jokes.size()>currentIndex) {
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, jokes.get(currentIndex));
            try {
                startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(mInterstitialAd!=null) {
            mInterstitialAd.show(this);
        }

    }
}