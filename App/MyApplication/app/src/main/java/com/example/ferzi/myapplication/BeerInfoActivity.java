package com.example.ferzi.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ferzi on 26/09/2017.
 */

public class BeerInfoActivity extends Activity {

    private final static String TAG = "BeerInfoActivity";

    private DownloadImage myDownloadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        Intent i = getIntent();
        Beer beer = (Beer) i.getSerializableExtra("beer");
        DownloadImage myDownloadImage = new DownloadImage(this);
        myDownloadImage.execute(beer);
    }

    public void loadBeerInfo (Beer beer, Bitmap bmp) {
        //myDownloadImage.detach();

        setContentView(R.layout.info_beer);
        Log.d(TAG, beer.toString());

        ImageView image = (ImageView) findViewById(R.id.image);
        image.setImageBitmap(bmp);

        TextView name = (TextView) findViewById(R.id.name);
        name.setText("Nombre: " + beer.getName());

        TextView brewery = (TextView) findViewById(R.id.brewery);
        brewery.setText("Cervecería: " + beer.getBrewery());

        TextView ibu = (TextView) findViewById(R.id.ibu);
        ibu.setText("Amargura: " + beer.getIbu());

        TextView abv = (TextView) findViewById(R.id.abv);
        abv.setText("Alcohol: " + beer.getAbv());

        TextView rating = (TextView) findViewById(R.id.rating);
        rating.setText("Puntuación (0-100): " + String.valueOf(beer.getStyle_rating()));

        TextView style = (TextView) findViewById(R.id.style);
        style.setText("Estilo: " + beer.getBeerStyle().replace("_", " "));
    }
}
