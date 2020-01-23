package com.example.quizzer;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class Utils {
    public static String getText(EditText editText){
        return editText.getText().toString().trim();
    }

    public static boolean isValidated(String ...params){
        for(String str:params){
            if(str.contentEquals(""))
                return false;
        }
        return true;
    }

    public static boolean isPasswordValidated(String password, String confirmPassword){
       if(password.contentEquals(confirmPassword))
                return true;
        return false;
    }

    public static void showMessage(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }


}
