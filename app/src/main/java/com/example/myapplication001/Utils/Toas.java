package com.example.myapplication001.Utils;

import android.content.Context;
import android.widget.Toast;

public class Toas {
    private static Toast toast;
    public static void ShowToast(String content){
        toast.setText(content);
        toast.show();
    }
    public static  void init(Context context){
        toast=Toast.makeText(context,"",Toast.LENGTH_SHORT);
    }
}
