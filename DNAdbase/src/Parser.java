import java.io.File;
import java.io.IOException;
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
    private HashTable<String, Handle> hashTable;

    /** array for hash table */
    private Handle[] handles;


    /**
     * parses command file
     * calls parseCommand with each line
     * 
     * @param file
     * @throws IOException
     */
    public Parser(String file, int hashTableSz, File memFile)
        throws IOException {
        
        if (hashTableSz % 32 != 0) {
            System.out.println("Error: hastable size must be a multiple of 32");
            
        } 
        else {
            handles = new Handle[hashTableSz];
            hashTable = new HashTable<String, Handle>(handles);
            memManager = new MemoryManager(memFile);
            File filee = new File(file);
            // reading each line
            Scanner reader = new Scanner(filee);
            while (reader.hasNextLine()) {
                String command = reader.nextLine();
                if (command.contains("insert")) {

                    // so that both lines are included in the insert command
                    command = command + " " + reader.nextLine();
                }
                parseCommand(command);

            }
            reader.close();
        }

        
    }


    /**
     * parses the command and calls its corresponding method
     * 
     * @param command
     *            , the command
     * @throws IOException
     */
    public void parseCommand(String command) throws IOException {

        String[] line = command.trim().split("\\s+");

        // insert seqID length
        if (line[0].trim().equals("insert")) {
            String seqID = line[1].trim();

            int seqLen = Integer.parseInt(line[2].trim());
            String seq = line[3].trim();
            
            // If the sequence length and given length does not match
            if (seqLen != seq.length()) {
                System.out.println("Warning: Actual sequence length (" + seq.length() + ") does not match given length (" + seqLen + ")");
            }

            
            Handle handle = memManager.insert(seqID, seq.length(), seq);

            if (handle != null) {
                hashTable.insert(seqID, handle, memManager);
            }
        }

        // remove seqID
        else if (line[0].trim().equals("remove")) {
            String seqID = line[1].trim();
            Handle handle = hashTable.remove(seqID, memManager);

            if (handle != null) {
                memManager.remove(handle);
                if (hashTable.getNumObjects() == 0) {
                    memManager.getFreeBlocksList().clear();
                }
                System.out.println("Sequence Removed " + memManager.getSequenceID(handle) + ":");
                System.out.println(memManager.getSequence(handle));
                
            }
            else {
                System.out.println("SequenceID " + seqID + " not found");
            }
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
     *            , the sequence ID
     * @throws IOException
     */
    public void search(String seqID) throws IOException {
        Handle handle = hashTable.find(seqID, memManager);
        if (handle == null) {
            System.out.println("SequenceID " + seqID + " not found");
        }
        else {
            String seq = memManager.getSequence(handle);
            System.out.println("Sequence Found: " + seq);
        }

    }


    /**
     * prints all data in the HashTable
     * 
     * @throws IOException
     */
    public void print() throws IOException {

        if (hashTable.getNumObjects() == 0) {
            System.out.println("Sequence IDs:");
            System.out.println("Free Block List: none");
        }
        else {

            Handle[] handles = hashTable.getArray();

            System.out.println("Sequence IDs:");
            for (int i = 0; i < handles.length; i++) {

                if (handles[i] != null) {
                    String seq = memManager.getSequenceID(handles[i]);
                    System.out.println(seq + ": hash slot [" + i + "]");

                }
            }
            // hashTable.printHashTable(memManager);

            if (memManager.getFreeBlocksList().size() == 0) {
                System.out.println("Free Block List: none");
            }
            else {

                System.out.println("Free Block List:");
                for (int i = 0; i < memManager.getFreeBlocksList()
                    .size(); i++) {

                    System.out.println("[Block " + (i + 1)
                        + "] Starting Byte Location: " + memManager
                            .getFreeBlocksList().get(i).getLoc() / 8 + ", Size "
                        + memManager.getFreeBlocksList().get(i).getLen() / 8
                        + " bytes");

                }

            }
        }

    }

}
