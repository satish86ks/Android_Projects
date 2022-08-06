package holybible.religious.christianity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static holybible.religious.christianity.R.id.pager;
import static holybible.religious.christianity.R.id.tab_layout;


/**
 * Fragment that appears in the "content_frame", shows a planet
 */
@SuppressLint("NewApi")
public class mantrafragment extends Fragment {
    private float lastX;
    ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private static int NUM_PAGES = 5;
    String arrays[] = null;
    public static final String ARG_PLANET_NUMBER = "planet_number";
    private int dotsCount = 5;    //No of tabs or images
    private ImageView[] dots;
    LinearLayout linearLayout;

    public mantrafragment() {
        // Empty constructor required for fragment subclasses
    }

    View rootView;
    int count = 0;
    int adAfter = 3;
    DbHelper myDbHelper;
    private ArrayList<HashMap<String, String>> ar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.aarti_layout, container,
                false);
        myDbHelper = new DbHelper(getActivity(), "christ.db");
        ar = myDbHelper.getprayerdetails(getArguments().getString("heading"));
        NUM_PAGES = ar.size();
        for (int i = ar.size() - 1; i > 0; i--) {
            TextView textView = new TextView(getActivity());
            textView.setText(ar.get(i).get("description"));
            textView.setTextSize(20);
            textView.setGravity(Gravity.CENTER);
            // viewFlipper.addView(textView);
        }
        mPager = (ViewPager) rootView.findViewById(pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(tab_layout);
        tabLayout.bringToFront();
        tabLayout.setupWithViewPager(mPager);


        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                count++;
                if (count > adAfter) {
                    count = 0;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return rootView;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = new ScreenSlidePageFragment();

            Bundle bdl = new Bundle();

            bdl.putString(ScreenSlidePageFragment.EXTRA_MESSAGE, ar.get(position).get("description"));
            bdl.putString("heading", ar.get(position).get("title"));
            //Toast.makeText(getActivity(),ar.get(position).get("title")+ " nnnn", Toast.LENGTH_SHORT).show();

            fragment.setArguments(bdl);

            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}