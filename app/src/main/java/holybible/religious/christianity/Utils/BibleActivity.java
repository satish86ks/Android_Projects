package holybible.religious.christianity.Utils;

import android.app.Application;

import com.facebook.ads.AudienceNetworkAds;

public class BibleActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AudienceNetworkAds.initialize(this);
    }
}
