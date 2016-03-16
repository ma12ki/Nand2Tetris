package pl.mzolkiewski.nand2tetris.assembler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import pl.mzolkiewski.nand2tetris.markers.IntegrationTest;
import pl.mzolkiewski.nand2tetris.utils.FileHelper;
import pl.mzolkiewski.nand2tetris.utils.TestHelper;

/**
 * Integration tests of the Assembler class.
 * 
 * @author Martin
 */
@Category(IntegrationTest.class)
@RunWith(Parameterized.class)
public class AssemblerTest {
    
    @Parameters
    public static Collection<Object[]> parameters() throws IOException {
        Path path = Paths.get("testfiles", "assembler");
        List parameterList = FileHelper.getFilePathsByExtensionInDirRecursive(path.toString(), "." + Assembler.ASSEMBLER_EXTENSION);
        return TestHelper.parameterizeOneDimensionalList(parameterList);
    }
    
    private String filePathString,
            canonicalDirectoryPathString,
            fileNameWithoutExtension,
            comparisonFilePathString,
            outputFilePathString;
    
    public AssemblerTest(String filePathString) throws IOException {
        this.filePathString = filePathString;
        Assembler.execute(filePathString);
    }
    
    @Before
    public void setUp() throws IOException {
        canonicalDirectoryPathString = FileHelper.getCanonicalDirectoryPath(filePathString);
        fileNameWithoutExtension = FileHelper.getFileNameWithoutExtension(filePathString);
        
        comparisonFilePathString = Paths.get(canonicalDirectoryPathString, 
                fileNameWithoutExtension 
                + "." + Assembler.HACK_EXTENSION 
                + "." + TestHelper.COMPARISON_EXTENSION).toString();
        outputFilePathString = Paths.get(canonicalDirectoryPathString, 
                fileNameWithoutExtension 
                + "." + Assembler.HACK_EXTENSION).toString();
    }
    
    @After
    public void tearDown() {
        FileHelper.deleteFile(outputFilePathString);
    }

    @Test
    public void testAssembledFileIsSameAsComparisonFile() throws FileNotFoundException, IOException {
        TestHelper.assertFileEquals(comparisonFilePathString, outputFilePathString);
    }

}
