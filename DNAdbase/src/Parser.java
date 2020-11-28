import java.io.File;
import java.util.Scanner;

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
 * Parses the command file
 * 
 * @author James Kim (thejameskim)
 * @author Natalie Kakish (Nataliekakish)
 * @version (2020-11-28)
 *
 */
public class Parser {

    /** the memory file manager */
    private MemoryManager memManager;

    /** the hash map storing the handles */
    private HashTable<Handle> hashTable;

    /** array for hash table */
    private Handle[] handles;


    /**
     * parses command file
     * calls parseCommand with each line
     * 
     * @param file
     */
    public Parser(String file, int hashTableSz, File memFile) {

        handles = new Handle[hashTableSz];
        hashTable = new HashTable<Handle>(handles);
        memManager = new MemoryManager(memFile);

        // reading each line
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String command = reader.nextLine();
            if (command.contains("insert")) {
                // so that both lines are included in the insert command
                command = command + reader.nextLine();
            }
            parseCommand(command);

        }
    }


    /**
     * parses the command and calls its corresponding method
     * 
     * @param command
     *            , the command
     */
    public void parseCommand(String command) {

        String[] line = command.split("\\s+");

        // insert seqID length
        if (line[0].trim().equals("insert")) {
            String seqID = line[1].trim();
            int seqLen = Integer.parseInt(line[2].trim());
            String seq = line[3].trim();

            Handle handle = memManager.insert(seqID, seqLen, seq);
            if (handle != null) {
                hashTable.insert(seq, handle);
            }
        }

        // remove seqID
        else if (line[0].trim().equals("remove")) {
            String seqID = line[1].trim();
            Handle handle = memManager.remove(seqID);
            hashTable.insert(seqID, handle);
        }
        // print
        else if (line[0].trim().equals("print")) {
            print();
        }
        // search seqID
        else if (line[0].trim().equals("search")) {
            String seqID = line[1].trim();
            search(seqID);
        }

    }


    /**
     * searches the HashTable for the sequence
     * with the given sequence ID
     * 
     * @param seqID
     */
    public void search(String seqID) {

    }


    /**
     * prints all data in the HashTable
     */
    public void print() {

    }

}
