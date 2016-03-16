package pl.mzolkiewski.nand2tetris.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Helper functions for tests.
 * 
 * @author Martin
 */
public class TestHelper {
    
    public static final String COMPARISON_EXTENSION = "cmp";
    
    // for use with methods that return parameters for parameterized tests (with @Parameters annotation)
    public static Collection<Object[]> parameterizeOneDimensionalList(List<Object> list) {
        Collection<Object[]> parameters = new ArrayList<>();
        
        for (Object element: list) {
            parameters.add(new Object[] {element});
        }
        
        return parameters;
    }
    
    public static void assertFileEquals(String pathStringToExpected, String pathStringToActual) throws FileNotFoundException, IOException {
        try (BufferedReader readerExpected = new BufferedReader(new FileReader(new File(pathStringToExpected)));
             BufferedReader readerActual = new BufferedReader(new FileReader(new File(pathStringToActual)))) {
           
            Object[] expectedLines = readerExpected.lines().toArray();
            Object[] actualLines = readerActual.lines().toArray();

            assertArrayEquals(expectedLines, actualLines);
        } finally {}
    }
}
