package com.soigo.faidaloans;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TermsConditionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);
        /*try {
            MobileAds.initialize(this,
                    getResources().getString(R.string.banner_ad_unit_id));

            AdView mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }*/
    }
}
