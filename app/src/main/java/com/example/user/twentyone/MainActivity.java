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
import com.example.user.twentyone.TwentyOne.Table;


public class MainActivity extends AppCompatActivity {

    Player player;
    Table table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // log the onCreate call
        Log.d("Main", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create table to play at
        table = new Table();

        // add players to the table
        player = new Player("kyle");

        // add those players to the table
        table.sitAtTable(player);

        // check table state
        table.checkTable();
        // clear the table after finished
        table.clearTable();

    }

    public void buttonClicked(View view) {
        player.setAction(Player.Action.HIT);
        table.clearTable();
//        TextView textView = (TextView) findViewById(R.id.textView);
//        Log.d("Main", "button clicked");
    }


}





