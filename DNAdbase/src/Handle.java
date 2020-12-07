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
 * Handle object that contains handles
 * of sequenceID and sequences
 * 
 * @author James Kim (thejameskim)
 * @author Natalie Kakish (Nataliekakish)
 * @version (2020-11-27)
 *
 */
public class Handle {

    
    private Pair seqIdHandle;
    private Pair seqHandle;

    /**
     * creates a new handle object
     * 
     * @param sequenceIDHandle
     *            , start pos and length of the sequence ID
     *            in the memory file
     * @param sequenceHandle
     *            , start pos and length of the sequence
     *            in the memory file
     */
    public Handle(Pair sequenceIDHandle, Pair sequenceHandle) {
        seqIdHandle = sequenceIDHandle;
        seqHandle = sequenceHandle;

    }


    /**
     * returns the sequence ID handle
     * start pos and length of the sequence ID
     * in the memory file
     * 
     * @return the sequence id handle
     */
    public Pair getSeqIdHandle() {
        return seqIdHandle;

    }


    /**
     * returns the sequence ID handle
     * start pos and length of the sequence ID
     * in the memory file
     * 
     * @return the sequence id handle
     */
    public Pair getSeqHandle() {
        return seqHandle;

    }


    /**
     * sets the sequence ID handle
     * 
     * @param sequenceIDHandle
     *            start pos and length of the sequence ID
     *            in the memory file
     */
    public void setSeqIdHandle(Pair sequenceIDHandle) {
        seqIdHandle = sequenceIDHandle;
    }


    /**
     * sets the sequence handle
     * 
     * @param sequenceHandle
     *            start pos and length of the sequence
     *            in the memory file
     */
    public void setSeqHandle(Pair sequenceHandle) {
        seqHandle = sequenceHandle;
    }

 

}
