package com.example.smartlabour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartlabour.DBCode.DBHelper;
import com.example.smartlabour.Others.Helper;
import com.example.smartlabour.Singleton.Labor;
import com.example.smartlabour.models.ContactDetails;
import com.example.smartlabour.models.Customer;
import com.example.smartlabour.models.Laboror;
import com.google.android.material.textfield.TextInputLayout;

public class LaborDetailActivity extends AppCompatActivity {

    TextView t_name, t_mail, t_user_name, work_type, t_duration, t_address, t_phone;
    Button button;
    ImageView editImg;
    View customerForm;
    Laboror laboror;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        try {
            laboror = Labor.getLaboror();
        }catch (Exception e){
            Helper.toast(this, e.toString());
            Log.d("u2_r", e.toString());
        }

        getViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            updateViews();
        }catch (Exception e){
            Helper.toast(this, e.toString());
            Log.d("u_r", e.toString());
        }
    }

    private void getViews(){
        t_name = findViewById(R.id.name);
        t_mail = findViewById(R.id.mail);
        t_user_name = findViewById(R.id.userName);
        work_type = findViewById(R.id.type_t);
        t_duration = findViewById(R.id.duration_t);
        t_address = findViewById(R.id.address_t);
        t_phone = findViewById(R.id.phone_t);
        editImg = findViewById(R.id.edit_profile_button);
        editImg.setVisibility(View.INVISIBLE);

        FrameLayout rootView = (FrameLayout) findViewById(R.id.layout).getParent().getParent();
        rootView.addView(getLayoutInflater().inflate(R.layout.button_book, null));

        button = findViewById(R.id.book_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookBtn();
            }
        });
        }
    private void updateViews(){
        t_name.setText(laboror.getName());
        t_user_name.setText(laboror.getUserName());
        t_mail.setText(laboror.getContactDetails().getEmail());
        work_type.setText(laboror.getExperience().getType_work());
        t_duration.setText(laboror.getExperience().getDuration() + " of experience");
        t_address.setText(laboror.getContactDetails().getAddress() + ", " + laboror.getContactDetails().getCity());
        t_phone.setText(laboror.getContactDetails().getPhoneNo());
        button.setText("Book " + laboror.getName());
    }

    private void bookBtn(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        customerForm = inflater.inflate(R.layout.customer_form, null);
        dialogBuilder.setView(customerForm);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialogBuilder.setNegativeButton("Cencel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addCustomer())
                    alertDialog.dismiss();
            }
        });
    }

    private boolean validateInput(TextInputLayout textInputLayout){
        String input = textInputLayout.getEditText().getText().toString().trim();
        if(input.isEmpty()){
            textInputLayout.setError("The field can't be empty!");
            return false;
        }else{
            textInputLayout.setError(null);
            return true;
        }
    }

    private boolean addCustomer(){
        TextInputLayout name_layout = customerForm.findViewById(R.id.name);
        TextInputLayout email_layout = customerForm.findViewById(R.id.email);
        TextInputLayout phone_layout = customerForm.findViewById(R.id.phone);
        TextInputLayout address_layout = customerForm.findViewById(R.id.address);
        TextInputLayout city_layout = customerForm.findViewById(R.id.city);

        String name = name_layout.getEditText().getText().toString().trim();
        String email = email_layout.getEditText().getText().toString().trim();
        String phone = phone_layout.getEditText().getText().toString().trim();
        String address = address_layout.getEditText().getText().toString().trim();
        String city = city_layout.getEditText().getText().toString().trim();

        if(validateInput(name_layout) & validateInput(phone_layout)) {

            Customer customer = new Customer(name, new ContactDetails(email, phone, address, city));

            if(isPermissionGranted(Manifest.permission.SEND_SMS)) {
                sendSMS(customer);
                DBHelper.addCustomer(customer, laboror.getUserName());
                Helper.toast(this, "order Placed");
                return true;
            }else{
                askForPermission(Manifest.permission.SEND_SMS);
                //Helper.toast(this, "SMS permission denied");
            }
        }
        return false;
    }

    private boolean isPermissionGranted(String permission){
        return (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED);
    }

    private void askForPermission(String permission){
        ActivityCompat.requestPermissions(this, new String[]{permission}, PackageManager.PERMISSION_GRANTED);
    }

    private void sendSMS(Customer customer){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(
                laboror.getContactDetails().getPhoneNo(), null,
                "From Smart Labor Booking App\nHello " + laboror.getName() + "\n" + laboror.getUserName()
                        + "\nYou are hired by "+ customer.getName()
                        + "\nyou can contact him at his number for more detail\n"
                        + customer.getContactDetails().getPhoneNo(),
                null, null
        );
    }
}
