package com.example.smartlabour.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.smartlabour.DBCode.DBHelper;
import com.example.smartlabour.Others.Helper;
import com.example.smartlabour.R;
import com.example.smartlabour.Singleton.Labor;
import com.example.smartlabour.models.Laboror;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    EditText t_name, t_address, t_city, t_mobile, t_work, t_duration;
    Switch availablity_switch;
    Button doneBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        t_name = view.findViewById(R.id.name);
        t_address = view.findViewById(R.id.address);
        t_city = view.findViewById(R.id.city);
        t_mobile = view.findViewById(R.id.mobphone);
        t_work = view.findViewById(R.id.type_work);
        t_duration = view.findViewById(R.id.duration);
        availablity_switch = view.findViewById(R.id.booking_switch);
        doneBtn = view.findViewById(R.id.done_btn);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateFields(Labor.getLaboror());
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDone();
            }
        });
    }

    public void btnDone(){
        Labor.getLaboror().setName(t_name.getText().toString().trim());
        Labor.getLaboror().getContactDetails().setAddress(t_address.getText().toString().trim());
        Labor.getLaboror().getContactDetails().setCity(t_city.getText().toString().trim());
        Labor.getLaboror().getContactDetails().setPhoneNo(t_mobile.getText().toString().trim());
        Labor.getLaboror().getExperience().setType_work(t_work.getText().toString().trim());
        Labor.getLaboror().getExperience().setDuration(t_duration.getText().toString().trim());
        Labor.getLaboror().setBooked(!availablity_switch.isChecked());
        try {
            DBHelper.updateUser(Labor.getLaboror());
            Helper.toast(getActivity(), "User Updated");
        }catch (Exception e){
            Helper.toast(getActivity(), e.toString());
        }

    }

    private void updateFields(Laboror laboror) {
        t_name.setText(laboror.getName());
        t_address.setText(laboror.getContactDetails().getAddress());
        t_city.setText(laboror.getContactDetails().getCity());
        t_mobile.setText(laboror.getContactDetails().getPhoneNo());
        t_work.setText(laboror.getExperience().getType_work());
        t_duration.setText(laboror.getExperience().getDuration());
        availablity_switch.setChecked(!laboror.isBooked());
    }

}
