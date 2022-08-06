package holybible.religious.christianity.BabyNames;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import holybible.religious.christianity.R;

public class CardAdapter extends BaseAdapter {

    Context context;
    private static LayoutInflater inflater = null;
    int gender;
    List<NameType> list;
    String type;

    public CardAdapter(NamesDashboard mainActivity, List<NameType> list, int gen, String type) {


        // TODO Auto-generated constructor stub
        this.list = list;
        this.context = mainActivity;
        this.gender = gen;
        this.type = type;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView tv, tv2, feast_tv, feast, saint;
        ImageView img;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        Holder holder = new Holder();

        rowView = inflater.inflate(R.layout.activity_card_adapter, null);
        holder.tv = rowView.findViewById(R.id.textView);
        holder.tv2 = rowView.findViewById(R.id.textView2);
        holder.feast_tv = rowView.findViewById(R.id.feast_tv);
        holder.img = rowView.findViewById(R.id.imageView);
        holder.feast = rowView.findViewById(R.id.feast);
        holder.saint = rowView.findViewById(R.id.saint);


        if (gender == 0) {
            holder.img.setImageResource(R.drawable.boys);
        } else if (gender == 1) {
            holder.img.setImageResource(R.drawable.girls);
        }

        if (!type.equals("saint")) {

            holder.feast_tv.setVisibility(View.GONE);
            holder.feast.setVisibility(View.GONE);
            holder.saint.setText("Meaning : ");
        }

        holder.tv.setText(list.get(position).getName());
        holder.tv2.setText(list.get(position).getMeaning());
        holder.feast_tv.setText(list.get(position).getFeast());


        return rowView;
    }
}