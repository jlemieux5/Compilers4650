package absyn;

public class NameTy{
  public final static int INT = 0;
  public final static int VOID = 1;
  public int pos;
  public int typ;

  public NameTy(int pos, int typ){
    this.pos = pos;
    this.typ = typ;
  }
}
