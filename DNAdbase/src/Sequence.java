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
 * Sequence class
 * 
 * @author James Kim (thejameskim)
 * @author Natalie Kakish (Nataliekakish)
 * @version (2020-11-28)
 *
 */
public class Sequence {

    private String seq;
    private int seqLen;
    private String seqID;


    /**
     * creates a dna Sequence object
     * 
     * @param sequence
     *            , the dna sequence
     * @param sequenceLen
     *            , the length of the sequence
     * @param sequenceID
     *            , the sequence ID
     */
    public Sequence(String sequence, int sequenceLen, String sequenceID) {
        seq = sequence;
        seqLen = sequenceLen;
        seqID = sequenceID;
    }


    /**
     * sets the sequence String
     * 
     * @param sequence
     *            , the dna sequence
     */
    public void setSeq(String sequence) {
        seq = sequence;

    }


    /**
     * sets the length of the dna sequence
     * 
     * @param sequenceLen
     *            , the length of the sequence
     */
    public void setSeqLen(int sequenceLen) {
        seqLen = sequenceLen;

    }


    /**
     * sets the dna sequence ID
     * 
     * @param sequenceID
     *            , the sequence id
     */
    public void setSeqID(String sequenceID) {
        seqID = sequenceID;

    }

}
