package com.erick.playaudiotest;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnPlay;
    private Button btnPause;
    private Button btnStop;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPause = (Button) findViewById(R.id.btnPause);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnStop = (Button) findViewById(R.id.btnStop);

        btnPause.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        } else {
            initPlayer();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initPlayer();
                } else {
                    Toast.makeText(this,"You denied Permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private void initPlayer() {
        try {
            player = new MediaPlayer();
           // File file = Environment.getExternalStorageDirectory();
            //String path = file.getPath()+"/Music/abc.mp3";
            String path = "/storage/emulated/0/MIUI/ringtone/abc.mp3";

            Log.d("wyg","filePath:" + path);
            player.setDataSource(path);
            player.prepare();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnPlay:
                if (!player.isPlaying()){
                    player.start();
                    Log.d("wyg","start:");
                }
                break;
            case R.id.btnPause:
                if (player.isPlaying()){
                    player.pause();
                    Log.d("wyg","pause:");
                }
                break;
            case R.id.btnStop:
                if (player.isPlaying()){
                    player.reset();
                    Log.d("wyg","stop:");

                    initPlayer();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(player != null) {
            player.stop();
            player.release();
        }
    }
}
