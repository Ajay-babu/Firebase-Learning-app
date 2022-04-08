package com.ajay.firebaselearningapp.FetchFireBaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.ajay.firebaselearningapp.InsertFirebaseDataBase.FirebaseDB;
import com.ajay.firebaselearningapp.InsertFirebaseDataBase.InfoClass;
import com.ajay.firebaselearningapp.R;
import com.ajay.firebaselearningapp.Utility.Utility;
import com.ajay.firebaselearningapp.databinding.ActivityDisplayBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {
    private ActivityDisplayBinding binding;
    private FirebaseDB firebaseDb;
    private List<InfoClass> studentInfoList = new ArrayList<>();
    DisplayAdapter displayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseDb = new FirebaseDB();
        displayAdapter = new DisplayAdapter(this, new OnItemClickListenerFirebase() {
            @Override
            public void onItemClick(int position, List<InfoClass> studentInfo) {
//                Toast.makeText(DisplayActivity.this, studentInfo.get(position).getFirstName(), Toast.LENGTH_SHORT).show();
                Utility.showLongToast(DisplayActivity.this, studentInfo.get(position).getFirstName());
                Intent intent = new Intent(DisplayActivity.this, DisplayInfoActivity.class);
                intent.putExtra("student_info", new Gson().toJson(studentInfo.get(position)));
                startActivity(intent);
            }
        });

        binding.revView.setLayoutManager(new LinearLayoutManager(this));
        binding.revView.setAdapter(displayAdapter);

        addUpdatedData();


    }
    public void addUpdatedData() {
        firebaseDb.get().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    InfoClass studentInfo = dataSnapshot.getValue(InfoClass.class);
                    //TODO: Here setKey is the setter method and getKey is the method of firebase to get Key
                    studentInfo.setKey(dataSnapshot.getKey());
                    studentInfoList.add(studentInfo);
                    displayAdapter.setStudentInfoList(studentInfoList);
                    displayAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (studentInfoList.size() > 0) studentInfoList.clear();
        addUpdatedData();
    }
}