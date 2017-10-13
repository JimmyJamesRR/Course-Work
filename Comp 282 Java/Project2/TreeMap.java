/*
    Team Member Spock: Alkheraigi, Meshari 
    Team Member Bones: Renger, James 
    Class: COMP282 
    Assignment: Project 2
    Filelist: Main.java, int.java, Treemap.java
 */

package pkg282project2;

public class TreeMap<K extends Comparable,V> {
    private K key;
    private V value;
    private int<K,V> root;
    private int size;
    TreeMap(){
        root = null;
        size = 0;
    }   //Clear Method empties tree.  No para, no return.
    protected void clear(){
        root = null;
        size = 0;                
    }           //ContainsKey() searches tree by key, and returns t/f. Para K.
    protected boolean containsKey(K key){
        if(!isEmpty())  //if the tree isn't empty, Return search T/F.  
            return root.containsKey(key);
        return false;   //Otherwise, false.
    }           //ContainsValue searches tree by value, and returns t/f.Para V
    protected boolean containsValue(V value){
        if(!isEmpty())  //if the tree isn't empty, Return search T/F.
            return root.containsValue(value);        
        return false;   //Otherwise, false.
    }       //get() searches by K, to return V.  Para K, return V/null.
    protected V get(K key){
        if(!isEmpty())  //if the tree isn't empty, Return search (V@K)/null.
            return root.get(key);
        return null;    //Otherwise, return null.
    }       //put() takes K,V, stores @ k, and returns V replaced.Para k,a.
    protected V put(K keyarg, V valarg){
        V replaced = null;  //return V/null temp.
        if(root == null){   //if the tree is empty, new root.  Update Size.
            root = new int(keyarg,valarg, true);
            size++;
        }
        else{               //Otherwise, add to tree, and update return & size
            replaced = root.put(keyarg,valarg);
            if(replaced != null)
                size++;
        }
        return replaced;        
    }       //isEmpty()checks the size, and returns T/F if anything is there.       
    protected boolean isEmpty(){
        if(size == 0)
            return true;
        return false;
    }   
    protected V remove(K key){
        if(!isEmpty())
            return root.remove(key);
        return null;
    }       //size() returns the size.  No para, return int.
    protected int size(){
        return size;
    }
}
