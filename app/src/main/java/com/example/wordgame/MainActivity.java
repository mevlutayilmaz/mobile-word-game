package com.example.wordgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    private Button buttonPlay, buttonScoreboard;
    private GifImageView game_time;
    private int durum = 0;
    private Animation right_trans, left_trans, down1, down2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPlay = findViewById(R.id.buttonPlay);
        buttonScoreboard = findViewById(R.id.buttonScoreboard);
        right_trans = AnimationUtils.loadAnimation(this, R.anim.right_trans);
        left_trans = AnimationUtils.loadAnimation(this, R.anim.left_trans);
        down1 = AnimationUtils.loadAnimation(this, R.anim.main_down1);
        down2 = AnimationUtils.loadAnimation(this, R.anim.main_down2);
        game_time = findViewById(R.id.game_time);

        game_time.startAnimation(down2);
        buttonPlay.startAnimation(left_trans);
        buttonScoreboard.startAnimation(right_trans);


        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameScreenActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        buttonScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScoreboardActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView volumeControl = findViewById(R.id.volumeControl);
        volumeControl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(durum == 1){
                    volumeControl.setImageResource(R.drawable.volume_up);
                    durum = 0;
                }else{
                    volumeControl.setImageResource(R.drawable.volume_off);
                    durum = 1;
                }
            }
        });




    }
}