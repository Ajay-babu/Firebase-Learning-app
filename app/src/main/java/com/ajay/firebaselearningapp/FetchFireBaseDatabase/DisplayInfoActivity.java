package com.ajay.firebaselearningapp.FetchFireBaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ajay.firebaselearningapp.InsertFirebaseDataBase.FirebaseDB;
import com.ajay.firebaselearningapp.InsertFirebaseDataBase.InfoClass;
import com.ajay.firebaselearningapp.R;
import com.ajay.firebaselearningapp.Utility.Utility;
import com.ajay.firebaselearningapp.databinding.ActivityDisplayInfoBinding;
import com.ajay.firebaselearningapp.databinding.LayoutUpdateInfoBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


public class DisplayInfoActivity extends AppCompatActivity {
    private ActivityDisplayInfoBinding binding;
    private FirebaseDB firebaseDb;
    private InfoClass studentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseDb  = new FirebaseDB();
        String studentString = getIntent().getStringExtra("student_info");
        studentInfo = new Gson().fromJson(studentString, InfoClass.class);

        binding.tvResult.setText(studentInfo.getFirstName()+" "+studentInfo.getLastName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_context_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case  R.id.item_update_record:
                // TODO : Update
                LayoutUpdateInfoBinding binding1 =
                        LayoutUpdateInfoBinding.inflate(getLayoutInflater());
                Dialog dialog = new Dialog(DisplayInfoActivity.this);
                dialog.setContentView(binding1.getRoot());
                binding1.etFname.setText(studentInfo.getFirstName());
                binding1.etLname.setText(studentInfo.getLastName());
                binding1.etMobileno.setText(String.valueOf(studentInfo.getMobileNo()));
                binding1.btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> hashMap = new HashMap<>();
                        hashMap.put("firstName", binding1.etFname.getText().toString());
                        hashMap.put("lastName", binding1.etLname.getText().toString());
                        hashMap.put("mobileNo", Long.parseLong(binding1.etMobileno.getText().toString()));

                        firebaseDb.update(studentInfo.getKey(), hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Utility.showLongToast(DisplayInfoActivity.this, "Updated....");
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Utility.showLongToast(DisplayInfoActivity.this, e.getMessage());
                            }
                        });
                    }
                });

                dialog.show();

                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                break;
            case R.id.item_delete_record:
                //TODO : Delete
                firebaseDb.remove(studentInfo.getKey()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Utility.showLongToast(DisplayInfoActivity.this, e.getMessage());
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}