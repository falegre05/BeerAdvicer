package com.example.ferzi.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private final static String TAG = "MainActivity";

    private ClientTask myClientTask;


    private Spinner spinnerAbv;
    private Spinner spinnerIbu;
    private Spinner spinnerStyles;
    private Spinner spinnerProperties;
    private String selectedAbv;
    private String selectedIbu;
    private String selectedStyle;
    private String selectedProperty;

    public ArrayList beers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        ontologyLoaded(0);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ontologyLoaded(0);
    }

    public void ontologyLoaded(Integer integer) {
        myClientTask = new ClientTask(this);

        if (integer != -1)
            //Toast.makeText(MainActivity.this, "Codigo de respuesta: " + integer, Toast.LENGTH_LONG).show();
        Log.d(TAG, "Código de respuesta: " + integer);
        setContentView(R.layout.ontology_loaded);

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


        //Spinnner Properties
        ArrayAdapter<CharSequence> adaptadorProperties = ArrayAdapter.createFromResource(this, R.array.beerProperties,
                android.R.layout.simple_spinner_item);
        spinnerProperties = (Spinner) findViewById(R.id.spinnerProperties);
        adaptadorStyles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProperties.setAdapter(adaptadorProperties);

        //Button Buscar
        Button buttonBuscar = (Button) findViewById(R.id.buttonBuscar);
        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedAbv = spinnerAbv.getSelectedItem().toString();
                selectedIbu = spinnerIbu.getSelectedItem().toString();
                selectedStyle = spinnerStyles.getSelectedItem().toString();
                selectedProperty = spinnerProperties.getSelectedItem().toString();
                Log.d(TAG, selectedAbv + selectedIbu + selectedStyle + selectedProperty);


                Log.d(TAG, "lanzamos la tarea asincrona myClientTask");
                myClientTask.execute(selectedAbv, selectedIbu, selectedStyle, selectedProperty);


                setContentView(R.layout.searching_screen);
            }
        });
    }

    public void searchDone(Integer integer) {
        myClientTask.detach();
        Log.d(TAG, "Search done");

        Intent intent = new Intent(MainActivity.this, SearchBeersActivity.class);
        intent.putExtra("beers", beers);
        startActivity(intent);
    }
}
