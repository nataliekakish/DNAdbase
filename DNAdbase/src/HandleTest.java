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
 * tests the Handle class
 * 
 * @author James Kim (thejameskim)
 * @author Natalie Kakish (Nataliekakish)
 * @version (2020-11-28)
 *
 */
public class HandleTest extends TestCase {

    
    private Handle handle;
    private Pair pair1;
    private Pair pair2;


    /**
     * set up for tests
     */
    public void setUp() {
        pair1 = new Pair(1, 1);
        pair2 = new Pair(2, 2);
        handle = new Handle(pair1, pair2);

    }


    /**
     * tests get sequence id handle
     */
    public void testGetSeqIdHandle() {
        assertEquals(handle.getSeqIdHandle(), pair1);
    }


    /**
     * tests get sequence handle
     */
    public void testGetSeqHandle() {
        assertEquals(handle.getSeqHandle(), pair2);
    }


    /**
     * tests set sequence id handle
     */
    public void testSetSeqIDHandle() {
        handle.setSeqIdHandle(pair2);
        assertEquals(handle.getSeqIdHandle(), pair2);
    }


    /**
     * tests set sequence handle
     */
    public void testSetSeqHandle() {
        handle.setSeqHandle(pair1);
        assertEquals(handle.getSeqHandle(), pair1);
    }

}
