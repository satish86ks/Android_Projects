package holybible.religious.christianity;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class SongsManager {
    // SDCard Path
    final String MEDIA_PATH = new String("/sdcard/");
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    String[] arr = {"Now That You Are Near", "Thank U Lord", "I will Be Your Freind", "More Than Enough", "Blessed Be your Name", "You Are My Refuge", "Power Of Your Love",
            "God Will Make Way", "We Will Not Be Defeated", "My Redeemer Alive", "Jesus Is The Sweetest", "You Are Alpha And Omega", "Here I Am To Worship",
            "Every Move I Make", "Never Give Up", "This Is My Desire"};

    // Constructor
    public SongsManager() {

    }

    /**
     * Function to read all mp3 files from sdcard
     * and store the details in ArrayList
     */
    public ArrayList<HashMap<String, String>> getPlayList() {
        Field[] fields = R.raw.class.getFields();

        if (fields.length > 0) {
            for (int count = 0; count < fields.length; count++) {
                HashMap<String, String> song = new HashMap<String, String>();
                song.put("songTitle", fields[count].getName());
                song.put("songPath", arr[count]);

                // Adding each song to SongList
                songsList.add(song);
            }
        }

        // return songs list array
        return songsList;
    }

    /**
     * Class to filter files which are having .mp3 extension
     */
    class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }
}
