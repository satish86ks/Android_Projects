package holybible.religious.christianity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import holybible.religious.christianity.R;

public class PrayerAdapter extends BaseAdapter {
    private Context mContext;

    ArrayList<HashMap<String, String>> list;

    public PrayerAdapter(Context c, ArrayList<HashMap<String, String>> list) {
        mContext = c;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public static class Holder {
        public ImageView img;
        public TextView title;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = null;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.prayer_items, null);
            holder = new Holder();
            holder.title = (TextView) convertView.findViewById(R.id.chapter_name);
            holder.title.setText(list.get(position).get("heading"));
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        /*int resourceId = mContext.getResources().getIdentifier(list.get(position).get("heading").replace(" ", "_").toLowerCase(), "drawable", mContext.getPackageName());
        holder.img.setImageResource(resourceId);*/

        return convertView;
    }
}