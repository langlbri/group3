/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

/**
 *
 * @author group 3 
 */
import java.util.Scanner;

public class BlackjackGame extends Game {

    private Shoe shoe;  //game has a shoe - shoe is made of 6 decks - see shoe class
    private Dealer dealer; // game has a dealer - only dealer handles shoe 
    private BlackjackPlayer player; //game has a BlackjackPlayer 
    private int playerScore; //keeping score of rounds won by player
    private int dealerScore;// keeping score of rounds won by dealer 

    public BlackjackGame(String name) {
        super(name);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();
        player = new BlackjackPlayer(playerName);

        // Create a shoe with 6 decks and pass it to the dealer.
        Shoe shoe = new Shoe(6);
        dealer = new Dealer("Dealer", shoe);
        getPlayers().add(player);
        getPlayers().add(dealer);

        //initialize scores to 0 
        playerScore = 0;
        dealerScore = 0;
    }

    @Override
    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Welcome to ** BLACKJACK ** \nWould you like to play a round of Blackjack? (Y/N): ");
        String startResponse = scanner.nextLine().trim();

        // if input is NOT "Y" for yes, then print goodbye message 
        if (!startResponse.equalsIgnoreCase("Y")) {
            System.out.println("Thanks for playing! Bye!");
            return;
        }

        //a flag to loop through game logic while continuePlaying = true, game ends when player says no, and flag changes to false. 
        boolean continuePlaying = true;
        //while player wants to keep playing, repeat the loop
        while (continuePlaying) {
            startRound(scanner); //call start round method below
            declareWinner(); //after the round, declare the winner 
            //print the score 
            System.out.println("Current Score: Dealer " + dealerScore + " : "
                    + player.getName() + " " + playerScore);

            //validation 
            boolean validResponse = false;
            while (!validResponse) {
                System.out.print("Play another round? (Y/N): ");
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("Y")) {
                    validResponse = true;
                    player.clearHand();
                    dealer.clearHand();
                } else if (response.equalsIgnoreCase("N")) {
                    validResponse = true;
                    continuePlaying = false;
                } else {
                    System.out.println("Invalid input. Please enter Y or N.");
                }
            }
        }
        System.out.println("Bye! Thanks for playing!");
    }

    //logic for starting the round and playing the round
    private void startRound(Scanner scanner) {
        // Dealer deals initial cards: first to player, then to dealer (face up), then player's 2nd card, then dealer's 2nd (hidden) card.
        dealer.dealInitialCards(player);

        System.out.println(player.getName() + "'s hand: " + player.getHand()
                + " (Total: " + player.getHand().calculateHandValue() + ")");
        // Showing only the dealer's first card 
        System.out.println("Dealer shows: " + dealer.getHand().getCards().get(0));

        // Checking if the dealer has blackjack on initial deal 
        if (dealer.mightHaveBlackjack()) {
            System.out.println("Dealer is checking for Blackjack...");
            if (dealer.getHand().isBlackjack()) {
                System.out.println("***** Dealer has Blackjack! *****");
                System.out.println("Dealer's full hand: " + dealer.getHand()
                        + " (Total: " + dealer.getHand().calculateHandValue() + ")");

                //checking if player ALSO has blackjack on initial deal 
                if (player.getHand().isBlackjack()) {
                    //if yes, it's a tie 
                    System.out.println("Both Dealer and Player have Blackjack! It's a PUSH.");
                    //if not, dealer wins 
                } else {
                    System.out.println("Dealer wins with Blackjack!");
                    //dealer score goes up for running total of rounds won 
                    dealerScore++;
                }
                return;
            }
        }

        // Check if the player has Blackjack on the initial deal 
        if (player.getHand().isBlackjack()) {
            System.out.println("******** Blackjack! " + player.getName() + " wins! ********");
            playerScore++;
            System.out.println("Dealer's full hand: " + dealer.getHand()
                    + " (Total: " + dealer.getHand().calculateHandValue() + ")");
            return;
        }

        // player's turn logic in the player's play() method
        // passing dealer and scanner
        player.play(dealer, scanner);

        // checking if player's hand is over 21, if so, the round ends. 
        if (player.getHand().isBusted()) {
            dealerScore++;
            return;
        }

        // dealer's turn (logic in Dealer class play() method)
        dealer.play();
    }

    //declaring the winner by comparing hand values, increasing the player or dealer score counter accordingly 
    @Override
    public void declareWinner() {
        int playerValue = player.getHand().calculateHandValue();
        int dealerValue = dealer.getHand().calculateHandValue();
        if (player.getHand().isBusted()) {
            System.out.println("PLAYER BUSTS. DEALER WINS.");
            dealerScore++;
        } else if (dealer.getHand().isBusted()) {
            System.out.println("DEALER BUSTS. PLAYER WINS!");
            playerScore++;
        } else if (playerValue > dealerValue) {
            System.out.println(player.getName() + " wins with " + playerValue
                    + " over dealer's " + dealerValue + ".");
            playerScore++;
        } else if (dealerValue > playerValue) {
            System.out.println("Dealer wins with " + dealerValue
                    + " over " + player.getName() + "'s " + playerValue + ".");
            dealerScore++;
        } else {
            System.out.println("PUSH. Both have " + playerValue + ".");
        }
    }
}

