package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button login, signup;
    EditText email,password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.main_email);
        password = findViewById(R.id.main_password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().trim().equals("")) {
                    email.setError("Email Id Required");
                }
                else if(!email.getText().toString().trim().matches(emailPattern)){
                    email.setError("Valid Email Id Required");
                }
                else if (password.getText().toString().equals("")) {
                    password.setError("Password Required");
                }
                else if(password.getText().toString().trim().length()<6) {
                    password.setError("Min, 6 Char Password Required");
                }
                else {
                    System.out.println("Login Successfully!");
                    //Toast.makeText(MainActivity.this, "Login Successfully!", Toast.LENGTH_LONG).show();
                    new CommonMethods(MainActivity.this, "Login Successfully!");
                    //Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                    //startActivity(intent);
                    new CommonMethods(MainActivity.this, HomeActivity.class);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Login Successfully!");
                //Toast.makeText(MainActivity.this, "Welcome To Our App!", Toast.LENGTH_LONG).show();
                new CommonMethods(MainActivity.this, "Welcome To Our App!");
                //Intent intent = new Intent(MainActivity.this,SignupActivity.class);
                //startActivity(intent);
                new CommonMethods(MainActivity.this,SignupActivity.class);
            }
        });

    }
}