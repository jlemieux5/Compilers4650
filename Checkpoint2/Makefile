JAVA=java
JAVAC=javac
JFLEX=jflex
CLASSPATH=-classpath /usr/share/java/cup.jar:.
#CUP=$(JAVA) $(CLASSPATH) java_cup.Main <
CUP=cup

all: Scanner.class

Scanner.class: absyn/*.java Scanner.java parser.java sym.java Lexer.java hash.java Syntax.java FunctionDict.java 

%.class: %.java
	$(JAVAC) $(CLASSPATH)  $^

Lexer.java: cminus.flex
	$(JFLEX) cminus.flex

parser.java: cminus.cup
	$(CUP) -dump -expect 3 cminus.cup

clean:
	rm -f parser.java Lexer.java sym.java *.class *~ absyn/*.class
