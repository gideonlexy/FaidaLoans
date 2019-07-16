package com.soigo.faidaloans;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText et_name,et_email,et_mobile,et_location,et_password,et_con_password;
    CheckBox chk;
    Button btn_signIn,btn_signUp;

    String Name,email,mobile,location,pwd,c_pwd;
    private AdView mAdView;

    DatabaseReference reff;

    ModelPerson person;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btn_signIn=(Button)findViewById(R.id.btn_signin);
        btn_signUp=(Button)findViewById(R.id.btn_signup);

        et_name=(EditText)findViewById(R.id.et_name);
        et_mobile=(EditText)findViewById(R.id.et_phone);
        et_email=(EditText)findViewById(R.id.et_email);
        et_location=(EditText)findViewById(R.id.et_loc);
        et_password=(EditText)findViewById(R.id.et_pwd);
        et_con_password=(EditText)findViewById(R.id.et_con_pwd);


        chk=(CheckBox) findViewById(R.id.chk);
        try {
            //test
           // MobileAds.initialize(SignUpActivity.this,"ca-app-pub-3940256099942544~3347511713");

            //original
            MobileAds.initialize(SignUpActivity.this,getResources().getString(R.string.app_id));

            mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder() .build();
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

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et_name.getText().toString().isEmpty() &&
                        !et_email.getText().toString().isEmpty() &&
                        !et_mobile.getText().toString().isEmpty() &&
                        !et_location.getText().toString().isEmpty() &&
                        !et_password.getText().toString().isEmpty() &&
                        !et_con_password.getText().toString().isEmpty()){
                    if(chk.isChecked()){

                        if(et_password.getText().toString().equals(et_con_password.getText().toString())) {

                            if(et_mobile.getText().toString().length()==10) {
                                SignUp(et_email.getText().toString().trim(), et_password.getText().toString().trim(),
                                        et_name.getText().toString().trim(), et_mobile.getText().toString().trim(),
                                        et_location.getText().toString().trim());
                                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
                           /* Intent i=new Intent(getApplicationContext(),SignInActivity.class);
                            startActivity(i);*/
                            }else {
                                Toast.makeText(getApplicationContext(),"Invalid phone number",
                                        Toast.LENGTH_LONG).show();
                                et_mobile.setError("Invalid Phone number");
                            }
                        }else {
                            Toast.makeText(getApplicationContext(),"Please Enter same password",
                                    Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"Please read the terms and conditons First",
                                Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Please Fill all fields",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(i);
            }
        });
    }

    private void SignUp(String email,String password, String name,String mobile,String location){
        try {
            person=new ModelPerson();
            person.setEmail(email);
            person.setPwd(password);
            person.setPname(name);
            person.setPhon(mobile);
            person.setLocation(location);
            person.setLoan("");
            reff=FirebaseDatabase.getInstance().getReference().child("ModelPerson").child(mobile);
            reff.setValue(person);
            Toast.makeText(getApplicationContext(),"Registered",Toast.LENGTH_LONG).show();
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
    }
}
