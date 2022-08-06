package holybible.religious.christianity.BabyNames;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;

import java.util.ArrayList;
import java.util.List;

import holybible.religious.christianity.R;

public class FrontActivity extends AppCompatActivity {
    ImageView imageView, imageView2, imageView3, imageView4;
    CardView cardView, cardView2, cardView3, cardView4;

    private InterstitialAd interstitialAd;

    private final String TAG = FrontActivity.class.getSimpleName();
    private NativeAdLayout nativeAdLayout;
    private LinearLayout adView;
    private NativeAd nativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Baby Names");

        imageView = findViewById(R.id.imageView2);
        imageView2 = findViewById(R.id.imageView3);
        imageView3 = findViewById(R.id.imageView4);
        imageView4 = findViewById(R.id.imageView1);
        cardView = findViewById(R.id.card);
        cardView2 = findViewById(R.id.card2);
        cardView3 = findViewById(R.id.card3);
        cardView4 = findViewById(R.id.card4);

        /*imageView.setImageResource(R.drawable.boys);
        imageView2.setImageResource(R.drawable.girls);
        imageView3.setImageResource(R.drawable.boys);
        imageView4.setImageResource(R.drawable.girls);*/

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontActivity.this, NamesDashboard.class);
                intent.putExtra("1", 1);
                intent.putExtra("type", "");
                startActivity(intent);

            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontActivity.this, NamesDashboard.class);
                intent.putExtra("1", 2);
                intent.putExtra("type", "");
                startActivity(intent);

            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontActivity.this, NamesDashboard.class);
                intent.putExtra("1", 3);
                intent.putExtra("type", "saint");
                startActivity(intent);

            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontActivity.this, NamesDashboard.class);
                intent.putExtra("1", 4);
                intent.putExtra("type", "saint");
                startActivity(intent);

            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
