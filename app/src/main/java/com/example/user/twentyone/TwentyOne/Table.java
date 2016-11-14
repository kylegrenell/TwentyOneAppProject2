package com.example.user.twentyone.TwentyOne;

import java.util.ArrayList;

/**
 * Created by user on 14/11/2016.
 */

public class Table {

    ArrayList<Player> players;
    Player dealer;
    Deck deck;
    State tableState;

    private enum State{
        NEW_GAME,
        PLAYING,
        PAUSE,
    }

    public Table(){
        tableState = State.NEW_GAME;
        players = new ArrayList<Player>();
        dealer = new Player("Dealer");
        deck = new Deck();
    }

    // player can join table
    public void sitAtTable(Player player){
        players.add(player);
    }

    // can leave the table
    public void leaveTable(Player player){
        players.remove(player);
    }


    // when start up a new game that will get everybody card and play first round
    // check the table state of each round . If it's a new game it will loop through, else it will
    // ask the players for their response. Once response given it goes back in and checks the loop
    public void checkTable(){

        switch(tableState){

            case NEW_GAME: {

                while (dealer.getCardCount() < 2) {

                    for (int i = 0; i < players.size(); i++)

                        players.get(i).hit(deck.dealCard());

                dealer.hit(deck.dealCard());
            }
            break;
            }
            default:
                break;
        }
    }

    // take in the list of current players and output the table
    public void removeTable(){
        for(int i = 0; i < players.size(); i++){
            players.get(i).showHand();
            }
        dealer.showHand();
        }
    }



