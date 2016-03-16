package pl.mzolkiewski.nand2tetris;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import pl.mzolkiewski.nand2tetris.assembler.Assembler;

/**
 *
 * @author Martin
 */
public class Main {
    
    public static final String MODULE_ASSEMBLER = "Assembler";
    public static final String MODULE_VM = "VM";
    public static final String MODULE_COMPILER = "Compiler";
    
    public static void main(String[] args) {
        if (argsAreCorrect(args)) {
            callAppropriateModule(args);
        } else {
            printHelp();
        }
    }

    private static boolean argsAreCorrect(String[] args) {
        boolean argumentsOk = true;
        
        if (args.length < 2) {
            argumentsOk = false;
        } else {
            List<String> moduleList = Arrays.asList(new String[]{MODULE_ASSEMBLER, MODULE_VM, MODULE_COMPILER});
            if (!moduleList.contains(args[0])) {
                argumentsOk = false;
            }
        }
        return argumentsOk;
    }
    
    private static void callAppropriateModule(String[] args) {
            String module = args[0],
                    directoryOrFile = args[1];
            
            try {
                switch(module) {
                    case MODULE_ASSEMBLER:
                        Assembler.execute(directoryOrFile);
                        break;
                    case MODULE_VM:
                        throw new UnsupportedOperationException("VM not implemented yet");
                        //break;
                    case MODULE_COMPILER:
                        throw new UnsupportedOperationException("Compiler not implemented yet");
                        //break;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
    }

    private static void printHelp() {
        System.out.println("--------------------------------------------------");
        System.out.println("USAGE:");
        System.out.println("java -jar <jarName> <moduleName> <fileOrDirectory>");
        System.out.println("where moduleName in (Assembler, VM, Compiler)");
        System.out.println("");
        System.out.println("EXAMPLE:");
        System.out.println("java -jar Nand2Tetris.jar Assembler ./pong.asm");
        System.out.println("--------------------------------------------------");
    }

}
