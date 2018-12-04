package com.example.a10012826.pokedex;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView tvdescription;
    TextView tvstrengths;
    TextView tvweaknesses;
    TextView tvname;
    ArrayList<Pokemon> list;
    int number = -1;
    public static final String POKEMON_LIST = "Pokemon List";
    public static final String POKEMON_NUMBER = "Pokemon Number";
    public static final String POKEMON_NAME = "Pokemon Name";
    public static final String POKEMON_URL = "POKEMON URL";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(POKEMON_LIST, list);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getName().equals(tvname.getText())) {
                    number = i;
                }
            }
        }
        outState.putSerializable(POKEMON_NUMBER, number);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        listView = findViewById(R.id.listView);

        list.add(new Pokemon("Bulbasaur", R.drawable.bulbasaur, 1, "Fire", "Water", "Bulbasaur, a grass type, is one of the three starters in the Kanto region. It evolves into Ivysaur, and then Venesaur, making it a well-rounded starter.", "https://bulbapedia.bulbagarden.net/wiki/Bulbasaur_(Pok%C3%A9mon)"));
        list.add(new Pokemon("Ivysaur", R.drawable.ivysaur, 2, "Fire", "Water", "Ivysaur, a grass type, is the evolved form of Bulbasaur. It evolves into Venusaur, a very strong grass type Pokemon.", "https://bulbapedia.bulbagarden.net/wiki/Ivysaur_(Pok%C3%A9mon)"));
        list.add(new Pokemon("Venusaur", R.drawable.venusaur, 3, "Fire", "Water", "Venusaur, a grass type, is the second evolution of Bulbasaur. It evolves directly from Ivysaur, and is a very strong grass type Pokemon.", "https://bulbapedia.bulbagarden.net/wiki/Venusaur_(Pok%C3%A9mon)"));

        list.add(new Pokemon("Charmander", R.drawable.charmander, 4,"Water", "Grass", "Charmander, the most popular starter of them all, is a fire type. Charmander evolves into Charmeleon and then Charizard, making it one of the strongest Pokemon in the game!", "https://bulbapedia.bulbagarden.net/wiki/Charmander_(Pok%C3%A9mon)"));
        list.add(new Pokemon("Charmeleon", R.drawable.charmeleon, 5, "Water", "Grass", "Charmeleon, a fire type, is the evolved form of Charmander. It evolves into Charizard, one of the strongest Pokemon in the entire region.", "https://bulbapedia.bulbagarden.net/wiki/Charmeleon_(Pok%C3%A9mon)"));
        list.add(new Pokemon("Charizard", R.drawable.charizard, 6, "Water", "Grass", "Charizard, a fire type, is the second evolution of Charmander. It evolves from Charmeleon, one of the strongest Pokemon in the entire region. It is also one of the fan favorites among many trainers.", "https://bulbapedia.bulbagarden.net/wiki/Charizard_(Pok%C3%A9mon)"));

        list.add(new Pokemon("Squirtle", R.drawable.squirtle, 7,"Grass", "Fire", "Squirtle, a water type, evolves into Wartortle and then Blastoise. Squirtle is a strong starter and helps the player take out the Pewter City Gym early on in the game.", "https://bulbapedia.bulbagarden.net/wiki/Squirtle_(Pok%C3%A9mon)"));
        list.add(new Pokemon("Wartortle", R.drawable.wartortle, 8, "Grass", "Fire", "Wartortle,, a water type, is the evolved form of Squirtle. It evolves into Blastoise, an extremely powerful Pokemon", "https://bulbapedia.bulbagarden.net/wiki/Wartortle_(Pok%C3%A9mon)"));
        list.add(new Pokemon("Blastoise", R.drawable.blastoise, 9, "Grass", "Fire", "Blastoise,, a water type, is the second evolution of Squirtle. It evolves from Wartortle, and is an extremely strong Pokemon.", "https://bulbapedia.bulbagarden.net/wiki/Blastoise_(Pok%C3%A9mon)"));

        if (savedInstanceState != null) {
            list = (ArrayList<Pokemon>)savedInstanceState.getSerializable(POKEMON_LIST);
            number = (int)savedInstanceState.getSerializable(POKEMON_NUMBER);
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            tvdescription = findViewById(R.id.tvdescription);
            tvstrengths = findViewById(R.id.tvstrengths);
            tvweaknesses = findViewById(R.id.tvweaknesses);
            tvname = findViewById(R.id.tvname);
            tvdescription.setMovementMethod(new ScrollingMovementMethod());
            if (number > -1) {
                tvdescription.setText(list.get(number).getDescription());
                tvweaknesses.setText("Weak Against:\n" + list.get(number).getWeaknesses());
                tvstrengths.setText("Strong Against:\n" + list.get(number).getStrengths());
                tvname.setText(list.get(number).getName());
            }
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (number > -1) {
                Intent intent = new Intent(this, PopUp.class);
                intent.putExtra(POKEMON_NAME, list.get(number).getName());
                intent.putExtra(POKEMON_NUMBER, list.get(number).getDescription());
                intent.putExtra(POKEMON_URL, list.get(number).getUrl());
                startActivity(intent);
            }
        }

        CustomAdapter adapter = new CustomAdapter(this, R.layout.pokemon_layout, list);

        listView.setAdapter(adapter);
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
            final ImageView favorite = adapterLayout.findViewById(R.id.id_favorite);
            Button button = adapterLayout.findViewById(R.id.id_button);

            textView.setText(list.get(position).getName());
            imageView.setImageResource(list.get(position).getImage());

            if (list.get(position).getFavorite() == 1) {
                favorite.setTag(R.drawable.staron);
                favorite.setImageResource(R.drawable.staron);
            } else {
                favorite.setTag(R.drawable.staroff);
                favorite.setImageResource(R.drawable.staroff);
            }

            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((Integer)favorite.getTag() == R.drawable.staroff) {
                        favorite.setImageResource(R.drawable.staron);
                        favorite.setTag(R.drawable.staron);
                        list.get(position).setFavorite(1);
                        Collections.sort(list);
                        notifyDataSetChanged();
                    } else {
                        favorite.setImageResource(R.drawable.staroff);
                        favorite.setTag(R.drawable.staroff);
                        list.get(position).setFavorite(0);
                        Collections.sort(list);
                        notifyDataSetChanged();
                    }
