package pl.mzolkiewski.nand2tetris.assembler;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps a correspondence between symbolic labels and numeric addresses.
 * 
 * @author Martin
 */
public class SymbolTable {
    
    private Map<String, Integer> symbolTable;
    
    // Incremented each time a new variable is added to the table
    // The allocated RAM addresses are consecutive numbers, starting at address 16 (just after the addresses allocated to the predefined symbols)
    private int firstFreeRAMAddress = 16;
    
    public SymbolTable() {
        this.symbolTable = new HashMap<>();
        
        // pre-fill the table with standard symbols
        this.symbolTable.put("SP",     0x0);
        this.symbolTable.put("LCL",    0x1);
        this.symbolTable.put("ARG",    0x2);
        this.symbolTable.put("THIS",   0x3);
        this.symbolTable.put("THAT",   0x4);
        this.symbolTable.put("SCREEN", 0x4000);
        this.symbolTable.put("KBD",    0x6000);
        this.symbolTable.put("R0",     0x0);
        this.symbolTable.put("R1",     0x1);
        this.symbolTable.put("R2",     0x2);
        this.symbolTable.put("R3",     0x3);
        this.symbolTable.put("R4",     0x4);
        this.symbolTable.put("R5",     0x5);
        this.symbolTable.put("R6",     0x6);
        this.symbolTable.put("R7",     0x7);
        this.symbolTable.put("R8",     0x8);
        this.symbolTable.put("R9",     0x9);
        this.symbolTable.put("R10",    0xa);
        this.symbolTable.put("R11",    0xb);
        this.symbolTable.put("R12",    0xc);
        this.symbolTable.put("R13",    0xd);
        this.symbolTable.put("R14",    0xe);
        this.symbolTable.put("R15",    0xf);
    }
    
    public void addEntry(String key, Integer value) {
        symbolTable.put(key, value);
    }
    
    public void addLabelEntry(String symbol, Integer programLine) {
        addEntry(symbol, programLine);
    }
    
    public void addVariableEntry(String symbol) {
        addEntry(symbol, firstFreeRAMAddress++);
    }
    
    public boolean contains(String key) {
        return symbolTable.containsKey(key);
    }
    
    public Integer getAddress(String key) {
        return symbolTable.get(key);
    }
    
    public Integer getSymbolTableLength() {
        return symbolTable.size();
    }
    
    public Integer getFirstFreeRAMAddress() {
        return firstFreeRAMAddress;
    }
}
