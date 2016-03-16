package pl.mzolkiewski.nand2tetris.assembler;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Encapsulates access to the input code. 
 * Reads assembly language command, parses it, and provides convenient access 
 * to the command's components (fields and symbols).
 * In addition, removes all whitespace and comments.
 * 
 * @author Martin
 */
public class Parser {
    
    public static enum Command {
        A, // @Xxx where Xxx is either a symbol or a decimal number
        C, // dest=comp;jump
        L  // (Xxx) where Xxx is a symbol (pseudocommand)
    }
    
    private List<String> parsedLines;
    private Map<Integer, Integer> humanReadableLineNumbers;
    private int currentLineIndex = 0;
    
    public Parser(BufferedReader reader) throws IOException {
        this.parsedLines = new ArrayList<>();
        this.humanReadableLineNumbers = new HashMap<>();
        
        Integer humanReadableLineNumber = 1;
        
        String line;
        
        // process all lines in the file
        while ((line = reader.readLine()) != null) {
            
            line = cleanUpLine(line);
            
            if (!line.isEmpty()) {
                this.parsedLines.add(line);
                this.humanReadableLineNumbers.put(this.parsedLines.size()-1, humanReadableLineNumber);
            }
            
            humanReadableLineNumber++;
        }
    }
    
    // returns a line without comments and whitespace
    public static String cleanUpLine(String line) {
        // remove the commments
        // "@SP // A_COMMAND" => "@SP "
        line = line.split("//")[0];
        // remove all whitespace
        line = line.replaceAll("\\s+", "");
        
        return line;
    }
    
    // returns true if there are more lines to parse
    public boolean hasMoreCommands() {
        return parsedLines.size() > currentLineIndex;
    }
    
    // gets next command in the file and makes it the current command
    // should be called only if hasMoreCommands returns true
    public void advance() {
        currentLineIndex++;
    }
    
    // sets the current line to the first line
    public void resetCurrentLine() {
        currentLineIndex = 0;
    }
    
    // returns the current line
    public String getCurrentLine() {
        return parsedLines.get(currentLineIndex);
    }
    
    // returns the string representation of the current line index
    public Integer getCurrentLineIndex() {
        return currentLineIndex;
    }
    
    // returns the human-readable line number of the current line
    public Integer getCurrentHumanReadableLineNumber() {
        return humanReadableLineNumbers.get(currentLineIndex);
    }
    
    // returns the type of the current command
    public Command commandType() {
        return commandType(getCurrentLine());
    }
    
    // returns the type of the given command
    public static Command commandType(String line) {
        if (line.startsWith("@")) {
            return Command.A;
        } else if (line.startsWith("(")) {
            return Command.L;
        }
        return Command.C;
    }
    
    // returns the symbol (or string representation of decimal) of the current command
    // should be called only when command type is A or L
    public String symbol() {
        return symbol(getCurrentLine());
    }
    
    // returns the symbol of the given command
    public static String symbol(String line) {
        Command commandType = commandType(line);
        
        if (commandType == Command.A || commandType == Command.L) {
            return line.replace("@", "").replace("(", "").replace(")", "");
        }
        return null;
    }
    
    // returns the 'dest' mnemonic in the current C command
    // should be called only when command type is C
    public String dest() {
        return dest(getCurrentLine());
    }
    
    // returns the 'dest' mnemonic in the given C command
    public static String dest(String line) {
        Command commandType = commandType(line);
        
        if (commandType == Command.C) {
            if (line.contains("=")) {
                return line.split("=")[0];
            }
        }
        
        return null;
    }
    
    // returns the 'comp' mnemonic in the current C command
    // should be called only when command type is C
    public String comp() {
        return comp(getCurrentLine());
    }
    
    // returns the 'comp' mnemonic in the given C command
    public static String comp(String line) {
        Command commandType = commandType(line);
        
        if (commandType == Command.C) {
            if (line.contains("=")) {
                return line.split("=")[1];
            }
            return line.split(";")[0];
        }
        
        return null;
    }
    
    // returns the 'jump' mnemonic in the current C command
    // should be called only when command type is C
    public String jump() {
        return jump(getCurrentLine());
    }
    
    // returns the 'jump' mnemonic in the given C command
    public static String jump(String line) {
        if (line.contains(";")) {
            return line.split(";")[1];
        }
        return null;
    }
}
