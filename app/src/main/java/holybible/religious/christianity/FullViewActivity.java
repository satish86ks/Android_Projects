package holybible.religious.christianity;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;
import java.util.HashMap;

public class FullViewActivity extends AppCompatActivity implements View.OnClickListener {

    TextView workout_title, workout_description;
    ScrollView scrollView;
    int selectedIndex, totalItems;
    ImageView nextButton, prevButton;
    ImageView workout_img;
    DbHelper myDbHelper;
    ArrayList<HashMap<String, String>> list;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    String type, selectedPrayer;
    String getHeading;
    private final String TAG = FullViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get MainCategory and Category index from intent
        Intent intent = getIntent();
        selectedIndex = intent.getIntExtra("SelectedIndex", 0);
        type = intent.getStringExtra("SelectedType");
        selectedPrayer = intent.getStringExtra("SelectedPrayer");

        scrollView = (ScrollView) findViewById(R.id.scrollview);
        workout_title = (TextView) findViewById(R.id.workout_title);
        workout_description = (TextView) findViewById(R.id.workout_description);
        nextButton = (ImageView) findViewById(R.id.nextbtn);
        prevButton = (ImageView) findViewById(R.id.prevbtn);
        workout_img = (ImageView) findViewById(R.id.workout_img);
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);

        myDbHelper = new DbHelper(this, "christ.db");

        if (type.equals("Prayers")) {
            getHeading = "title";

            int resourceId = getResources().getIdentifier(selectedPrayer.replace(" ", "_").toLowerCase() + "_banner", "drawable", getPackageName());
            workout_img.setImageResource(resourceId);

            list = myDbHelper.getprayerdetails(selectedPrayer);
            workout_title.setText(list.get(selectedIndex).get(getHeading));
        } else {
            getHeading = "heading";
            list = myDbHelper.gethistorydata();
            workout_title.setText(list.get(selectedIndex).get(getHeading));
        }


        totalItems = list.size() - 1;
//        workout_title.setText(list.get(selectedIndex).get("heading"));
        workout_description.setText(list.get(selectedIndex).get("description"));

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        com.google.android.gms.ads.interstitial.InterstitialAd.load(this,getString(R.string.admob_interstitial_id), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.prevbtn:
                prevButton();
                return;

            case R.id.nextbtn:
                nextButton();
                return;

            default:
                return;
        }
    }

    void nextButton() {

        selectedIndex++;

        if (this.selectedIndex <= totalItems) {

            //  setImageOnBanner(selectedIndex);
            workout_title.setText(list.get(selectedIndex).get(getHeading));
            workout_description.setText(list.get(selectedIndex).get("description"));

        } else {
            selectedIndex = totalItems;
        }

        setbtnvisibility();
        scrollView.scrollTo(0, 0);
    }

    void prevButton() {

        selectedIndex--;

        if (this.selectedIndex >= 0) {

//            setImageOnBanner(selectedIndex);
            workout_title.setText(list.get(selectedIndex).get(getHeading));
            workout_description.setText(list.get(selectedIndex).get("description"));
        } else {
            this.selectedIndex = 0;
        }

        setbtnvisibility();
        scrollView.scrollTo(0, 0);
    }

    void setbtnvisibility() {
        if (selectedIndex == 0) {
            prevButton.setVisibility(View.GONE);
        } else if (selectedIndex == totalItems) {
            nextButton.setVisibility(View.GONE);

        } else {
            prevButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(mInterstitialAd!=null) {
            mInterstitialAd.show(this);
        }

    }
}