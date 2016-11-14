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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // log the onCreate call
        Log.d("Main", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create table to play at
        Table table = new Table();

        // add players to the table
        Player player = new Player("kyle");
        Player player2 = new Player("jeff");

        // add those players to the table
        table.sitAtTable(player);
        table.sitAtTable(player2);

        // check table state
        table.checkTable();
        // remove the table after finished
        table.removeTable();

    }

//    public void buttonClicked(View view) {
//        TextView textView = (TextView) findViewById(R.id.textView);
//        Log.d("Main", "button clicked");
//    }


}





