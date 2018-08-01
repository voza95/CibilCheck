package com.example.cibilcheck;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.cibilcheck.adapter.AllAppsAdapter;
import com.example.cibilcheck.dto.AppCard;
import com.example.cibilcheck.fragment.AppsListFragment;
import com.example.cibilcheck.util.SheetConstant;

import java.util.ArrayList;

public class BackForAllAppsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AllAppsAdapter adapter;
    private ArrayList<AppCard> appCards;
    private boolean doubleBackToExitPressedOnce = false;
    private boolean isNoButtonClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_for_all_apps);

    /*    AdView mAdView = (AdView) findViewById(R.id.adView);
        //code for ads
        if (new ConnectionDetector(this).isConnectingToInternet())
        {            mAdView.setVisibility(View.VISIBLE);
            AdRequest adBRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adBRequest);
        }else
        {
            mAdView.setVisibility(View.GONE);
        }*/
        //setup exit (yes)
        findViewById(R.id.exitBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //setup exit (rate us)
        findViewById(R.id.rateUsBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("market://details?id=" + getPackageName()
                );
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + getPackageName())));
                }
            }
        });

        //setup exit (no)
        findViewById(R.id.noBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update click flag
                isNoButtonClick = true;
                Intent intent = new Intent(BackForAllAppsActivity.this, MoreAppsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        SheetConstant.loadFragment(AppsListFragment.newInstance(""), getSupportFragmentManager(), R.id.fragmentContainer);

    }




    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        if (isNoButtonClick) {
            super.onDestroy();
        } else {
            super.onDestroy();
            System.exit(0);
        }
    }
}
