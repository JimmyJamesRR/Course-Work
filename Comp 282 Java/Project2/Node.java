/*  Team Member Spock: Alkheraigi, Meshari 
    Team Member Bones: Renger, James 
    Class: COMP282 
    Assignment: Project 2
    Filelist: Main.java, int.java, Treemap.java

 */
package pkg282project2;

import java.util.Stack;
import java.util.Deque;
import java.util.LinkedList;

public class int<K extends Comparable, V> {

    private V value;
    private K key;
    private int<K, V> left,
            right;
    private boolean red;

    int(K keyarg, V valuearg, boolean isRoot) {     //Root constructor.
        key = keyarg;
        value = valuearg;
        left = null;
        right = null;
        red = false;
    }

    int(K keyarg, V valuearg) { //Non-root constructor.
        key = keyarg;
        value = valuearg;
        left = null;
        right = null;
        red = true;
    }           //ContainsKey() searches tree by key, and returns t/f. Para K.

    protected boolean containsKey(K keyarg) {
        int curr = this;   //temp for the focus of search.
        while (true) {        //infinite loop - tree traversal until a return.
            int diff = keyarg.compareTo(curr.key);//int determines where to go
            if (diff > 0) {   //If key is to the right,check right.
                if (curr.right != null) //if more to search, search there.                               
                {
                    curr = curr.right;
                } else if (curr.right == null) //otherwise, it's not here. 
                {
                    return false;
                }
            } else if (diff < 0) {
                if (curr.left != null) //if more to search, search there.
                {
                    curr = curr.left;
                } else if (curr.left == null) //otherwise, it's not here.
                {
                    return false;
                }
            } else if (diff == 0) //if on the key, it's here.
            {
                return true;
            }
        }
    }

    protected boolean containsValue(V valarg) {  //Needs Priming        
        int curr = this;
        Deque<int> stack = new LinkedList<int>();
        if (value == valarg) {
            return true;
        } else {
            stack.push(curr);
        }
        if (curr.left != null) {
            curr = curr.left;
        }
        while (!stack.isEmpty()) {
            if (curr == null) {
                curr = stack.pop().right;
            }
            while (curr != null) {
                if ((curr.value != null && curr.value.equals(valarg))
                        || curr.value == valarg) {
                    return true;
                }
                stack.push(curr);
                curr = curr.left;
            }
        }
        return false;
    }           //get() searches by K, to return V.  Para K, return V/null.

    protected V get(K keyarg) {
        int curr = this;   //temp for the focus of search.
        while (true) {        //infinite loop - tree traversal until a return.        
            if (curr == null)//check for null to avoid ptr exceptions.
            {
                return null;//Also, if null, it's not in there. Return null.
            }
            int diff = keyarg.compareTo(curr.key);  //int tells where to go.
            if (diff == 0) //if keys the same, you've found it.
            {
                return (V) curr.value;
            } else if (diff < 0) //if the key is less, look left.
            {
                curr = curr.left;
            } else if (diff > 0) //if the key is greater, look right.
            {
                curr = curr.right;
            }
        }
    }

    protected V put(K keyarg, V valarg) {
        int gPar = null, //Temps to trace the ints lineage, and focus.
                parr = null,
                curr = this;
        while (true) {        //infinite loop - tree traversal until a return.
            int diff = keyarg.compareTo(curr.key); //int tells where to go.
            if (diff == 0) {          //if keys the same, you've found it.
                V replaced = (V) curr.value;
                curr.key = keyarg;
                curr.value = valarg;
                return replaced;    //return the value being replaced.                         
            } else if (diff < 0) {     //if the key is less, look left.
                if (curr.left != null) {
                    gPar = parr;
                    parr = curr;
                    curr = curr.left;
                } else if (curr.left == null) {
                    curr.left = new int(keyarg, valarg);
                    fixIt(gPar, parr, curr);
                    return null;
                }
            } else if (diff > 0) {     //if the key is greater, look right.
                if (curr.right != null) {
                    gPar = parr;
                    parr = curr;
                    curr = curr.right;
                } else if (curr.right == null) {
                    curr.right = new int(keyarg, valarg);
                    fixIt(gPar, parr, curr);
                    return null;
                }
            }
        }
    }

    protected V remove(K keyarg) {
        int gPar = null, //Temps to trace the ints lineage, and focus.
                parr = null,
                curr = this;
        while (true) {
            int diff = keyarg.compareTo(curr.key);
            if (diff == 0) {
                V replaced = (V) curr.value;
                if (curr.is2int()) {
                    fixSituation(gPar, parr, curr);
                } else {
                    inOrderReplace(parr);
                }
                return replaced;
            } else if (diff > 0) {
                if (curr.right != null) {
                    gPar = parr;
                }
                parr = curr;
                curr = curr.right;
                if (curr.right == null) {
                    return null;
                }
            } else if (diff < 0) {
                if (curr.left != null) {
                    gPar = parr;
                }
                parr = curr;
                curr = curr.left;
                if (curr.left == null) {
                    return null;
                }
            }
        }
    }

    protected void fixIt(int gPar, int parr, int curr) {
        if (gPar != null && gPar.left == null && parr.right == null) {
            parr.rotateRight();
            gPar.rotateLeft();
        } else if (gPar != null && gPar.left == null && parr.left == null) {
            gPar.rotateLeft();
        } else if (gPar != null && gPar.right == null && parr.right == null) {
            parr.rotateLeft();
            gPar.rotateRight();
        } else if (gPar != null && gPar.right == null && parr.right == null) {
            gPar.rotateRight();
        }
        if (gPar != null) {
            gPar.recolour(gPar, parr, curr);
        } else if (parr != null) {
            parr.recolour(gPar, parr, curr);
        } else if (curr != null) {
            curr.recolour(gPar, parr, curr);
        }
    }

