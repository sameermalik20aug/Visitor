package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Calendar;

public class Main3Activity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference rootReference, childReference;
    FirebaseDatabase firebaseDatabase;

    EditText pnum, tomeet, purpose;
    Button send;
    String PhoneNum;
    String ls;
    ArrayList<Visit> vis;
    boolean fghh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        vis = new ArrayList<>();
        ls = "";

        firebaseDatabase = FirebaseDatabase.getInstance();
        rootReference = firebaseDatabase.getReference();

        pnum = findViewById(R.id.Phone);
        tomeet = findViewById(R.id.Meet);
        purpose = findViewById(R.id.Purpose);
        send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pnum.getText().toString().length() == 0){
                    Toast.makeText(Main3Activity.this, "Please enter phone number", Toast.LENGTH_SHORT).show();
                }else{
                    Log.e("er","here1");
                    PhoneNum = pnum.getText().toString();
                    Visit visit = new Visit(tomeet.getText().toString(), purpose.getText().toString(), Calendar.getInstance().getTime().toString());
                    Query query = rootReference.orderByChild("phone").equalTo(PhoneNum);
                    query.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            Log.e("asgdlkmsl",dataSnapshot.getKey());
                            ls = dataSnapshot.getKey();
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    DatabaseReference xcv = rootReference.child(ls);
//                    Log.e("gdh",query.getPath().toString());
                    DatabaseReference ch = xcv.child("user");
                    DatabaseReference ch1 = ch.child("visits");
////                    Log.e("er", ch.toString());
//                    DatabaseReference xcv = ch.child("user/visits");
//                    Log.e("er", ch.toString());
//                    ch.orderByKey().limitToLast(1).addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                            ls = dataSnapshot.getKey();
//                            Log.e("jjk",ls);
//                        }
//
//                        @Override
//                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                        }
//
//                        @Override
//                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                        }
//
//                        @Override
//                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                    int x = Integer.parseInt(ls)+1;
//                    ch = ch.child(""+x);
                    if(fghh){
                        Toast.makeText(Main3Activity.this, "Try Again", Toast.LENGTH_SHORT).show();
                        fghh = false;
                    }else{
                        ch1.push().setValue(visit);
                        Toast.makeText(Main3Activity.this, "Check In Successful", Toast.LENGTH_SHORT).show();
                        fghh = true;
                    }
                }
            }
        });
    }
}
