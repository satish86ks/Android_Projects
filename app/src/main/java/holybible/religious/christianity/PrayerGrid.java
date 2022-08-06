package holybible.religious.christianity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

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

import holybible.religious.christianity.adapter.PrayerAdapter;

public class PrayerGrid extends AppCompatActivity {

    GridView prayers_grid;
    PrayerAdapter prayerAdapter;

    DbHelper myDbHelper;
    ArrayList<HashMap<String, String>> list;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    private final String TAG = PrayerGrid.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_grid);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Bible Prayers");

        prayers_grid = findViewById(R.id.prayers_grid);

        myDbHelper = new DbHelper(this, "christ.db");
        list = myDbHelper.getprayerdata();

        prayerAdapter = new PrayerAdapter(this, list);
        prayers_grid.setAdapter(prayerAdapter);
        prayers_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(PrayerGrid.this, CategoryList.class);
                intent.putExtra("SelectedIndex", position);
                intent.putExtra("SelectedType", "Prayers");
                intent.putExtra("SelectedPrayer", list.get(position).get("heading"));
                startActivity(intent);
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
    public boolean onSupportNavigateUp() {
        onBackPressed();

        if(mInterstitialAd!=null) {
            mInterstitialAd.show(this);
        }

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