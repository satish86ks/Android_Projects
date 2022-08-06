package holybible.religious.christianity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends FragmentActivity {

    String DB_NAME = "christ.db";

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);


        NewAssetDatabaseOpenHelper dbHelper = new NewAssetDatabaseOpenHelper(this);
    }
}
