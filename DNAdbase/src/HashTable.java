import java.util.Arrays;

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
 * Generic HashTable implementation
 * 
 * @author James Kim (thejameskim)
 * @author Natalie Kakish (Nataliekakish)
 * @version (2020-11-27)
 *
 */
public class HashTable<K, V> {

    private V[] hashTable;
    private int size;
    private Boolean[] tombstones;

    /**
     * creates a fixed size hashtable
     * with given array
     * 
     * @param hTable
     */
    public HashTable(V[] hTable) {
        size = hTable.length;
        tombstones = new Boolean[size];
        // set all tombstones to false
        Arrays.fill(tombstones, false);
        hashTable = hTable;

    }


    /**
     * inserts
     * 
     * @param s
     *            , the string representing object
     * @param x
     *            , the object
     */
    public boolean insert(K s, V x) {
        // use hash function to find position
        int pos = (int)sfold((String)s, size);
        // if spot is taken
        if (hashTable[pos] != null) {

            // position after probing
            int pos2 = linearProbeInsert(pos);
            // if bucket is full
            if (pos2 == -1) {
                return false;
            }
            else {
                hashTable[pos2] = x;
                return true;
            }
        }
        // if spot is empty
        else {
            hashTable[pos] = x;
            return true;
        }
    }


    /**
     * removes an obj from the HashTable
     * 
     * @param s
     *            , the string representing object
     * @param x
     *            , the object
     * @return true if successful, false otherwise
     */
    public boolean remove(K s, V x) {
        int pos = (int)sfold((String)s, size);

        if (hashTable[pos] != null) {

            hashTable[pos] = null;
            tombstones[pos] = true;
            return true;
        }
        else {
            if (tombstones[pos] == true) {
                return false;
            }
            // we have to linear probe to find the
            // object to remove
            return true;

        }
    }


    public int linearProbeRemove(int pos) {
        int startOfBucket = findStartOfBucket(pos); // find start of bucket
        int endOfBucket = findEndOfBucket(pos);

        // Position to end of bucket
        for (int i = pos; i < endOfBucket; i++) {
            // if found empty spot
            if (hashTable[i] != null) {
                // if there's a tombstone
                if (tombstones[i] == false) {
                    return i;
                }

            }
        }

        // Wrap around to start of bucket to position
        for (int i = startOfBucket; i < pos; i++) {
            // if found empty spot
            if (hashTable[i] == null) {
                // if there's a tombstone
                if (tombstones[i] == false) {
                    return i;
                }
            }
        }
        return -1;
    }


    /**
     * linearly probes bucket to find empty spot
     * for the insert function
     * 
     * @return the spot found, -1 if not found
     */
    public int linearProbeInsert(int pos) {
        int startOfBucket = findStartOfBucket(pos); // find start of bucket
        int endOfBucket = findEndOfBucket(pos);


        // Position to end of bucket
        for (int i = pos; i <= endOfBucket; i++) {
            // if found empty spot
            if (hashTable[i] == null) {
                // if there's a tombstone
                if (tombstones[i] == false) {
                    return i;
                }

            }
        }

        // Wrap around to start of bucket to position
        for (int i = startOfBucket; i < pos; i++) {
            // if found empty spot
            if (hashTable[i] == null) {
                // if there's a tombstone
                if (tombstones[i] == false) {
                    return i;
                }
            }
        }
        return -1;
    }


    /**
     * Helper function to find the end pos of bucket
     * 
     * @param pos
     *            position
     * @return int position of end of bucket
     */
    private int findEndOfBucket(int pos) {
        int bucketNum = 1;

        while (pos > 32) {
            pos %= 32;
            bucketNum++;
        }

        int endOfBucket = (bucketNum * 32) - 1;

        return endOfBucket;
    }


    /**
     * Helper function to find the start pos of bucket
     * 
     * @param pos
     *            position
     * @return int position of start of bucket
     */
    private int findStartOfBucket(int pos) {

        return findEndOfBucket(pos) - 31;

    }


    /**
     * Hash Function
     * 
     * @param s
     *            , the string to be hashed
     * @param M
     *            , the size of the hash table
     * @return s's position in the hash table
     */
    long sfold(String s, int M) {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
            char c[] = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++) {
                sum += c[k] * mult;
                mult *= 256;
            }
        }

        char c[] = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256;
        }

        sum = (sum * sum) >> 8;
        return (Math.abs(sum) % M);
    }
    
    /**
     * Prints the contents of the hash table
     */
    public void printHashTable() {
        
        for (int i = 0; i < size; i++) {
            if (hashTable[i] == null) {
                System.out.println("Index: " + i + " [null]");
                
            }
            else {
                System.out.println("Index: " + i + " [" + hashTable[i] + "]");
            }
        }
            
        
        
    }

}
