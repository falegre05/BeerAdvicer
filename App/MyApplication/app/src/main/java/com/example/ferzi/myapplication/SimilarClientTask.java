package com.example.ferzi.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by ferzi on 06/10/2017.
 */

public class SimilarClientTask extends AsyncTask<String, Void, Integer> {

    private final static String TAG = "SimilarClientTask";

    private static ArrayList<Beer> beers;

    private String selectedAbv;
    private String selectedIbu;
    private String selectedStyle;
    private String selectedProperty;

    // Para almacenar la dirección y número de puerto donde escucha el servidor
    //static private String SERVER_ADDRESS = "192.168.1.33";  //CASA
    //static private String SERVER_ADDRESS = "81.37.241.232";  //CASA GLOBAL
    //static private String SERVER_ADDRESS = "54.93.186.30";  //AWS HOST 1
    static private String SERVER_ADDRESS = "35.176.203.138";  //AWS HOST 2
    static private int SERVER_PORT = 2000;

    // Creación del socket con el que se llevará a cabo
    // la comunicación con el servidor.
    static private Socket socketAlServidor = null;


    private BeerInfoActivity mActivity = null;
    public SimilarClientTask(BeerInfoActivity activity) {
        this.mActivity = activity;
    }


    @Override
    protected Integer doInBackground(String... params) {
        boolean exito; //¿conectado?

        exito = conectarServidor(10); //10 intentos

        if(!exito){
            Log.d(TAG, "Don't know about host:"
                    + SERVER_ADDRESS);
            System.exit(1); //abortar si hay problemas
        }


        // Ya hay conexíón
        // Inicialización de los flujos de datos del socket
        // para la comunicación con el servidor

        PrintWriter canalSalidaAlServidor = null;
        BufferedReader canalEntradaDelServidor = null;
        try {
            canalSalidaAlServidor = new PrintWriter(
                    socketAlServidor.getOutputStream(),
                    true
            );
            canalEntradaDelServidor = new BufferedReader(
                    new InputStreamReader(
                            socketAlServidor.getInputStream()
                    )
            );
        } catch (IOException e) { //abortar si hay problemas
            Log.d(TAG, "I/O problem:" + SERVER_ADDRESS);
            System.exit(1);
        }

        // Definición de un buffer de entrada para leer
        // de la entrada standard.
        BufferedReader entradaStandard = new BufferedReader(
                new InputStreamReader(System.in));

        // Protocolo de comunicación con el Servidor.
        // Mientras no se reciba la secuencia
        // "END OF SERVICE"el servidor contará el número
        // de vocales que aparecen en las frases que le
        // envía el cliente. El cliente obtiene las frases
        // que le pasa al servidordel usuario que lo
        // está ejecutando.

        selectedAbv = params[0].replace(" ", "");
        selectedIbu = params[1].replace(" ", "");
        selectedStyle = params[2].replace(" ", "_");
        selectedProperty = params[3];
        String query = selectedAbv + " " + selectedIbu + " " + selectedStyle + " " + selectedProperty;
        Log.d(TAG, query);
        try{
            canalSalidaAlServidor.println(query);
            String respuesta = canalEntradaDelServidor.readLine();
            respuesta = respuesta.replace("[","").replace("]", "");
            Log.d(TAG, "Respuesta del servidor: "+ respuesta);
            beers = new ArrayList<Beer>();
            if (respuesta.length() > 5) {
                String tablaBeers[] = respuesta.split(Pattern.quote("},"));
                Beer beer;
                for (int i = 0; i < tablaBeers.length; i++){
                    beer = toBeer(tablaBeers[i]);
                    beers.add(beer);
                    Log.d(TAG, beer.toString());
                }
            } else {
                Log.d(TAG, "Comm. is closed!");
            }

            // Al cerrar cualquiera de los canales de
            // comunicación usados por un socket, el socket
            // se cierra. Como no nos importa perder información
            // cerramos el canal de entrada.
            canalEntradaDelServidor.close();

            // Cierre del Socket para comunicarse con el servidor.
            socketAlServidor.close();
        } catch (Exception e){
            Log.d(TAG, e.toString());
        }
        return null;
    }

    protected void onPostExecute(Integer integer) {
        mActivity.beers = beers;
        mActivity.SimilarSearchDone(integer);
    }

    static private boolean conectarServidor(int maxIntentos){
        //hasta maxIntentos intentos de conexión, para
        //darle tiempo al servidor a arrancar
        boolean exito = false; //¿hay servidor?
        int van = 0;

        while((van<maxIntentos) && !exito){
            try {
                socketAlServidor = new Socket(SERVER_ADDRESS, SERVER_PORT);
                exito = true;
            } catch (Exception e) {
                van++;
                Log.d(TAG, "Failures:" + van);
            }
        }
        return exito;
    }

    public static Beer toBeer(String s){
        int n = s.indexOf("name")+6;
        String name = s.substring(n, s.indexOf("'", n));

        int a = s.indexOf("abv")+5;
        String abv = s.substring(a, s.indexOf("'", a));

        int i = s.indexOf("ibu")+5;
        String ibu = s.substring(i, s.indexOf("'", i));

        int im = s.indexOf("img")+5;
        String img = s.substring(im, s.indexOf("'", im));

        int st = s.indexOf("style_rating")+13;
        String style_rating = s.substring(st, s.indexOf(",", st));

        int f = s.indexOf("func_pertenencia")+17;
        String func_pertenencia = s.substring(f, s.indexOf(",", f));

        int b = s.indexOf("brewery")+9;
        String brewery = s.substring(b, s.indexOf("'", b));

        int bs = s.indexOf("beerStyle")+11;
        String beerStyle = s.substring(bs, s.indexOf("'", bs));

        return new Beer(name, abv, ibu, img, Integer.valueOf(style_rating), Double.valueOf(func_pertenencia), brewery, beerStyle);
    }

    void detach() {
        this.mActivity = null;
    }
}
