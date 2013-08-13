Compiler = javac
CompilerPath = "C:/Program Files/Java/jdk1.7.0_11/bin"

BinDIR = bin
ObjDIR = obj
SrcDIR = src
Package = classes
MainFile = $(Package).Main
OutBIN = Kasi32Engine.jar
	
JavaFiles = \
	$(wildcard $(SrcDIR)/*.java)
	
ClassFiles = \
	$(wildcard $(ObjSRC)/*.class)
	
all:
	@echo
	@echo "  Instructions For Making KasiEngine:"
	@echo
	@echo "    To Build Files:  make everything"
	@echo "    To Clean Files: 	make clean"

clean:
	@echo "    Cleaning Build Files."
	@rm -rf $(ClassFiles) $(BinDIR) $(ObjDIR)

	
everything: 
	@echo "Compiling Java Files.."
	@mkdir -p $(ObjDIR)
	@$(CompilerPath)/$(Compiler) -d $(ObjDIR) $(JavaFiles) -sourcepath $(SrcDIR) -classpath $(ObjDIR)
	@mkdir -p $(BinDIR)
	@$(CompilerPath)/jar cfe $(BinDIR)/$(OutBIN) $(MainFile) -C $(ObjDIR) .
	@echo
	@echo "Running Jar File.."
	@echo
	@$(CompilerPath)/java -jar $(BinDIR)/$(OutBIN)