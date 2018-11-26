package com.example.a10012826.pokedex;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Pokemon> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        listView = findViewById(R.id.listView);

        list.add(new Pokemon("Bulbasaur", R.drawable.bulbasaur, "Fire", "Water"));
        list.add(new Pokemon("Charmander", R.drawable.charmander, "Water", "Grass"));
        list.add(new Pokemon("Bulbasaur", R.drawable.squirtle, "Grass", "Fire"));


        CustomAdapter adapter = new CustomAdapter(this, R.layout.pokemon_layout, list);
        if (getApplicationContext() != null) {
            listView.setAdapter(adapter);
        }

    }

    public class CustomAdapter extends ArrayAdapter<Pokemon> {

        Context context;
        int resource;
        List<Pokemon> list;

        public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Pokemon> objects) {
            super(context, resource, objects);

            this.context = context;
            this.resource = resource;
            this.list = objects;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View adapterLayout = layoutInflater.inflate(resource, null);
            TextView textView = adapterLayout.findViewById(R.id.id_textview);
            ImageView imageView = adapterLayout.findViewById(R.id.id_image);
            Button button = adapterLayout.findViewById(R.id.id_button);

            textView.setText(list.get(position).getName());
            imageView.setImageResource(list.get(position).getImage());

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Clicked " + list.get(position).getName(), Toast.LENGTH_LONG).show();
                }
            });

            return adapterLayout;
        }
    }

    public class Pokemon {

        String name;
        int image;
        String weaknesses;
        String strengths;

        public Pokemon(String name, int image, String weaknesses, String strengths) {
            this.name = name;
            this.image = image;
            this.weaknesses = weaknesses;
            this.strengths = strengths;
        }

        public String getName() {
            return name;
        }

        public int getImage() {
            return image;
        }

        public String getWeaknesses() {
            return weaknesses;
        }

        public String getStrengths() {
            return strengths;
        }
    }
}
