import absyn.*;

// Syntax class to find syntax error and report them
public class Syntax{
  public final static int INT = 0;
  public final static int VOID = 1;
  public static int errorCount = 0;
  public DecList absTree;

  public Syntax(DecList tree){
    absTree = tree;
  }
  // Print the tree
  public void printTree(){
    Absyn.showTree(absTree, 0);
  }

  // Checks to see if a variable is already defined
  public boolean hasVariable(String var){
    return hash.getLastHash().containsKey(var);
  }

  // Checks if the type is void, if it is return true
  public boolean checkType(int type){
    if(type == VOID)
      return true;
    else
      return false;
  }

  // Checks a variable to make sure that it is defined before use, if not throws an error
  public int evalExp(VarExp varexp){
    if(!(Scanner.firstHash.containsVal(varexp.variable.name)))
      undefinedVarError(varexp.variable.name, varexp.pos);
    if(varexp.variable instanceof IndexVar)
      return evalExp((IndexVar)varexp.variable);
    return INT;
  }

  // Check a indexvar to make sure the bounds evaulate to an int, if not throws an error
  public int evalExp(IndexVar indexvar){
      int returnVal = evalExp(indexvar.index);
      if(returnVal != INT)
        arrayRangeError(indexvar);
      return returnVal;
  }

  // Check an assignExp to make sure both sides evauluate to each other
  public int checkAssign(AssignExp assignexp){
    if(!(Scanner.firstHash.containsVal(assignexp.lhs.name)))
      undefinedVarError(assignexp.lhs.name, assignexp.pos);
    if(assignexp.lhs instanceof IndexVar)
      evalExp((IndexVar)assignexp.lhs);
    if(evalExp(assignexp.rhs) != INT){
      opExpError(assignexp.pos);
      return VOID;
    }

    return INT;
  }

  // Check an opExp to make sure both sides evauluate to each other
  public int evalExp(OpExp opexp){
    if(evalExp(opexp.left) == INT && evalExp(opexp.right) == INT)
      return INT;
    else
      return VOID;
  }

  // Checks a call exp and returns its return type
  public int evalExp(CallExp callexp){
    return FunctionDict.getReturnType(callexp.func);
  }

  // Checks an if exp to determine if the test condition evauluates to an integer, throws an error if not
  public int evalExp(IfExp ifexp){
    if(evalExp(ifexp.test) != INT)
      ifExpError(ifexp.pos);
    evalExp(ifexp.thenpart);
    return INT;
  }

  // Check a while exp to see if the test condition evauluates to an interger, throws an error if not
  public int evalExp(WhileExp whileexp){
    if(evalExp(whileexp.test) != INT)
      whileExpError(whileexp.pos);
    return INT;
  }

  // Taks an exp and passes it to other Exp functions
  public int evalExp(Exp exp){
    if(exp instanceof IntExp)
      return evalExp((IntExp)exp);
    else if(exp instanceof VarExp)
      return evalExp((VarExp)exp);
    else if(exp instanceof CallExp)
      return evalExp((CallExp)exp);
    else if(exp instanceof OpExp)
      return evalExp((OpExp)exp);
    else if(exp instanceof AssignExp)
      return checkAssign((AssignExp)exp);
    else if(exp instanceof IfExp)
      return evalExp((IfExp)exp);
    return VOID;
  }

  public int evalExp(IntExp intexp){
    return INT;
  }

  // Checks if a Function that requires a return value has one, if it does not, throws an error
  public void checkReturn(boolean hasReturn, String name){
    if(!(hasReturn) && FunctionDict.getReturnType(name) == INT){
      System.out.println("Return Type Error. Function is type INT but has no Return. In Function: " + name );
      errorCount++;
    }
  }

  public void arrayRangeError(IndexVar indexvar){
      System.out.println("Line: " + indexvar.pos + ", Range for Array: " + indexvar.name + " does not evaluate to INT");
      errorCount++;
  }
  // Prints out a undefined variable error with name and position
  public void undefinedVarError(String name, int pos){
    System.out.println("Line: " + pos + ", Syntax Error: " + name + " is not declared before use");
    errorCount++;
  }
  // Prints out a type Error with the var name and position
  public void typeError(String name, int pos){
      System.out.println("Line: " + pos + ", Type Error: " + name + " must be declared INT");
      errorCount++;
  }

  // Prints out a redefined error with the var name and position
  public void syntaxNameError(String name, int pos){
      System.out.println("Line: " + pos + ", Syntax Error: " + name + " is already defined");
      errorCount++;
  }
  // Prints a return type error with name and position
  public void returnTypeError(String name, int pos){
      System.out.println("Line: " + pos + ", Return Type Error. In Function: " + name);
      errorCount++;
  }

  // prints an expression error with position
  public void opExpError(int pos){
      System.out.println("Line: " + pos + ", Expression does not evaluate to INT");
      errorCount++;
  }

  // prints a error if a funciton is called and not defined
  public void undefinedFuncError(CallExp callexp){
      System.out.println("Line: " + callexp.pos + ", Function: " + callexp.func + " is not defined");
      errorCount++;
  }

  // Prints out an if exp error if the test condition evals to non INT
  public void ifExpError(int pos){
      System.out.println("Line: " + pos + ", If condition does not evaulate to INT");
      errorCount++;
  }

  // While exp error
  public void whileExpError(int pos){
      System.out.println("Line: " + pos + ", While condition does not evaulate to INT");
      errorCount++;
  }
  // Adds the function to the Function Dictionary for later lookup
  public void addToFuncDict(FunctionDec func){
    if(Scanner.funcDict.returnType == 999){
      Scanner.funcDict.setValues(func.func, func.result.typ, func.params);
    }
    else{
      Scanner.funcDict.setLast(func.func, func.result.typ, func.params);
    }
  }

