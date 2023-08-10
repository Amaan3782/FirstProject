package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity {

    Button login, signup;
    RadioGroup gender;
    Spinner city;
    ArrayList<String> arrayList;
    Calendar calendar;
    EditText name, contact, email, password, confirmPassword, dob;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,16}$";
    String sCity = "";
    String sGender;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = openOrCreateDatabase("FirstProject",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT INT(10),PASSWORD VARCHAR(20),GENDER VARCHAR(5),CITY VARCHAR(50),DOB VARCHAR(10))";
        db.execSQL(tableQuery);

        name = findViewById(R.id.signup_name);
        contact = findViewById(R.id.signup_contact);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        confirmPassword = findViewById(R.id.signup_confirmPassword);

        login = findViewById(R.id.signup_login);
        signup = findViewById(R.id.signup_signup);

        gender = findViewById(R.id.signup_gender);

        city = findViewById(R.id.signup_city);

        dob = findViewById(R.id.signup_dob);

        calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateClick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                dob.setText(sdf.format(calendar.getTime()));

            }
        };

        dob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this,dateClick,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                return true;
            }
        });

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
                sGender = radioButton.getText().toString();
                new CommonMethods(SignupActivity.this, sGender);
            }
        });

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0) {
                    sCity = "";
                } else {
                    sCity = arrayList.get(i);
                    new CommonMethods(SignupActivity.this, sCity);
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
                } else if (contact.getText().toString().trim().equals("")) {
                    contact.setError("contact Number Is Required!");
                } else if (contact.getText().toString().length()<10) {
                    contact.setError("contact Number Must Be 10 Digit!");
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
                } else if (gender.getCheckedRadioButtonId() == -1) {
                    new CommonMethods(SignupActivity.this,"Please Select Gender");
                } else if (sCity.equals("")) {
                    new CommonMethods(SignupActivity.this,"Please Select City");
                } else if (dob.getText().toString().trim().equals("")) {
                    dob.setError("Please Select Date Of Birth");
                } else {
                    String selectQuery = "SELECT * FROM USERS WHERE EMAIL='"+email.getText().toString()+"' OR '"+contact.getText().toString()+"'";
                    Cursor cursor = db.rawQuery(selectQuery,null);
                    if(cursor.getCount() > 0){
                        new CommonMethods(SignupActivity.this,"Email Or contact Dose Not Match");
                    }else {
                        String insertQuery = "INSERT INTO USERS VALUES(NULL,'" + name.getText().toString() + "','" + email.getText().toString() + "','" + contact.getText().toString() + "','" + password.getText().toString() + "','" + sGender + "','" + sGender + "','" + dob.getText().toString() + "')";
                        db.execSQL(insertQuery);

                        new CommonMethods(SignupActivity.this, "Signup Successfully!");
                        new CommonMethods(view,"Login Successfully");
                        onBackPressed();
                    }
                }
            }
        });
    }
}