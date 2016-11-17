package com.example.user.twentyone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.twentyone.TwentyOne.Card;
import com.example.user.twentyone.TwentyOne.Player;
import com.example.user.twentyone.TwentyOne.Game;

public class MainActivity extends AppCompatActivity {

    Player mPlayer;
    Game mGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // log the onCreate call
        Log.d("Main", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // create table to play at, add player to the table
        mGame = new Game();
        mPlayer = new Player("You ");
        mGame.sitAtTable(mPlayer);
        mGame.checkTable();

    }


    // event handler for HIT
    public void buttonHit(View view) {
        mGame.getCurrentPlayerIndex().setAction(Player.Action.HIT);
        Log.d("TwentyOne", "Hit button clicked!");
        mGame.checkTable();
        mGame.clearTable();

        TextView playerText = (TextView) findViewById(R.id.player_result_text);
        playerText.setText("Your Hand: " + mGame.getCurrentPlayerIndex().getHandValue());

        TextView dealerText = (TextView) findViewById(R.id.dealer_result_text);
        dealerText.setText("Dealer Hand: " + mGame.getDealer().getHandValue());

        resolve();
    }


    // event handler for the STAND option
    public void buttonStand(View view) {

        mGame.getCurrentPlayerIndex().setAction(Player.Action.STAND);
        Log.d("TwentyOne", "Stand button clicked!");
        mGame.checkTable();

        TextView playerText = (TextView)findViewById(R.id.player_result_text);
        playerText.setText("Your Hand: " + mGame.getCurrentPlayerIndex().getHandValue());

        TextView dealerText = (TextView)findViewById(R.id.dealer_result_text);
        dealerText.setText("Dealer Hand: " + mGame.getDealer().getHandValue());

        resolve();
    }


    // resolve the hand
    public void resolve() {
        if (mGame.checkTableFinished()) {
            TextView resultsText = (TextView) findViewById(R.id.result_text);
            resultsText.setText(mGame.printResults());
            mGame.printResults();
        }
    }


    // event handler for deal button
    public void buttonDeal(View view){
        Log.d("TwentyOne", "Deal button clicked!");

        if(mGame.startNewGame()) {
            mGame.checkTable();
        }

        TextView playerText = (TextView)findViewById(R.id.player_result_text);
        playerText.setText("Your Hand: " + mGame.getCurrentPlayerIndex().getHandValue());

        TextView dealerText = (TextView)findViewById(R.id.dealer_result_text);
        dealerText.setText("Dealer Hand: " + mGame.getDealer().getHandValue());
    }

    @Override
    // boolean return type - whether menu as created successfully
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if (item.getItemId() == R.id.action_pity){
            Log.d("Menu", "Menu item 'Pity Fool' clicked");
            Toast.makeText(MainActivity.this, R.string.give_pity, Toast.LENGTH_LONG).show();
            return true;
        }
        if (item.getItemId() == R.id.cash_out){
            Log.d("Menu", "Menu item 'Pity Fool' clicked");
            Toast.makeText(MainActivity.this, R.string.cash_out, Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}