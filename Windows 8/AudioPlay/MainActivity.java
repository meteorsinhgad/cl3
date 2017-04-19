package com.example.nero.audiofiles;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    // variable declaration
    private ListView mainList;
    private MediaPlayer mp;
    private final String[] listContent = { "song1", "song2", "song3", "song4" };
    private final int[] resID = { R.raw.k, R.raw.m, R.raw.s, R.raw.f };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initializing variables
        mp = new MediaPlayer();
        mainList = (ListView) findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listContent);
        mainList.setAdapter(adapter);
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                playSong(position);
            }
        });
    }
    public void playSong(int songIndex) {
        // Play song
        mp.reset();// stops any current playing song
        mp = MediaPlayer.create(getApplicationContext(), resID[songIndex]);
        // create's new media player with songs..
        mp.start();
        // starting mediaplayer
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.release();
    }


}
