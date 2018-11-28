package com.example.a10012826.pokedex;

import android.app.Activity;
import android.os.Bundle;

public class PopUp extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow);

        getWindow().setLayout(800, 1000);
    }
}
