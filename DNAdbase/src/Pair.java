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
 * Pair object
 * 
 * @author James Kim (thejameskim)
 * @author Natalie Kakish (Nataliekakish)
 * @version (2020-11-27)
 *
 */
public class Pair {

    private int loc;
    private int len;


    /**
     * creates a new handle object
     * 
     * @param location
     *            , the location in the memory file
     * @param length
     *            , the length of the block
     */
    public Pair(int location, int length) {
        loc = location;
        len = length;
    }


    /**
     * sets the location in the memory file
     * 
     * @param location
     *            , the location in the memory file
     */
    public void setLoc(int location) {
        loc = location;

    }


    /**
     * sets the length of block
     * 
     * @param length
     *            , the length of the block
     */
    public void setLen(int length) {
        len = length;

    }

    /**
     * gets the location in the memory file
     * 
     * @return the location
     */
    public int getLoc() {
        return loc;

    }


    /**
     * gets the length of block
     * 
     * @return the length of the block
     */
    public int getLen() {
        return len;

    }

}
