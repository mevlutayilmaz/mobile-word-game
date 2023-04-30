package com.example.wordgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ScoreAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> listPlayer = new ArrayList<>();
    ArrayList<String> listScore = new ArrayList<>();
    ArrayList<Integer> sıralama = new ArrayList<>();
    LayoutInflater inflater;

    public ScoreAdapter(Context ctx, ArrayList<String> listPlayer, ArrayList<String> listScore, ArrayList<Integer> sıralama) {
        this.context = ctx;
        this.listPlayer = listPlayer;
        this.listScore = listScore;
        this.sıralama = sıralama;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return listScore.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.point_item, null);
        TextView txtViewPlayer = (TextView) convertView.findViewById(R.id.point_item_player);
        TextView txtViewScore = (TextView) convertView.findViewById(R.id.point_item_score);
        TextView textView = (TextView) convertView.findViewById(R.id.sıralama);
        txtViewPlayer.setText(listPlayer.get(position));
        txtViewScore.setText(listScore.get(position));
        if(position == 0)
            textView.setBackgroundResource(R.drawable.gold);
        else if(position == 1)
            textView.setBackgroundResource(R.drawable.silver);
        else if(position == 2)
            textView.setBackgroundResource(R.drawable.bronze);
        else
            textView.setText(""+sıralama.get(position));
        return convertView;
    }
}
