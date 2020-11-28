import java.io.File;
import java.util.LinkedList;

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
 * 
 * Manages the memory on disk
 * 
 * 1- receives command
 * 2- updates memory file and free blocks list
 * 3- returns handle
 * 
 * @author James Kim (thejameskim)
 * @author Natalie Kakish (Nataliekakish)
 * @version (2020-11-28)
 *
 */
public class MemoryManager {

    /** the list of free blocks */
    private LinkedList<Pair> freeBlocksList;

    /** the memory file to manage */
    private File memFile;


    /**
     * creates a MemoryManager object for the
     * given file
     * 
     * @param memoryFile
     *            , the memory file to be managed
     */
    public MemoryManager(File memoryFile) {
        freeBlocksList = new LinkedList<Pair>();
        memFile = memoryFile;

    }


    /**
     * inserts a sequence into the memory file
     * 
     * @param seqID
     *            , the sequence ID
     * @param length
     *            , the sequence length
     * @param seq
     *            the sequence
     */
    public Handle insert(String seqID, int length, String seq) {
        // checking if length is the actual length of seq
        int seqLength = seq.length();
        if (seqLength != length) {
            System.out.println("Warning: Actual sequence length " + seqLength
                + " does not match given length " + length);
        }
        Pair pair1 = insertSequenceID(seqID);
        Pair pair2 = insertSequence(seq);
        return new Handle(pair1, pair2);

    }


    /**
     * removes sequence with given
     * sequence ID from Memory file
     * 
     * updates freeBlocksList
     * 
     * @param seqID
     *            , the sequence ID
     */
    public Handle remove(String seqID) {
        return new Handle(new Pair(0, 0), new Pair(0, 0));

    }


    /**
     * returns the list of free blocks in the memory file
     * 
     * @return the free blocks list
     */
    public LinkedList<Pair> getFreeBlocksList() {
        return freeBlocksList;
    }


    /**
     * inserts sequence ID
     * 
     * @param seqID
     * @param length
     */
    private Pair insertSequenceID(String seqID) {
        int pos = bestFitPos();
        // not sure if length in bytes or string
        int length = seqID.length();
        // convert to binary
        // output to binary file
        // update freeBlocksList
        return new Pair(pos, length);

    }


    /**
     * inserts sequence
     * 
     * @param sequence
     */
    private Pair insertSequence(String sequence) {
        int pos = bestFitPos();
        int length = sequence.length();
        // convert to binary
        // output to binary file
        // update freeBlocksList
        return new Pair(pos, length);

    }


    /**
     * finds the position in the memory file
     * best fit for the sequence/sequenceID
     * 
     * *** might need parameter
     * 
     * @return
     */
    private int bestFitPos() {
        int currbestFit = 0;
        // difference between the size of the free block and the size of the new
        // entry
        int currDiff;
        for (int i = 0; i < freeBlocksList.size(); i++) {

        }
        return 0;
    }


    /**
     * converts sequence to corresponding
     * Binary representation
     * 
     * @param seq
     * @return byte array
     */
    private byte[] seqToBinary(String seq) {

        for (int i = 0; i < seq.length(); i++) {

            char currChar = seq.charAt(i);
            // 00
            if (currChar == 'A') {

            }
            // 01
            else if (currChar == 'C') {

            }
            // 10
            else if (currChar == 'G') {

            }
            // 11
            else if (currChar == 'T') {

            }
        }
        return new byte[5];

    }


    /**
     * converts binary representation of
     * sequence to string
     * 
     * @return sequence
     */
    private String binaryToSeq(byte[] bin) {

        return "";
    }

}
