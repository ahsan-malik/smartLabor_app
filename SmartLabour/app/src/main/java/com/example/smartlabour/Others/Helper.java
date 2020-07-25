package com.example.smartlabour.Others;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartlabour.R;

public class Helper {
    public static void toast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void addToolbar(AppCompatActivity activity, String title){
        try {
            Toolbar toolbar = activity.findViewById(R.id.toolbar);
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setTitle(title);

        }catch (Exception e){
            Log.d("tool", e.toString());
            Helper.toast(activity, e.toString());
        }
    }
}
