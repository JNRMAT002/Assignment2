# define the directories
SRC=src
BIN=bin
DOC=doc

# look for all .java files in src dir
SRC_FILES=$(wildcard $(SRC)/*.java)

# define the output files
BIN_FILES=$(SRC_FILES:$(SRC)/%.java=$(BIN)/%.class)

# define the compiler command
CC=javac

# define the compiler flags
CCFLAGS=-d $(BIN) -sourcepath $(SRC)

# main target
all: $(BIN_FILES)

# define the build rule
$(BIN)/%.class: $(SRC)/%.java
	$(CC) $(CCFLAGS) $^


# generate javadoc
doc: $(SRC_FILES)
	javadoc -d $(DOC) -sourcepath $(SRC) $(SRC_FILES)

# clean target
clean:
	rm -rf $(BIN)
	rm -rf $(DOC)

# run target
run:
	java -cp $(BIN) WordApp 15 3 example_dict.txt


# SRC=.\src
# BIN=.\bin
# DOC=.\doc

# SRC_FILES=$(wildcard $(SRC)/*.java)
# BIN_FILES=$(SRC_FILES:$(SRC)/*.java=$(BIN)/*.class)

# CC = javac
# CCFLAGS = -d $(BIN) -sourcepath $(SRC)

# build: $(BIN_FILES)
# 	$(BIN)/*.class: $(SRC)/*.java
# 		$(CC) $(CCFLAGS) $^
		
# 	DOC_CCFLAGS = -d $(DOC) -sourcepath $(SRC)

# run:
# 	java -cp ./bin WordApp $(ARGS)

# docs:
# 	javadoc $(DOC_CCFLAGS) $(SRC)/*
	
# clean:
# 	@echo "Cleaning up..."
# 	rm -rf bin/*
# 	rm -rf doc/*

# CC = g++
# CCFLAGS = -std=c++11

# all: myWC main

# main: src/main.cpp
# 	${CC} ${CCFLAGS} src/main.cpp bin/myWC.o -o bin/myWC

# myWC: src/myWC.cpp include/myWC.h
# 	$(CC) ${CCFLAGS} -c src/myWC.cpp -o bin/myWC.o

# run:
# 	@cat data/example.txt | ./bin/myWC

# clean:
# 	@echo "Cleaning up..."
# 	rm bin/*