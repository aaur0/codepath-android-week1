package com.walmartlabs.agupta.instagramclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "9a8bdf234ec44eebb4c47c0c99fd7cf5";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aphotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        // SEND OUT API REQUEST to get popular photos

        photos = new ArrayList<InstagramPhoto>();
        aphotos = new InstagramPhotosAdapter(this, photos);

        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(aphotos);

        fetchPopularPhotos();

    }

    public void fetchPopularPhotos(){
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray photosJSON = null;
                try {
                    photosJSON = response.getJSONArray("data"); //array of posts
                    // iterate arrays of posts
                    for(int i=0; i< photosJSON.length();i++){
                        //get the json object
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        // decode the attribye into a data model

                        InstagramPhoto photo = new InstagramPhoto();
                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        photo.caption =  photoJSON.getJSONObject("caption").getString("text");
                        photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                        photo.imageURL = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photos.add(photo);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
                aphotos.notifyDataSetChanged();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i("ERROR", errorResponse.toString());

            }
        });



    }


}
