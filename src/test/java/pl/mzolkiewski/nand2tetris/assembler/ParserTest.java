package pl.mzolkiewski.nand2tetris.assembler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Martin
 */
public class ParserTest {
    
    String testInput = "  @i \n"
            + "// assign 1 to i"
            + "  M=1 \n"
            + "  @sum \n"
            + "  M=0 // sum = 0 \n"
            + "(LOOP)";
    
    BufferedReader br;
    Parser p;
    
    @Before
    public void setUp() throws IOException {
        br = new BufferedReader(new StringReader(testInput));
        p = new Parser(br);
    }
    
    @After
    public void tearDown() {
        p = null;
        try {
            br.close();
        } catch (IOException e) {}
    }

    @Test
    public void testParser_CurrentLineIndexGetsIncrementedAfterAdvance() {
        assertEquals(0, (int) p.getCurrentLineIndex());
        p.advance();
        assertEquals(1, (int) p.getCurrentLineIndex());
    }
    
    @Test
    public void testParser_CurrentLineChangesAfterAdvance() {
        String line = p.getCurrentLine();
        p.advance();
        String nextLine = p.getCurrentLine();
        assertNotSame(nextLine, line);
    }
    
    @Test
    public void testParser_CurrentLineResets() {
        assertEquals(0, (int) p.getCurrentLineIndex());
        p.advance();
        p.resetCurrentLine();
        assertEquals(0, (int) p.getCurrentLineIndex());
    }
    
    @Test
    public void testParser_LinesHaveHumanReadableLineNumbers() {
        while (p.hasMoreCommands()) {
            assertNotNull(p.getCurrentHumanReadableLineNumber());
            p.advance();
        }
    }
    
    @Test
    public void testParser_LinesHaveIncreasingHumanReadableLineNumbers() {
        int previousLineNumber = -1,
                currentLineNumber;
        
        while (p.hasMoreCommands()) {
            currentLineNumber = p.getCurrentHumanReadableLineNumber();
            if (currentLineNumber <= previousLineNumber) {
                fail(String.format("Current line number %d is less than or equal to previous line number %d", currentLineNumber, previousLineNumber));
            }
            previousLineNumber = currentLineNumber;
            p.advance();
        }
    }
}
