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

    Player mPlayer;
    Table mTable;
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

        // create table to play at, add player to the table
        mTable = new Table();
        mPlayer = new Player("kyle");
        mTable.sitAtTable(mPlayer);
        mTable.clearTable();
    }


    // event handler for HIT
    public void buttonHit(View view) {
            mTable.getCurrentPlayerIndex().setAction(Player.Action.HIT);
            Log.d("TwentyOne", "Hit button clicked!");
            mTable.checkTable();
            mTable.clearTable();

            TextView playerText = (TextView) findViewById(R.id.player_result_text);
            playerText.setText(String.valueOf(mTable.getCurrentPlayerIndex().getHandValue()));

            TextView dealerText = (TextView) findViewById(R.id.dealer_result_text);
            dealerText.setText(String.valueOf(mTable.getDealer().getHandValue()));

            resolve();
        }


    // event handler for the STAND option
    public void buttonStand(View view) {

        mTable.getCurrentPlayerIndex().setAction(Player.Action.STAND);
        Log.d("TwentyOne", "Stand button clicked!");
        mTable.checkTable();

        TextView playerText = (TextView)findViewById(R.id.player_result_text);
        playerText.setText(String.valueOf(mTable.getCurrentPlayerIndex().getHandValue()));

        TextView dealerText = (TextView)findViewById(R.id.dealer_result_text);
        dealerText.setText(String.valueOf(mTable.getDealer().getHandValue()));

            resolve();
}


    // resolve the hand
    public void resolve() {
        if (mTable.checkTableFinished()) {
            TextView resultsText = (TextView) findViewById(R.id.result_text);
            resultsText.setText(mTable.printResults());
            mTable.printResults();
        }
    }


    // event handler for deal button
    public void buttonDeal(View view){
        Log.d("TwentyOne", "Deal button clicked!");

        if(mTable.startNewGame())
            mTable.checkTable();

        TextView playerText = (TextView)findViewById(R.id.player_result_text);
        playerText.setText(String.valueOf(mTable.getCurrentPlayerIndex().getHandValue()));

        TextView dealerText = (TextView)findViewById(R.id.dealer_result_text);
        dealerText.setText(String.valueOf(mTable.getDealer().getHandValue()));
    }
}







