package com.inbar.memgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

public abstract class level extends AppCompatActivity {

    private final static int MATCHED = -1;
    private int numOfMatches = 0;
    private int numOfFlips = 0;
    private boolean started = false;
    private ImageView firstCard = null;
    private ImageView secondCard = null;
    private String newHighScore = "";

    private Chronometer currTime;
    private TextView userName;

    private Context levelContext;
    private int level;
    private int totalCards;
    private int columns;
    private ArrayList<Integer> cards;

    public Context getLevelContext() {
        return levelContext;
    }

    protected void setLevelContext(Context levelContext) {
        this.levelContext = levelContext;
    }

    public int getLevel() {
        return level;
    }

    protected void setLevel(int level) {
        this.level = level;
    }

    public int getTotalCards() {
        return totalCards;
    }

    protected void setTotalCards(int totalCards) {
        this.totalCards = totalCards;
    }

    public ArrayList<Integer> getCards() {
        return cards;
    }

    protected void setCards(ArrayList<Integer> cards) {
        this.cards = cards;
    }

    public int getColumns() {
        return columns;
    }

    protected void setColumns(int columns) {
        this.columns = columns;
    }

    protected abstract void initCards();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void init() {
        currTime = (Chronometer)findViewById(R.id.ch_time);
        userName = (TextView)findViewById(R.id.tv_user_name);

        // Retrieve user name from shared pref
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.user_name_pref), Context.MODE_PRIVATE);
        userName.setText(sharedPref.getString(getString(R.string.user_name_pref), null));

        // Init cards
        initCards();

        Collections.shuffle(cards);

        GridView board = (GridView)findViewById(R.id.board);

        board.setNumColumns(getColumns());
        board.setAdapter(new ImageAdapter(getLevelContext()));
        board.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

            // Make sure card isn't already matched
            if (getCards().get(v.getId()) == MATCHED)
                return;

            // If timer isn't started - start it
            if (!started) {
                currTime.setBase(SystemClock.elapsedRealtime());
                currTime.start();
                started = true;
            }

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
                getCards().set(firstCard.getId(), MATCHED);
                getCards().set(secondCard.getId(), MATCHED);
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
                Toast.makeText(getLevelContext(), "Oops, the cards don't match", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void endTurn() {
        // Refresh cards
        firstCard = null;
        secondCard = null;

        // Check the state of the entire game
        gameState();
    }

    private void gameState() {
        if (numOfMatches * 2 == getTotalCards()) {
            currTime.stop();

            setHighScore();

            Toast.makeText(getLevelContext(), "Congratulations!!! You Won!!!\n" +
                    "You needed only " + numOfFlips + " flips and " +
                    currTime.getText().toString().trim() + " minutes" + newHighScore, Toast.LENGTH_LONG).show();
            
            Intent lobby = new Intent(getLevelContext(), lobby.class);
            lobby.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            lobby.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            lobby.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
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

        newHighScore = " and set a new High Score!!";

        if (!sharedPref.contains(name))
            hs = new HighScore(getLevel(), elapsedTime);
        else
        {
            // Get the current high score for comparison
            json = sharedPref.getString(name, "");
            hs = gson.fromJson(json, HighScore.class);

            if ((hs.getLevel() < getLevel()) ||
                    ((hs.getLevel() == getLevel()) && (hs.getTime() > elapsedTime))) {
                hs.setTime(elapsedTime);
                hs.setLevel(getLevel());
            }
            else
                newHighScore = "";
        }

        Editor prefsEditor = sharedPref.edit();
        json = gson.toJson(hs);
        prefsEditor.putString(name, json);
        prefsEditor.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        numOfMatches = 0;
        numOfFlips = 0;
    }

    /**
     * Image Adapter
     * An adapter for displaying the cards in a grid view
     */
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return getCards().size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // Create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            SquareImageView imageView;
            if (convertView == null) {
                // If it's not recycled, initialize some attributes
                imageView = new SquareImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView = (SquareImageView) convertView;
            }

            imageView.setImageResource(R.drawable.square);
            imageView.setTag(getCards().get(position));
            imageView.setId(position);
            return imageView;
        }

        /**
         * Square Image View
         * An image view which is defined as a square according to the view's width
         */
        public class SquareImageView extends ImageView {
            public SquareImageView(Context context) {
                super(context);
            }

            public SquareImageView(Context context, AttributeSet attrs) {
                super(context, attrs);
            }

            public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
                super(context, attrs, defStyle);
            }

            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
            }
        }
    }
}
