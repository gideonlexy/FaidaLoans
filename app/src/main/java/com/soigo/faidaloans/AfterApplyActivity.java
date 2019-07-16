package com.soigo.faidaloans;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AfterApplyActivity extends AppCompatActivity {

    Button btn_rate,btn_share;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_apply);

        MyPrefs prefs=new MyPrefs(getApplicationContext());

        final TextView et_loan=(TextView) findViewById(R.id.et_loan);

        reff=FirebaseDatabase.getInstance().getReference().child("ModelPerson")
                .child(prefs.get_Val("phone"));
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String loan=dataSnapshot.child("loan").getValue().toString();
                et_loan.setText("Your Loan Application of Ksh "+loan+" has been received And is under review. Once your Application is approved, your account will be activated.You will receive your loan via M-pesa.This may take 1-7 days For the loans To be awarded.Rate our App To increase your chances of being awarded.Thank you.");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        TextView tv_user=(TextView) findViewById(R.id.tv_name);
        tv_user.setText("Dear "+prefs.get_Val("name"));
        btn_rate=(Button)findViewById(R.id.btn_rate);
        btn_share=(Button)findViewById(R.id.btn_share);
        btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=PackageName")));
            }
        });
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp(getApplicationContext());
            }
        });
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
    public static void shareApp(Context context)
    {
        final String appPackageName = context.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Check out the App at: https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }
}
