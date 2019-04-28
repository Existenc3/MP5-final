package com.example.soundpoolpractice;

import android.content.Context;
import android.graphics.Color;
import android.media.SoundPool;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    private List<Sound> lstSource;
    private Context context;

    private SoundPool soundPool;

    //toTest change rate
    private float rate = 1;

    public GridViewAdapter(List<Sound> setLstSource, SoundPool setSoundPool, Context setContext) {
        lstSource = setLstSource;
        context = setContext;
        soundPool = setSoundPool;
    }

    @Override
    public int getCount() {
        return lstSource.size();
    }

    @Override
    public Object getItem(int position) {
        return lstSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position + 1;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Button button;
        if (convertView == null) {
            button = new Button(context);
            button.setLayoutParams(new GridView.LayoutParams(160, 125));
            button.setPadding(0, 0, 0, 0);
            button.setText(lstSource.get(position).getName());
            button.setTextSize(11f);
            button.setBackgroundColor(Color.DKGRAY);
            button.setTextColor(Color.WHITE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = lstSource.get(position).getSoundPoolId();
                    soundPool.play(id, 1, 1, 0, 0, rate);
                }
            });
        } else {
            button = (Button) convertView;
        }
        return button;

    }
}
