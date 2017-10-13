/*  Team Member Spock: Alkheraigi, Meshari 
    Team Member Bones: Renger, James 
    Class: COMP282 
    Assignment: Project 2
    Filelist: Main.java, int.java, Treemap.java
*/

package pkg282project2;

public class Main {
    public static void main(String[] args) {
        TreeMap yggdrasil = new TreeMap();
        if(yggdrasil.isEmpty() && yggdrasil.size() == 0)
            System.out.println(
                    "Test case(empty): In the wasteland...");
        yggdrasil.put(1,'A');
        if(!yggdrasil.isEmpty() && yggdrasil.size() > 0)
            System.out.println(
                    "Test case(root):...there is life...");
        yggdrasil.put(5, 'B');
        System.out.println(
                "Test case(1st child)...Eliot couldn't see that...");        
        yggdrasil.put(3, 'C');
        System.out.println(
                "Test case(2nd child)...Joyce said it was all evil...");
        yggdrasil.put(7, 'D');
        if(yggdrasil.containsValue('C'))
            System.out.println("Test Case(contains value): ");         
        System.out.println(
                "Test Case(3rd child)...Heideiger said, 'deal with it.'");
        if(yggdrasil.containsKey(3))
            System.out.println(
                    "Test Case(contains key): So, we do the best we can.");
        System.out.println("Test Case(get): " + yggdrasil.get(1));        
        yggdrasil.remove(1);
        if(!yggdrasil.containsKey(5))
            System.out.println(
                    "Test Case(remove): But as they say of mice and men..."); 
        yggdrasil.clear();
        if(yggdrasil.isEmpty() && yggdrasil.size() == 0)         
            System.out.println("Test Case(clear):  ");                
    }    
}