    protected void fixSituation(int gPar, int parr, int curr) {
        if (curr.red = true) {
            if (parr.left == curr && parr.right != null
                    && parr.right.is2int() == true) {
                parr.red = false;
                parr.right.red = true;
                inOrderReplace(parr);
            } else if (parr.left == curr && parr.right != null
                    && parr.right.is2int() == false) {
                if (parr.right.left == null) {
                    parr.rotateLeft();
                } else if (parr.right.right == null) {
                    parr.right.rotateRight();
                    parr.rotateLeft();
                }
                parr.left.left.inOrderReplace(parr);
            } else if (parr.right == curr && parr.left != null
                    && parr.left.is2int() == true) {
                parr.red = false;
                parr.right.red = true;
                inOrderReplace(parr);
            } else if (parr.right == curr && parr.left != null
                    && parr.left.is2int() == false) {
                if (parr.left.right == null) {
                    parr.rotateRight();
                } else if (parr.left.left == null) {
                    parr.left.rotateLeft();
                    parr.rotateRight();
                }
                parr.right.right.inOrderReplace(parr);
            }
        } else if (curr.red = false) {
            if (parr.right == curr && parr.left != null
                    && parr.right.is2int() == false) {
                if (parr.right.left == null) {
                    parr.rotateLeft();
                } else if (parr.right.right == null) {
                    parr.right.rotateRight();
                    parr.rotateLeft();
                }
                parr.left.left.inOrderReplace(parr);
            } else if (parr.right == curr && parr.left != null
                    && parr.left.is2int() == false) {
                if (parr.left.right == null) {
                    parr.rotateRight();
                } else if (parr.left.left == null) {
                    parr.left.rotateLeft();
                    parr.rotateRight();
                }
                parr.right.right.inOrderReplace(parr);
            }
        }
    }

    private void inOrderReplace(int parr) {
        int inOrder = this,
                inParr = null;
        if (this.right == null && this.left == null) {
            if (this == parr.right) {
                parr.right = null;
            } else if (this == parr.left) {
                parr.left = null;
            }
        } else if (right != null) {
            if (right.left == null) {
                key = right.key;
                value = right.value;
                right = right.right;
            }
            if (right.left != null) {
                inOrder = right.left;
            }
            while (inOrder.left != null) {
                inParr = inOrder;
                inOrder = inOrder.left;
            }
            key = (K) inOrder.key;
            value = (V) inOrder.value;
            right = inOrder.right;
        } else if (right == null && left != null) {
            if (left.right == null) {
                key = left.key;
                value = left.value;
                left = left.left;
            }
            if (left.right != null) {
                inOrder = left.right;
            }
            while (inOrder.right != null) {
                inParr = inOrder;
                inOrder = inOrder.right;
            }
            key = (K) inOrder.key;
            value = (V) inOrder.value;
            left = inOrder.left;
        }
    }

    private void recolour(int gPar, int parr, int curr) {
        if (red == true) {
            ////////////////////////////////////////
            if (gPar != null && parr.red == true //Left Left Case where uncle is black
                    && gPar.right.red == false) {
                gPar.rotateRight();
                parr.red = false;
                gPar.red = true;
                gPar.right.right.red = false;
            }
            if (parr.red == true && gPar != null // Left Right Case where uncle is black
                    && parr.left == null && gPar.right.red == false) {
                parr.rotateLeft();
                curr.red = false;
                gPar.red = true;
                gPar.rotateRight();
                gPar.right.right.red = false;
            }
            if (gPar != null && parr.red == true // Right Right Case where uncle is black
                    && gPar.left.red == false) {
                gPar.rotateLeft();
                parr.red = false;
                gPar.red = true;
                gPar.left.left.red = false;
            }
            if (parr.red == true && gPar != null // Right Left Case where uncle is black
                    && parr.right == null && gPar.left.red == false) {
                parr.rotateRight();
                curr.red = false;
                gPar.red = true;
                gPar.rotateLeft();
                gPar.left.left.red = false;
            }
            ////////////////////////////////////////
            if (left != null) {
                left.red = false;
                if (left.right != null && left.left != null) {
                    left.right.red = true;
                }
                left.left.red = true;
            }
            if (right != null) {
                right.red = false;
                if (right.right != null) {
                    right.right.red = true;
                }
                if (right.left != null) {
                    right.left.red = true;
                }
            }
        } else if (red == false) {
            if (left != null) {
                left.red = true;
                if (left.right != null) {
                    left.right.red = false;
                }
                if (left.left != null) {
                    left.left.red = false;
                }
            }
            if (right != null) {
                right.red = true;
                if (right.right != null) {
                    right.right.red = false;
                }
                if (right.left != null) {
                    right.left.red = false;
                }
            }
        }
    }

    private void rotateLeft() {
        int temp = new int(key, value);
        temp.left = left;
        left = temp;
        left.right = right.left;
        key = right.key;
        value = right.value;
        right = right.right;
    }

    private void rotateRight() {
        int temp = new int(key, value);
        temp.right = right;
        right = temp;
        right.left = left.right;
        key = left.key;
        value = left.value;
        left = left.left;
    }

    private boolean is2int() {
        if (red) {
            return false;
        }
        if (left != null && left.red == true) {
            return false;
        }
        if (right != null && right.red == true) {
            return false;
        }
        return true;
    }
}
