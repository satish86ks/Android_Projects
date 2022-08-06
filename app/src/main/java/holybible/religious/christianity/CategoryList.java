package holybible.religious.christianity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


import java.util.ArrayList;
import java.util.HashMap;

import holybible.religious.christianity.adapter.ExcersizeListAdapter;

public class CategoryList extends AppCompatActivity {

    ArrayList<HashMap<String, String>> data;
    DbHelper myDbHelper;

    ImageView backDropImage;
    int index, getContent;
    String type, selectedPrayer;

    RecyclerView categoryRecyclerview;
    ExcersizeListAdapter excersizeListAdapter;
    View ChildView;
    //  int[] backDropImagesArray = {R.drawable.cover_arm_3, R.drawable.cover_abs3, R.drawable.cover_chest_3, R.drawable.cover_shoulder_3, R.drawable.triceap, R.drawable.forearms, R.drawable.cover_back, R.drawable.cardio, R.drawable.cover_leg_3, R.drawable.calf};

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private final String TAG = CategoryList.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_grid);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();

        Intent intent = getIntent();
        index = intent.getIntExtra("SelectedIndex", 0);
        //getContent = intent.getIntExtra("position", -1);
        type = intent.getStringExtra("SelectedType");
        selectedPrayer = intent.getStringExtra("SelectedPrayer");

        myDbHelper = new DbHelper(this, "christ.db");

        backDropImage = (ImageView) findViewById(R.id.description_backdrop);

        if (type.equals("Prayers")) {
            data = myDbHelper.getprayerdetails(selectedPrayer);

            int resourceId = getResources().getIdentifier(selectedPrayer.replace(" ", "_").toLowerCase() + "_banner", "drawable", getPackageName());
            backDropImage.setImageResource(resourceId);

        } else {
            data = myDbHelper.gethistorydata();
        }


        categoryRecyclerview = (RecyclerView) findViewById(R.id.category_list);
        categoryRecyclerview.setNestedScrollingEnabled(false);
        categoryRecyclerview.setLayoutManager(new LinearLayoutManager(this));
      //  RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
      //  categoryRecyclerview.addItemDecoration(itemDecoration);


        excersizeListAdapter = new ExcersizeListAdapter(this, data, type);
        categoryRecyclerview.setAdapter(excersizeListAdapter);

        categoryRecyclerview.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(CategoryList.this, new GestureDetector.SimpleOnGestureListener() {

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

                    Intent intent = new Intent(CategoryList.this, FullViewActivity.class);
                    intent.putExtra("SelectedIndex", position);
                    intent.putExtra("SelectedType", type);
                    intent.putExtra("SelectedPrayer", selectedPrayer);
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

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(selectedPrayer);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(selectedPrayer);
                    isShow = false;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        if(mInterstitialAd!=null) {
            mInterstitialAd.show(this);
        }

    }


}