import java.io.IOException;
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

    private int numObjects;
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

        numObjects = 0;

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
     * @throws IOException
     */
    public boolean insert(K s, V x, MemoryManager manager) throws IOException {
        // use hash function to find position
        int pos = (int)sfold((String)s, size);
        // if spot is taken
        if (hashTable[pos] != null) {

            // position after probing
            int pos2 = linearProbeInsert(pos, s, manager);
            // if bucket is full
            if (pos2 == -1) {
                return false;
            }
            else {
                hashTable[pos2] = x;
                numObjects++;
                tombstones[pos2] = false;
                return true;
            }
        }
        // if spot is empty
        else {
            hashTable[pos] = x;
            numObjects++;
            tombstones[pos] = false;
            System.out.println(numObjects);
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
     * @throws IOException
     */
    public V remove(K s, MemoryManager manager) throws IOException {
        int pos = (int)sfold((String)s, size);

        if (hashTable[pos] != null) {

            // position after probing
            int pos2 = linearProbeRemove(pos, s, manager);

            if (pos2 == -1) {
                return hashTable[pos2];
            }
            else {
                hashTable[pos2] = null;
                tombstones[pos2] = true;
                numObjects--;
                return hashTable[pos2];
            }

        }
        else {
            return hashTable[pos];

        }
    }


    /**
     * finds the object in the hash table and returns it
     * 
     * @param s
     *            , the object we're looking for
     * @param manager
     *            the manager
     * @return the object
     * @throws IOException
     */
    public V find(K s, MemoryManager manager) throws IOException {

        int pos = (int)sfold((String)s, size);

        if (hashTable[pos] != null) {
            // position after probing
            int pos2 = linearProbeRemove(pos, s, manager);

            if (pos2 == -1) {
                return hashTable[pos2];
            }
            else {

                return hashTable[pos2];
            }

        }
        else {
            return hashTable[pos];
        }

    }


    /**
     * Probe for the index to remove
     * 
     * @param pos
     *            position
     * @param s
     *            key
     * @param manager
     *            memory manager
     * @return int index
     * @throws IOException
     */
    public int linearProbeRemove(int pos, K s, MemoryManager manager)
        throws IOException {
        int startOfBucket = findStartOfBucket(pos); // find start of bucket
        int endOfBucket = findEndOfBucket(pos);

        // Position to end of bucket
        for (int i = pos; i < endOfBucket; i++) {
            // if the value is not null and it's not a tombstone
            if (hashTable[i] != null && tombstones[i] == false) {
                // if the handles are equal to each other
                if (s.equals(manager.getSequenceID((Handle)hashTable[i]))) {
                    return i;
                }

            } // if empty spot is encountered while probing, nothing to remove
              // Keep probing if there's a tombstone
            else if (hashTable[i] == null && tombstones[i] == false) {
                return -1;
            }
        }

        // Wrap around to start of bucket to position
        for (int i = startOfBucket; i < pos; i++) {
            // if the value is not null and it's not a tombstone
            if (hashTable[i] != null && tombstones[i] == false) {
                // if the handles are equal to each other
                if (s.equals(manager.getSequenceID((Handle)hashTable[i]))) {
                    return i;
                }

            } // if empty spot is encountered while probing, nothing to remove
            else if (hashTable[i] == null && tombstones[i] == false) {
                return -1;
            }
        }
        // Reached end of probing and nothing was found
        return -1;
    }


    /**
     * linearly probes bucket to find empty spot
     * for the insert function
     * 
     * @param pos
     *            position
     * @param s
     *            key
     * @param manager
     *            memory manager
     * @return the spot found, -1 if not found
     * @throws IOException
     */
    public int linearProbeInsert(int pos, K s, MemoryManager manager)
        throws IOException {
        int startOfBucket = findStartOfBucket(pos); // find start of bucket
        int endOfBucket = findEndOfBucket(pos); // find end of the bucket

        int indexOfTombstone = -1;

        // Position to end of bucket
        for (int i = pos; i <= endOfBucket; i++) {
            // If found empty spot or if there is a tombstone at that index
            if (hashTable[i] == null) {

                // If we already found a tombstone we can insert at
                if (indexOfTombstone != -1) {
                    return indexOfTombstone;
                }
                // Else insert at this empty spot
                return i;

            }
            // Found duplicate, return -1; if key is equal to key of handle at
            // index i
            else if (hashTable[i] != null && s.equals(manager.getSequenceID(
                (Handle)hashTable[i]))) {
                return -1;
            }
            // If found potential tombstone to insert; keep probing for
            // duplicates
            else if (tombstones[i] == true && indexOfTombstone == -1) {
                indexOfTombstone = i;
            }

        }

        // Wrap around to start of bucket to position
        for (int i = startOfBucket; i < pos; i++) {
            // If found empty spot or if there is a tombstone at that index
            if (hashTable[i] == null) {

                // If we already found a tombstone we can insert at
                if (indexOfTombstone != -1) {
                    return indexOfTombstone;
                }
                // Else insert at this empty spot
                return i;

            }
            // Found duplicate, return -1
            else if (hashTable[i] != null && s.equals(manager.getSequenceID(
                (Handle)hashTable[i]))) {

                return -1;
            }
            // If found potential tombstone to insert; keep probing for
            // duplicates
            else if (tombstones[i] == true && indexOfTombstone == -1) {
                indexOfTombstone = i;
            }
        }

        if (indexOfTombstone != -1) {
            return indexOfTombstone;
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


    /**
     * returns the num of objects in the hashtable
     * 
     * @return numObjects the num of objects
     */
    public int getNumObjects() {
        return numObjects;
    }


    /**
     * returns hashTable array
     */
    public V[] getArray() {
        return hashTable;
    }

}
