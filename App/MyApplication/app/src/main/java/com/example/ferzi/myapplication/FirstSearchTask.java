package com.example.ferzi.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 * Created by ferzi on 05/10/2017.
 */

class FirstSearchTask extends AsyncTask<String, Void, Integer> {

    private final String TAG = "FirstSearchTask";

    private OWLDataFactory factory;
    private PrefixManager pm;
    private OWLReasoner hermit;

    private MainActivity mActivity;
    public FirstSearchTask(MainActivity activity) {
        mActivity = activity;
    }

    @Override
    protected Integer doInBackground(String... params) {
        factory = mActivity.factory;
        pm = mActivity.pm;
        hermit = mActivity.hermit;

        OWLClass style = factory.getOWLClass("Ale", pm);
        NodeSet<OWLNamedIndividual> lista = hermit.getInstances(style, false);
        Log.d(TAG, "Done");

        return null;
    }

    protected void onPostExecute(Integer integer) {
        mActivity.hermit = hermit;
        mActivity.firstSearchDone(integer);
    }


    void detach() {
        this.mActivity = null;
    }
}
