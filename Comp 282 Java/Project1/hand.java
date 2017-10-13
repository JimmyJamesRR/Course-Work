// Team Member Spock: Alkheraigi, Meshari 
// Team Member Bones: Renger, James 
// Class: COMP282 
// Assignment: Project 1
// Filelist: Main.java, card.java, hand.java, handScoreComparator.java

package pkg282project1;

import java.text.DecimalFormat;

public class hand {
    card[] playerCards = new card[3],
           communityCards = new card[5],
           bestHand = new card[6];
    int playerNum,
        topRank = 0,
        topSuit = 0,
        score = 0;
    DecimalFormat scoreFormat = new DecimalFormat("000000");
    
    protected hand(String input, int playNumb){
        playerNum = playNumb;
        String[] split = new String[20];
        split = input.split("\\s+");
        card[] totalCards = new card[8]; 
        for(int index=0; index < 5;index++){
            communityCards[index] = new card(split[index]);
            totalCards[index] = communityCards[index];
        }
        for(int index=0; index < 3;index++){
            playerCards[index] = new card(split[(2+(playNumb*3)+index)]);
            totalCards[index+5] = new card(split[(2+(playNumb*3)+index)]);
        }
        order(totalCards);
    }
    protected void printBest(){
        System.out.print("Player " + playerNum + " with ");
        for(int index = 0; index < 6;index++)
            bestHand[index].print();        
        System.out.println("" + scoreFormat.format(score));
    }
    private void order(card[] availibleCards){   
      card[] temp = new card[6];
      for(int firstCard = 0; firstCard < 8; firstCard++){       
          temp[0] = availibleCards[firstCard];
          for(int secondCard = 0; secondCard < 8 ; secondCard++){           
            if(secondCard == firstCard)
                continue;            
            temp[1] = availibleCards[secondCard];       
            for(int thirdCard = 0; thirdCard < 8; thirdCard++){            
             if(thirdCard == firstCard || thirdCard == secondCard)
                 continue;                              
             temp[2] = availibleCards[thirdCard];  
             for(int fourthCard = 0; fourthCard < 8 ;fourthCard++){             
                 if(fourthCard == firstCard || fourthCard == secondCard 
                 || fourthCard == thirdCard)
                     continue;                                  
                 temp[3] = availibleCards[fourthCard];   
                 for(int fifthCard = 0; fifthCard < 8; fifthCard++){
                   if(fifthCard == firstCard || fifthCard == secondCard 
                         || fifthCard == thirdCard || fifthCard == fourthCard)
                       continue;
                   temp[4] = availibleCards[fifthCard];
                   for(int sixthCard = 0; sixthCard < 8 ;sixthCard++){
                     if(sixthCard == firstCard || sixthCard == secondCard 
                        || sixthCard == thirdCard || sixthCard == fourthCard
                        || sixthCard == fifthCard)
                         continue;
                       temp[5] = availibleCards[sixthCard];
                     score(temp);                       
                     }
                    }
                }
            }
        }
    }   
 }
    private void score(card[] heldHand){  
        // The heldHand provides you player and community cards combined,
        // You can test these cards to make a hand of 6, and then output
        // The total score
        // Scores should be ranked by rank/top card/suit 2 digits each        
        // (ex. 010204)1 pair of 2s, top spades
        nonRainbow(heldHand);                   //2♣ 3♣ 4♣ 5♦ 8♦ J♥ You don’t have at least one of each suit. 
        onePair(heldHand);                      //J♣ J♦ 4♦ 8♥ 7♥ 5♦ You have two cards of the same rank. 
        rainbow(heldHand);                      //8♣ 4♥ 6♦ Q♠ K♠ A♣ You have one of each suit. 
        twoPair(heldHand);                      //8♥ 8♦ A♥ A♣ 3♥ 7♣ You hold four cards that consist of only two ranks. 
/*
        ThreeOfAKind(heldHand);                 //2♥ 3♥ 4♣ 4♦ 4♣ 9♠ You have three cards of the same rank. 
        swingers(heldHand);                     //K♠ Q♠ 8♦ 3♥ K♦ Q♦ You have two sets of suited Kings and Queens. 
        /////fiveCardStraight(heldHand);             //3♦ 7♥ 8♣ 9♠ T♥ J♦ You hold ﬁve cards in numerical sequence (A is either 1 or 13). 
*/
        monochromatic(heldHand);                //8♦ 4♦ Q♥ 4♦ 9♥ K♦ Your cards are either all black or all red. 
        fullhouse(heldHand);                    //4♥ 4♦ 8♣ Q♠ Q♠ Q♠ You hold both a pair of something and three of a kind. 
        threePair(heldHand);                    //4♠ 4♦ 8♣ 8♦ 9♥ 9♠ Cards consist of exactly three unique ranks. 
/*
        Monarchy(heldHand);                     //4♠ 8♠ T♥ J♠ Q♠ K♠ A Jack, Queen and King of the same suit and no other face cards. 
        Even(heldHand);                         //2♥ 4♠ 6♦ 8♣ T♣ T♥ All your cards are a 2,4,6,8 or 10. 
        sixCardStraight(heldHand);              //6♦ 7♥ 8♣ 9♠ T♥ J♦ You have six cards in numerical order (like 5 card straight but 6). 
*/
        fourOfKind(heldHand);                   //6♣ 6♦ 6♠ 6♥ 8♥ 9♦ You hold four of a kind. 
        odd(heldHand);                          //3♠ 5♥ 7♦ 7♦ 9♣ 9♠ All of your cards are a 3,5,7 or 9. 
        flush(heldHand);                        //3♠ 5♠ 6♠ 7♠ J♠ Q♠ All six of your cards are the same suit. 
/*
        triplets(heldHand);                     //3♠ 3♦ 3♥ T♦ T♣ T♥ You have two diﬀerent three of a kinds. 
        overFullHouse(heldHand);                //5♠ 5♦ 5♥ 5♣ J♠ J♣ Four of a kind and a pair. 
        homosapiens(heldHand);                  //J♠ J♦ J♣ Q♠ Q♦ K♣ All your cards are face cards. 
*/
        kingdom(heldHand);                      //4♠ 8♠ T♠ J♠ Q♠ K♠ (Monarchy + ﬂush) a Monarchy with remaining cards of the same suit! 
        fiveCardStraightFlush(heldHand);         //3♦ 7♦ 8♦ 9♦ T♦ J♦ 5 cards straight but all six cards are the same suit. 
        sixCardStraightFlush(heldHand);         //6♦ 7♦ 8♦ 9♦ T♦ J♦ 6 cards straight all in the same suit. 
/*
        orgy(heldHand);                         //J♠ J♦ J♣ J♥ Q♠ Q♥ All your cards are Jacks and Queens. 
        politics(heldHand);                     //J♠ J♦ Q♠ Q♦ K♠ K♦ You hold two Monarchys. 
        dinnerParty(heldHand);   */               //Q♠ Q♦ Q♥ K♠ K♦ K♥ All your cards are suited kings and queens.
    }
    private void updateScore(int sValue,int tRank,int tSuit,card[] currHeld){
        
        if(score < ((sValue * 10000) + (tRank * 100) + tSuit)){
                topRank = tRank;
                topSuit = tSuit;
                score = (sValue * 10000) + (tRank * 100) + tSuit;
                bestHand = currHeld.clone();
            }
    }
    private void nonRainbow(card[] noRainbowHeld){
        
        int indiSuitCounter[] = new int[4]; //I heard you like counters, dawg.  
        int totalSuitCounter = 0,     //So we've got counters for your counters.
            tempSuit = 0,
            tempRank = 0;
        for(int index = 0; index < 6;index++){
            if(noRainbowHeld[index].suit >= tempSuit){ 
             if(noRainbowHeld[index].rank >= tempRank|| 
                noRainbowHeld[index].suit >= tempSuit){
                    tempRank = noRainbowHeld[index].rank;
                    tempSuit = noRainbowHeld[index].suit;
                }
            }
            switch (noRainbowHeld[index].suit) {
                case 4:
                    indiSuitCounter[3]++;
                    break;
                case 3:
                    indiSuitCounter[2]++;
                    break;
                case 2:
                    indiSuitCounter[1]++;
                    break;
                case 1:
                    indiSuitCounter[0]++;
                    break;
                default:
                    break;
            }
        }           
        for(int index = 0;index < 4;index++){        
            if(indiSuitCounter[index] > 0)
                totalSuitCounter++;                        
        }
        if(totalSuitCounter < 4)
            updateScore(1, tempRank,tempSuit,noRainbowHeld);
    }
    private void onePair(card[] pairHeld){
        
        int tempSuit = pairHeld[0].suit,
            tempRank = pairHeld[0].suit;       
        if(pairHeld[0].rank == pairHeld[1].rank){
            if(pairHeld[0].rank < pairHeld[1].rank)
                tempSuit = pairHeld[1].suit;                    
            updateScore(2, tempRank,tempSuit,pairHeld);
        }                        

    }
    private void rainbow(card[] rainbowHeld){
        
        int indiSuitCounter[] = new int[4]; //I heard you like counters, dawg.  
        int totalSuitCounter = 0,     //So we've got counters for your counters.
            tempSuit = 0,
            tempRank = 0;
        for(int index = 0; index < 6; index++){
            if(rainbowHeld[index].suit >= tempSuit){ 
             if(rainbowHeld[index].rank >= tempRank|| 
                rainbowHeld[index].suit >= tempSuit){
                    tempRank = rainbowHeld[index].rank;
                    tempSuit = rainbowHeld[index].suit;
                }
            }
            switch (rainbowHeld[index].suit) {
                case 4:
                    indiSuitCounter[3]++;                    
                    break;
                case 3:
                    indiSuitCounter[2]++;
                    break;
                case 2:
                    indiSuitCounter[1]++;
                    break;
                case 1:
                    indiSuitCounter[0]++;
                    break;
                default:
                    break;
            }
        }           
        for(int index = 0;index < 4;index++){        
            if(indiSuitCounter[index] > 0)
                totalSuitCounter++;                        
        }
        if(totalSuitCounter == 4)
            updateScore(3,tempRank,tempSuit,rainbowHeld);        
    }
    private void twoPair(card[] twoPairHeld){
        int tempSuit = twoPairHeld[0].suit,
            tempRank = twoPairHeld[0].suit;       
        if(twoPairHeld[0].rank == twoPairHeld[1].rank 
         && twoPairHeld[2].rank == twoPairHeld[3].rank){
            for(int index = 0;index < 3;index++){
                if(twoPairHeld[index].rank >= tempRank){
                    if(twoPairHeld[index].suit > tempSuit 
                      || twoPairHeld[index].rank >= tempRank)
                        tempSuit = twoPairHeld[index].suit;
                }
            }
            updateScore(4, tempRank,tempSuit,twoPairHeld);
        }
    }
    
