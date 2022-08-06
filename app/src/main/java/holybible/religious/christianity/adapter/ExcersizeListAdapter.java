package holybible.religious.christianity.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import holybible.religious.christianity.R;

/**
 * Created by HP349340 on 11/28/2018.
 */

public class ExcersizeListAdapter extends RecyclerView.Adapter<ExcersizeListAdapter.myViewHolder> {

    ArrayList<HashMap<String, String>> excersize_names;
    Context context;
    String type;
    //  int[] images = {R.drawable.cover_fullbody, R.drawable.cover_lower_body, R.drawable.cover_chest_3, R.drawable.cover_shoulder_3, R.drawable.cover_arm_3, R.drawable.cover_abs3, R.drawable.cover_leg_3};


    public ExcersizeListAdapter(Context context, ArrayList<HashMap<String, String>> data, String type) {

        this.excersize_names = data;
        this.context = context;
        this.type = type;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_view, parent, false);
        return new myViewHolder(v);

    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        if (type.equals("Prayers")) {
            holder.ex_name.setText(excersize_names.get(position).get("title"));
        } else {
            holder.ex_name.setText(excersize_names.get(position).get("heading"));
        }

    }

    @Override
    public int getItemCount() {

        return excersize_names.size();

    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        TextView ex_name;
        ImageView icon;

        public myViewHolder(View itemView) {
            super(itemView);

            ex_name = (TextView) itemView.findViewById(R.id.exercize_name);
            icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }
}