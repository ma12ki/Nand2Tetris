package pl.mzolkiewski.nand2tetris.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import pl.mzolkiewski.nand2tetris.assembler.Assembler;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 * Tests for methods in the FileHelper class.
 * 
 * @author Martin
 */
public class FileHelperTest {

    private static Path basePath = Paths.get("testfiles", "utils", "files_in_dir_and_subdirs"),
            nonExistentPath = Paths.get("testfiles", "THIS_DIR_DOES_NOT_EXIST");
    
    @Test
    public void testGetFilePathsByExtensionRecursive_FindsAllFiles() throws IOException {
        List<String> paths = FileHelper.getFilePathsByExtensionInDirRecursive(basePath.toString(), ".txt");
        
        assertEquals(getExpectedFilePaths(), paths);
    }
    
    private List<String> getExpectedFilePaths() {
        List<String> paths = new ArrayList<>();
        paths.add(Paths.get(basePath.toString(), "File1.txt").toAbsolutePath().toString());
        paths.add(Paths.get(basePath.toString(), "File2.txt").toAbsolutePath().toString());
        paths.add(Paths.get(basePath.toString(), "sub", "File3.txt").toAbsolutePath().toString());
        paths.add(Paths.get(basePath.toString(), "sub", "File4.txt").toAbsolutePath().toString());
        paths.add(Paths.get(basePath.toString(), "sub", "File5.txt").toAbsolutePath().toString());
        
        return paths;
    }
    
    @Test
    public void testGetFilePathsByExtensionRecursive_ReturnsEmptyList() throws IOException {
        List<String> paths = FileHelper.getFilePathsByExtensionInDirRecursive(nonExistentPath.toString(), ".txt");
        
        assertEquals(new ArrayList<String>(), paths);
    }
    
    /////////////////
    
    @Test(expected=IOException.class)
    public void testOpenReadOnlyFile_Throws_IOException() throws IOException {
        Path path = Paths.get(nonExistentPath.toString(), "somefile.txt");
        FileHelper.openReadOnlyFile(path.toString());
    }
    
    @Test
    public void testOpenReadOnlyFile_Returns_ReadOnlyFile() throws IOException {
        Path path = Paths.get(basePath.toString(), "File1.txt");
        File file = FileHelper.openReadOnlyFile(path.toString());
        
        if (file.canWrite()) {
            fail(file.getAbsolutePath() + " should be read only");
        }
    }
    
    ///////////////
    
    @Test(expected=IOException.class)
    public void testCheckFileExists_Throws_IOException() throws IOException {
        Path path = Paths.get(nonExistentPath.toString(), "somefile.txt");
        FileHelper.checkFileExists(new File(path.toString()));
    }
    
    @Test
    public void testCheckFileExists_DoesNotThrow() throws IOException {
        Path path = Paths.get(basePath.toString(), "File1.txt");
        FileHelper.checkFileExists(new File(path.toString()));
    }
    
    //////////////
    
    @Test(expected=IOException.class)
    public void testCheckIsFile_Throws_IOException() throws IOException {
        FileHelper.checkIsFile(new File(basePath.toString()));
    }
    
    @Test
    public void testCheckIsFile_DoesNotThrow() throws IOException {
        Path path = Paths.get(basePath.toString(), "File1.txt");
        FileHelper.checkIsFile(new File(path.toString()));
    }
    
    //////////////
    
    @Test
    public void testCheckInputFileNameExtension_DoesNotThrow_IllegalArgumentException() {
        FileHelper.checkInputFileExtension("MyProgram.asm", Assembler.ASSEMBLER_EXTENSION);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testCheckInputFileNameExtension_Throws_IllegalArgumentException() {
        FileHelper.checkInputFileExtension("MyProgram.lol", Assembler.ASSEMBLER_EXTENSION);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCheckInputFileNameExtension_CannotBeFooled_AndThrows_IllegalArgumentException() {
        FileHelper.checkInputFileExtension("MyProgram.gasm", Assembler.ASSEMBLER_EXTENSION);
    }
    
    //////////////
    
    @Test(expected=IOException.class)
    public void testGetGetCanonicalFilePath_Throws_IOException() throws IOException {
        FileHelper.getCanonicalDirectoryPath(nonExistentPath.toString());
    }
    
    @Test
    public void testGetGetCanonicalFilePath_Returns_CorrectPathForDirectory() throws IOException {
        String canonicalPath = FileHelper.getCanonicalDirectoryPath(basePath.toString());
        assertEquals(getExpectedCanonicalPath(), canonicalPath);
    }
    
    @Test
    public void testGetGetCanonicalFilePath_Returns_CorrectPathForFile() throws IOException {
        Path path = Paths.get(basePath.toString(), "File1.txt");
        String canonicalPath = FileHelper.getCanonicalDirectoryPath(path.toString());
        assertEquals(getExpectedCanonicalPath(), canonicalPath);
    } 
   
    private String getExpectedCanonicalPath() {
        return Paths.get(basePath.toString()).toAbsolutePath().toString();
    }
    
    /////////////
    
    @Test
    public void testGetFileNameWithoutExtension_Returns_CorrectFileName() {
        assertEquals("test", FileHelper.getFileNameWithoutExtension("test.extension"));
        assertEquals("another.test", FileHelper.getFileNameWithoutExtension("another.test.extension"));
    }
    
    @Test
    public void testGetFileNameWithoutExtensionFromFile_Returns_CorrectFileName() {
        assertEquals("test", FileHelper.getFileNameWithoutExtension(new File("test." + Assembler.ASSEMBLER_EXTENSION)));
        assertEquals("another.test", FileHelper.getFileNameWithoutExtension(new File("another.test." + Assembler.ASSEMBLER_EXTENSION)));
    }
}
