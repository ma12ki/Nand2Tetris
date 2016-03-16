package pl.mzolkiewski.nand2tetris.assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import pl.mzolkiewski.nand2tetris.assembler.Parser.Command;
import pl.mzolkiewski.nand2tetris.utils.FileHelper;

/**
 * Main Assembler class.
 * 
 * @author Martin
 */
public class Assembler {
    
    public static final String HACK_EXTENSION = "hack";
    public static final String ASSEMBLER_EXTENSION = "asm";
    
    public Parser parser;
    public SymbolTable symbolTable;
    
    private final String canonicalDirectoryPathString,
            fileNameWithoutExtension;
    
    private List<String> outputList;
    
    public static void execute(String filePath) throws IOException {
        Assembler assembler = new Assembler(filePath);
    }
    
    private Assembler(String filePath) throws IOException {
        FileHelper.checkInputFileExtension(filePath, ASSEMBLER_EXTENSION);
        File inputFile = FileHelper.openReadOnlyFile(filePath);
        canonicalDirectoryPathString = FileHelper.getCanonicalDirectoryPath(inputFile);
        fileNameWithoutExtension = FileHelper.getFileNameWithoutExtension(inputFile);
        parser = new Parser(new BufferedReader(new FileReader(inputFile)));
        symbolTable = new SymbolTable();
        outputList = new ArrayList<>();
        
        fillSymbolTable();
        assembleAllLines();
        writeOutputToFile();
    }
    
    // Goes through the entire assembly program line by line
    // and builds the symbol table without generating any code.
    // As it marches through the program lines, it keeps a running number
    // recording the ROM address into which the current command will be evetually loaded.
    // This number starts at 0 and gets incremented by 1 whenever a C-instruction
    // or A-instruction are encountered, but does not change when an L-instruction
    // or comment is encountered.
    private void fillSymbolTable() {
        int currentLine = 0;
        
        parser.resetCurrentLine();
        
        while (parser.hasMoreCommands()) {
            Command commandType = parser.commandType();
            
            // add every L-command label to the table if it's not there already.
            if (commandType == Command.L) {
                String symbol = parser.symbol();
                
                if (!symbolTable.contains(symbol)) {
                    symbolTable.addLabelEntry(symbol, currentLine);
                }
                
            // if A-command or C-command then just increment currentLine
            } else {
                currentLine++;
            }
            
            parser.advance();
        }
        
        parser.resetCurrentLine();
    }
    
    private void assembleAllLines() {
        parser.resetCurrentLine();
        
        while (parser.hasMoreCommands()) {
            assembleCurrentLine();
            parser.advance();
        }
    }

    private void assembleCurrentLine() {
       Command command = parser.commandType();
       
       if (command == Command.A || command == Command.C) {
           String binaryString = (command == Command.A) ? aCommandAssemblyToBinaryCurrentLine() : cCommandAssemblyToBinaryCurrentLine();
           outputList.add(binaryString);
       }
    }

    private String aCommandAssemblyToBinaryCurrentLine() {
        String binaryString;
        String symbol = parser.symbol();
        
        try {
            // if the symbol is an integer, then we just convert it to its binary representation
            int address = Integer.parseInt(symbol);
            binaryString = Code.intToBinaryString(address);
        } catch(NumberFormatException e) {
            // if the symbol is not in the table, then it must be a variable
            if (!symbolTable.contains(symbol)) {
                symbolTable.addVariableEntry(symbol);
            }
            // if it's a name, then we convert it's address from the symbol table
            binaryString = Code.intToBinaryString(symbolTable.getAddress(symbol));
        }
        
        return binaryString;
    }

    private String cCommandAssemblyToBinaryCurrentLine() {
        String binaryString = "111"; // C command starts with 1, followed by 2 unused bits
        
        binaryString += Code.comp(parser.comp())
                + Code.dest(parser.dest())
                + Code.jump(parser.jump());
        
        return binaryString;
    }

    private void writeOutputToFile() throws IOException {
        Path path = Paths.get(canonicalDirectoryPathString, fileNameWithoutExtension + "." + HACK_EXTENSION);
        FileHelper.writeOutputToFile(path.toString(), outputList);
    }
    
}
