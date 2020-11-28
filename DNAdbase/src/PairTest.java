import student.TestCase;

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
 * tests the Pair class
 * 
 * @author James Kim (thejameskim)
 * @author Natalie Kakish (Nataliekakish)
 * @version (2020-11-27)
 *
 */
public class PairTest extends TestCase {

    private Pair pair;


    /**
     * set up for tests
     */
    public void setUp() {
        pair = new Pair(0, 1);
    }


    /**
     * tests set location
     */
    public void testSetLoc() {
        pair.setLoc(1);
        assertEquals(1, pair.getLoc());
    }


    /**
     * tests get location
     */
    public void testGetLoc() {
        assertEquals(0, pair.getLoc());
    }


    /**
     * tests set length
     */
    public void testSetLen() {
        pair.setLen(0);
        assertEquals(0, pair.getLen());
    }


    /**
     * tests get length
     */
    public void testGetLen() {
        assertEquals(1, pair.getLen());
    }

}
