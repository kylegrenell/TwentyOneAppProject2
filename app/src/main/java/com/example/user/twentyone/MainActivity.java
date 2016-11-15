package com.example.user.twentyone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.twentyone.TwentyOne.Player;
import com.example.user.twentyone.TwentyOne.Table;



public class MainActivity extends AppCompatActivity {

    Player player;
    Table table;
    TextView mPlayerResultText;
    TextView mDealerResultText;
    Button mbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // log the onCreate call
        Log.d("Main", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mPlayerResultText = (TextView) findViewById(R.id.player_result_text);
//        mDealerResultText = (TextView) findViewById(R.id.dealer_result_text);
//        mDealButton = (Button) findViewById(R.id.button_hit);

        // create table to play at
        table = new Table();
        // add player to the game
        player = new Player("kyle");
        // add players to sit at the table
        table.sitAtTable(player);

        table.clearTable();
//        table.checkTable();

    }

    // event handler for the HIT option
    public void buttonHit(View view) {
        table.getCurrentPlayer().setAction(Player.Action.HIT);
        Log.d("TwentyOne", "Hit button clicked!");
        table.checkTable();
        table.clearTable();
//        display the hand text on the screen
//        TextView playerText = (TextView)findViewById(R.id.player_result_text);
//        playerText.setText(String.valueOf(table.getCurrentPlayer().getHandValue()));
//
//        TextView dealerText = (TextView)findViewById(R.id.dealer_result_text);
//        dealerText.setText(String.valueOf(table.getDealer().getHandValue()));

    }

    // event handler for the STAND option
    public void buttonStand(View view) {
        table.getCurrentPlayer().setAction(Player.Action.STAND);
        Log.d("TwentyOne", "Stand button clicked!");
//        table.checkTable();
//        table.clearTable();
    }

    // event handler for deal button
    public void buttonDeal(View view){
//        Log.d("TwentyOne", "Deal button clicked!");
        table.startNewGame();
        table.checkTable();
    }





}







