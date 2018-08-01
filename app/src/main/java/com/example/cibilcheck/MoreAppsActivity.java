package com.example.cibilcheck;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdView;
import com.example.cibilcheck.dto.AppCard;
import com.example.cibilcheck.fragment.AppsListFragment;
import com.example.cibilcheck.util.SheetConstant;


import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;


public class MoreAppsActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST = 102;
    private ArrayList<AppCard> appCards;
    private RelativeLayout relativeLayout;
    /* private AdView mAdView;
     AdRequest adRequest;*/
    private NativeAd nativeAd;


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_apps);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        // relativeLayout.setVisibility(v);
        //storageTask();

      /*  mAdView = (AdView) findViewById(R.id.adView);
        if (new ConnectionDetector(this).isConnectingToInternet())
        {            mAdView.setVisibility(View.VISIBLE);
            adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }else
        {
            mAdView.setVisibility(View.GONE);
        }*/
        // codes for native ad
       /* NativeExpressAdView nativeExpressAdView = (NativeExpressAdView) findViewById(R.id.adViewExpress);
        AdRequest adBRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("0D18E7ADF186A5703273874B522EF74B")
                .build();
        nativeExpressAdView.loadAd(adBRequest);*/

        //prepare data for list
        //prepareDataSource();
        nativeAd = new NativeAd(this, getResources().getString(R.string.moreapp_banner_ad_unit_id));
        nativeAd.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                View adView = NativeAdView.render(MoreAppsActivity.this, nativeAd, NativeAdView.Type.HEIGHT_300);
                LinearLayout nativeAdContainer = (LinearLayout) findViewById(R.id.native_ad_container);
                // Add the Native Ad View to your ad container
                nativeAdContainer.addView(adView);

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });

        // Initiate a request to load an ad.
        nativeAd.loadAd();
        //casting View
        Button startBTN = (Button) findViewById(R.id.startBTN);
        Button moreAppsBTN = (Button) findViewById(R.id.moreAppsBTN);

        startBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start = new Intent(MoreAppsActivity.this, MainActivity.class);
                startActivity(start);
                finish();


            }
        });

        moreAppsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(Intent.ACTION_VIEW);
                _intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Wondrous+Mobile+Apps"));
                startActivity(_intent);
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();

        Intent intent = new Intent(MoreAppsActivity.this, BackForAllAppsActivity.class);
        finish();
        startActivity(intent);

    }

    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        } else {
            // Toast.makeText(MainActivity.this, "permission Allowed", Toast.LENGTH_SHORT).show();
            return true;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        SheetConstant.loadFragment(AppsListFragment.newInstance(""), getSupportFragmentManager(), R.id.fragmentContainer);

    }

    public boolean loadPrefs() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MoreAppsActivity.this);
        //  Log.d("Prefrence", " load pref REQUEST" + cbValue);
        return sp.getBoolean("REQUEST", false);
    }


    //to request permission
    public void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MoreAppsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            ActivityCompat.requestPermissions(MoreAppsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);

            //  Toast.makeText(getApplicationContext(), "permission allows us to access storage without permission we won't be able to download video", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MoreAppsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }
}

