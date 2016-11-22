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



    // when start up a new game that will get everybody a card to play the first round
    // check the table state of each round. If it's a new game it will loop through, else it will
    // ask the players for their response. Once response given it goes back in and checks the loop


    private void setUpNewGame() {
        currentPlayerIndex = 0;
        deck.initialiseDeck ();
        dealer.clearHand ();
//------------- players start at zero, initialise the deck, reset each game - clear the hand
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


// now only handles the playing state, the instance of current player and current states are used to compare and evaluate
    // where game play is

    private void handlePlaying() {
        switch(tableState) {
        // table state is the variable being tested
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

//    allows a variable to be tested for equality against a list of values. Each value is called a case, and the variable being
// switched on is checked for each case.
//    test for a range of values for your variables

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
//                            what you want to happen if the value matches.
                            if ( this.players.get(i).getHandValue() < dealer.getHandValue() )
                                players.get(i).setState( Player.State.LOST );
//                            what you want to happen if the value matches.
                            if ( players.get(i).getHandValue() > dealer.getHandValue() )
                                players.get(i).setState( Player.State.WON );
//                            what you want to happen if the value matches.
                            if ( players.get(i).getHandValue() == dealer.getHandValue() )
                                players.get(i).setState( Player.State.PUSH );
                                }
                            }
                        }
            default :
                break;
                }
            }


// the overall procedure of game play where the main activity checks the state of the players and their hands/cards
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
//           default terminates the enclosing switch statement
        }
    }



    public boolean startNewGame () {
        if ( tableState != State.PLAYING )
            tableState = State.NEW_GAME;
        return tableState != State.PLAYING;
    }

// initialised once ; while true (player.size) ; increment
//Conditional check - It checks to verify whether or not certain expression evaluates to true. If it is, then the loop execution
// continues.
    public void clearTable () {
        for (int i = 0 ; i < players.size() ; i++ ) {
            Log.v (String.valueOf(i), String.valueOf(players.get(i).getHandValue ()) );
        }
        Log.v( "Dealer", String.valueOf ( dealer.getHandValue ()) );
    }


    public String printResults () {
        String result = " ";
        for ( int i = 0 ; i < players.size() ; i++ ) {
            result += "#####  PLAYER " + players.get(i).getName() + " " + players.get(i).getState().toString() + "  #####";
        }
        return result;
    }

}