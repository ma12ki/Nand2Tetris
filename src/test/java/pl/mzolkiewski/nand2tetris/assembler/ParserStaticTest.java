package pl.mzolkiewski.nand2tetris.assembler;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for static methods of the Parser class.
 * 
 * @author Martin
 */
public class ParserStaticTest {
    
    @Test
    public void testCleanUp_RemovesComments() {
        assertEquals("@i", Parser.cleanUpLine("@i // comment"));
    }
    
    @Test
    public void testCleanUp_RemovesWhitespace() {
        assertEquals("M=1", Parser.cleanUpLine("   M=1     \n"));
    }
    
    @Test
    public void testCleanUp_DoesNotChangeCorrectInput() {
        assertEquals("(LOOP)", Parser.cleanUpLine("(LOOP)"));
    }
    
    @Test
    public void testCommandTypeA() {
        assertEquals(Parser.Command.A, Parser.commandType("@m"));
    }
    
    @Test
    public void testCommandTypeC() {
        assertEquals(Parser.Command.C, Parser.commandType("D=M"));
    }
    
    @Test
    public void testCommandTypeL() {
        assertEquals(Parser.Command.L, Parser.commandType("(LOOP)"));
    }
    
    @Test
    public void testSymbolForACommand() {
        assertEquals("lol", Parser.symbol("@lol"));
    }
    
    @Test
    public void testSymbolForLCommand() {
        assertEquals("MAO", Parser.symbol("(MAO)"));
    }
    
    @Test
    public void testDestForAssignmentOperation() {
        assertEquals("M", Parser.dest("M=D"));
    }
    
    @Test
    public void testDestForJumpOperationIsNull() {
        assertNull(Parser.dest("M;JGT"));
    }
    
    @Test
    public void testCompForAssignmentOperation() {
        assertEquals("D", Parser.comp("M=D"));
    }
    
    @Test
    public void testCompForJumpOperation() {
        assertEquals("D", Parser.comp("D;JEQ"));
    }
    
    @Test
    public void testJumpForAssignmentOperation() {
        assertNull(Parser.jump("M=D"));
    }
    
    @Test
    public void testJumpForJumpOperation() {
        assertEquals("JLT", Parser.jump("D;JLT"));
    }
}
