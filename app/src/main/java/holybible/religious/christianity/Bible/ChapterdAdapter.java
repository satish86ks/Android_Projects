package holybible.religious.christianity.Bible;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import holybible.religious.christianity.R;

public class ChapterdAdapter extends RecyclerView.Adapter<ChapterdAdapter.myViewHolder> {

    List<TitleModel> chapter_names;
    Context context;

    public ChapterdAdapter(Context context, List<TitleModel> druid_names) {

        this.chapter_names = druid_names;
        this.context = context;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_bible_titles, parent, false);
        return new myViewHolder(v);

    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        holder.chapter_name.setText(chapter_names.get(position).getName());
        holder.total_pages.setText(String.valueOf(chapter_names.get(position).getChapters()));
    }

    @Override
    public int getItemCount() {

        return chapter_names.size();

    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView total_pages, chapter_name;
        LinearLayout container;

        public myViewHolder(View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container);
            chapter_name = (TextView) itemView.findViewById(R.id.chapter_name);
            total_pages = (TextView) itemView.findViewById(R.id.total_pages);

            /*container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, BibleChaptersDescription.class);
                    context.startActivity(intent);
                }
            });*/
        }
    }
}