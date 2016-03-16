package pl.mzolkiewski.nand2tetris.assembler;

import java.util.HashMap;
import java.util.Map;

/**
 * Translates Hack assembly language mnemonics into binary codes.
 * 
 * @author Martin
 */
public class Code {
    public static final Map<String, String> COMP_MAP, DEST_MAP, JUMP_MAP;
    
    // fill static mnemonic maps
    static {
        // comp - 7  bits
        Map<String, String> tmp_comp_map = new HashMap<>();
        tmp_comp_map.put("0",   "0101010");
        tmp_comp_map.put("1",   "0111111");
        tmp_comp_map.put("-1",  "0111010");
        tmp_comp_map.put("D",   "0001100");
        tmp_comp_map.put("A",   "0110000");
        tmp_comp_map.put("!D",  "0001101");
        tmp_comp_map.put("!A",  "0110001");
        tmp_comp_map.put("-D",  "0001111");
        tmp_comp_map.put("-A",  "0110011");
        tmp_comp_map.put("D+1", "0011111");
        tmp_comp_map.put("A+1", "0110111");
        tmp_comp_map.put("D-1", "0001110");
        tmp_comp_map.put("A-1", "0110010");
        tmp_comp_map.put("D+A", "0000010");
        tmp_comp_map.put("D-A", "0010011");
        tmp_comp_map.put("A-D", "0000111");
        tmp_comp_map.put("D&A", "0000000");
        tmp_comp_map.put("D|A", "0010101");
        tmp_comp_map.put("M",   "1110000");
        tmp_comp_map.put("!M",  "1110001");
        tmp_comp_map.put("-M",  "1110011");
        tmp_comp_map.put("M+1", "1110111");
        tmp_comp_map.put("M-1", "1110010");
        tmp_comp_map.put("D+M", "1000010");
        tmp_comp_map.put("D-M", "1010011");
        tmp_comp_map.put("M-D", "1000111");
        tmp_comp_map.put("D&M", "1000000");
        tmp_comp_map.put("D|M", "1010101");
        COMP_MAP = tmp_comp_map;
        
        // dest - 3 bits
        Map<String, String> tmp_dest_map = new HashMap<>();
        tmp_dest_map.put("null", "000");
        tmp_dest_map.put(null,   "000");
        tmp_dest_map.put("M",    "001");
        tmp_dest_map.put("D",    "010");
        tmp_dest_map.put("MD",   "011");
        tmp_dest_map.put("A",    "100");
        tmp_dest_map.put("AM",   "101");
        tmp_dest_map.put("AD",   "110");
        tmp_dest_map.put("AMD",  "111");
        DEST_MAP = tmp_dest_map;
        
        // jump - 3 bits
        Map<String, String> tmp_jump_map = new HashMap<>();
        tmp_jump_map.put("null", "000");
        tmp_jump_map.put(null,   "000");
        tmp_jump_map.put("JGT",  "001");
        tmp_jump_map.put("JEQ",  "010");
        tmp_jump_map.put("JGE",  "011");
        tmp_jump_map.put("JLT",  "100");
        tmp_jump_map.put("JNE",  "101");
        tmp_jump_map.put("JLE",  "110");
        tmp_jump_map.put("JMP",  "111");
        JUMP_MAP = tmp_jump_map;
    }
    
    // returns the binary code for the 'comp' mnemonic
    public static String comp(String c) {
        return COMP_MAP.get(c);
    }
    
    // returns the binary code of the 'dest' mnemonic
    public static String dest(String d) {
        return DEST_MAP.get(d);
    }
    
    // returns the binary code of the 'jump' mnemonic
    public static String jump(String j) {
        return JUMP_MAP.get(j);
    }
    
    public static String intToBinaryString(Integer i){
        return Integer.toBinaryString(0x10000 | i).substring(1);
    }
    
    public static String intStringToBinaryString(String s){
        return intToBinaryString(Integer.parseInt(s));
    }
}
