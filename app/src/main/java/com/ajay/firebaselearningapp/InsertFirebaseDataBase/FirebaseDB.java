package com.ajay.firebaselearningapp.InsertFirebaseDataBase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.Map;

public class FirebaseDB {
    public DatabaseReference databaseReference;

    public FirebaseDB() {
        databaseReference = com.google.firebase.database.FirebaseDatabase.getInstance().getReference(InfoClass.class.getSimpleName());
    }

    public Task<Void> add(InfoClass studentInfo) {
        return databaseReference.push().setValue(studentInfo);
    }
    public Query get(){
        return databaseReference;
    }

    public Task<Void> remove(String key){
        return databaseReference.child(key).removeValue();
    }


    public Task<Void> update(String key, Map<String, Object> data){
        return databaseReference.child(key).updateChildren(data);
    }
}