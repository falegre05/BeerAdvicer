package com.example.ferzi.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.PrefixManager;
//import org.semanticweb.owlapi.reasoner.OWLReasoner;
//import org.semanticweb.HermiT.Reasoner;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private final static String TAG = "MainActivity";

    private LoadOntTask myLoadTask = null;
    private SearchTask mySearchTask;


    private Spinner spinnerAbv;
    private Spinner spinnerIbu;
    private Spinner spinnerStyles;
    private String selectedAbv;
    private String selectedIbu;
    private String selectedStyle;

    public OWLDataFactory factory;
    public PrefixManager pm;
    //public Reasoner hermit;
    public ArrayList beers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.loading_screen);


        // Si no existe una AsyncTask la crea
        Log.d(TAG, "onCreate: About to create MyAsyncTask");
        myLoadTask = new LoadOntTask(this);
        Log.d(TAG, "lanzamos la tarea asincrona myLoadTask");
        myLoadTask.execute();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ontologyLoaded(0);
    }

    public void ontologyLoaded(Integer integer) {
        myLoadTask.detach();
        if (integer != -1)
            //Toast.makeText(MainActivity.this, "Codigo de respuesta: " + integer, Toast.LENGTH_LONG).show();
        Log.d(TAG, "Código de respuesta: " + integer);
        setContentView(R.layout.ontology_loaded);

        mySearchTask = new SearchTask(this);

        //Spinnner abv
        ArrayAdapter<CharSequence> adaptadorAbv = ArrayAdapter.createFromResource(this, R.array.etiquetas,
                android.R.layout.simple_spinner_item);
        spinnerAbv = (Spinner) findViewById(R.id.spinnerAbv);
        adaptadorAbv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAbv.setAdapter(adaptadorAbv);


        //Spinnner ibu
        ArrayAdapter<CharSequence> adaptadorIbu = ArrayAdapter.createFromResource(this, R.array.etiquetas,
                android.R.layout.simple_spinner_item);
        spinnerIbu = (Spinner) findViewById(R.id.spinnerIbu);
        adaptadorIbu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIbu.setAdapter(adaptadorIbu);

        //Spinnner Styles
        ArrayAdapter<CharSequence> adaptadorStyles = ArrayAdapter.createFromResource(this, R.array.beerStyles,
                android.R.layout.simple_spinner_item);
        spinnerStyles = (Spinner) findViewById(R.id.spinnerStyles);
        adaptadorStyles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStyles.setAdapter(adaptadorStyles);

        //Button Buscar
        Button buttonBuscar = (Button) findViewById(R.id.buttonBuscar);
        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedAbv = spinnerAbv.getSelectedItem().toString();
                selectedIbu = spinnerIbu.getSelectedItem().toString();
                selectedStyle = spinnerStyles.getSelectedItem().toString();
                Log.d(TAG, selectedAbv + selectedIbu + selectedStyle);

                // Obtener el valor devuelto en onRetainCustomNonConfigurationInstace
                Log.d(TAG, "lanzamos la tarea asincrona mySearchTask");
                mySearchTask.execute(selectedAbv, selectedIbu, selectedStyle);
                setContentView(R.layout.searching_screen);
            }
        });
    }

    public void searchDone(Integer integer) {
        mySearchTask.detach();

        Intent intent = new Intent(MainActivity.this, SearchBeersActivity.class);
        intent.putExtra("beers", beers);
        startActivity(intent);
    }
}
