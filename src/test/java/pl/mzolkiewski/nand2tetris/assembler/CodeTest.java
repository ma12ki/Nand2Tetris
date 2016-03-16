package pl.mzolkiewski.nand2tetris.assembler;

import org.junit.Test;
import static org.junit.Assert.*;

import static pl.mzolkiewski.nand2tetris.assembler.Code.*;

/**
 *
 * @author Martin
 */
public class CodeTest {
    
    @Test
    public void testCompMap_AllValuesHaveLength7() {
        for (String val : COMP_MAP.values()) {
            assertEquals(7, val.length());
        }
    }
    
    @Test
    public void testCompMap_CorrectValueFor0() {
        assertEquals("0101010", COMP_MAP.get("0"));
    }
    
    @Test
    public void testCompMap_DoesNotContain1111111() {
        assertFalse(COMP_MAP.values().contains("1111111"));
    }
    
    @Test
    public void testComp_CorrectValueForM() {
        assertEquals(COMP_MAP.get("M"), Code.comp("M"));
    }
    
    @Test
    public void testComp_DoesNotContainF() {
        assertNull(Code.comp("F"));
    }
}
