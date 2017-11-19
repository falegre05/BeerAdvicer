package com.example.ferzi.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ferzi on 26/09/2017.
 */

public class BeerInfoActivity extends Activity {

    private final static String TAG = "BeerInfoActivity";

    private DownloadImageTask myDownloadImageTask;
    private SimilarClientTask mySimilarClientTask;

    private String selectedAbv;
    private String selectedIbu;
    private Beer beer;

    public ArrayList beers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        Intent i = getIntent();
        beer = (Beer) i.getSerializableExtra("beer");
        DownloadImageTask myDownloadImageTask = new DownloadImageTask(this);
        myDownloadImageTask.execute(beer);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        DownloadImageTask myDownloadImageTask = new DownloadImageTask(this);
        myDownloadImageTask.execute(beer);
    }

    public void loadBeerInfo (final Beer beer, Bitmap bmp) {
        //myDownloadImageTask.detach();

        setContentView(R.layout.info_beer);
        Log.d(TAG, beer.toString());

        ImageView image = (ImageView) findViewById(R.id.image);
        image.setImageBitmap(bmp);

        TextView name = (TextView) findViewById(R.id.name);
        name.setText("Nombre: " + beer.getName());

        TextView brewery = (TextView) findViewById(R.id.brewery);
        brewery.setText("Cervecería: " + beer.getBrewery());

        TextView ibu = (TextView) findViewById(R.id.ibu);
        if(beer.getIbu().equals("0")){
            ibu.setText("Amargura: --");
        } else {
            ibu.setText("Amargura: " + beer.getIbu());
        }

        TextView abv = (TextView) findViewById(R.id.abv);
        abv.setText("Alcohol: " + beer.getAbv());

        TextView rating = (TextView) findViewById(R.id.rating);
        rating.setText("Puntuación (0-100): " + String.valueOf(beer.getStyle_rating()));

        TextView style = (TextView) findViewById(R.id.style);
        style.setText("Estilo: " + beer.getBeerStyle().replace("_", " "));

        Button button = (Button) findViewById(R.id.buttonSimilares);
        mySimilarClientTask = new SimilarClientTask(this);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Log.d(TAG, selectedAbv + selectedIbu + beer.getBeerStyle() + "Puntuación");
                Log.d(TAG, "lanzamos la tarea asincrona mySimilarClientTask");
                mySimilarClientTask.execute(beer.getAbv(), beer.getIbu(), beer.getBeerStyle());


                setContentView(R.layout.searching_screen);
            }
        });
    }

    public void similarSearchDone(Integer integer) {
        mySimilarClientTask.detach();
        Log.d(TAG, "Advanced search done");

        Intent intent = new Intent(BeerInfoActivity.this, BeersListActivity.class);
        intent.putExtra("beers", beers);
        startActivity(intent);
    }
}
