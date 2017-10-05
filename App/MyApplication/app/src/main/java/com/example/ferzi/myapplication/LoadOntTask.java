package com.example.ferzi.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import java.io.InputStream;

/**
 * Created by ferzi on 30/08/2017.
 */

class LoadOntTask extends AsyncTask<String, Void, Integer> {

    private final String TAG = "LoadOntTask";

    private Context mContext;

    public OWLDataFactory factory;
    public PrefixManager pm;
    public OWLReasoner hermit;

    private MainActivity mActivity = null;
    public LoadOntTask(MainActivity activity) {
        attach(activity);
        mContext = activity;
    }

    @Override
    protected Integer doInBackground(String... params) {
        IRI ontologyIRI = IRI.create("http://beerOntology.es/");
        // First, we create an OWLOntologyManager object. The manager will load and save ontologies.
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        // We use the OWL API to load the ontology.
        InputStream ontology = mContext.getApplicationContext().getResources().openRawResource(R.raw.ontology_fuzzy);

        try {
            OWLOntology ont = manager.loadOntologyFromOntologyDocument(ontology);
            OWLDataFactory factory = manager.getOWLDataFactory();
            PrefixManager pm = new DefaultPrefixManager(ontologyIRI.toString());
            Log.d(TAG, "ONTOLOGIA CARGADA CORRECTAMENTE");
            // Now, we instantiate HermiT by creating an instance of the Reasoner class in the package org.semanticweb.HermiT.
            OWLReasoner hermit = new Reasoner.ReasonerFactory().createReasoner(ont);
            // Finally, we output whether the ontology is consistent.
            Log.d(TAG, "RAZONADOR CARGADO CORRECTAMENTE");

            /*if (hermit.isConsistent()){
                Log.d(TAG, "Es consistente");
            } else {
                Log.d(TAG, "No es consistente");
            }*/

            mActivity.factory = factory;
            mActivity.pm = pm;
            mActivity.hermit = hermit;
            return 0;
        }
        catch(OWLOntologyCreationException e){
            e.getStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        if (mActivity == null)
            Log.d(TAG, "Me salto onPostExecute() -- no hay nueva activity");
        else
            mActivity.ontologyLoaded(integer);
    }

    void attach(MainActivity activity) {
        this.mActivity = activity;
    }

    void detach() {
        this.mActivity = null;
    }
}
