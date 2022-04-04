package com.ajay.firebaselearningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ajay.firebaselearningapp.FetchFireBaseDatabase.DisplayActivity;
import com.ajay.firebaselearningapp.InsertFirebaseDataBase.FirebaseDB;
import com.ajay.firebaselearningapp.InsertFirebaseDataBase.InfoClass;
import com.ajay.firebaselearningapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityMainBinding binding;
    private FirebaseDB firebaseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        firebaseDB=new FirebaseDB();

           binding.btnInsert.setOnClickListener(this);
           binding.btnFetchData.setOnClickListener(this);



            }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_insert:

                InfoClass infoClass=new InfoClass();
                infoClass.setFirstName(binding.etFirstname.getText().toString());
                infoClass.setLastName(binding.etLastname.getText().toString());
                infoClass.setMobileNo(Long.parseLong(binding.etMobileNo.getText().toString()));


                firebaseDB.add(infoClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(MainActivity.this,"successfully inserted...",Toast.LENGTH_SHORT).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"it has failed",Toast.LENGTH_SHORT).show();


                    }
                });

                break;

            case R.id.btn_fetch_data:
                startActivity(new Intent(MainActivity.this, DisplayActivity.class));
                break;

                }
    }
}