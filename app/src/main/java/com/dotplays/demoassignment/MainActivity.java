package com.dotplays.demoassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

    private int page = 1;

    private SwipeRefreshLayout swipeLayout;

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
        loadPhotos(page);
        swipeLayout = findViewById(R.id.swipeLayout);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MainActivity.this.page = 1;
                photos.clear();
                loadPhotos(MainActivity.this.page);
            }
        });


        lvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                MainActivity.this.page++;
                loadPhotos(MainActivity.this.page++);
            }
        });
    }


    public void loadPhotos(int page) {

        AndroidNetworking.post("https://www.flickr.com/services/rest/")
                .addBodyParameter("method", "flickr.favorites.getList")
                .addBodyParameter("api_key", "24bf810575bc5bfbe2aef1ed6cd4517b")
                .addBodyParameter("user_id", "184057905@N03")
                .addBodyParameter("format", "json")
                .addBodyParameter("nojsoncallback", "1")
                .addBodyParameter("extras", "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o")
                .addBodyParameter("per_page", "10")
                .addBodyParameter("page", String.valueOf(page))
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(Fave.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        // tat su kien loading
                        swipeLayout.setRefreshing(false);
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
