package absyn;

abstract public class Absyn {
  public int pos;

  final static int SPACES = 4;

  static private void indent( int spaces ) {
    for( int i = 0; i < spaces; i++ ) System.out.print( " " );
  }

  static public void showTree( DecList tree, int spaces ) {
    while( tree != null) {
      showTree( tree.head, spaces);
      tree = tree.tail;
    }
  }

  static public void showTree( VarDecList tree, int spaces ) {
    while( tree != null) {
      showTree( tree.head, spaces);
      tree = tree.tail;
    }
  }
  static public void showTree( ExpList tree, int spaces ) {
    while( tree != null ) {
      showTree( tree.head, spaces );
      tree = tree.tail;
    }
  }

  static private void showTree( Exp tree, int spaces ) {
    if( tree instanceof AssignExp )
      showTree( (AssignExp)tree, spaces );
    else if( tree instanceof IfExp )
      showTree( (IfExp)tree, spaces );
    else if( tree instanceof IntExp )
      showTree( (IntExp)tree, spaces );
    else if( tree instanceof OpExp )
      showTree( (OpExp)tree, spaces );
    else if( tree instanceof NilExp )
      showTree( (NilExp)tree, spaces );
    else if( tree instanceof WhileExp )
      showTree( (WhileExp)tree, spaces );
    else if( tree instanceof VarExp )
      showTree( (VarExp)tree, spaces );
    else if( tree instanceof ReturnExp )
      showTree( (ReturnExp)tree, spaces );
    else if( tree instanceof CallExp)
      showTree( (CallExp)tree, spaces );
    else if( tree instanceof CompoundExp)
      showTree( (CompoundExp)tree, spaces);
    else if( tree == null)
      System.out.print("");
    else {
      indent( spaces );
      System.out.println( "Illegal expression at line " + tree.pos  );
    }
  }

  static private void showTree( VarDec tree, int spaces)
  {

    if( tree instanceof SimpleDec) {
      showTree( (SimpleDec)tree, spaces);}
    else if( tree instanceof ArrayDec){
      showTree( (ArrayDec)tree, spaces);}
    else if( tree == null)
        System.out.print("");
    else{
      indent( spaces );
      System.out.println( "Illegal expression at line " + tree.pos );
    }
  }

  static private void showTree( Dec tree, int spaces) {
    if( tree instanceof FunctionDec) {
      showTree( (FunctionDec)tree, spaces); }
    else if( tree instanceof SimpleDec) {
      showTree( (SimpleDec)tree, spaces);}
    else if( tree instanceof ArrayDec){
      showTree( (ArrayDec)tree, spaces);}
    else{
      indent( spaces );
      System.out.println( "Illegal expression at line " + tree.pos );
    }
  }

  // MUST ADD TO THIS PARAMS AND BODY
  static private void showTree( FunctionDec tree, int spaces) {
    indent( spaces );
    System.out.println( "FunctionDec: " + tree.func );
    spaces += SPACES;
    showTree( tree.params, spaces);
    showTree( tree.body, spaces);
  }

  static private void showTree( SimpleDec tree, int spaces) {
      indent( spaces );
      System.out.println( "SimpleDec: " + tree.name);
  }

  static private void showTree( ArrayDec tree, int spaces) {
    indent( spaces );
    System.out.println("ArrayDec: " + tree.name + " Type: " + tree.typ.typ);
    spaces += SPACES;
    showTree( tree.size, spaces);
  }
  static private void showTree( AssignExp tree, int spaces ) {
    indent( spaces );
    System.out.println( "AssignExp: " + tree.lhs.name);
    spaces += SPACES;
    //showTree( tree.lhs, spaces );
    showTree( tree.rhs, spaces );
  }

  static private void showTree( IfExp tree, int spaces ) {
    indent( spaces );
    System.out.println( "IfExp:" );
    spaces += SPACES;
    showTree( tree.test, spaces );
    showTree( tree.thenpart, spaces );
    showTree( tree.elsepart, spaces );
  }

  static private void showTree( IntExp tree, int spaces ) {
    indent( spaces );
    System.out.println( "IntExp: " + tree.value );
  }

  static private void showTree( OpExp tree, int spaces ) {
    indent( spaces );
    System.out.print( "OpExp:" );
    switch( tree.op ) {
      case OpExp.PLUS:
        System.out.println( " + " );
        break;
      case OpExp.MINUS:
        System.out.println( " - " );
        break;
      case OpExp.TIMES:
        System.out.println( " * " );
        break;
      case OpExp.DIV:
        System.out.println( " / " );
        break;
      case OpExp.EQ:
        System.out.println( " = " );
        break;
      case OpExp.LT:
        System.out.println( " < " );
        break;
      case OpExp.GT:
        System.out.println( " > " );
        break;
      case OpExp.LTE:
        System.out.println(" <= ");
        break;
      case OpExp.GTE:
        System.out.println(" >= ");
        break;
      case OpExp.NEQ:
        System.out.println(" != ");
        break;
      default:
        System.out.println( "Unrecognized operator at line " + tree.pos);
    }
    spaces += SPACES;
    showTree( tree.left, spaces );
    showTree( tree.right, spaces );
  }
  //CHANGE NAME
  static private void showTree( VarExp tree, int spaces ) {
    indent( spaces );
    System.out.println( "VarExp: " + tree.variable.name );
  }

  static private void showTree( ReturnExp tree, int spaces){
    indent( spaces );
    System.out.println( "ReturnExp:" );
    showTree( tree.exp, spaces + SPACES);
  }

  static private void showTree( NilExp tree, int spaces ) {
    indent( spaces );
    System.out.println( "NilExp" );
  }

  static private void showTree( WhileExp tree, int spaces ) {
    indent( spaces );
    System.out.println( "WhileExp:" );
    spaces += SPACES;
    showTree( tree.test, spaces );
    showTree( tree.body, spaces );
  }

  static private void showTree( CallExp tree, int spaces ) {
    indent( spaces );
    System.out.println( "CallExp: " + tree.func);
    spaces += SPACES;
    showTree( tree.args, spaces );
  }

  // Possibly look into this
  static private void showTree( CompoundExp tree, int spaces ){
    indent( spaces );
    System.out.println( "CompoundExp:" );
    spaces += SPACES;
    showTree( tree.decs, spaces );
    showTree( tree.exps, spaces );
  }
}
