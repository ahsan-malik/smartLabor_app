package com.example.smartlabour.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartlabour.Interfaces.ProfileInterface;
import com.example.smartlabour.Others.Helper;
import com.example.smartlabour.ProfileActivity;
import com.example.smartlabour.R;
import com.example.smartlabour.Singleton.Labor;
import com.example.smartlabour.models.Laboror;

public class ProfileFragment extends Fragment {

    //Labor labor;

    TextView t_name, t_mail, t_user_name, work_type, t_duration, t_address, t_phone;
    ImageView editImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        t_name = view.findViewById(R.id.name);
        t_mail = view.findViewById(R.id.mail);
        t_user_name = view.findViewById(R.id.userName);
        work_type = view.findViewById(R.id.type_t);
        t_duration = view.findViewById(R.id.duration_t);
        t_address = view.findViewById(R.id.address_t);
        t_phone = view.findViewById(R.id.phone_t);
        editImg = view.findViewById(R.id.edit_profile_button);

        //writeUser();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateFields(Labor.getLaboror());
        editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction;
                transaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditProfileFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void writeUser() {
        try {
            Laboror laboror = ((ProfileActivity) getActivity()).getLaboror();
            if (laboror != null) {
                updateFields(laboror);
            }
            else {
                ((ProfileActivity) getActivity()).passVal(new ProfileInterface() {
                    @Override
                    public void passData(Laboror laboror) {
                        Toast.makeText(getActivity(), "inside", Toast.LENGTH_SHORT).show();
                        updateFields(laboror);
                    }
                });
            }
        } catch (Exception e) {
            Helper.toast(getActivity(), e.toString());
            Log.d("errorf", e.toString());
        }
    }

    private void updateFields(Laboror laboror) {
        t_name.setText(laboror.getName());
        t_user_name.setText(laboror.getUserName());
        t_mail.setText(laboror.getContactDetails().getEmail());
        work_type.setText(laboror.getExperience().getType_work());
        t_duration.setText(laboror.getExperience().getDuration() + " of experience");
        t_address.setText(laboror.getContactDetails().getAddress() + ", " + laboror.getContactDetails().getCity());
        t_phone.setText(laboror.getContactDetails().getPhoneNo());
    }

}
