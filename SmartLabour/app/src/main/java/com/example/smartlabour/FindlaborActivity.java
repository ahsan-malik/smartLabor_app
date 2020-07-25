package com.example.smartlabour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.example.smartlabour.DBCode.DBHelper;
import com.example.smartlabour.Others.LaborAdapter;
import com.example.smartlabour.models.Laboror;

import java.util.ArrayList;

public class FindlaborActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SearchView searchBox;
    ArrayList<Laboror> laborList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findlabor);

        laborList = DBHelper.getAll();
        searchBox = findViewById(R.id.search_bar);

        recyclerView  = findViewById(R.id.labor_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        recyclerView.setAdapter(new LaborAdapter(laborList));

        if(searchBox != null){
            searchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    ArrayList<Laboror> list = new ArrayList<>();
                    for(Laboror laboror : laborList){
                        if(
                                laboror.getName().toLowerCase().contains(newText.toLowerCase())
                                || laboror.getExperience().getType_work().toLowerCase().contains(newText.toLowerCase())
                                || laboror.getUserName().toLowerCase().contains(newText.toLowerCase())
                                || laboror.getContactDetails().getCity().toLowerCase().contains(newText.toLowerCase())
                                || laboror.getContactDetails().getEmail().toLowerCase().contains(newText.toLowerCase())
                        )
                            list.add(laboror);
                    }
                    recyclerView.setAdapter(new LaborAdapter(list));
                    return true;
                }
            });
        }
    }
}
