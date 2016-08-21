package com.inbar.memgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inbar.memgame.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class level1 extends AppCompatActivity {

    private final int level = 1;
    private final static int TOTAL_CARDS = 4;
    private final static int MATCHED = -1;
    private static int numOfMatches = 0;
    private static int numOfFlips = 0;
    private static ImageView firstCard = null;
    private static ImageView secondCard = null;
    private ArrayList<Integer> cards;

    private Chronometer currTime;
    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);

        currTime = (Chronometer)findViewById(R.id.ch_time);
        userName = (TextView)findViewById(R.id.tv_user_name);

        // Retrieve user name from shared pref
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.user_name_pref), Context.MODE_PRIVATE);
        userName.setText(sharedPref.getString(getString(R.string.user_name_pref), null));
        Log.d("MemGame:LEVEL1", "User name: " + userName.getText().toString().trim());

        // Init cards
        initCards();

        Collections.shuffle(cards);

        GridView board = (GridView)findViewById(R.id.board);

        board.setAdapter(new ImageAdapter(this));

        board.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // Make sure card isn't already matched
                if (cards.get(v.getId()) == MATCHED)
                    return;

                numOfFlips++;

                // If it's a first flip
                if (firstCard == null) {

                    // Get first card and flip it
                    firstCard = (ImageView)v;
                    firstCard.setImageResource((int)firstCard.getTag());
                }
                else { // Second flip
                    secondCard = (ImageView)v;

                    checkMatch();
                }
            }
        });
    }

    private void checkMatch() {
        // Check if user pressed the same card
        if (firstCard.getId() == secondCard.getId()) {
            firstCard.setImageResource(R.drawable.square);
            endTurn();
        }
        else {
            // Flip card
            secondCard.setImageResource((int) secondCard.getTag());

            // Check if cards match
            if ((int) firstCard.getTag() == (int) secondCard.getTag()) {
                // Mark match
                numOfMatches++;
                cards.set(firstCard.getId(), MATCHED);
                cards.set(secondCard.getId(), MATCHED);
                endTurn();
            } else { // In case they don't match
                // Halt before flipping back
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Flip back
                        firstCard.setImageResource(R.drawable.square);
                        secondCard.setImageResource(R.drawable.square);
                        endTurn();
                    }
                }, 500);

                // Inform the user
                Toast.makeText(level1.this, "Oops, the cards don't match", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initCards() {
        cards = new ArrayList<>(Arrays.asList(
                R.drawable.begin, R.drawable.ben_gurion,
                R.drawable.begin, R.drawable.ben_gurion));
    }

    private void endTurn() {
        // Refresh cards
        firstCard = null;
        secondCard = null;

        // Check the state of the entire game
        gameState();
    }

    private void gameState() {
        if (numOfMatches * 2 == TOTAL_CARDS) {
            currTime.stop();
            Toast.makeText(level1.this, "Congratulations!!! You Won!!!\n" +
                    "You needed only " + numOfFlips + " flips and " +
                    currTime.getText().toString().trim() + " minutes", Toast.LENGTH_LONG).show();

            setHighScore();

            Intent lobby = new Intent(this, lobby.class);
            startActivity(lobby);
        }
    }

    private void setHighScore() {
        HighScore hs;
        Gson gson = new Gson();
        String json;

        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.high_score_pref), Context.MODE_PRIVATE);

        String name = userName.getText().toString().trim();
        long elapsedTime = SystemClock.elapsedRealtime() - currTime.getBase();

        // Check if the user already exists in the high scores
        if (sharedPref.contains(name)) {
            json = sharedPref.getString(name, "");
            hs = gson.fromJson(json, HighScore.class);

            // Check if the existing better thn the current time
            if (hs.getTime() > elapsedTime)
                hs.setTime(elapsedTime);
        }
        // User's first high score
        else
            hs = new HighScore(level, elapsedTime);

        Editor prefsEditor = sharedPref.edit();
        json = gson.toJson(hs);
        prefsEditor.putString(name, json);
        prefsEditor.commit();
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return cards.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // Create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(R.drawable.square);
            imageView.setTag(cards.get(position));
            imageView.setId(position);
            return imageView;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        numOfMatches = 0;
        numOfFlips = 0;
        currTime.setBase(SystemClock.elapsedRealtime());
        currTime.start();
    }
}