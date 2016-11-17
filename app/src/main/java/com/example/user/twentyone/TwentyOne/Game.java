package com.example.user.twentyone.TwentyOne;

import android.util.Log;

import java.util.ArrayList;

import static com.example.user.twentyone.TwentyOne.Game.State.NEW_GAME;
import static com.example.user.twentyone.TwentyOne.Game.State.RESOLVE;

/**
 * Created by user on 14/11/2016.
 */

public class Game {

    private ArrayList<Player> players;
    private Player dealer;
    private Deck deck;
    private State tableState;
    int currentPlayerIndex;


    public enum State {
        NEW_GAME,
        PLAYING,
        RESOLVE
    }

    public Game() {
        this.tableState = NEW_GAME;
        this.players = new ArrayList<Player>();
        this.dealer = new Player("Dealer");
        this.deck = new Deck();
    }

    // ----------- player can join table
    public void sitAtTable(Player player) {
        this.players.add(player);
    }


    // ----------- getter for current player
    public Player getCurrentPlayerIndex() {
        if (this.currentPlayerIndex > players.size() - 1)
            return this.players.get(currentPlayerIndex - 1);
        else
            return players.get(currentPlayerIndex);
    }

    //  ----------  getter for the dealer
    public Player getDealer() {
        return this.dealer;
    }


    // ----------- check if the table hand has finished
    public boolean checkTableFinished(){
        if(this.tableState == State.RESOLVE) {
            return true;
        }
        else {
            return false;
        }
    }

    // when start up a new game that will get everybody a card to play the first round
    // check the table state of each round. If it's a new game it will loop through, else it will
    // ask the players for their response. Once response given it goes back in and checks the loop

    private void setUpNewGame() {
//------------- players start at zero, initialise the deck, reset each game - clear the hand
        currentPlayerIndex = 0;
        deck.initialiseDeck();
        dealer.clearHand();

        for (int i = 0; i < players.size(); i++) {
            this.players.get(i).setState(Player.State.PLAYING);
            this.players.get(i).clearHand();
        }
        while (dealer.getCardCount() < 2) {
            for (int i = 0; i < this.players.size(); i++)
                this.players.get(i).hit(deck.dealCard());
            this.dealer.hit(deck.dealCard());
        }
        this.tableState = State.PLAYING;
    }


// -------------- massively needs refactoring, this is where the results are being printed wrong
    public void checkTable() {
        switch (tableState) {
            case NEW_GAME:
                setUpNewGame();
                break;

// ------------- state of Playing, ask players for an action, loops for players action to see if it's hit, bust or stand
// ------------- checks if the current player is bust if not then increment++

            case PLAYING: {

                Player currentPlayer = this.players.get(currentPlayerIndex);
                Player.State currentState =  currentPlayer.getState();


                if (currentState != Player.State.STAND ||
                        currentState != Player.State.BUST) {

                    if (currentPlayer.askAction() == Player.Action.HIT) {
                        currentPlayer.hit(deck.dealCard());

                        if (currentPlayer.getHandValue() > 21) {
                            currentPlayer.setState(Player.State.BUST);

                        } else if (currentPlayer.getHandValue() == 21) {
                            currentPlayer.setState(Player.State.STAND);
                        }
                    }
                    // check if the player stands, else increment the current player by 1 (++)
                    if (currentPlayer.askAction() == Player.Action.STAND) {
                        currentPlayer.setState(Player.State.STAND);
                        currentPlayer.setAction(Player.Action.WAIT);
                        currentPlayerIndex++;
                    }
                    else if (currentState != Player.State.BUST) {
                        currentPlayer.setAction(Player.Action.WAIT);
                        currentPlayerIndex++; // action for the player to wait
                    }
                }
                if (currentPlayerIndex > players.size() - 1)
                    tableState = RESOLVE;
                else
                    break;
            }
            case RESOLVE:

// -------------- if the dealer is less than 17 they have to draw a card, hit deck...
                while (this.dealer.getHandValue() < 17)
                    this.dealer.hit(deck.dealCard());

//                resolve dealer hand
                if(this.dealer.getHandValue() > 21)
                    this.dealer.setState(Player.State.BUST);

// --------------- show winner result
                if (this.dealer.getState() == Player.State.BUST) {

                    for (int i = 0; i < this.players.size(); i++) {
                        if (this.players.get(i).getState() != Player.State.BUST) {
                            this.players.get(i).setState(Player.State.WON);
                        }
                    }
                }
// not bust, check players hand value > or < than dealers and not bust. If hand < dealers hand Lose, > Win.
                else
                {
                    for (int i = 0; i < this.players.size(); i++) {
                        if (this.players.get(i).getState() != Player.State.BUST) {
                            if (this.players.get(i).getHandValue() < this.dealer.getHandValue())
                                this.players.get(i).setState(Player.State.LOST);
                            if (this.players.get(i).getHandValue() < this.dealer.getHandValue())
                                this.players.get(i).setState(Player.State.WON);
                            if (this.players.get(i).getHandValue() == this.dealer.getHandValue())
                                this.players.get(i).setState(Player.State.PUSH);
                        }
                    }
                }

            default:
                break;
        }
    }


    // ----------------- start a new game
    public boolean startNewGame() {
        if (this.tableState != State.PLAYING)
            this.tableState = State.NEW_GAME;
        return this.tableState != State.PLAYING;
    }


    // ----------------- take in the list of current players and output the table
    public void clearTable() {
        for (int i = 0; i < this.players.size(); i++) {
            Log.v(String.valueOf(i), String.valueOf(this.players.get(i).getHandValue()));
        }
        Log.v("Dealer", String.valueOf(this.dealer.getHandValue()));
    }


//  print hand result
    public String printResults() {
        String result = " ";
        for (int i = 0; i < this.players.size(); i++)
        {
            // concatenate / chain string with name and results
            result += "Player: " + this.players.get(i).getName() + " " + this.players.get(i).getState().toString();
        }
        return result;
    }
}