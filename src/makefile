JC = javac

# Diretórios...
BIN=../bin

JFLAGS = -g -d $(BASE)$(BIN)

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	BalancedBST.java \
	Intervalo.java\
	IntervalTreeNode.java\
	Ponto.java\
	Segmento.java\
	Intervalo2D.java\
	IntervalTree.java\
	RangeTreeNode.java\
	RangeTree.java\
	Windowing.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) $(BASE)$(BIN)/*.class
