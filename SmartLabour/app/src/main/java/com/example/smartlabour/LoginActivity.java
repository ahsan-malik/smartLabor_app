package com.example.smartlabour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.smartlabour.Others.Helper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText userNameText, passwordText;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Helper.addToolbar(this, "Log In");

        userNameText = findViewById(R.id.userName);
        passwordText = findViewById(R.id.password);
        progressBar = findViewById(R.id.loginProgress);

        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            try {
                finish();
                startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
            }
            catch (Exception e){
                Helper.toast(this, e.toString());
                Log.d("error1", e.toString());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.find || id == R.id.find1) {
            //Helper.toast(this, "clicked");
            startActivity(new Intent(this, FindlaborActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addToolbar(){
        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Log In");
        }catch (Exception e){
            Log.d("tool", e.toString());
            Helper.toast(this, e.toString());
        }
    }

    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.sup:
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                break;
            case R.id.lin:
                if (fieldValidation())
                    loginUser();
                break;
        }
    }

    private boolean fieldValidation() {
        if (TextUtils.isEmpty(userNameText.getText().toString().trim())) {
            userNameText.setError("UserName is required!");
            return false;
        }
        if (TextUtils.isEmpty(passwordText.getText().toString().trim())) {
            passwordText.setError("Password is required!");
            return false;
        }
        progressBar.setVisibility(View.VISIBLE);
        return true;
    }

    private void loginUser() {
        String userName = userNameText.getText().toString().trim();
        final String password = passwordText.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        //check if user name exit or not
        reference.child("Labor").child(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        //check if given password matches with saved password or not
                        String userPassword = dataSnapshot.child("password").getValue(String.class);
                        String userEmail = dataSnapshot.child("contactDetails/email").getValue(String.class);
                        if (password.equals(userPassword)) {
                            Toast.makeText(LoginActivity.this, "matched", Toast.LENGTH_SHORT).show();
                            signIn(userEmail, password);
                             } else
                            passwordText.setError("Incorrect Password!");
                    } catch (Exception e) {
                        Log.d("errorlogin", e.toString());
                    }
                } else
                    userNameText.setError("User not found!");
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void signIn(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();
                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                }
            }
        });
    }
}
