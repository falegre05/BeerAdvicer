package com.example.ferzi.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ferzi on 19/09/2017.
 */

public class DownloadImage extends AsyncTask<Beer, Void, Integer> {

    private Context mContext;
    Beer beer;
    Bitmap bmp;

    private BeerInfoActivity mActivity = null;

    public DownloadImage(BeerInfoActivity activity) {
        attach(activity);
        mContext=activity;
    }

    @Override
    protected Integer doInBackground(Beer... params) {
        beer = params[0];
        URL url = null;
        try {
            url = new URL(beer.getImg());
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Integer integer) {
        mActivity.loadBeerInfo(beer, bmp);
    }

    void detach()
    {
        this.mActivity = null;
    }
    void attach(BeerInfoActivity activity)
    {
        this.mActivity = activity;
    }
}
