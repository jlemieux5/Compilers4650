import java.util.HashMap;

// Class used to store linked hashmaps that have variable declarations in different scopes
public class hash{
  public HashMap hashtable;
  private boolean hasNext;
  public hash hashTableNext;

  // Constructor that creates a new hashmap
  public hash(){
    hashtable = new HashMap(61);
    hasNext = false;
  }

  // Adds a new scope , adds a new hashmap to the linked list
  public void addNewScope(){
    hash temp = Scanner.firstHash;
    while(temp.hasNext == true){
      temp = temp.hashTableNext;
    }
    temp.hashTableNext = new hash();
    temp.hasNext = true;
  }

  // Deletes the last scope (hashmap)
  public void removeScope(){
    hash temp = Scanner.firstHash;
    if(temp.hasNext == true){
      while(temp.hashTableNext.hasNext == true){
        temp = temp.hashTableNext;
      }
    }
    temp.hashTableNext = null;
    temp.hasNext = false;
  }

  // Prints all the lnked hashmaps, new scopes are shown through indents
  public void printHash(){
      int spaces = 2;
      hash temp = Scanner.firstHash;
      System.out.println(temp.hashtable.entrySet());
      while(temp.hasNext == true){
        temp = temp.hashTableNext;
        printSpace(spaces);
        System.out.println(temp.hashtable.entrySet());
        spaces += 2;
      }
  }
  // Prints # of spaces in arg
  public void printSpace(int space){
    for(int x = 0; x < space ;x++){
      System.out.print("  ");
    }
  }

  // returns the last hashmap in the linked list
  public static HashMap getLastHash(){
    hash temp = Scanner.firstHash;
    while(temp.hasNext == true)
      temp = temp.hashTableNext;

    return temp.hashtable;
  }

  // Checks all hash tables (scopes)to determine if a name is already declared
  public boolean containsVal(String val){
    hash temp = Scanner.firstHash;
    if(temp.hashtable.containsKey(val))
      return true;
    else{
      while(temp.hasNext == true){
        temp = temp.hashTableNext;
        if(temp.hashtable.containsKey(val))
          return true;
      }
    }
    return false;
  }

  public boolean hasNextHash(){
    return this.hasNext;
  }
}
