package com.example.smartlabour.DBCode;

import androidx.annotation.NonNull;

import com.example.smartlabour.models.Customer;
import com.example.smartlabour.models.Laboror;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DBHelper {

    static DatabaseReference db;
    static FirebaseUser user;

    public static boolean checkUserState(){
        db = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
            return true;
        return false;
    }

    public static void updateUser(Laboror laboror){
        db = FirebaseDatabase.getInstance().getReference("Labor").child(laboror.getUserName());
        db.setValue(laboror);
    }

    public static void addCustomer(Customer customer, String labor_id){
        db = FirebaseDatabase.getInstance().getReference("Customer").child(customer.getContactDetails().getPhoneNo());
        db.child(labor_id).setValue(customer);
    }

    public static Laboror getLaborWithId(String id){
        final Laboror[] laboror = new Laboror[1];
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Labor").child(id);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                laboror[0] = dataSnapshot.getValue(Laboror.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return laboror[0];
    }

    public static ArrayList<Laboror> getAll(){
        final ArrayList<Laboror> laborors = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Labor");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren())
                    laborors.add(snapshot.getValue(Laboror.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return laborors;
    }
}
