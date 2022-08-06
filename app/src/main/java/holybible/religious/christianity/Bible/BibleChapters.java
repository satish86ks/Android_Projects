package holybible.religious.christianity.Bible;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.List;

import holybible.religious.christianity.DbHelper;
import holybible.religious.christianity.QuotesActivity;
import holybible.religious.christianity.R;

public class BibleChapters extends AppCompatActivity {

    RecyclerView chaptersRecyclerview;
    ChapterdAdapter druidAdapter;
    List<TitleModel> chapterList;
    DbHelper dbHelper;
    View ChildView;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private final String TAG = BibleChapters.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bible_chapters);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Holy Bible");

        chaptersRecyclerview = findViewById(R.id.chapter_list);
        chaptersRecyclerview.setLayoutManager(new LinearLayoutManager(this));
      //  RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
     //   chaptersRecyclerview.addItemDecoration(itemDecoration);

        dbHelper = new DbHelper(this, "christ.db");
        chapterList = dbHelper.getBibleChaptersTitles();
        druidAdapter = new ChapterdAdapter(this, chapterList);
        chaptersRecyclerview.setAdapter(druidAdapter);

        chaptersRecyclerview.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(BibleChapters.this, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                int position = Recyclerview.getChildAdapterPosition(ChildView);

                if (ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {

                    Intent intent = new Intent(BibleChapters.this, QuotesActivity.class);
                    intent.putExtra("selected_chapter", chapterList.get(position).getName());
                    intent.putExtra("selected_category", "Holy Bible");
                    startActivity(intent);

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

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
