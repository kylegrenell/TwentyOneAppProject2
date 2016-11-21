package com.example.user.twentyone.TwentyOne;

import android.util.Log;

import java.util.ArrayList;

import static com.example.user.twentyone.TwentyOne.Game.State.NEW_GAME;
import static com.example.user.twentyone.TwentyOne.Game.State.RESOLVE;

/**
 * Created by user on 14/11/2016.
 */

public class Game {

    private final ArrayList <Player> players;
    private final Player dealer;
    private Card card;
    private final Deck deck;
    private State tableState;
    private int currentPlayerIndex;


    public enum State {
        NEW_GAME,
        PLAYING,
        RESOLVE
    }

    public Game() {
        this.tableState = NEW_GAME;
        this.players = new ArrayList <Player> ();
        this.dealer = new Player( "Dealer" );
        this.deck = new Deck();
    }


    public void sitAtTable(Player player) {
        this.players.add(player);
    }


    public Player getCurrentPlayerIndex() {
        if ( this.currentPlayerIndex > players.size () - 1 )
            return this.players.get(currentPlayerIndex - 1 );
        else
            return players.get ( currentPlayerIndex );
    }

    public Player getDealer() {
        return this.dealer;
    }


    public boolean checkTableFinished() {
        return this.tableState == State.RESOLVE;
    }


    private void setUpNewGame() {
        currentPlayerIndex = 0;
        deck.initialiseDeck ();
        dealer.clearHand ();

        for ( int i = 0 ; i < players.size () ; i++ ) {
            this.players.get(i).setState( Player.State.PLAYING );
            this.players.get(i).clearHand();
        }
        while ( dealer.getCardCount () < 2 ) {
            for ( int i = 0 ; i < this.players.size() ; i++ )
                this.players.get( i ).hit ( deck.dealCard () );
            this.dealer.hit( deck.dealCard() );
        }
        this.tableState = State.PLAYING;
    }


    private void handlePlaying() {
        switch(tableState) {

            case PLAYING:

                Player currentPlayer = this.players.get( currentPlayerIndex );
                Player.State currentState = currentPlayer.getState ();

                if ( currentPlayer.askAction() == Player.Action.HIT ) {
                    currentPlayer.hit( deck.dealCard() );
                    if ( currentPlayer.getHandValue() > 21 ) {
                        currentPlayer.setState( Player.State.BUST );
                    } else if
                            ( currentPlayer.getHandValue() == 21 ) {
                        currentPlayer.setState( Player.State.STAND );
                    }
                }
                if ( currentPlayer.askAction() == Player.Action.STAND ) {
                    currentPlayer.setState( Player.State.STAND );
                    currentPlayer.setAction( Player.Action.WAIT );
                    currentPlayerIndex++;
                } else if ( currentState != Player.State.BUST ) {
                    currentPlayer.setAction( Player.Action.WAIT );
                    currentPlayerIndex++;
                }
                if ( currentPlayerIndex > players.size() - 1 )
                    tableState = RESOLVE;
                else
                    break;
            default :
                break;
            }
        }



    private void handleResolve ( ) {
        switch(tableState) {

            case RESOLVE:

                while ( this.dealer.getHandValue() < 17 )
                    this.dealer.hit( deck.dealCard() );

                if ( this.dealer.getHandValue() > 21 )
                    this.dealer.setState( Player.State.BUST );

                if ( this.dealer.getState() == Player.State.BUST ) {
                    for ( int i = 0 ; i < this.players.size() ; i++ )
                    {
                        if
                            ( this.players.get(i).getState() != Player.State.BUST ) {
                            this.players.get(i).setState( Player.State.WON );
                        }
                    }
                } else {
                    for ( int i = 0 ; i < this.players.size() ; i++ ) {

                        if ( this.players.get(i).getState() != Player.State.BUST ) {

                            if ( this.players.get(i).getHandValue() < dealer.getHandValue() )
                                players.get(i).setState( Player.State.LOST );

                            if ( players.get(i).getHandValue() > dealer.getHandValue() )
                                players.get(i).setState( Player.State.WON );

                            if ( players.get(i).getHandValue() == dealer.getHandValue() )
                                players.get(i).setState( Player.State.PUSH );
                                }
                            }
                        }
            default :
                break;
                }
            }



    public void checkTable() {
        switch (tableState) {
            case NEW_GAME:
                setUpNewGame();
                break;

            case PLAYING:
                handlePlaying();
                break;

            case RESOLVE:
                handleResolve();
                break;

            default:
                break;
        }
    }



    public boolean startNewGame () {
        if ( tableState != State.PLAYING )
            tableState = State.NEW_GAME;
        return tableState != State.PLAYING;
    }


    public void clearTable () {
        for (int i = 0 ; i < players.size() ; i++ ) {
            Log.v (String.valueOf(i), String.valueOf(players.get(i).getHandValue ()) );
        }
        Log.v( "Dealer", String.valueOf ( dealer.getHandValue ()) );
    }


    public String printResults () {
        String result = " ";
        for ( int i = 0 ; i < players.size() ; i++ ) {
            result += "Player: " + players.get(i).getName() + " " + players.get(i).getState().toString();
        }
        return result;
    }




}