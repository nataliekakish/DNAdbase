import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
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
 * tests the MemoryManager class
 * 
 * @author James Kim (thejameskim)
 * @author Natalie Kakish (Nataliekakish)
 * @version (2020-11-28)
 *
 */
public class MemoryManagerTest extends TestCase {
    File file = new File("test");
    MemoryManager memManager;


    /**
     * Setup
     */
    public void setUp() {
        memManager = new MemoryManager(file);
    }


    /**
     * tests sequence to binary
     */
    public void testSeqToBinary() {
        byte[] arr = memManager.seqToBinary(
            "AAAATTTTCCCCGGGGAAAACCCCGGGGTTTTAAAATTTT");

        assertEquals(10, arr.length);
        String s = memManager.binaryToSeq(arr, 40);

        assertEquals("AAAATTTTCCCCGGGGAAAACCCCGGGGTTTTAAAATTTT", s);

        byte[] arr2 = memManager.seqToBinary("AAAAA");
        assertEquals(2, arr2.length);

        String s2 = memManager.binaryToSeq(arr2, 5);
        assertEquals("AAAAA", s2);

    }


    /**
     * tests best fit position
     * 
     * @throws IOException
     */
    public void testBestFitPos() throws IOException {
        LinkedList<Pair> freeBlocks = new LinkedList<Pair>();
        freeBlocks.add(new Pair(0, 5));
        freeBlocks.add(new Pair(15, 10));
        freeBlocks.add(new Pair(15, 10));
        freeBlocks.add(new Pair(5, 10));
        memManager.setFreeBlocksList(freeBlocks);

        assertEquals(0, memManager.bestFitPos(5));
        assertEquals(1, memManager.bestFitPos(6));

    }


    /**
     * tests insert
     * 
     * @throws IOException
     */
    public void testInsert() throws IOException {
        LinkedList<Pair> freeBlocks = new LinkedList<Pair>();
        freeBlocks.add(new Pair(0, 5));
        freeBlocks.add(new Pair(15, 10));
        freeBlocks.add(new Pair(15, 10));
        freeBlocks.add(new Pair(5, 10));

        memManager.setFreeBlocksList(freeBlocks);

        // should go into first block
        // linked list should have size 3 because the first free block is
        // removed
        memManager.insertS("AAAAAAAAAAAAAAAAAAAA");
        // new freeBlocksList
        LinkedList<Pair> list = memManager.getFreeBlocksList();

        assertEquals(3, list.size());
        assertEquals(15, list.get(0).getLoc());

        // should go into first block
        // linked list should have size same size
        // first block should have location 19, size 6
        memManager.insertS("AAAAAAAAAAAAA");
        // new freeBlocksList
        LinkedList<Pair> list2 = memManager.getFreeBlocksList();

        assertEquals(3, list2.size());
        assertEquals(6, list2.get(0).getLen());
        assertEquals(19, list2.get(0).getLoc());

    }


    /**
     * tests updateFreeBlocksList
     */
    public void testUpdateFreeBlocksList() {
        LinkedList<Pair> freeBlocks = new LinkedList<Pair>();
        freeBlocks.add(new Pair(0, 5));
        freeBlocks.add(new Pair(5, 10));
        memManager.setFreeBlocksList(freeBlocks);
        memManager.updateFreeBlocksList();
        assertEquals(1, freeBlocks.size());
        assertEquals(15, freeBlocks.get(0).getLen());

    }

}
