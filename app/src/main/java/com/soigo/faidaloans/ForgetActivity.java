package com.soigo.faidaloans;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgetActivity extends AppCompatActivity {

    EditText et_num,et_pwd;
    Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        et_num=(EditText)findViewById(R.id.et_num);
        et_pwd=(EditText)findViewById(R.id.et_pwd);
        btn1=(Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(!TextUtils.isEmpty(et_num.getText().toString().trim()) &&
                            !TextUtils.isEmpty(et_pwd.getText().toString().trim())){
                        DatabaseReference reff=FirebaseDatabase.getInstance().getReference().child("ModelPerson")
                                .child(et_num.getText().toString().trim());

                            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()) {
                                        String name = dataSnapshot.child("pname").getValue().toString();
                                        String pwd = dataSnapshot.child("pwd").getValue().toString();
                                        String email = dataSnapshot.child("email").getValue().toString();
                                        String loan = dataSnapshot.child("loan").getValue().toString();
                                        String location = dataSnapshot.child("location").getValue().toString();

                                        ModelPerson person = new ModelPerson();
                                        person.setLocation(location);
                                        person.setLoan(loan);
                                        person.setPname(name);
                                        person.setPwd(et_pwd.getText().toString().trim());
                                        person.setEmail(email);
                                        person.setPhon(et_num.getText().toString().trim());

                                        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("ModelPerson").child(et_num.getText().toString().trim());
                                        reff.setValue(person);


                                        Toast.makeText(getApplicationContext(), "Password is changed", Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Not registered Number", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                    }
                }catch (Exception ex){

                }
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
}
