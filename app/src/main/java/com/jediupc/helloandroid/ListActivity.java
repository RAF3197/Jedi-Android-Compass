package com.jediupc.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.jediupc.helloandroid.musicplayer.MusicActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    final ArrayList<MenuItem> myDataset = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setTitle(R.string.title_activity_contenido);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        myDataset.add(new MenuItem("LEVEL",LevelActivity.class));
        myDataset.add(new MenuItem("MAIN",MainActivity.class));
        myDataset.add(new MenuItem("Music",MusicActivity.class));
        myDataset.add(new MenuItem("Compass",Compass.class));

        mAdapter = new MyAdapter(myDataset, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Log.d("ListActivity","Click item "+ String.valueOf(pos));
                MenuItem i = myDataset.get(pos);
                startActivity(i);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void startActivity(MenuItem aux) {
        Intent i = new Intent(ListActivity.this,aux.cls);
        startActivity(i);
    }
}
