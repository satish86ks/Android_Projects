package holybible.religious.christianity.BabyNames;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import holybible.religious.christianity.R;

public class NamesDashboard extends AppCompatActivity {
    TextView textView, textView2;
    ImageView imageView;
    String bbnames[];
    String bgnames[];
    String sbnames[];
    String sgnames[];
    ListView listView;
    int gender;

    List<NameType> dataList;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Baby Names");

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        listView = findViewById(R.id.listview);


        bbnames = getResources().getStringArray(R.array.bbnames);
        bgnames = getResources().getStringArray(R.array.bgnames);
        sbnames = getResources().getStringArray(R.array.sbnames);
        sgnames = getResources().getStringArray(R.array.sgnames);

        Integer integer = getIntent().getIntExtra("1", 0);
        String type = getIntent().getStringExtra("type");
        dataList = new ArrayList<>();

        if (integer == 1) {
            for (int i = 0; i < bbnames.length; i++) {
                gender = 0;
                String[] name = (bbnames[i].split("\\|"));
                dataList.add(new NameType(name[0], name[1], name[1]));
            }

        } else if (integer == 2) {
            for (int i = 0; i < bgnames.length; i++) {
                gender = 1;
                String[] name = (bgnames[i].split("\\|"));
                dataList.add(new NameType(name[0], name[1], name[1]));
            }
        } else if (integer == 3) {
            for (int i = 0; i < sbnames.length; i++) {
                gender = 0;
                String[] name = (sbnames[i].split("\\|"));
                String data = name[1] + name[2];
                dataList.add(new NameType(name[0], name[1], name[2]));
            }
        } else if (integer == 4) {
            for (int i = 0; i < sgnames.length; i++) {
                gender = 1;
                String[] name = (sgnames[i].split("\\|"));
                String data = name[1] + name[2];
                dataList.add(new NameType(name[0], name[1], name[2]));
            }
        }

        listView.setAdapter(new CardAdapter(this, dataList, gender, type));


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}