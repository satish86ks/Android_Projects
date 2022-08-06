package holybible.religious.christianity;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import holybible.religious.christianity.BabyNames.FrontActivity;
import holybible.religious.christianity.Bible.BibleChapters;
import holybible.religious.christianity.adapter.NavigationListAdapter;
import holybible.religious.christianity.entity.NavDrawerItem;

public class MainActivity extends FragmentActivity implements OnItemClickListener {

    private String[] navMenuTitles;


    private InterstitialAd mInterstitialAd;
    SharedPreferences sharedPreferences;
    Calendar c;
    String ratestar;
    private final String TAG = MainActivity.class.getSimpleName();
    AdView mAdView;


    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.main_activity);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();

        navMenuTitles = getResources().getStringArray(R.array.navigation_array);
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], R.drawable.verses, NavDrawerItem.TYPE_CHALISA));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], R.drawable.prayer, NavDrawerItem.TYPE_MANTRA));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], R.drawable.audio, NavDrawerItem.TYPE_CALENDAR));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], R.drawable.bible, NavDrawerItem.TYPE_BIBLE));

        NavigationListAdapter adapter = new NavigationListAdapter(this, navDrawerItems);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        sharedPreferences = getSharedPreferences("RATING", Context.MODE_PRIVATE);
        c = Calendar.getInstance();

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

     //   MediationTestSuite.launch(this);


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
        NavDrawerItem item = (NavDrawerItem) adapterView.getAdapter().getItem(arg2);
        switch (item.getType()) {

            case NavDrawerItem.TYPE_CHALISA:

                // verses by topic
                startActivity(new Intent(this, ChalisaActivity.class));

                break;
            case NavDrawerItem.TYPE_MANTRA:

                // Prayers
                Intent intent2 = new Intent(this, PrayerGrid.class);
                intent2.putExtra("SelectedType", "Prayers");
                startActivity(intent2);


                break;
            case NavDrawerItem.TYPE_CALENDAR:

                // Audio Players
                startActivity(new Intent(this, PlayListActivity.class));

                break;

            case NavDrawerItem.TYPE_BIBLE:

                // Holy Bible
                Intent bible = new Intent(this, BibleChapters.class);
                startActivity(bible);

                break;

            case NavDrawerItem.TYPE_BABY_NAMES:

                startActivity(new Intent(this, FrontActivity.class));
                break;

            case NavDrawerItem.TYPE_QUOTES:

                Intent quotes = new Intent(this, QuotesActivity.class);
                startActivity(quotes);
                break;

            case NavDrawerItem.TYPE_AARTI:

                Intent intent = new Intent(this, CategoryList.class);
                intent.putExtra("SelectedType", "History Of Christianity");
                intent.putExtra("SelectedPrayer", "History Of Christianity");
                startActivity(intent);
                break;

            default:
                break;
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {

        exitMessage();
    }

    private void exitMessage() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.exist_custom_layout, null);
        builder.setView(viewInflated);

        final AlertDialog alert = builder.create();

        TextView moreApp = (TextView) viewInflated.findViewById(R.id.moreBTN);
        // TextView askLatter = (TextView) viewInflated.findViewById(R.id.nagetiveBTN);
        final TextView rateAs = (TextView) viewInflated.findViewById(R.id.positiveBTN);
        final RatingBar ratingBar = (RatingBar) viewInflated.findViewById(R.id.ratebar);


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                ratestar = String.valueOf(rating);

                if (rating > 3) {
                    rateUs();
                    alert.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Thanks For Your Feedback", Toast.LENGTH_SHORT).show();
                    alert.dismiss();
                }


            }
        });
        rateAs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
                finishAffinity();
                moveTaskToBack(true);
            }
        });

        moreApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
                gotoPlaystore();
            }
        });

        alert.show();
    }

    private void gotoPlaystore() {

        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=MS+TECH+APPS")));
        }
    }

    private void rateUs() {

        String appPackageName = getApplicationContext().getPackageName();
        Intent intent3 = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName));
        startActivity(intent3);

    }

    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }

        if (mInterstitialAd != null) {

        }
        super.onDestroy();
    }
}