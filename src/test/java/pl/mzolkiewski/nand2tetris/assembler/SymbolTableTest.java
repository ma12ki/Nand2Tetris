package pl.mzolkiewski.nand2tetris.assembler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin
 */
public class SymbolTableTest {
    
    private SymbolTable st;
    
    @Before
    public void setUp() {
        st = new SymbolTable();
    }
    
    @After
    public void tearDown() {
        st = null;
    }

    @Test
    public void test_NewTableLengthIs23() {
        assertEquals(23, (int) st.getSymbolTableLength());
    }
    
    @Test
    public void test_ContainsSCREEN() {
        assertTrue(st.contains("SCREEN"));
    }
    
    @Test
    public void test_DoesNotContainFAKE() {
        assertFalse(st.contains("FAKE"));
    }
    
    @Test
    public void test_HasCorrectAddressForSCREEN() {
        Integer screenAddress = st.getAddress("SCREEN");
        
        assertEquals(0x4000, (int) screenAddress);
    }
    
    @Test
    public void test_SymbolTableSizeIncreasesAfterAddingNewKey() {
        Integer sizeBeforeAdd = st.getSymbolTableLength();
        
        st.addEntry("NEWSYMBOL", 0xaa);
        
        assertEquals(sizeBeforeAdd+1, (int) st.getSymbolTableLength());
    }
   
    @Test
    public void test_CanReadAddressForAddedSymbol() {
        Integer symbolAddress = 0xff;
        String symbolName = "NEWSYMBOL";
        
        st.addEntry(symbolName, symbolAddress);
        
        assertEquals(symbolAddress, st.getAddress(symbolName));
    }
}
