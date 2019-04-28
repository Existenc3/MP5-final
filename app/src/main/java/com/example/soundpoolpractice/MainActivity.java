package com.example.soundpoolpractice;

import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SoundPool soundPool;
    private int airhorn, john_cena, leeroy_jenkins, legitness, mission_failed,
            rick_astley, wow;
    private List<Integer> soundPoolIds = new ArrayList<>();
    private List<Integer> buttonNames = new ArrayList<>();

    //toTest change maxStreams
    // 13-14 Seems to be the max amount that doesn't cause audioflinger errors
    private int maxStreams = 13;
    //toTest change rate
    private float rate = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // toTest change usages.
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
            .setMaxStreams(maxStreams)
            .setAudioAttributes(audioAttributes)
            .build();
        } else {
            soundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
        }

//        airhorn = soundPool.load(this, R.raw.airhorn, 1);
//        john_cena = soundPool.load(this, R.raw.john_cena, 1);
//        leeroy_jenkins = soundPool.load(this, R.raw.leeroy_jenkins, 1);
//        legitness = soundPool.load(this, R.raw.legitness, 1);
//        mission_failed = soundPool.load(this, R.raw.mission_failed, 1);
//        rick_astley = soundPool.load(this, R.raw.rick_astley, 1);
//        wow = soundPool.load(this, R.raw.wow, 1);

//        soundPoolIds.add(soundPool.load(this, R.raw.airhorn, 1));
//        soundPoolIds.add(soundPool.load(this, R.raw.john_cena, 1));
//        soundPoolIds.add(soundPool.load(this, R.raw.leeroy_jenkins, 1));
//        soundPoolIds.add(soundPool.load(this, R.raw.legitness, 1));
//        soundPoolIds.add(soundPool.load(this, R.raw.mission_failed, 1));
//        soundPoolIds.add(soundPool.load(this, R.raw.rick_astley, 1));
//        soundPoolIds.add(soundPool.load(this, R.raw.wow, 1));

        //toTest remove later
        assetsToSoundPoolIds();
    }

    public void playSound(View v) {
        switch (v.getId()) {
            case R.id.airhorn:
                soundPool.play(soundPoolIds.get(0), 1, 1, 0, 0, rate);
                break;
            case R.id.john_cena:
                soundPool.play(soundPoolIds.get(1), 1, 1, 0, 0, rate);
                break;
            case R.id.leeroy_jenkins:
                soundPool.play(soundPoolIds.get(2), 1, 1, 0, 0, rate);
                break;
            case R.id.legitness:
                soundPool.play(soundPoolIds.get(3), 1, 1, 0, 0, rate);
                break;
            case R.id.mission_failed:
                soundPool.play(soundPoolIds.get(4), 1, 1, 0, 0, rate);
                break;
            case R.id.rick_astley:
                soundPool.play(soundPoolIds.get(5), 1, 1, 0, 0, rate);
                break;
            case R.id.wow:
                soundPool.play(soundPoolIds.get(6), 1, 1, 0, 0, rate);
                break;
        }
    }

    public void stopSounds(View v) {
        soundPool.autoPause();
        for (int id : soundPoolIds) {
            Log.d("stopSounds","stopping id: " + id);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        soundPool = null;
    }

    protected void assetsToSoundPoolIds() {
        final AssetManager mgr = getAssets();
        try {
            //String[] files = mgr.list("");
            String[] folders = mgr.list("");

            //region data validation
            if (folders == null) {
                Log.d("Assets", "folders null");
                return;
            }
            if (folders.length == 0) {
                Log.d("Assets", "folders has no length");
                return;
            }
            //endregion

            for (int i = 0; i  < folders.length; i++) {
                String[] files = mgr.list(folders[i]);

                //region data validation
                if (files == null) {
                    Log.d("Assets", "files null");
                    return;
                }
                if (files.length == 0) {
                    Log.d("Assets", "files has no length");
                    return;
                }
                //endregion

                for (int j = 0; j < files.length; j++) {
                    if (files[j].substring(files[j].length() - 3).equals("mp3")) {
                        soundPoolIds.add(soundPool.load(mgr.openFd(files[j]), 1));
                        Log.d("Assets", "file Loaded: " + files[j]);
                    }
                    System.out.println("File: " + files[j]);
                }
            }
//            String[] subfiles = mgr.list(files[0]);
//            for (String s : files) {
//                System.out.println("file: " + s);
//            }
//            System.out.println(subfiles[0]);

//            if (files == null) {
//                Log.d("Assets", "files null");
//                return;
//            }
//            if (files.length == 0) {
//                Log.d("Assets", "files has no length");
//                return;
//            }
//
//            for (int i = 0; i < files.length; i++) {
//                if (files[i].substring(files[i].length() - 3).equals("mp3")) {
//                    soundPoolIds.add(soundPool.load(mgr.openFd(files[i]), 1));
//                    Log.d("Assets", "file Loaded: " + files[i]);
//                }
//            }
        } catch (IOException e) {
            Log.d("List error:", "can't list assets");
        }
    }
}
