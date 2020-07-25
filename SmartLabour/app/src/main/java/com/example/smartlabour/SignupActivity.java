package com.example.smartlabour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartlabour.models.Laboror;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    EditText mail, userName, password, phone;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mail = findViewById(R.id.mail);
        userName = findViewById(R.id.usrusr);
        password = findViewById(R.id.pswrdd);
        phone = findViewById(R.id.mobphone);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.sup:
                if (fieldValidation()) {
                    createUser();
                }
                break;
            case R.id.lin:
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                break;
        }
    }

    private boolean fieldValidation() {
        if (TextUtils.isEmpty(mail.getText().toString().trim())) {
            mail.setError("Email is required!");
            return false;
        }
        if (TextUtils.isEmpty(userName.getText().toString().trim())) {
            userName.setError("UserName not set!");
            return false;
        }
        if (TextUtils.isEmpty(password.getText().toString().trim())) {
            password.setError("Password is required!");
            return false;
        }
        if (password.length() < 6) {
            password.setError("Password should have minimum of 6 characters!");
            return false;
        }
        return true;
    }

    private void createUser() {
        DatabaseReference reference = database.getReference();
        reference.child("Labor").child(userName.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userName.setError("UserName already taken! choose another.");
                } else {
                    final String email = mail.getText().toString().trim();
                    final String paswrd = password.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(email, paswrd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                try {
                                    DatabaseReference reference = database.getReference();
                                    Laboror laboror = new Laboror();
                                    laboror.setUserName(userName.getText().toString());
                                    laboror.getContactDetails().setEmail(email);
                                    laboror.setPassword(paswrd);
                                    laboror.getContactDetails().setPhoneNo(phone.getText().toString());
                                    laboror.getExperience().setType_work("not-set");
                                    //reference.child("Laboror").setValue(laboror);
                                    reference.child("Labor").child(laboror.getUserName()).setValue(laboror);

                                    Toast.makeText(SignupActivity.this, "SignUp Succeed", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignupActivity.this, ProfileActivity.class));
                                    finish();
                                } catch (Exception e) {
                                    Log.d("error1", e.toString());
                                    Toast.makeText(SignupActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                    mail.setError("This email address is already used!");
                                Toast.makeText(SignupActivity.this, "failed to signup!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