    private void monochromatic(card[] monoHeldHand){                //8♦ 4♦ Q♥ 4♦ 9♥ K♦ Your cards are either all black or all red. 
        int totalSuitCounter = 0,     
            tempSuit = 0,
            tempRank = 0;
        for(int index = 0; index < 6;index++){
            if(monoHeldHand[index].suit == 4 || monoHeldHand[index].suit == 2)
                totalSuitCounter++;            
            if(monoHeldHand[index].suit > tempSuit)
                tempSuit = monoHeldHand[index].suit;
            if(monoHeldHand[index].suit == tempSuit 
                    && monoHeldHand[index].rank > tempRank)
                tempRank = monoHeldHand[index].rank;            
        }
        if(totalSuitCounter == 0 || totalSuitCounter == 6)
            updateScore(8, tempRank,tempSuit,monoHeldHand);
    }
    private void fullhouse(card[] fullHeldHand){                    //4♥ 4♦ 8♣ Q♠ Q♠ Q♠ You hold both a pair of something and three of a kind. 
        int tempSuit = 0,
            tempRank = fullHeldHand[2].rank;
        if(fullHeldHand[0].rank == fullHeldHand[1].rank 
           && fullHeldHand[2].rank== fullHeldHand[3].rank 
           && fullHeldHand[2].rank == fullHeldHand[4].rank){
            tempRank = fullHeldHand[2].rank;
            for(int index = 2; index < 5;index++){
                if(fullHeldHand[index].suit > tempSuit)
                    tempSuit = fullHeldHand[index].suit ;
            }
            updateScore(9, tempRank,tempSuit,fullHeldHand);
        }        
    }    
    private void threePair(card[] threePairHeldHand){                    //4♠ 4♦ 8♣ 8♦ 9♥ 9♠ Cards consist of exactly three unique ranks. 
        int tempRank = 0,
            tempSuit = 0;
        if(threePairHeldHand[0].rank == threePairHeldHand[1].rank
                && threePairHeldHand[2].rank == threePairHeldHand[3].rank
                && threePairHeldHand[4].rank == threePairHeldHand[5].rank){
            for(int index = 0; index < 6; index++){
                if(threePairHeldHand[index].suit >= tempSuit){ 
                    if(threePairHeldHand[index].rank >= tempRank|| 
                        threePairHeldHand[index].suit >= tempSuit){
                        tempRank = threePairHeldHand[index].rank;
                        tempSuit = threePairHeldHand[index].suit;
                    }
                }
            }
            updateScore(10,threePairHeldHand[5].rank,tempSuit,threePairHeldHand);
        }        
    }
    
