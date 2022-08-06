package holybible.religious.christianity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static holybible.religious.christianity.R.id.aartiheader;
import static holybible.religious.christianity.R.id.aartipager;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
@SuppressLint("NewApi")
public class aartifragment extends Fragment {
    private float lastX;
    TextView mPager;
    private PagerAdapter mPagerAdapter;
    private static int NUM_PAGES = 5;
    String arrays[] = null;
    public static final String ARG_PLANET_NUMBER = "planet_number";
    private int dotsCount = 5;    //No of tabs or images
    private ImageView[] dots;
    LinearLayout linearLayout;

    public aartifragment() {
        // Empty constructor required for fragment subclasses
    }

    View rootView;
    TextView header;
    int count = 0;
    int adAfter = 3;
    DbHelper myDbHelper;
    ArrayList<HashMap<String, String>> al;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.aartilayout2, container, false);

        arrays = getArguments().getString(aartifragment.ARG_PLANET_NUMBER).split("-");
        NUM_PAGES = arrays.length;
        // al=myDbHelper.gethistorydata();
        /*for (int i = al.size() - 1; i > 0; i--) {
            TextView textView = new TextView(getActivity());
            textView.setText(al.get(i).get("description"));
            textView.setTextSize(20);
            textView.setGravity(Gravity.CENTER);
            // viewFlipper.addView(textView);
        }
*/

        mPager = (TextView) rootView.findViewById(aartipager);
        header = (TextView) rootView.findViewById(aartiheader);
        header.setText(getArguments().getString("heading"));
        mPager.setText(getArguments().getString(aartifragment.ARG_PLANET_NUMBER));

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

            bdl.putString(ScreenSlidePageFragment.EXTRA_MESSAGE,
                    arrays[position]);

            fragment.setArguments(bdl);

            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}