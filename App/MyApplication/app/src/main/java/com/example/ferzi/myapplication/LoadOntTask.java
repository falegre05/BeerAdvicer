package com.example.ferzi.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import java.io.InputStream;
import java.util.Iterator;

import org.mindswap.pellet.jena.PelletReasonerFactory;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

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

    // HERMIT
    @Override
    /*protected Integer doInBackground(String... params) {
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

            //OWLReasoner hermit = new Reasoner.ReasonerFactory().createReasoner(ont);
            Reasoner hermit = new Reasoner(ont);


            Log.d(TAG, "RAZONADOR CARGADO CORRECTAMENTE");



            mActivity.factory = factory;
            mActivity.pm = pm;
            mActivity.hermit = hermit;
            return 0;
        }
        catch(OWLOntologyCreationException e){
            e.getStackTrace();
        }
        return null;
    }*/
    //PELLET
    protected Integer doInBackground(String... params) {
        Log.d("PELLET", "pellet lanzado");
        // We use the OWL API to load the ontology.
        //InputStream ontology = mContext.getApplicationContext().getResources().openRawResource(R.raw.ontology_fuzzy);

        String ont = "http://www.mindswap.org/2004/owl/mindswappers#";
        // load the ontology with its imports and no reasoning
        OntModel model = ModelFactory.createOntologyModel( PelletReasonerFactory.THE_SPEC );
        Log.d("PELLET", "Hello" + String.valueOf(model));
        model.read( ont );
        Log.d("PELLET", String.valueOf(model));
        // load the model to the reasoner
        model.prepare();

        // create property and resources to query the reasoner
        OntClass Person = model.getOntClass("http://xmlns.com/foaf/0.1/Person");
        Property workHomepage = model.getProperty("http://xmlns.com/foaf/0.1/workInfoHomepage");
        Property foafName = model.getProperty("http://xmlns.com/foaf/0.1/name");

        // get all instances of Person class
        Iterator<?> i = Person.listInstances();
        while( i.hasNext() ) {
            Individual ind = (Individual) i.next();

            // get the info about this specific individual
            String name = ((Literal) ind.getPropertyValue( foafName )).getString();
            Resource type = ind.getRDFType();
            Resource homepage = (Resource) ind.getPropertyValue(workHomepage);

            // print the results
            Log.d("PELLET", "Name: " + name);
            Log.d("PELLET", "Type: " + type.getLocalName());
            if(homepage == null)
                Log.d("PELLET", "Homepage: Unknown");
            else
                Log.d("PELLET", "Homepage: " + homepage);
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
