package com.soigo.faidaloans;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoanStatusActivity extends AppCompatActivity {

    Button btn1;
    TextView et_loan;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_status);
        btn1=(Button)findViewById(R.id.btn_reapply);
        MyPrefs prefs=new MyPrefs(getApplicationContext());
        TextView tv_user=(TextView) findViewById(R.id.tv_name);
        et_loan=(TextView) findViewById(R.id.et_loan);
        MyPrefs preff=new MyPrefs(getApplicationContext());


        reff=FirebaseDatabase.getInstance().getReference().child("ModelPerson")
                .child(preff.get_Val("phone"));
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String loan=dataSnapshot.child("loan").getValue().toString();
                et_loan.setText("Your loan applicaiton of Ksh "+loan+" is being processed,");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        tv_user.setText("Dear "+prefs.get_Val("name"));
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Reapplied for Loan successfully",Toast.LENGTH_LONG).show();
            }
        });
        try {
            MobileAds.initialize(LoanStatusActivity.this,getResources().getString(R.string.app_id));
            AdView mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder() .build();
            mAdView.loadAd(adRequest);
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
