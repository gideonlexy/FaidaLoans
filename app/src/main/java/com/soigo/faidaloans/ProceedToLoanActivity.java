package com.soigo.faidaloans;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProceedToLoanActivity extends AppCompatActivity {

    Button btn1;
    EditText et_number, et_loan;
    MyPrefs prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed_to_loan);
        btn1=(Button)findViewById(R.id.btn_req_loan);
        et_number=(EditText)findViewById(R.id.et_number);
        et_loan=(EditText)findViewById(R.id.et_loan);

        et_number.setEnabled(false);
        prefs= new MyPrefs(getApplicationContext());
        et_number.setText(prefs.get_Val("phone"));

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!et_number.getText().toString().trim().equals("") &&
                            !et_loan.getText().toString().trim().equals("")) {
                        if (Integer.parseInt(et_loan.getText().toString().trim()) < 2500) {

                            ModelPerson person = new ModelPerson();
                            person.setLocation(prefs.get_Val("location"));
                            person.setLoan(et_loan.getText().toString());
                            person.setPname(prefs.get_Val("name"));
                            person.setPwd(prefs.get_Val("pwd"));
                            person.setEmail(prefs.get_Val("email"));
                            person.setPhon(prefs.get_Val("phone"));

                            DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("ModelPerson").child(prefs.get_Val("phone"));
                            reff.setValue(person);

                            Intent i = new Intent(ProceedToLoanActivity.this, AfterApplyActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Loan Limit is Ksh 2500", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter Loan", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception ex){

                }
            }
        });
        try {
            MobileAds.initialize(this,
                    getResources().getString(R.string.banner_ad_unit_id));

            AdView mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

            final InterstitialAd mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
            AdRequest adRequestInter = new AdRequest.Builder().build();
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    mInterstitialAd.show();
                }
            });
            mInterstitialAd.loadAd(adRequestInter);

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
