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

    private DownloadImage myDownloadImage;
    private SimilarClientTask mySimilarClientTask;

    private String selectedAbv;
    private String selectedIbu;
    private String tipo;

    public ArrayList beers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        Intent i = getIntent();
        Beer beer = (Beer) i.getSerializableExtra("beer");
        tipo = i.getExtras().getString("tipo");
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

        Button button = (Button) findViewById(R.id.buttonSimilares);
        if (tipo.equals("basic")){
            button.setVisibility(View.VISIBLE);
            mySimilarClientTask = new SimilarClientTask(this);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    getTags(beer);
                    Log.d(TAG, selectedAbv + selectedIbu + beer.getBeerStyle() + "Puntuación");
                    Log.d(TAG, "lanzamos la tarea asincrona myAdvancedClientTask");
                    mySimilarClientTask.execute(selectedAbv, selectedIbu, beer.getBeerStyle(), "Puntuación");


                    setContentView(R.layout.searching_screen);
                }
            });
        }
    }

    private void getTags (Beer beer){
        double abv = Double.parseDouble(beer.getAbv());
        double ibu = Double.parseDouble(beer.getIbu());

        double Muypoco = 0, Poco = 0, Moderado = 0, Mucho = 0, Muchisimo =0;

        //Muypoco ABV
        if (abv >= 5){
            Muypoco = 0;
        } else if (abv <= 3.6) {
            Muypoco = 1;
        } else {
            Muypoco = (5 - abv) / 1.4;
        }

        //Poco ABV
        if (abv >= 6.55 || abv <= 3.6){
            Poco = 0;
        } else if (abv >= 5 && abv < 6.55) {
            Poco = (6.55 - abv)/1.55;
        } else if (abv > 3.6 && abv < 5){
            Poco = (abv - 3.6) / 1.4;
        }

        //Moderado ABV
        if (abv >= 8.7 || abv <= 5) {
            Moderado = 0;
        } else if (abv >= 6.55 && abv < 8.7) {
            Moderado = (8.7 - abv) / 2.15;
        } else if (abv > 5 && abv < 6.55) {
            Moderado = (abv - 5) / 1.55;
        }

        //Mucho ABV
        if (abv >= 13.2 || abv <= 6.55) {
            Mucho = 0;
        } else if (abv >= 8.7 && abv < 13.2) {
            Mucho = (13.2 - abv) / 4.5;
        } else if (abv > 6.55 && abv < 8.7) {
            Mucho = (abv - 6.55) / 2.15;
        }

        //Muchisimo ABV
        if (abv >= 13.2){
            Muchisimo = 1;
        } else if (abv <= 8.7) {
            Muchisimo = 0;
        } else {
            Muchisimo = (abv - 8.7) / 4.5;
        }

        double max = Math.max(Math.max(Math.max(Math.max(Muypoco, Poco), Moderado), Mucho),Muchisimo);
        if (max == Muypoco){ selectedAbv = "Muypoco"; }
        else if(max == Poco){ selectedAbv = "Poco";}
        else if(max == Moderado){ selectedAbv = "Moderado";}
        else if(max == Mucho){ selectedAbv = "Mucho";}
        else if(max == Muchisimo){ selectedAbv = "Muchísimo";}

        Muypoco = 0; Poco = 0; Moderado = 0; Mucho = 0; Muchisimo =0;

        //Muypoco IBU
        if (ibu >= 27){
            Muypoco = 0;
        } else if (ibu <= 15) {
            Muypoco = 1;
        } else {
            Muypoco = (27 - ibu) / 12;
        }

        //Poco IBU
        if (ibu >= 41 || ibu <= 15){
            Poco = 0;
        } else if (ibu >= 27 && ibu < 41) {
            Poco = (41 - ibu) / 14;
        } else if (ibu > 15 && ibu < 27){
            Poco = (ibu - 15) / 12;
        }

        //Moderado IBU
        if (ibu >= 60 || ibu <= 27) {
            Moderado = 0;
        } else if (ibu >= 41 && ibu < 60) {
            Moderado = (60 - ibu) / 19;
        } else if (ibu > 27 && ibu < 41) {
            Moderado = (ibu - 27) / 14;
        }

        //Mucho IBU
        if (ibu >= 90 || ibu <= 41) {
            Mucho = 0;
        } else if (ibu >= 60 && ibu < 90) {
            Mucho = (90 - ibu) / 30;
        } else if (ibu > 41 && ibu < 60) {
            Mucho = (ibu - 41) / 19;
        }

        //Muchisimo
        if (ibu >= 90){
            Muchisimo = 1;
        } else if (ibu <= 60) {
            Muchisimo = 0;
        } else {
            Muchisimo = (ibu - 60) / 30;
        }

        max = Math.max(Math.max(Math.max(Math.max(Muypoco, Poco), Moderado), Mucho),Muchisimo);
        if (max == Muypoco){ selectedIbu = "Muypoco"; }
        else if(max == Poco){ selectedIbu = "Poco";}
        else if(max == Moderado){ selectedIbu = "Moderado";}
        else if(max == Mucho){ selectedIbu = "Mucho";}
        else if(max == Muchisimo){ selectedIbu = "Muchísimo";}
    }

    public void SimilarSearchDone(Integer integer) {
        mySimilarClientTask.detach();
        Log.d(TAG, "Advanced search done");

        Intent intent = new Intent(BeerInfoActivity.this, BeersListActivity.class);
        intent.putExtra("beers", beers);
        String tipo = "advanced";
        intent.putExtra("tipo", tipo);
        startActivity(intent);
    }
}
