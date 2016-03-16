package pl.mzolkiewski.nand2tetris.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Some helper methods for file interaction.
 * 
 * @author Martin
 */
public class FileHelper {
    
    private FileHelper() {}
    
    public static List<String> getFilePathsByExtensionInDirRecursive(String directoryName, String extension) throws IOException {
        extension = extension.toLowerCase();
        List<String> filePaths = new ArrayList<>();
        File directory = new File(directoryName);
        
        if (directory.exists() && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(extension)) {
                    filePaths.add(file.getCanonicalPath());
                } else if (file.isDirectory()) {
                    filePaths.addAll(getFilePathsByExtensionInDirRecursive(file.getPath(), extension));
                }
            }
        }
        
        return filePaths;
    }
    
    public static File openReadOnlyFile(String filePath) throws IOException {
        File inputFile = new File(filePath);
        checkFileExists(inputFile);
        checkIsFile(inputFile);
        inputFile.setReadOnly();
        return inputFile;
    }
    
    public static void checkFileExists(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException(file.getName() + " does not exist.");
        }
    }
    
    public static void checkIsFile(File file) throws IOException  {
        if (!file.isFile()) {
            throw new IOException(file.getName() + " is not a file.");
        }
    }
    
    public static void checkInputFileExtension(String filePath, String extension) {
        if (!extension.startsWith(".")) {
            extension = "." + extension;
        }
        if (!filePath.toLowerCase().endsWith(extension)) {
            throw new IllegalArgumentException(String.format("Only %s files are accepted", extension));
        }
    }
    
    public static String getCanonicalDirectoryPath(File file) throws IOException {
        checkFileExists(file);
        if (file.isFile()) {
            file = file.getParentFile();
        }
        return file.getCanonicalPath();
    }

    public static String getCanonicalDirectoryPath(String filePath) throws IOException {
        return getCanonicalDirectoryPath(new File(filePath));
    }
    
    public static String getFileNameWithoutExtension(File file) {
        return getFileNameWithoutExtension(file.getName());
    }
    
    public static String getFileNameWithoutExtension(String fileName) {
        return Paths.get(fileName).getFileName().toString().replaceFirst("[.][^.]+$", "");
    }
    
    public static void writeOutputToFile(String path, List<String> outputList) throws IOException {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(path))) {
            for (String line : outputList) {
                output.append(line);
                output.newLine();
            }
        } finally {}
    }
    
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile()) {
            file.delete();
        }
    }
}
