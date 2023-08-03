package com.example.firstproject;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CommonMethods {

    CommonMethods(Context context,String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    CommonMethods(Context context,Class<?> nextClass){
        Intent intent = new Intent(context, nextClass);
        context.startActivity(intent);
    }

}
