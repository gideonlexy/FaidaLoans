package com.soigo.faidaloans;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {


    Button btn_login, btn_forget_pwd;
    String number, pwd;
    EditText et_number, et_pwd;

    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        et_number = (EditText) findViewById(R.id.et_number);
        et_pwd = (EditText) findViewById(R.id.et_pwd);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_forget_pwd = (Button) findViewById(R.id.btn_forgetpwd);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                number = et_number.getText().toString();
                pwd = et_pwd.getText().toString();
                if (!number.isEmpty() &&
                        !pwd.isEmpty()) {
                    if(et_number.getText().toString().length()==10) {
                    new LoadPerson().execute();
                }else {
                    Toast.makeText(getApplicationContext(),"Invalid phone number",
                            Toast.LENGTH_LONG).show();
                    et_number.setError("Invalid Phone number");
                }





                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all fields",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_forget_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ForgetActivity.class);
                startActivity(i);
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



    class LoadPerson extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(SignInActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Signing In");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                reff=FirebaseDatabase.getInstance().getReference().child("ModelPerson")
                        .child(et_number.getText().toString().trim());

                reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String name=dataSnapshot.child("pname").getValue().toString();
                                    String pwd=dataSnapshot.child("pwd").getValue().toString();
                                    String email=dataSnapshot.child("email").getValue().toString();
                                    String loan=dataSnapshot.child("loan").getValue().toString();
                                    String location=dataSnapshot.child("location").getValue().toString();

                                    MyPrefs prefs=new MyPrefs(getApplicationContext());
                                    prefs.put_Val("name",name);
                                    prefs.put_Val("email",email);
                                    prefs.put_Val("location",location);
                                    prefs.put_Val("loan",loan);
                                    prefs.put_Val("pwd",pwd);
                                    prefs.put_Val("phone",et_number.getText().toString().trim());

                                    if(et_pwd.getText().toString().equals(pwd)){
                                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(i);
                                    }else {
                                        Toast.makeText(getApplicationContext(),"Password not correct",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }else {
                            Toast.makeText(getApplicationContext(),"Not Registered Number or wrong password",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

              /*  if(!reff.equals(null)){



                }else {
                    Toast.makeText(getApplicationContext(),"Wrong number or Password",Toast.LENGTH_SHORT).show();
                }
*/
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            progressDialog.hide();
        }

    }
}