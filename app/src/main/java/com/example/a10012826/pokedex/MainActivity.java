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
    ArrayList<Pokemon> list;
    int number = -1;
    public static final String POKEMON_LIST = "Pokemon List";
    public static final String POKEMON_NUMBER = "Pokemon Number";
    public static final String POKEMON_NAME = "Pokemon Name";


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(POKEMON_LIST, list);
        outState.putSerializable(POKEMON_NUMBER, number);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        listView = findViewById(R.id.listView);

        list.add(new Pokemon("Bulbasaur", R.drawable.bulbasaur, 1, "Fire", "Water", "Bulbasaur is a cute Pokémon born with a large seed firmly affixed to its back; the seed grows in size as the Pokémon does. Along with Squirtle and Charmander, Bulbasaur is one of the three Pokémon available at the beginning of Pokémon Red and Blue. It evolves into Ivysaur."));
        list.add(new Pokemon("Charmander", R.drawable.charmander, 4,"Water", "Grass", "Charmander is a bipedal, reptilian Pokémon with a primarily orange body. Its underside from the chest down and soles are cream-colored. It has two small fangs visible in its upper jaw and two smaller fangs in its lower jaw. Charmander has blue eyes. Its arms and legs are short with four fingers and three clawed toes. A fire burns at the tip of this Pokémon's slender tail and has blazed there since Charmander's birth. The flame can be used as an indication of Charmander's health and mood, burning brightly when the Pokémon is strong, weakly when it is exhausted, wavering when it is happy, and blazing when it is enraged. It is said that Charmander dies if its flame goes out. However, if the Pokémon is healthy, the flame will continue to burn even if it gets a bit wet and is said to steam in the rain."));
        list.add(new Pokemon("Squirtle", R.drawable.squirtle, 7,"Grass", "Fire", "It evolves into Wartortle starting at level 16, which evolves into Blastoise starting at level 36. Along with Bulbasaur and Charmander, Squirtle is one of three starter Pokémon of Kanto available at the beginning of Pokémon Red, Green, Blue, FireRed, and LeafGreen."));

        if (savedInstanceState != null) {
            list = (ArrayList<Pokemon>)savedInstanceState.getSerializable(POKEMON_LIST);
            number = (int)savedInstanceState.getSerializable(POKEMON_NUMBER);
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            tvdescription = findViewById(R.id.tvdescription);
            tvdescription.setMovementMethod(new ScrollingMovementMethod());
            if (number > -1) {
                tvdescription.setText(list.get(number).getDescription());
            }
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (number > -1) {
                Intent intent = new Intent(this, PopUp.class);
                intent.putExtra(POKEMON_NAME, list.get(number).getName());
                intent.putExtra(POKEMON_NUMBER, list.get(number).getDescription());
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
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Clicked " + list.get(position).getName(), Toast.LENGTH_LONG).show();
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        number = position;
                        tvdescription.setText(list.get(position).getDescription());
                } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        number = position;
                        Intent intent = new Intent(MainActivity.this, PopUp.class);
                        intent.putExtra(POKEMON_NUMBER, list.get(position).getDescription());
                        intent.putExtra(POKEMON_NAME, list.get(position).getName());
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
        int favorite;

        public Pokemon(String name, int image, int number, String weaknesses, String strengths, String description) {
            this.name = name;
            this.image = image;
            this.number = number;
            this.weaknesses = weaknesses;
            this.strengths = strengths;
            this.description = description;
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
