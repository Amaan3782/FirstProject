package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {

    Button login, signup;
    RadioGroup gender;
    Spinner city;
    //String[] cityArray = {"Ahmedabad", "Gandhinagar"};
    ArrayList<String> arrayList;
    EditText name, phone, email, password, confirmPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,16}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.signup_name);
        phone = findViewById(R.id.signup_phone);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        confirmPassword = findViewById(R.id.signup_confirmPassword);

        login = findViewById(R.id.signup_login);
        signup = findViewById(R.id.signup_signup);

        gender = findViewById(R.id.signup_gender);

        city = findViewById(R.id.signup_city);

        arrayList = new ArrayList<>();
        arrayList.add("Select City");
        arrayList.add("Gandhinagar");
        arrayList.add("Ahmedabad");

        ArrayAdapter adapter = new ArrayAdapter(SignupActivity.this, android.R.layout.simple_list_item_1,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        city.setAdapter(adapter);

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                new CommonMethods(SignupActivity.this,radioButton.getText().toString());
            }
        });

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0) {

                } else {
                    new CommonMethods(SignupActivity.this, arrayList.get(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().trim().equals("")) {
                    name.setError("Name Is Required!");
                } else if (email.getText().toString().trim().equals("")) {
                    email.setError("Email Id Required!");
                } else if(!email.getText().toString().trim().matches(emailPattern)){
                    email.setError("Valid Email Id Required!");
                } else if (phone.getText().toString().trim().equals("")) {
                    phone.setError("Phone Number Is Required!");
                } else if (phone.getText().toString().length()<10) {
                    phone.setError("Phone Number Must Be 10 Digit!");
                } else if (password.getText().toString().equals("")) {
                    password.setError("Password Required!");
                } else if (!password.getText().toString().matches(passwordPattern)) {
                    password.setError("Password Must Contain At Least One Uppercase, One Lower Case, One Number And One Special Symbol!");
                } else if(password.getText().toString().trim().length()<6) {
                    password.setError("Min, 6 Char Password Required");
                } else if (confirmPassword.getText().toString().equals("")) {
                    confirmPassword.setError("Password Required!");
                } else if(confirmPassword.getText().toString().trim().length()<6) {
                    confirmPassword.setError("Min, 6 Char Password Required!");
                } else if (!confirmPassword.getText().toString().trim().matches(password.getText().toString().trim())) {
                     confirmPassword.setError("Password Does Not Match!");
                } else {
                    System.out.println("Signup Successfully!");
                    new CommonMethods(SignupActivity.this, "Signup Successfully!");
                    onBackPressed();
                }
            }
        });
    }
}