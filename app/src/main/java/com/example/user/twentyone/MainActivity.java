package com.example.user.twentyone;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import com.example.user.twentyone.TwentyOne.Deck;
import com.example.user.twentyone.TwentyOne.Player;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // log the onCreate call
        Log.d("Main", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Deck deck = new Deck();
//        deck.removeCards();

        Player player = new Player("Kyle");
        player.hit(deck.dealCard());
        player.hit(deck.dealCard());
        player.showHand();

    }

//    public void buttonClicked(View view) {
//        TextView textView = (TextView) findViewById(R.id.textView);
//        Log.d("Main", "button clicked");
//    }


}





