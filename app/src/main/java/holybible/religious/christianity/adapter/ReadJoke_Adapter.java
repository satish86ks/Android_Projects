package holybible.religious.christianity.adapter;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import androidx.cardview.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import holybible.religious.christianity.R;

/**
 * Created by Amit on 2/25/2018.
 */

public class ReadJoke_Adapter extends PagerAdapter {

    private int textSize;
    Context mContext;
    LayoutInflater mLayoutInflater;
    private List<String> jokes;
    float[] fontSize = {18, 20, 22, 23, 28, 32, 38};

    public ReadJoke_Adapter(Context context, List<String> jokes) {
        mContext = context;
        this.jokes = jokes;
        this.textSize = textSize;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return jokes.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((CardView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.reading_custom_layout, container, false);

        TextView joke_textview = (TextView) itemView.findViewById(R.id.tv);
        joke_textview.setText(Html.fromHtml(jokes.get(position)));
        joke_textview.setTextSize(fontSize[textSize]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((CardView) object);
    }
}