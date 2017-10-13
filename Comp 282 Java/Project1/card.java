// Team Member Spock: Alkheraigi, Meshari 
// Team Member Bones: Renger, James 
// Class: COMP282 
// Assignment: Project 1
// Filelist: Main.java, card.java, hand.java, handScoreComparator.java

package pkg282project1;

public class card {
    int suit;
    int rank;
    
    protected card(String rankSuit){
        
        char tempRank = rankSuit.charAt(0);
        char tempSuit = rankSuit.charAt(1);
        
        switch(tempRank){
            case '2': case '3': case '4': case '5':  
            case '6': case '7': case '8': case '9':
                rank = Character.getNumericValue(rankSuit.charAt(0));
                break;
            case 'T': case 't':
                rank = 10;
                break;
            case 'J': case 'j':
                rank = 11;
                break;
            case 'Q': case 'q':
                rank = 12;
                break;
            case 'K': case 'k':
                rank = 13;
                break;
            case 'A': case 'a':
                rank = 14;
                break;
            default:
                System.out.println("There was an error in rank entry.");    
                break;
        }
        switch(tempSuit){
            case 'D': case 'd':
                suit = 1;
                break;
            case 'C': case 'c':
                suit = 2;
                break;
            case 'H': case 'h':
                suit = 3;
                break;
            case 'S': case 's':
                suit = 4;
                break;                
            default:
                System.out.println("There was an error in suit entry.");
                break;
        }
    }
    protected void print(){
        switch(rank){
            case 2: case 3: case 4: case 5:  
            case 6: case 7: case 8: case 9:
                System.out.print(rank);
                break;
            case 10:
                System.out.print("T");
                break;
            case 11:
                System.out.print("J");
                break;
            case 12:
                System.out.print("Q");
                break;
            case 13:
                System.out.print("K");
                break;
            case 14:
                System.out.print("A");
                break;
            default:
                System.out.println("There was an error in rank print.");                   
                break;
        }
        switch(suit){
            case 1:
                System.out.print("♦ ");
                break;
            case 2:
                System.out.print("♣ ");
                break;
            case 3:
                System.out.print("♥ ");
                break;
            case 4:
                System.out.print("♠ ");
                break;                
            default:
                System.out.println("There was an error in suit print.");
                break;
        }
    }
}