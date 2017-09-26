package com.example.ferzi.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by ferzi on 06/09/2017.
 */

class SearchTask extends AsyncTask<String, Void, Integer> {

    private final String TAG = "SearchTask";

    private Context mContext;

    private OWLDataFactory factory;
    private PrefixManager pm;
    private OWLReasoner hermit;
    private String selectedAbv;
    private String selectedIbu;
    private String selectedStyle;
    private ArrayList beers;

    private MainActivity mActivity = null;
    public SearchTask(MainActivity activity) {
        attach(activity);
        mContext = activity;
    }

    @Override
    protected Integer doInBackground(String... params) {
        factory = mActivity.factory;
        pm = mActivity.pm;
        hermit = mActivity.hermit;
        selectedAbv = params[0];
        selectedIbu = params[1];
        selectedStyle = params[2].replace(" ", "_");

        OWLDataProperty ABV = factory.getOWLDataProperty("ABV", pm);
        OWLDataProperty IBU = factory.getOWLDataProperty("IBU", pm);
        OWLDataProperty IMG = factory.getOWLDataProperty("img", pm);
        OWLDataProperty RATING = factory.getOWLDataProperty("style_rating", pm);
        OWLDataProperty BREW = factory.getOWLDataProperty("brewery", pm);

        //COGER TODAS LAS CERVEZAS DE UN ESTILO
        OWLClass style = factory.getOWLClass(selectedStyle, pm);
        Log.d(TAG, style.toString());
        NodeSet<OWLNamedIndividual> lista = hermit.getInstances(style, false);
        Iterator iter = lista.getFlattened().iterator();
        String name, abv, ibu, img, style_rating, brewery;
        double func_pertenencia;
        Beer beer;
        beers = new ArrayList();
        while(iter.hasNext()) {
            OWLNamedIndividual ind = (OWLNamedIndividual) iter.next();

            name = ind.toString().substring(24, ind.toString().indexOf(">")).replace("_", " ");
            abv = String.valueOf(hermit.getDataPropertyValues(ind, ABV));
            abv = abv.substring(2, abv.indexOf("\"", 2));
            ibu = String.valueOf(hermit.getDataPropertyValues(ind, IBU));
            img = String.valueOf(hermit.getDataPropertyValues(ind, IMG));
            img = img.substring(2, img.indexOf("\"", 2));
            style_rating = String.valueOf(hermit.getDataPropertyValues(ind, RATING));
            style_rating = style_rating.substring(2, style_rating.indexOf("\"", 2));
            brewery = String.valueOf(hermit.getDataPropertyValues(ind, BREW));
            brewery = brewery.substring(2, brewery.indexOf("\"", 2));
            if(ibu.length() > 3) {
                ibu = ibu.substring(2, ibu.indexOf("\"", 2));
            } else {
                ibu = "0";
            }
            func_pertenencia = funcion(selectedAbv, selectedAbv, Double.valueOf(abv), Double.valueOf(ibu));
            beer = new Beer(name, abv, ibu, img, Integer.valueOf(style_rating), func_pertenencia, brewery);
            beers.add(beer);
            Log.d(TAG, name + " " + abv + " " + ibu + " " + img + " " + style_rating + " " + brewery);
        }

        Collections.sort(beers);
        return 0;
    }

    protected void onPostExecute(Integer integer) {
        mActivity.beers = beers;
        mActivity.searchDone(integer);
    }

    private double funcion (String tagAbv, String tagIbu, double abv, double ibu) {
        double meanAbv = 0, meanIbu = 0;
        switch (tagAbv){
            case "MuyPoco":
                if (abv >= 5){
                    meanAbv = 0;
                } else if (abv <= 3.6) {
                    meanAbv = 1;
                } else {
                    meanAbv = (5 - abv) / 1.4;
                }
                break;
            case "Poco":
                if (abv >= 6.55 || abv <= 3.6){
                    meanAbv = 0;
                } else if (abv >= 5 && abv < 6.55) {
                    meanAbv = (6.55 - abv)/1.55;
                } else if (abv > 3.6 && abv < 5){
                    meanAbv = (abv - 3.6) / 1.4;
                }
                break;
            case "Moderado":
                if (abv >= 8.7 || abv <= 5) {
                    meanAbv = 0;
                } else if (abv >= 6.55 && abv < 8.7) {
                    meanAbv = (8.7 - abv) / 2.15;
                } else if (abv > 5 && abv < 6.55) {
                    meanAbv = (abv - 5) / 1.55;
                }

                break;
            case "Mucho":
                if (abv >= 13.2 || abv <= 6.55) {
                    meanAbv = 0;
                } else if (abv >= 8.7 && abv < 13.2) {
                    meanAbv = (13.2 - abv) / 4.5;
                } else if (abv > 6.55 && abv < 8.7) {
                    meanAbv = (abv - 6.55) / 2.15;
                }
                break;
            case "Muchisimo":
                if (abv >= 13.2){
                    meanAbv = 1;
                } else if (abv <= 8.7) {
                    meanAbv = 0;
                } else {
                    meanAbv = (abv - 8.7) / 4.5;
                }
                break;
        }
        if (ibu > 0) {
            switch (tagIbu){
                case "MuyPoco":
                    if (ibu >= 27){
                        meanIbu = 0;
                    } else if (ibu <= 15) {
                        meanIbu = 1;
                    } else {
                        meanIbu = (27 - ibu) / 12;
                    }
                    break;
                case "Poco":
                    if (ibu >= 41 || ibu <= 15){
                        meanIbu = 0;
                    } else if (ibu >= 27 && ibu < 41) {
                        meanIbu = (41 - ibu) / 14;
                    } else if (ibu > 15 && ibu < 27){
                        meanIbu = (ibu - 15) / 12;
                    }
                    break;
                case "Moderado":
                    if (ibu >= 60 || ibu <= 27) {
                        meanIbu = 0;
                    } else if (ibu >= 41 && ibu < 60) {
                        meanIbu = (60 - ibu) / 19;
                    } else if (ibu > 27 && ibu < 41) {
                        meanIbu = (ibu - 27) / 14;
                    }

                    break;
                case "Mucho":
                    if (ibu >= 90 || ibu <= 41) {
                        meanIbu = 0;
                    } else if (ibu >= 60 && ibu < 90) {
                        meanIbu = (90 - ibu) / 30;
                    } else if (ibu > 41 && ibu < 60) {
                        meanIbu = (ibu - 41) / 19;
                    }
                    break;
                case "Muchisimo":
                    if (ibu >= 90){
                        meanIbu = 1;
                    } else if (ibu <= 60) {
                        meanIbu = 0;
                    } else {
                        meanIbu = (ibu - 60) / 30;
                    }
                    break;
            }
            Log.d(TAG, "El valor dela funcion de pertenencia es: " + (meanAbv + meanIbu)/2);
            return (meanAbv + meanIbu)/2;
        }
        Log.d(TAG, "El valor dela funcion de pertenencia es: " + meanAbv);
        return meanAbv;
    }

    void attach(MainActivity activity) {
        this.mActivity = activity;
    }

    void detach() {
        this.mActivity = null;
    }
}
