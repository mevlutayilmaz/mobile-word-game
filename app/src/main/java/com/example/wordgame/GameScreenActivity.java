package com.example.wordgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameScreenActivity extends AppCompatActivity {
    private LinearLayout game_over_layout;
    private ImageView false1, false2;
    private TextView text_input, point_text;
    private Button clearButton, checkButton;
    private Button Btn_game_over_yes, Btn_game_over_no;
    private ArrayList<TextView> input = new ArrayList<>();
    private ArrayList<String> sonant = new ArrayList<>();
    private ArrayList<String> consonant = new ArrayList<>();
    private int point_sonant[] = {1,1,2,1,4,4,5,2};
    private int point_consonant[] = {3,4,4,3,7,5,8,5,10,1,1,2,1,5,1,2,4,1,7,3,4};
    private int id, random,randomRow,randomColumn, false_counter, drop_time, son_counter, con_counter, down_counter, flag;
    private TextView[][] words;
    private Animation fade, word_down, down;
    private CountDownTimer timer;
    private Random rand = new Random();

    Integer[] background_sonant = {
            R.drawable.sonant_bg1,
            R.drawable.sonant_bg2,
            R.drawable.sonant_bg3,
            R.drawable.sonant_bg4,
            R.drawable.sonant_bg5,
            R.drawable.sonant_bg6,
            R.drawable.sonant_bg7,
            R.drawable.sonant_bg8
    };
    Integer[] background_consonant = {
            R.drawable.consonant_bg1,
            R.drawable.consonant_bg2,
            R.drawable.consonant_bg3,
            R.drawable.consonant_bg4,
            R.drawable.consonant_bg5,
            R.drawable.consonant_bg6,
            R.drawable.consonant_bg7,
            R.drawable.consonant_bg8,
            R.drawable.consonant_bg9,
            R.drawable.consonant_bg10,
            R.drawable.consonant_bg11,
            R.drawable.consonant_bg12,
            R.drawable.consonant_bg13,
            R.drawable.consonant_bg14,
            R.drawable.consonant_bg15,
            R.drawable.consonant_bg16,
            R.drawable.consonant_bg17,
            R.drawable.consonant_bg18,
            R.drawable.consonant_bg19,
            R.drawable.consonant_bg20,
            R.drawable.consonant_bg21,
    };

    public void words_down(int index, int word_id, int x){
        MediaPlayer game_over = MediaPlayer.create(getApplicationContext(),R.raw.game_over);
        MediaPlayer witch_laugh = MediaPlayer.create(getApplicationContext(),R.raw.witch_laugh);
        MediaPlayer poof_smoke = MediaPlayer.create(getApplicationContext(),R.raw.poof_smoke);
        int counterRow = x;

        while(counterRow != 9 && words[counterRow + 1][index].getText().equals(""))
            counterRow++;

        if(word_id != 40 && word_id != 30 && counterRow == 0){
            timer.cancel();
            game_over.start();
            game_over_layout.startAnimation(fade);
            game_over_layout.setVisibility(View.VISIBLE);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("Score");

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int count = (int) dataSnapshot.getChildrenCount()+1;
                    reference.child("Player_"+count).setValue(point_text.getText().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else{
            if(word_id == 30){
                words[counterRow][index].setBackgroundResource(R.drawable.dynemite);
            }
            else if(word_id == 40){
                witch_laugh.start();
                counterRow = 0;
                words[counterRow][index].setBackgroundResource(R.drawable.witch);
            }
            else if(!consonant.contains(words[x][index].getText().toString())){
                words[counterRow][index].setText(sonant.get(word_id));
                words[counterRow][index].setBackgroundResource(background_sonant[word_id]);
            }else{
                words[counterRow][index].setText(consonant.get(word_id));
                words[counterRow][index].setBackgroundResource(background_consonant[word_id]);
            }

            if(word_id != 30 && word_id != 40){
                words[x][index].setText("");
                words[x][index].setBackgroundResource(R.drawable.gray1);
            }

            if(x == 0){
                down_counter++;
                if(word_id != 40)
                    words[counterRow][index].startAnimation(word_down);

                if(word_id == 30){
                    //Dynemite
                    int finalCounterRow = counterRow;
                    new CountDownTimer(850, 850) {
                        public void onTick(long millisUntilFinished) {}

                        public void onFinish() {
                            new CountDownTimer(250, 250) {
                                public void onTick(long millisUntilFinished) {
                                    MediaPlayer explosion = MediaPlayer.create(getApplicationContext(),R.raw.explosion);
                                    explosion.start();

                                    for(int i = 0; i < 10; i++){
                                        if(!words[i][index].getText().equals("")){
                                            if(!consonant.contains(words[i][index].getText().toString()))
                                                son_counter--;
                                            else
                                                con_counter--;
                                        }

                                        words[i][index].setText("");
                                        if(words[i][index].getError() != null){
                                            words[i][index].setError(null);
                                            input.remove(words[i][index]);
                                        }
                                        words[i][index].setBackgroundResource(R.drawable.red);
                                    }

                                    /*for(int i = 0; i < 8; i++){
                                        words[finalCounterRow][i].setBackgroundResource(R.drawable.red);
                                        if(!words[finalCounterRow][i].getText().equals("")) {
                                            if (!consonant.contains(words[finalCounterRow][i].getText().toString()))
                                                son_counter--;
                                            else
                                                con_counter--;
                                            words[finalCounterRow][i].setText("");
                                        }

                                        if(words[finalCounterRow][i].getError() != null){
                                            words[finalCounterRow][i].setError(null);
                                            input.remove(words[finalCounterRow][i]);
                                        }
                                    }*/

                                    String text = "";
                                    for(int k = 0; k < input.size(); k++)
                                        text += input.get(k).getText();
                                    text_input.setText(text);
                                }

                                public void onFinish() {
                                    /*for(int i = 0; i < 8; i++){
                                        int row = finalCounterRow-1;
                                        while(!words[row][i].getText().equals("")){
                                            int id_word = consonant.indexOf(words[row][i].getText().toString());
                                            if(id_word == -1)
                                                id_word = sonant.indexOf(words[row][i].getText().toString());

                                            if(words[row][i].getError() != null){
                                                words[row][i].setError(null);
                                                input.remove(words[row][i]);
                                            }
                                            words_down(i, id_word, row);
                                            row--;
                                        }
                                    }*/
                                    for(int i = 0; i < 10; i++)
                                        words[i][index].setBackgroundResource(R.drawable.gray1);
                                    /*for(int i = 0; i < 8; i++)
                                        if(words[finalCounterRow][i].getText().equals(""))
                                            words[finalCounterRow][i].setBackgroundResource(R.drawable.gray1);*/

                                    flag = 0;
                                }
                            }.start();

                        }
                    }.start();
                }

                if(word_id == 40){
                    int Random, durum = 0;
                    if(son_counter > con_counter){
                        Random = rand.nextInt(21);
                        durum = 1;
                    }
                    else
                        Random = rand.nextInt(8);

                    int finalCounterRow1 = counterRow;
                    int finalDurum = durum;
                    new CountDownTimer(1600, 1600) {

                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            for(int i = 0; i < 8; i++){
                                if(i == index)
                                    continue;

                                if(finalDurum == 0){
                                    son_counter++;
                                    words[0][i].setText(sonant.get(Random));
                                    words[0][i].setBackgroundResource(background_sonant[Random]);
                                    words_down(i, Random, 0);
                                }else{
                                    con_counter++;
                                    words[0][i].setText(consonant.get(Random));
                                    words[0][i].setBackgroundResource(background_consonant[Random]);
                                    words_down(i, Random, 0);
                                }
                            }

                            new CountDownTimer(100, 100) {

                                public void onTick(long millisUntilFinished) {}

                                public void onFinish() {
                                    down_counter -= 7;
                                    poof_smoke.start();
                                    words[finalCounterRow1][index].setBackgroundResource(R.drawable.gray1);
                                    flag = 0;
                                }

                            }.start();

                        }

                    }.start();
                }
            } else
                words[counterRow][index].startAnimation(down);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        words = new TextView[][]{
                {findViewById(R.id.c00), findViewById(R.id.c01), findViewById(R.id.c02), findViewById(R.id.c03), findViewById(R.id.c04), findViewById(R.id.c05), findViewById(R.id.c06), findViewById(R.id.c07)},
                {findViewById(R.id.c10), findViewById(R.id.c11), findViewById(R.id.c12), findViewById(R.id.c13), findViewById(R.id.c14), findViewById(R.id.c15), findViewById(R.id.c16), findViewById(R.id.c17)},
                {findViewById(R.id.c20), findViewById(R.id.c21), findViewById(R.id.c22), findViewById(R.id.c23), findViewById(R.id.c24), findViewById(R.id.c25), findViewById(R.id.c26), findViewById(R.id.c27)},
                {findViewById(R.id.c30), findViewById(R.id.c31), findViewById(R.id.c32), findViewById(R.id.c33), findViewById(R.id.c34), findViewById(R.id.c35), findViewById(R.id.c36), findViewById(R.id.c37)},
                {findViewById(R.id.c40), findViewById(R.id.c41), findViewById(R.id.c42), findViewById(R.id.c43), findViewById(R.id.c44), findViewById(R.id.c45), findViewById(R.id.c46), findViewById(R.id.c47)},
                {findViewById(R.id.c50), findViewById(R.id.c51), findViewById(R.id.c52), findViewById(R.id.c53), findViewById(R.id.c54), findViewById(R.id.c55), findViewById(R.id.c56), findViewById(R.id.c57)},
                {findViewById(R.id.c60), findViewById(R.id.c61), findViewById(R.id.c62), findViewById(R.id.c63), findViewById(R.id.c64), findViewById(R.id.c65), findViewById(R.id.c66), findViewById(R.id.c67)},
                {findViewById(R.id.c70), findViewById(R.id.c71), findViewById(R.id.c72), findViewById(R.id.c73), findViewById(R.id.c74), findViewById(R.id.c75), findViewById(R.id.c76), findViewById(R.id.c77)},
                {findViewById(R.id.c80), findViewById(R.id.c81), findViewById(R.id.c82), findViewById(R.id.c83), findViewById(R.id.c84), findViewById(R.id.c85), findViewById(R.id.c86), findViewById(R.id.c87)},
                {findViewById(R.id.c90), findViewById(R.id.c91), findViewById(R.id.c92), findViewById(R.id.c93), findViewById(R.id.c94), findViewById(R.id.c95), findViewById(R.id.c96), findViewById(R.id.c97)}
        };
        text_input = findViewById(R.id.text_input);
        clearButton = findViewById(R.id.clearButton);
        checkButton = findViewById(R.id.checkButton);
        point_text = findViewById(R.id.point);
        false1 = findViewById(R.id.false1);
        false2 = findViewById(R.id.false2);
        game_over_layout = findViewById(R.id.game_over_layout);
        Btn_game_over_yes = findViewById(R.id.game_over_yes);
        Btn_game_over_no = findViewById(R.id.game_over_no);
        fade = AnimationUtils.loadAnimation(this, R.anim.fade);
        word_down = AnimationUtils.loadAnimation(this, R.anim.main_down1);
        down = AnimationUtils.loadAnimation(this, R.anim.down);

        sonant.addAll(Arrays.asList("A","E","I","İ","O","Ö","U","Ü"));
        consonant.addAll(Arrays.asList("B","C","Ç","D","F","G","Ğ","H","J","K","L","M","N","P","R","S","Ş","T","V","Y","Z"));
        false_counter = 0;
        down_counter = 0;
        son_counter = 12;
        con_counter = 12;
        flag = 0;

        MediaPlayer click_word = MediaPlayer.create(getApplicationContext(),R.raw.click);
        MediaPlayer start_game = MediaPlayer.create(getApplicationContext(),R.raw.start_game);
        MediaPlayer correct = MediaPlayer.create(getApplicationContext(),R.raw.correct);
        MediaPlayer wrong = MediaPlayer.create(getApplicationContext(),R.raw.wrong);

        new CountDownTimer(4000,1000) {
            @Override
            public void onTick(long l) {
                point_text.setText(""+l/1000);
                start_game.start();
            }

            @Override
            public void onFinish() {
                for(int i = 0; i < 24; i++){
                    do{
                        randomRow = rand.nextInt(3)+7;
                        randomColumn = rand.nextInt(8);
                    }while(!words[randomRow][randomColumn].getText().equals(""));

                    if(i%2 == 0){
                        random = rand.nextInt(8);
                        words[randomRow][randomColumn].setText(sonant.get(random));
                        words[randomRow][randomColumn].setBackgroundResource(background_sonant[random]);
                    }else{
                        random = rand.nextInt(21);
                        words[randomRow][randomColumn].setText(consonant.get(random));
                        words[randomRow][randomColumn].setBackgroundResource(background_consonant[random]);
                    }
                    words[randomRow][randomColumn].startAnimation(word_down);
                }

                drop_time = 5;
                int ozellik[] = {30,40};
                timer = new CountDownTimer(30000000,1000) {
                    @Override
                    public void onTick(long l) {
                        if(Integer.parseInt(point_text.getText().toString()) > 400)
                            drop_time = 1;
                        else if(Integer.parseInt(point_text.getText().toString()) > 300)
                            drop_time = 2;
                        else if(Integer.parseInt(point_text.getText().toString()) > 200)
                            drop_time = 3;
                        else if(Integer.parseInt(point_text.getText().toString()) > 100)
                            drop_time = 4;

                        if(flag == 0 && (l/1000)%drop_time == 0){
                            int ColumbRandom = rand.nextInt(8);
                            int random_wordid;

                            if(down_counter != 0 && down_counter%20 == 0){
                                flag = 1;
                                int x = rand.nextInt(2);
                                random_wordid = ozellik[x];
                            }
                            else if(son_counter > con_counter){
                                random_wordid = rand.nextInt(21);
                                words[0][ColumbRandom].setText(consonant.get(random_wordid));
                                words[0][ColumbRandom].setBackgroundResource(background_consonant[random_wordid]);
                                con_counter++;
                            }else{
                                random_wordid = rand.nextInt(8);
                                words[0][ColumbRandom].setText(sonant.get(random_wordid));
                                words[0][ColumbRandom].setBackgroundResource(background_sonant[random_wordid]);
                                son_counter++;
                            }
                            words_down(ColumbRandom, random_wordid, 0);
                        }
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();

            }
        }.start();

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 8; j++){
                int finalI = i;
                int finalJ = j;
                words[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!words[finalI][finalJ].getText().equals("")){
                            click_word.start();
                            String text = "";
                            text += text_input.getText();
                            id = consonant.indexOf(words[finalI][finalJ].getText().toString());

                            if(words[finalI][finalJ].getError() == null){
                                input.add(words[finalI][finalJ]);
                                words[finalI][finalJ].setError("");
                                text += words[finalI][finalJ].getText();

                                if(id == -1){
                                    id = sonant.indexOf(words[finalI][finalJ].getText().toString());
                                    words[finalI][finalJ].setBackgroundResource(R.drawable.sonant_black);
                                }else{
                                    words[finalI][finalJ].setBackgroundResource(R.drawable.consonant_black);
                                }
                            }else{
                                input.remove(words[finalI][finalJ]);
                                words[finalI][finalJ].setError(null);
                                text = "";

                                for(int k = 0; k < input.size(); k++)
                                    text += input.get(k).getText();

                                if(id == -1){
                                    id = sonant.indexOf(words[finalI][finalJ].getText().toString());
                                    words[finalI][finalJ].setBackgroundResource(background_sonant[id]);

                                }else{
                                    words[finalI][finalJ].setBackgroundResource(background_consonant[id]);
                                }
                            }
                            text_input.setText(text);
                        }
                    }
                });
            }
        }

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < input.size(); i++){
                    if(consonant.contains(input.get(i).getText().toString()))
                        input.get(i).setBackgroundResource(background_consonant[consonant.indexOf(input.get(i).getText().toString())]);
                    else
                        input.get(i).setBackgroundResource(background_sonant[sonant.indexOf(input.get(i).getText().toString())]);
                    input.get(i).setError(null);
                }
                input.clear();
                text_input.setText("");
            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("Words/"+text_input.getText().toString().toLowerCase());
                dbReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            correct.start();
                            int point = 0;
                            int idWord, row, column;
                            String[] x;
                            String[] a = text_input.getText().toString().split("");
                            for(int i = 0; i < a.length; i++){
                                idWord = consonant.indexOf(a[i]);
                                if(idWord == -1) {
                                    idWord = sonant.indexOf(a[i]);
                                    son_counter--;
                                    point += point_sonant[idWord];
                                }else{
                                    con_counter--;
                                    point += point_consonant[idWord];
                                }
                                input.get(i).setText("");
                                input.get(i).setBackgroundResource(R.drawable.gray1);
                                input.get(i).setError(null);
                                x = input.get(i).getTag().toString().split(",");
                                row = Integer.parseInt(x[0]) - 1;
                                column = Integer.parseInt(x[1]);

                                while(!words[row][column].getText().equals("")){
                                    int id = consonant.indexOf(words[row][column].getText().toString());
                                    if(id == -1)
                                        id = sonant.indexOf(words[row][column].getText().toString());

                                    words_down(column, id, row);
                                    row--;
                                }
                            }
                            point += Integer.parseInt(point_text.getText().toString());
                            point_text.setText(""+point);

                        }else{
                            wrong.start();
                            if(false_counter == 0){
                                false_counter++;
                                false1.setImageResource(R.drawable.clear_red);
                            }else if(false_counter == 1){
                                false_counter++;
                                false2.setImageResource(R.drawable.clear_red);
                            }else{
                                int randColumn, word_id;
                                ArrayList<Integer> arr = new ArrayList<>();

                                for(int i = 0; i < 8; i++) {
                                    do {
                                        randColumn = rand.nextInt(8);
                                    } while (arr.contains(randColumn));

                                    arr.add(randColumn);
                                    if(i%2 == 0){
                                        son_counter++;
                                        word_id = rand.nextInt(8);
                                        words[0][randColumn].setText(sonant.get(word_id));
                                        words[0][randColumn].setBackgroundResource(background_sonant[word_id]);
                                    }else{
                                        con_counter++;
                                        word_id = rand.nextInt(21);
                                        words[0][randColumn].setText(consonant.get(word_id));
                                        words[0][randColumn].setBackgroundResource(background_consonant[word_id]);
                                    }
                                    words_down(randColumn,word_id, 0);
                                }

                                false_counter = 0;
                                false1.setImageResource(R.drawable.clear_gray);
                                false2.setImageResource(R.drawable.clear_gray);
                            }

                            for(int i = 0; i < input.size(); i++){
                                if(consonant.contains(input.get(i).getText().toString()))
                                    input.get(i).setBackgroundResource(background_consonant[consonant.indexOf(input.get(i).getText().toString())]);
                                else
                                    input.get(i).setBackgroundResource(background_sonant[sonant.indexOf(input.get(i).getText().toString())]);
                                input.get(i).setError(null);
                            }
                        }
                        text_input.setText("");
                        input.clear();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("*************************************-->"+error);
                    }
                });

            }
        });

        Btn_game_over_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameScreenActivity.this,GameScreenActivity.class));
            }
        });

        Btn_game_over_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameScreenActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }
}