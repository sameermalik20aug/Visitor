package com.example.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Main2Activity extends AppCompatActivity {

    EditText pNum,e_name,e_add,e_meet,e_pur;
    Button pSend;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    String mVerificationId;
    private int btnType = 0;
    String phoneNumber;
    TextView t_name,t_add,t_phn,t_meet,t_pur;

    //  public User user;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mAuth = FirebaseAuth.getInstance();
        pNum = findViewById(R.id.Phone);
        pSend = findViewById(R.id.Submit);
        e_add = findViewById(R.id.address);
        e_meet = findViewById(R.id.Meet);
        e_name = findViewById(R.id.name);
        e_pur = findViewById(R.id.Purpose);
        t_add = findViewById(R.id.t_add);
        t_add = findViewById(R.id.t_add);
        t_name = findViewById(R.id.t_name);
        t_phn = findViewById(R.id.t_phn);
        t_meet = findViewById(R.id.t_meet);
        t_pur = findViewById(R.id.t_pur);



        pSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnType == 0) {
                    phoneNumber = pNum.getText().toString();

                    if (phoneNumber.length() == 10) {

                        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + phoneNumber,
                                60,
                                TimeUnit.SECONDS,
                                Main2Activity.this,
                                mCallBacks
                        );
                    } else {
                        Toast.makeText(Main2Activity.this, "Please enter a Number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String verificationCode = pNum.getText().toString();

                    if (verificationCode.length() == 6) {
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                        signInWithPhoneAuthCredential(credential);
                    } else {
                        Toast.makeText(Main2Activity.this, "Please enter the verification code", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                Log.d("TAG", "onVerificationCompleted:" + phoneAuthCredential);

                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Toast.makeText(Main2Activity.this, "Invalid mobile number", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("TAG", "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                btnType =1;
                t_add.setVisibility(View.INVISIBLE);
                t_name.setVisibility(View.INVISIBLE);
                t_meet.setVisibility(View.INVISIBLE);
                t_pur.setVisibility(View.INVISIBLE);
                e_add.setVisibility(View.INVISIBLE);
                e_meet.setVisibility(View.INVISIBLE);
                e_name.setVisibility(View.INVISIBLE);
                e_pur.setVisibility(View.INVISIBLE);
                t_phn.setText("OTP");
                pNum.setText("");
                pSend.setText("Verify OTP");
                // ...
            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");

                            Toast.makeText(Main2Activity.this, "Sign in successful", Toast.LENGTH_SHORT).show();

                           // FirebaseUser user = task.getResult().getUser();

                           // user.PhoneNum = phoneNumber;

                            //rootRef.child(mAuth.getCurrentUser().getUid());

                            //Intent newIntent = new Intent(.this,ReferralActivity.class);
                            //newIntent.putExtra("user", user);
                            //startActivity(newIntent);
                            //finish();

                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(Main2Activity.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });



    }

    }
