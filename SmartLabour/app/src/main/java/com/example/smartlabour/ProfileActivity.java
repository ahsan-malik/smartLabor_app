package com.example.smartlabour;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.util.Log;
import android.view.MenuItem;

import com.example.smartlabour.DBCode.DBHelper;
import com.example.smartlabour.Interfaces.ProfileInterface;
import com.example.smartlabour.Singleton.Labor;
import com.example.smartlabour.fragments.EditProfileFragment;
import com.example.smartlabour.fragments.ProfileFragment;
import com.example.smartlabour.models.Laboror;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Laboror laboror;
    //Labor labor;

    FragmentTransaction transaction;
    //ProfileInterface profileInterface;

    DatabaseReference db;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //labor = (Labor) getApplication();
        //laboror = labor.getLaboror();
        if (!DBHelper.checkUserState()) {
            Toast.makeText(this, "user is null", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            Toast.makeText(this, "before userinfo", Toast.LENGTH_LONG).show();
            userInfo();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar s if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.edit_profile) {
            transaction = getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditProfileFragment());
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (id == R.id.sign_out) {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void userInfo() {
        db = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        Query query = db.child("Labor");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    laboror = snapshot.getValue(Laboror.class);
                    if (user.getEmail().equals(laboror.getContactDetails().getEmail())) {
                        try {
                            Labor.copy(laboror);
                            //profileInterface.passData(laboror);
                            NavigationView navigationView = findViewById(R.id.nav_view);
                            View headerView = navigationView.getHeaderView(0);
                            TextView navHeaderUserName = headerView.findViewById(R.id.nav_header_userName);
                            TextView navHeaderMail = headerView.findViewById(R.id.nav_header_mail);
                            navHeaderUserName.setText(laboror.getUserName());
                            navHeaderMail.setText(laboror.getContactDetails().getEmail());
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                        } catch (Exception e) {
                            Toast.makeText(ProfileActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("1111111111111111111111", e.toString());
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void passVal(ProfileInterface profileInterface) {
        //this.profileInterface = profileInterface;

    }

    public Laboror getLaboror(){
        return laboror;
    }

}