  // Takes an expList and searches for any type-check errors
  public void findExpErr(ExpList expList, String funcName){
    boolean hasReturnStmt = false;

    while( expList != null ) {
      if(expList.head instanceof ReturnExp){
        hasReturnStmt = true;
        if(!(matchReturnVal(getExpType((ReturnExp)expList.head),funcName)))
          returnTypeError(funcName, expList.head.pos);
      }
      else if(expList.head instanceof VarExp){
        evalExp((VarExp)expList.head);
      }
      else if(expList.head instanceof AssignExp){
        checkAssign((AssignExp)expList.head);
      }
      else if(expList.head instanceof OpExp){
        if(evalExp((OpExp)expList.head) == VOID)
          opExpError(expList.head.pos);
      }
      else if(expList.head instanceof CallExp){
        if(evalExp((CallExp)expList.head) == -1)
          undefinedFuncError((CallExp)expList.head);
      }
      else if(expList.head instanceof IfExp){
        evalExp((IfExp)expList.head);
      }
      else if(expList.head instanceof WhileExp){
        evalExp((WhileExp)expList.head);
      }
      expList = expList.tail;
    }
    checkReturn(hasReturnStmt, funcName);
  }


  public int getExpType(ReturnExp exp){
    if(exp.exp == null){
      return VOID;}
    else
      return INT;
  }

  // Checks to see if a funcitons return value matches its returnType
  public boolean matchReturnVal(int type, String funcName){
    if(FunctionDict.getReturnType(funcName) == type){
      return true;}
    else
      return false;
  }

  // Inputs all declarations into a linked hashmap
  public void storeHash(){

    ArrayDec tempArray;
    SimpleDec tempSimp;
    FunctionDec tempFunc;
    VarDecList tempDecList;

    while(absTree != null) {
      if(absTree.head instanceof SimpleDec){
        tempSimp = (SimpleDec)absTree.head;
        if(hasVariable(tempSimp.name))
          syntaxNameError(tempSimp.name, tempSimp.pos);
        else if(checkType(tempSimp.typ.typ))
          typeError(tempSimp.name, tempSimp.pos);
        else
          Scanner.firstHash.hashtable.put(tempSimp.name, tempSimp.typ.typ);
      }
      else if(absTree.head instanceof ArrayDec){
        tempArray = (ArrayDec)absTree.head;
        if(hasVariable(tempArray.name))
          syntaxNameError(tempArray.name, tempArray.pos);
        else if(checkType(tempArray.typ.typ))
          typeError(tempArray.name, tempArray.pos);
        else
          Scanner.firstHash.hashtable.put(tempArray.name, tempArray.typ.typ);
      }

      // Checking inside a funciton now
      else if(absTree.head instanceof FunctionDec){
        Scanner.firstHash.addNewScope();
        tempFunc = (FunctionDec)absTree.head;
        addToFuncDict(tempFunc);

        // Inside a functions paramaters
        while(tempFunc.params != null){
          if(tempFunc.params.head instanceof SimpleDec){
            tempSimp = (SimpleDec)tempFunc.params.head;
            if(hasVariable(tempSimp.name))
              syntaxNameError(tempSimp.name, tempSimp.pos);
            else if(checkType(tempSimp.typ.typ))
              typeError(tempSimp.name, tempSimp.pos);
            else
              Scanner.firstHash.hashTableNext.hashtable.put(tempSimp.name, tempSimp.typ.typ);
          }
          else if(tempFunc.params.head instanceof ArrayDec){
            tempArray = (ArrayDec)tempFunc.params.head;
            if(hasVariable(tempArray.name))
              syntaxNameError(tempArray.name, tempArray.pos);
            else if(checkType(tempArray.typ.typ))
              typeError(tempArray.name, tempArray.pos);
            else
              Scanner.firstHash.hashtable.put(tempArray.name, tempArray.typ.typ);
            }

          tempFunc.params = tempFunc.params.tail;
        }

        // Inside the body of the function
        while(tempFunc.body.decs != null){
          if(tempFunc.body.decs.head instanceof SimpleDec){
            tempSimp = (SimpleDec)tempFunc.body.decs.head;
            if(hasVariable(tempSimp.name))
              syntaxNameError(tempSimp.name, tempSimp.pos);
            else if(checkType(tempSimp.typ.typ))
              typeError(tempSimp.name, tempSimp.pos);
            else
              Scanner.firstHash.hashTableNext.hashtable.put(tempSimp.name, tempSimp.typ.typ);
          }

          else if(tempFunc.body.decs.head instanceof ArrayDec){
            tempArray = (ArrayDec)tempFunc.body.decs.head;
            if(hasVariable(tempArray.name))
              syntaxNameError(tempArray.name, tempArray.pos);
            else if(checkType(tempArray.typ.typ))
              typeError(tempArray.name, tempArray.pos);
            else
              Scanner.firstHash.hashtable.put(tempArray.name, tempArray.typ.typ);
            }

          tempFunc.body.decs = tempFunc.body.decs.tail;
        }
        // If mode is symbol mode output the linked hashtables
        if(Scanner.mode == 1){
          System.out.println("Entering new Scope: " + tempFunc.func);
          Scanner.firstHash.printHash();
        }

        findExpErr(tempFunc.body.exps, tempFunc.func);
        Scanner.firstHash.removeScope();
      }
      absTree = absTree.tail;
    }
  }

}
