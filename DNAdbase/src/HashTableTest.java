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
 * tests the HashTable class
 * 
 * @author James Kim (thejameskim)
 * @author Natalie Kakish (Nataliekakish)
 * @version (2020-11-28)
 *
 */
public class HashTableTest extends TestCase {
    
    private HashTable<String, Integer> hashTable;
    
    /**
     * Setup
     */
    public void setUp() {
        
        Integer[] arr = new Integer[64];
        hashTable = new HashTable<String, Integer>(arr);
        
    }
    
    
    /**
     * test for insert
     */
    public void testInsert() {
        
//        for (int i = 0; i < 34; i++) {
//            hashTable.insert("A", i + 1);
//        }
//        for (int i = 0; i < 34; i++) {
//            hashTable.insert("XXXXX", i + 1);
//        }
//        
//        hashTable.printHashTable();
//        
//        assertEquals("Index: 0 [17]\n"
//            + "Index: 1 [18]\n"
//            + "Index: 2 [19]\n"
//            + "Index: 3 [20]\n"
//            + "Index: 4 [21]\n"
//            + "Index: 5 [22]\n"
//            + "Index: 6 [23]\n"
//            + "Index: 7 [24]\n"
//            + "Index: 8 [25]\n"
//            + "Index: 9 [26]\n"
//            + "Index: 10 [27]\n"
//            + "Index: 11 [28]\n"
//            + "Index: 12 [29]\n"
//            + "Index: 13 [30]\n"
//            + "Index: 14 [31]\n"
//            + "Index: 15 [32]\n"
//            + "Index: 16 [1]\n"
//            + "Index: 17 [2]\n"
//            + "Index: 18 [3]\n"
//            + "Index: 19 [4]\n"
//            + "Index: 20 [5]\n"
//            + "Index: 21 [6]\n"
//            + "Index: 22 [7]\n"
//            + "Index: 23 [8]\n"
//            + "Index: 24 [9]\n"
//            + "Index: 25 [10]\n"
//            + "Index: 26 [11]\n"
//            + "Index: 27 [12]\n"
//            + "Index: 28 [13]\n"
//            + "Index: 29 [14]\n"
//            + "Index: 30 [15]\n"
//            + "Index: 31 [16]\n"
//            + "Index: 32 [8]\n"
//            + "Index: 33 [9]\n"
//            + "Index: 34 [10]\n"
//            + "Index: 35 [11]\n"
//            + "Index: 36 [12]\n"
//            + "Index: 37 [13]\n"
//            + "Index: 38 [14]\n"
//            + "Index: 39 [15]\n"
//            + "Index: 40 [16]\n"
//            + "Index: 41 [17]\n"
//            + "Index: 42 [18]\n"
//            + "Index: 43 [19]\n"
//            + "Index: 44 [20]\n"
//            + "Index: 45 [21]\n"
//            + "Index: 46 [22]\n"
//            + "Index: 47 [23]\n"
//            + "Index: 48 [24]\n"
//            + "Index: 49 [25]\n"
//            + "Index: 50 [26]\n"
//            + "Index: 51 [27]\n"
//            + "Index: 52 [28]\n"
//            + "Index: 53 [29]\n"
//            + "Index: 54 [30]\n"
//            + "Index: 55 [31]\n"
//            + "Index: 56 [32]\n"
//            + "Index: 57 [1]\n"
//            + "Index: 58 [2]\n"
//            + "Index: 59 [3]\n"
//            + "Index: 60 [4]\n"
//            + "Index: 61 [5]\n"
//            + "Index: 62 [6]\n"
//            + "Index: 63 [7]\n", systemOut().getHistory());
        
        
    }
    
    
    
    
    
}