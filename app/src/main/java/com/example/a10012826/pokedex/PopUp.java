package com.example.a10012826.pokedex;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class PopUp extends Activity implements Serializable {

    String name;
    public static final String POKEMON_DESCRIPTION = "Pokemon Description";
    public static final String POKEMON_NAME = "Pokemon Name";
    TextView tvdescription;
    TextView tvname;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.popupwindow);
            getWindow().setLayout(800, 1000);

            tvdescription = findViewById(R.id.tv_descirption);
            tvname = findViewById(R.id.tv_name);
            tvdescription.setMovementMethod(new ScrollingMovementMethod());

            String description = getIntent().getStringExtra(POKEMON_DESCRIPTION);
            String name = getIntent().getStringExtra(POKEMON_NAME);
            tvname.setText(name);
            tvdescription.setText(description);
        } else {
            finish();
        }
    }
}
