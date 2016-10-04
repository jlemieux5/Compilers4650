import java.io.*;

class Scanner {
  public static hash firstHash = new hash();
  public static FunctionDict funcDict = new FunctionDict();
  // mode 0 is -a abstract and mode 1 is -s symbol table
  public static int mode;
  static public void main(String argv[]) {
    // Checking the command line args to determine mode
    if(argv.length > 1){
      if(argv[1].equals("-a"))
        mode = 0;
      else if(argv[1].equals("-s"))
        mode = 1;
    }
    else
      mode = -1;

    try {
      // Starting the parsing process
      parser p = new parser(new Lexer(new FileReader(argv[0])));
      Object result = p.parse().value;
    } catch (Exception e) {}
  }
}
