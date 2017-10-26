package com.example.ferzi.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private final static String TAG = "MainActivity";

    private AdvancedClientTask myAdvancedClientTask;
    private BasicClientTask myBasicClientTask;


    private Spinner spinnerAbv;
    private Spinner spinnerIbu;
    private Spinner spinnerStyles;
    private Spinner spinnerProperties;
    private String selectedAbv;
    private String selectedIbu;
    private String selectedStyle;
    private String selectedProperty;
    private RadioButton cerveza;
    private RadioButton cerveceria;
    private EditText editQuery;

    public ArrayList beers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        loadMainScreen();
    }

    protected void loadMainScreen() {

        setContentView(R.layout.main_screen);

        ImageView image = (ImageView) findViewById(R.id.image);
        image.setImageResource(R.drawable.beers);

        //Button Buscar Personalizada
        Button buttonPersonalizada = (Button) findViewById(R.id.buttonPersonalizada);
        buttonPersonalizada.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                customSearch();
            }
        });

        //Button Buscar Básica
        Button buttonSimple = (Button) findViewById(R.id.buttonSimple);
        buttonSimple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                normalSearch();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadMainScreen();
    }

    public void customSearch() {
        myAdvancedClientTask = new AdvancedClientTask(this);

        setContentView(R.layout.custom_search);

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


                Log.d(TAG, "lanzamos la tarea asincrona myAdvancedClientTask");
                myAdvancedClientTask.execute(selectedAbv, selectedIbu, selectedStyle, selectedProperty);


                setContentView(R.layout.searching_screen);
            }
        });
    }

    public void normalSearch() {

        setContentView(R.layout.normal_search);

        cerveza = (RadioButton)findViewById(R.id.radio_cerveza);
        cerveceria = (RadioButton)findViewById(R.id.radio_cerveceria);
        myBasicClientTask = new BasicClientTask(this);
        editQuery = (EditText)findViewById((R.id.editQuery));


        //Button Buscar Personalizada
        Button buttonBuscar = (Button) findViewById(R.id.buttonSearch);
        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String busqueda = String.valueOf(editQuery.getText());
                if (!busqueda.isEmpty()){
                    if (cerveza.isChecked()){
                        Log.d(TAG, "cerveza" + busqueda);
                        Log.d(TAG, "lanzamos la tarea asincrona myBasicClientTask");
                        myBasicClientTask.execute("cerveza", busqueda.replace(" ", "_"));
                        setContentView(R.layout.searching_screen);
                    } else{
                        Log.d(TAG, "cerveceria" + busqueda);
                        Log.d(TAG, "lanzamos la tarea asincrona myBasicClientTask");
                        myBasicClientTask.execute("cerveceria", busqueda.replace(" ", "_"));
                        setContentView(R.layout.searching_screen);
                    }
                }
            }
        });
    }

    public void advancedSearchDone(Integer integer) {
        myAdvancedClientTask.detach();
        Log.d(TAG, "Advanced search done");

        Intent intent = new Intent(MainActivity.this, SearchBeersActivity.class);
        intent.putExtra("beers", beers);
        startActivity(intent);
    }

    public void basicSearchDone(Integer integer) {
        myBasicClientTask.detach();
        Log.d(TAG, "Basic search done");

        Intent intent = new Intent(MainActivity.this, SearchBeersActivity.class);
        intent.putExtra("beers", beers);
        startActivity(intent);
    }
}
