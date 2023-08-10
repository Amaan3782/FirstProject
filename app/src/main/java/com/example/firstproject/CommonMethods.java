package com.example.firstproject;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class CommonMethods {

    CommonMethods(Context context,String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    CommonMethods(View view, String message){
        Snackbar.make(view, message, Toast.LENGTH_SHORT).show();
    }

    CommonMethods(Context context,Class<?> nextClass){
        Intent intent = new Intent(context, nextClass);
        context.startActivity(intent);
    }

}