//                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
//                        tvname.setText(list.get(number).getName());
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Clicked " + list.get(position).getName(), Toast.LENGTH_LONG).show();
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        number = position;
                        tvdescription.setText(list.get(position).getDescription());
                        tvweaknesses.setText("Weak Against:\n" + list.get(number).getWeaknesses());
                        tvstrengths.setText("Strong Against:\n" + list.get(number).getStrengths());
                        tvname.setText(list.get(number).getName());
                } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        number = position;
                        Intent intent = new Intent(MainActivity.this, PopUp.class);
                        intent.putExtra(POKEMON_NUMBER, list.get(position).getDescription());
                        intent.putExtra(POKEMON_NAME, list.get(position).getName());
                        intent.putExtra(POKEMON_URL, list.get(position).getUrl());
                        startActivity(intent);
                    }
                }
            });
            return adapterLayout;
        }
    }

    public class Pokemon implements Serializable, Comparable<Pokemon> {

        String name;
        int image;
        int number;
        String weaknesses;
        String strengths;
        String description;
        String url;
        int favorite;

        public Pokemon(String name, int image, int number, String weaknesses, String strengths, String description, String url) {
            this.name = name;
            this.image = image;
            this.number = number;
            this.weaknesses = weaknesses;
            this.strengths = strengths;
            this.description = description;
            this.url = url;
            favorite = 0;
        }

        public String getName() {
            return name;
        }

        public int getImage() {
            return image;
        }

        public int getNumber() {
            return number;
        }

        public String getWeaknesses() {
            return weaknesses;
        }

        public String getStrengths() {
            return strengths;
        }

        public String getDescription() {
            return description;
        }

        public int getFavorite() {
            return favorite;
        }

        public void setFavorite(int favorite) {
            this.favorite = favorite;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public int compareTo(Pokemon o) {
            if (favorite > o.getFavorite()) {
                return -1;
            } else if (favorite < o.getFavorite()) {
                return 1;
            } else {
                if (number < o.getNumber()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }
    }
}
