package com.dotplays.demoassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.dotplays.demoassignment.adapter.PhotoAdapter;
import com.dotplays.demoassignment.model.Fave;
import com.dotplays.demoassignment.model.Photo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView lvList;
    private List<Photo> photos;
    private PhotoAdapter photoAdapter;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvList = findViewById(R.id.lvList);
        photos = new ArrayList<>();
        photoAdapter = new PhotoAdapter(this, photos);
        gridLayoutManager = new GridLayoutManager(this, 2);
        lvList.setAdapter(photoAdapter);
        lvList.setLayoutManager(gridLayoutManager);
        loadPhotos();
    }


    public void loadPhotos() {

        AndroidNetworking.post("https://www.flickr.com/services/rest/")
                .addBodyParameter("method", "flickr.favorites.getList")
                .addBodyParameter("api_key", "Your api key")
                .addBodyParameter("user_id", "your user id")
                .addBodyParameter("format", "json")
                .addBodyParameter("nojsoncallback", "1")
                .addBodyParameter("extras", "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o")
                .addBodyParameter("per_page", "10")
                .addBodyParameter("page", "1")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(Fave.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        Fave fave = (Fave) response;
                        List<Photo> photos = fave.getPhotos().getPhoto();

                        MainActivity.this.photos.addAll(photos);
                        photoAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
