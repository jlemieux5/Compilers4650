import absyn.*;

// Class to store all functions, including their name, return type and parameters for later use if they made be called
public class FunctionDict{
    public String name;
    public int returnType;
    private VarDecList params;
    public FunctionDict nextFuncDict;

    // Constructor for first Function Dictionary, sets returnType to 999 to show no values were given
    public FunctionDict(){
      returnType = 999;
    }

    // Sets initial values
    public void setValues(String name, int returnType, VarDecList params){
      this.name = name;
      this.returnType = returnType;
      this.params = params;
    }

    // Adds to the linked function list by going to the last and setting values
    public void setLast(String name, int returnType, VarDecList params){
      FunctionDict temp = Scanner.funcDict;

      while(temp.nextFuncDict != null){
        temp = temp.nextFuncDict;}

      temp.nextFuncDict = new FunctionDict();
      temp.nextFuncDict.name = name;
      temp.nextFuncDict.returnType = returnType;
      temp.nextFuncDict.params = params;
    }

    // Gets the return type of the function name provided, returns -1 on error
    public static int getReturnType(String name){
      FunctionDict temp = Scanner.funcDict;
      while(!(temp.name.equals(name))){
        if(temp.nextFuncDict == null)
          return -1;
        temp = temp.nextFuncDict;
      }
      return temp.returnType;
    }

    // Prints all functions in the list
    public void printFunctions(){
      FunctionDict temp = Scanner.funcDict;
      if(temp.returnType == 999)
        System.out.println("No functions");
      else{
        while(temp != null){
          System.out.println("Name: " + temp.name + ", Type: " + temp.returnType);
          temp = temp.nextFuncDict;
        }
      }
    }
}
