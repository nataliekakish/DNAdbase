import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.
/**
 * Main class
 * 
 * @author James Kim (thejameskim)
 * @author Natalie Kakish (Nataliekakish)
 * @version (2020-11-27)
 *
 */
public class DNAdbase {

    
    /**
     * main method
     * calls Parser to parse command file
     * 
     * @param args
     *            , commandline arguments
     * @throws IOException
     * @throws NumberFormatException
     */
    public static void main(String[] args)
        throws NumberFormatException,
        IOException {
        File memFile = null;
        // creating the memory file
        if (args.length >= 1) {
            try {
                memFile = new File(args[3]);
                memFile.createNewFile();
                if (memFile != null) {
                    new Parser(args[0], Integer.parseInt(args[2].trim()),
                        memFile);
                }
            }
            catch (IOException e) {
                System.out.println("Couldnt create memory file");
                e.printStackTrace();
            }

        }

        else {
            try {
                memFile = new File("output.bin");
                memFile.createNewFile();
                if (memFile != null) {
                    new Parser("sampleIn.txt", 32, memFile);
                }
                
                Path path = Paths.get("output.bin");
                Files.delete(path);
            }
            catch (IOException e) {
                System.out.println("Couldnt create memory file");
                e.printStackTrace();
            }

        }

    }
}
