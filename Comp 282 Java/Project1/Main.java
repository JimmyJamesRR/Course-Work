// Team Member Spock: Alkheraigi, Meshari 
// Team Member Bones: Renger, James 
// Class: COMP282 
// Assignment: Project 1
// Filelist: Main.java, card.java, hand.java, handScoreComparator.java

package pkg282project1;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {        
        //this will be converted to a scanner, to allow user input, this is just static for testing.
       /*Scanner scanner = new Scanner( System.in );
       System.out.println("Please enter the dealth cards seperated by spaces:");
       System.out.println("(Ex: 2c 2s 6d 7s 8h Kh..."); 
       String input = scanner.nextLine();*/
       String input = 
                "2c 2s 6d 7s 8h 2h 2d 4d Jc Kc Qc Tc 6c 7h 9s 9h 9d Qs Ks Js";  //Instructor Input: 2c 2s 6d 7s 8h Kh Jd 4d Jc Kc Qc Tc 9c Jh 9s 9h 9d Qs Ks Js      
        Comparator<hand> handScoreComparator = new handScoreComparator();
        PriorityQueue<hand> table = 
            new PriorityQueue<hand>(5, handScoreComparator);
        
        for(int players = 1; players < 6;players++){
            table.offer(new hand(input,players));
        }
        System.out.println("The winning hands order is.... ::drumroll::");
        while(!(table.peek() == null))
            table.poll().printBest();        
    }   
}