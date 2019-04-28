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
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SoundPool soundPool;

    private List<Sound> sounds = new ArrayList<>();
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

        //toTest remove later
        assetsToSoundPoolIds();
    }

    public void playSound(View v) {
        switch (v.getId()) {
            case R.id.airhorn:
                soundPool.play(sounds.get(0).getSoundPoolId(), 1, 1, 0, 0, rate);
                break;
            case R.id.john_cena:
                soundPool.play(sounds.get(1).getSoundPoolId(), 1, 1, 0, 0, rate);
                break;
            case R.id.leeroy_jenkins:
                soundPool.play(sounds.get(2).getSoundPoolId(), 1, 1, 0, 0, rate);
                break;
            case R.id.legitness:
                soundPool.play(sounds.get(3).getSoundPoolId(), 1, 1, 0, 0, rate);
                break;
            case R.id.mission_failed:
                soundPool.play(sounds.get(4).getSoundPoolId(), 1, 1, 0, 0, rate);
                break;
            case R.id.rick_astley:
                soundPool.play(sounds.get(5).getSoundPoolId(), 1, 1, 0, 0, rate);
                break;
            case R.id.wow:
                soundPool.play(sounds.get(6).getSoundPoolId(), 1, 1, 0, 0, rate);
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
                System.out.println("Folder: " + folders[i]);
                String[] files = mgr.list(folders[i]);
                System.out.println("Folder listed");

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

                Sound toAdd = new Sound();

                for (int j = 0; j < files.length; j++) {
                    System.out.println("1 File: " + files[j]);
                    String directory = folders[i] + "/" + files[j];
                    if (files[j].substring(files[j].length() - 3).equals("mp3")) {
                        toAdd.setSoundPoolId(soundPool.load(mgr.openFd(directory), 1));
                        sounds.add(toAdd);
                        Log.d("Assets", "file Loaded: " + files[j]);
                    } else if (files[j].substring(files[j].length() - 3).equals("txt")) {
                        //toTest if condition might give false negatives
                        String name = txtToString(directory);
                        System.out.println("name from txt file: " + name);
                        toAdd.setName(name);
                    }
                }
            }
        } catch (IOException e) {
            Log.e("List error:", "can't list assets");
            e.printStackTrace();
        }
    }

    public String txtToString(String directory) {
        try {
            InputStream inputStream = getAssets().open(directory);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer);
        } catch (IOException e) {
            Log.e("txtToString", "Couldn't get string from txt file");
            e.printStackTrace();
        }
        return "Name not found";
    }
}
