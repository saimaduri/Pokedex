package com.example.a10012826.pokedex;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class PopUp extends Activity implements Serializable {

    String name;
    String url;
    public static final String POKEMON_NUMBER = "Pokemon Number";
    public static final String POKEMON_NAME = "Pokemon Name";
    public static final String POKEMON_URL = "POKEMON URL";
    TextView tvdescription;
    TextView tvname;
    Button button;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(POKEMON_NUMBER, -1);
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

            String description = getIntent().getStringExtra(POKEMON_NUMBER);
            String name = getIntent().getStringExtra(POKEMON_NAME);
            tvname.setText(name);
            tvdescription.setText(description);
            button = findViewById(R.id.button);
            url = getIntent().getStringExtra(POKEMON_URL);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openWebPage(url);
                    finish();
                }
            });

        } else {
            finish();
        }
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(intent);
    }
}