    private void fourOfKind(card[] fourHeldHand){       //6♣ 6♦ 6♠ 6♥ 8♥ 9♦ You hold four of a kind. 
        if(fourHeldHand[0].rank == fourHeldHand[1].rank 
                && fourHeldHand[0].rank == fourHeldHand[2].rank
                && fourHeldHand[0].rank == fourHeldHand[3].rank)
        updateScore(14, fourHeldHand[0].rank,4,fourHeldHand);
    }   
    private void odd(card[] oddHeldHand){                          //3♠ 5♥ 7♦ 7♦ 9♣ 9♠ All of your cards are a 3,5,7 or 9. 
        int oddCounter = 0,     
            tempSuit = 0,
            tempRank = 0;
            
        for(int index = 1; index < 6;index++){
            if(oddHeldHand[index].rank != 11 && oddHeldHand[index].rank != 13
                    && (oddHeldHand[index].rank % 2 == 1)){
                if(oddHeldHand[index].suit >= tempSuit){
                    tempSuit = oddHeldHand[index].suit;
                    if(oddHeldHand[index].rank > tempRank)
                        tempRank = oddHeldHand[index].rank;
                }
                oddCounter++;                
            }
        }
        if(oddCounter == 6)
            updateScore(15, tempRank,tempSuit,oddHeldHand);
    }
    private void flush(card[] flushHeldHand){                        //3♠ 5♠ 6♠ 7♠ J♠ Q♠ All six of your cards are the same suit. 
        int totalSuitCounter = 1,     
            tempSuit = flushHeldHand[0].suit,
            tempRank = flushHeldHand[0].rank;
        for(int index = 1; index < 6;index++){
            if(flushHeldHand[index].suit == tempSuit){
                if(flushHeldHand[index].rank > tempRank)
                    tempRank = flushHeldHand[index].rank;
            }
            else if(flushHeldHand[index].suit != tempSuit)
                totalSuitCounter++;
        }
        if(totalSuitCounter == 1)
            updateScore(16, tempRank,tempSuit,flushHeldHand);
    }
    
