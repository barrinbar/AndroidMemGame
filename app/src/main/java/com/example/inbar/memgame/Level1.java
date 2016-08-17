package com.example.inbar.memgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class level1 extends AppCompatActivity {

    private final static int TOTAL_CARDS = 4;
    private static int numOfMatches = 0;
    private static int numOfFlips = 0;
    private static ImageButton firstCard;

    Chronometer curr_time;
    TextView user_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);

        curr_time = (Chronometer)findViewById(R.id.ch_time);
        user_name = (TextView)findViewById(R.id.tv_user_name);
        ArrayList<Integer> cards;

        // Retrieve user name from shared pref
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        user_name.setText(sharedPref.getString(getString(R.string.saved_user_name), null));

        // Init cards
        cards = new ArrayList<>();
        cards.add(R.drawable.begin);
        cards.add(R.drawable.begin);
        cards.add(R.drawable.ben_gurion);
        cards.add(R.drawable.ben_gurion);
        Collections.shuffle(cards);

        GridView board = (GridView)findViewById(R.id.board);

        board.setAdapter(new ImageAdapter(this));

        board.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
        /*        Toast.makeText(level1.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
        View.OnClickListener imageClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {*/
                numOfFlips++;

                // TODO: flip curr card and disable clickable on image
                if (numOfFlips % 2 == 0) {
                    // get first card and init
                    firstCard = (ImageButton)v;
                    firstCard.setImageResource((int)firstCard.getTag());
                    firstCard.setClickable(false);
                }
                else {
                    ImageButton secondCard = (ImageButton)v;
                    secondCard.setImageResource((int)v.getTag());
                    firstCard.setClickable(false);

                    // Halt
                    try {
                        wait(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Compare to flipped card
                    if (firstCard.getTag() == secondCard.getTag())
                        numOfMatches++;
                    else {
                        // Flip back
                        firstCard.setImageResource(R.drawable.square);
                        firstCard.setClickable(true);
                        secondCard.setImageResource(R.drawable.square);
                        secondCard.setClickable(true);

                        // Inform the user
                        Toast.makeText(level1.this, "Oops, the cards don't match", Toast.LENGTH_SHORT).show();
                    }

                    // Check the state of the entire game
                    gameState();
                }
            }
        });
/*
        // Place cards
        for (int card: cards) {
            ImageButton btnCard = new ImageButton(this);
            btnCard.setImageResource(R.drawable.square);
            btnCard.setClickable(true);
            btnCard.setOnClickListener(imageClickListener);
            btnCard.setBackgroundColor(Color.TRANSPARENT);
            btnCard.setTag(card);
            btnCard.setId(card);
            board.addView(btnCard);
        }*/
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            imageView.setTag(mThumbIds[position]);
            return imageView;
        }

        // references to our images
        private Integer[] mThumbIds = {
                R.drawable.begin, R.drawable.ben_gurion,
                R.drawable.begin, R.drawable.ben_gurion
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        numOfMatches = 0;
        numOfFlips = 0;
        curr_time.start();
    }

    private void gameState() {
        if (numOfMatches * 2 == TOTAL_CARDS) {
            curr_time.stop();
            Toast.makeText(level1.this, "Congratulations!!! You Won!!!\n" +
                    "You needed only " + numOfFlips + " turns", Toast.LENGTH_LONG).show();

            // Pass game info to HiScore
        }
    }
}
