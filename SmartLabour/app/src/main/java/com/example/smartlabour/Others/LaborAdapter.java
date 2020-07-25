package com.example.smartlabour.Others;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartlabour.LaborDetailActivity;
import com.example.smartlabour.R;
import com.example.smartlabour.Singleton.Labor;
import com.example.smartlabour.models.Laboror;

import java.util.ArrayList;

public class LaborAdapter extends RecyclerView.Adapter<LaborAdapter.LaborViewHolder> {

    ArrayList<Laboror> laborors;

    public LaborAdapter(ArrayList<Laboror> labororArrayList){
        this.laborors = labororArrayList;
    }

    @NonNull
    @Override
    public LaborViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        return new LaborViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LaborViewHolder holder, int position) {
        final Laboror laboror = laborors.get(position);

        holder.name_view.setText(laboror.getName());
        holder.category_view.setText(laboror.getExperience().getType_work());
        holder.city_view.setText(laboror.getContactDetails().getCity());
        holder.phone_view.setText(laboror.getContactDetails().getPhoneNo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Labor.copy(laboror);
                Intent intent = new Intent(v.getContext(), LaborDetailActivity.class);
                //intent.putExtra("id", laboror.getUserName());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return laborors.size();
    }

    public class LaborViewHolder extends RecyclerView.ViewHolder{
        TextView name_view, category_view, city_view, phone_view;
        public LaborViewHolder(@NonNull View itemView) {
            super(itemView);
            name_view = itemView.findViewById(R.id.name_text);
            category_view = itemView.findViewById(R.id.exp_text);
            city_view = itemView.findViewById(R.id.city_text);
            phone_view = itemView.findViewById(R.id.phone_text);

        }
    }
}
