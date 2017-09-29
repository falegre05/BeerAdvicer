package com.example.ferzi.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * Created by ferzi on 27/09/2017.
 */

public class SearchBeersActivity extends Activity {

    private final static String TAG = "SearchBeersActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        setContentView(R.layout.beers_list);

        Intent i = getIntent();
        ArrayList beers = (ArrayList) i.getSerializableExtra("beers");
        if (!beers.isEmpty()) {
            ListViewAdapter listAdapter = new ListViewAdapter(this, beers);
            final ListView listView = (ListView) findViewById(R.id.beers_listView);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Beer beer = (Beer) listView.getItemAtPosition(i);

                    Intent intent = new Intent(SearchBeersActivity.this, BeerInfoActivity.class);
                    intent.putExtra("beer", beer);
                    startActivity(intent);
                }
            });
        } else {
            setContentView((R.layout.no_results));
        }

    }
}
