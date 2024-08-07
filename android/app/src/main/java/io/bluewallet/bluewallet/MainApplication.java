package io.bluewallet.bluewallet;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.bugsnag.android.Bugsnag;
import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint;
import com.facebook.react.defaults.DefaultReactNativeHost;
import com.facebook.soloader.SoLoader;
import java.lang.reflect.InvocationTargetException;
import com.facebook.react.modules.i18nmanager.I18nUtil;
import java.util.List;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import java.util.concurrent.TimeUnit;

public class MainApplication extends Application implements ReactApplication {

    private final ReactNativeHost mReactNativeHost =
        new DefaultReactNativeHost(this) {
            @Override
            public boolean getUseDeveloperSupport() {
                return BuildConfig.DEBUG;
            }

            @Override
            protected List<ReactPackage> getPackages() {
                @SuppressWarnings("UnnecessaryLocalVariable")
                List<ReactPackage> packages = new PackageList(this).getPackages();
                return packages;
            }

            @Override
            protected String getJSMainModuleName() {
                return "index";
            }

            @Override
            protected boolean isNewArchEnabled() {
                return BuildConfig.IS_NEW_ARCHITECTURE_ENABLED;
            }

            @Override
            protected Boolean isHermesEnabled() {
                return BuildConfig.IS_HERMES_ENABLED;
            }
        };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        I18nUtil sharedI18nUtilInstance = I18nUtil.getInstance();
        sharedI18nUtilInstance.allowRTL(getApplicationContext(), true);
        SoLoader.init(this, /* native exopackage */ false);

        if (BuildConfig.IS_NEW_ARCHITECTURE_ENABLED) {
            DefaultNewArchitectureEntryPoint.load();
        }

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("group.io.bluewallet.bluewallet", Context.MODE_PRIVATE);
        String isDoNotTrackEnabled = sharedPref.getString("donottrack", "0");

        if (!isDoNotTrackEnabled.equals("1")) {
            Bugsnag.start(this);
        }


    // Schedule periodic widget updates
    PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(WidgetUpdateWorker.class, 4, TimeUnit.MINUTES).build();
    WorkManager.getInstance(this).enqueueUniquePeriodicWork("UpdateWidgetWork", ExistingPeriodicWorkPolicy.REPLACE, workRequest);

    }
}
