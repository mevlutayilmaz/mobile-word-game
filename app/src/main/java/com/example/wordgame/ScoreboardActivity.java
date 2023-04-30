package com.example.wordgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import pl.droidsonroids.gif.GifImageView;

public class ScoreboardActivity extends AppCompatActivity {
    private ListView listView;
    private GifImageView leaderboard;
    private ImageView back;
    private Animation right_trans, left_trans;
    private ArrayList<String> scoreList = new ArrayList<>();
    private ArrayList<String> playerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        listView = findViewById(R.id.listView);
        back = findViewById(R.id.back);
        leaderboard = findViewById(R.id.leaderboard);
        right_trans = AnimationUtils.loadAnimation(this, R.anim.right_trans);
        left_trans = AnimationUtils.loadAnimation(this, R.anim.left_trans);

        leaderboard.startAnimation(left_trans);
        listView.startAnimation(right_trans);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Score");

        TreeMap<Integer, String> scoreMap = new TreeMap<>(Collections.reverseOrder());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Integer> sıralama = new ArrayList<>();
                int sıra = 1;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    String value = ds.getValue(String.class);
                    scoreMap.put(Integer.parseInt(value), key);
                }

                for (Map.Entry<Integer, String> entry : scoreMap.entrySet()){
                    playerList.add(entry.getValue().toString());
                    scoreList.add(entry.getKey().toString());
                    sıralama.add(sıra);
                    sıra++;
                }

                ScoreAdapter scoreAdapter = new ScoreAdapter(getApplicationContext(),playerList, scoreList, sıralama);
                listView.setAdapter(scoreAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreboardActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }
}