    private void kingdom(card[] kingHeldHand){                      //4♠ 8♠ T♠ J♠ Q♠ K♠ (Monarchy + ﬂush) a Monarchy with remaining cards of the same suit! 
        int totalSuitCounter = 1,     
            tempSuit = kingHeldHand[0].suit,
            tempRank = kingHeldHand[0].rank;
        for(int index = 1; index < 6;index++){
            if(kingHeldHand[index].suit == tempSuit){
                if(kingHeldHand[index].rank > tempRank)
                    tempRank = kingHeldHand[index].rank;
            }
            else if(kingHeldHand[index].suit != tempSuit)
                totalSuitCounter++;
        }
        if(totalSuitCounter == 1 && kingHeldHand[2].rank == 10 
           && kingHeldHand[3].rank == 11 && kingHeldHand[4].rank == 12
                && kingHeldHand[5].rank == 13)
            updateScore(20, tempRank,tempSuit,kingHeldHand);
    }    
    private void fiveCardStraightFlush(card[] fiveStraHeldHand){         //3♦ 7♦ 8♦ 9♦ T♦ J♦ 5 cards straight but all six cards are the same suit. 
        int totalSuitCounter = 1,     
            tempSuit = fiveStraHeldHand[0].suit,
            tempRank = 0;
        for(int index = 1; index < 5;index++){
            if(fiveStraHeldHand[index].suit == tempSuit){
                if(fiveStraHeldHand[index].rank > tempRank)
                    tempRank = fiveStraHeldHand[index].rank;
            }
            else if(fiveStraHeldHand[index].suit != tempSuit || 
              (fiveStraHeldHand[index].rank - 1) != 
                    fiveStraHeldHand[index - 1].rank)
                totalSuitCounter++;
        }
        if(totalSuitCounter == 1 && fiveStraHeldHand[5].suit == tempSuit)
            updateScore(21, tempRank,tempSuit,fiveStraHeldHand);
    }       
    private void sixCardStraightFlush(card[] sixStraHeldHand){
        int totalSuitCounter = 1,     //So we've got counters for your counters.
            tempSuit = sixStraHeldHand[0].suit,
            tempRank = 0;
        for(int index = 1; index < 6;index++){
            if(sixStraHeldHand[index].suit == tempSuit){
                if(sixStraHeldHand[index].rank > tempRank)
                    tempRank = sixStraHeldHand[index].rank;
            }
            else if(sixStraHeldHand[index].suit != tempSuit || 
              (sixStraHeldHand[index].rank - 1) != 
                    sixStraHeldHand[index - 1].rank)
                totalSuitCounter++;
        }
        if(totalSuitCounter == 1)
        updateScore(22, tempRank,tempSuit,sixStraHeldHand);
    }   
}