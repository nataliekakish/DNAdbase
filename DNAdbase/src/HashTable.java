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
 * Generic table implementation
 * 
 * @author James Kim (thejameskim)
 * @author Natalie Kakish (Nataliekakish)
 * @version (2020-11-27)
 * @param <K>
 *            The key
 * @param <V>
 *            The key value
 *
 */
public class HashTable<K, V> {

    private int numObjects;
    private V[] table;
    private int size;
    private Boolean[] tombstones;

    /**
     * The Hash table object
     * 
     * @param hTable
     *            array for the hash table
     */
    public HashTable(V[] hTable) {

        numObjects = 0;

        size = hTable.length;
        tombstones = new Boolean[size];
        // set all tombstones to false
        Arrays.fill(tombstones, false);
        table = hTable;

    }


    /**
     * Insert for hash table
     * @param s key to insert
     * @param x value
     * @param manager Memory Manager
     * @return position to insert at
     * @throws IOException
     */
    public int insert(K s, V x, MemoryManager manager) throws IOException {

        // use hash function to find position
        int pos = (int)sfold((String)s, size);
        // if spot is taken
        if (table[pos] != null) {

            // position after probing
            int pos2 = linearProbeInsert(pos, s, manager);
            // if bucket is full
            if (pos2 == -2 || pos2 == -1) {
                return pos2;
            }
            else {
                table[pos2] = x;
                numObjects++;
                tombstones[pos2] = false;
                return 0;
            }
        }
        // if spot is empty
        else {
            table[pos] = x;
            numObjects++;
            tombstones[pos] = false;
            return 0;
        }
    }


    /**
     * Remove method for hash table
     * @param s key to remvoe
     * @param manager Memory Manager
     * @return V value removed
     * @throws IOException
     */
    public V remove(K s, MemoryManager manager) throws IOException {
        int pos = (int)sfold((String)s, size);

        if (table[pos] != null || tombstones[pos]) {

            // position after probing
            int pos2 = linearProbeRemove(pos, s, manager);

            if (pos2 == -1) {
                return null;
            }
            else {
                V v = table[pos2];
                table[pos2] = null;
                tombstones[pos2] = true;
                numObjects--;
                return v;
            }

        }
        else {
            return null;

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

        if (table[pos] != null || tombstones[pos]) {
            // position after probing
            int pos2 = linearProbeRemove(pos, s, manager);

            if (pos2 == -1) {
                return null;
            }
            else {

                return table[pos2];
            }

        }
        return null;

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
        for (int i = pos; i <= endOfBucket; i++) {
            // if the value is not null and it's not a tombstone
            if (table[i] != null && !tombstones[i]) {
                // if the handles are equal to each other
                if (s.equals(manager.getSequenceID((Handle)table[i]))) {
                    return i;
                }

            } // if empty spot is encountered while probing, nothing to remove
              // Keep probing if there's a tombstone
            else if (table[i] == null && !tombstones[i]) {
                return -1;
            }
        }

        // Wrap around to start of bucket to position
        for (int i = startOfBucket; i < pos; i++) {
            // if the value is not null and it's not a tombstone
            if (table[i] != null && !tombstones[i]) {
                // if the handles are equal to each other
                if (s.equals(manager.getSequenceID((Handle)table[i]))) {
                    return i;
                }

            } // if empty spot is encountered while probing, nothing to remove
            else if (table[i] == null && !tombstones[i]) {
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
            if (table[i] == null) {

                // If we already found a tombstone we can insert at
                if (indexOfTombstone != -1) {
                    return indexOfTombstone;
                }
                // Else insert at this empty spot
                return i;

            }
            // Found duplicate, return -1; if key is equal to key of handle at
            // index i
            else if (table[i] != null && s.equals(manager.getSequenceID(
                (Handle)table[i]))) {
                return -1;
            }
            // If found potential tombstone to insert; keep probing for
            // duplicates
            else if (tombstones[i] && indexOfTombstone == -1) {
                indexOfTombstone = i;
            }

        }

        // Wrap around to start of bucket to position
        for (int i = startOfBucket; i < pos; i++) {
            // If found empty spot or if there is a tombstone at that index
            if (table[i] == null) {

                // If we already found a tombstone we can insert at
                if (indexOfTombstone != -1) {
                    return indexOfTombstone;
                }
                // Else insert at this empty spot
                return i;

            }
            // Found duplicate, return -1
            else if (table[i] != null && s.equals(manager.getSequenceID(
                (Handle)table[i]))) {

                return -1;
            }
            // If found potential tombstone to insert; keep probing for
            // duplicates
            else if (tombstones[i] && indexOfTombstone == -1) {
                indexOfTombstone = i;
            }
        }

        if (indexOfTombstone != -1) {
            return indexOfTombstone;
        }
        return -2;
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
     * Hash function
     * 
     * @param s
     *            string to base the function from
     * @param m
     *            int
     * @return value from hashing
     */
    private long sfold(String s, int m) {

        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++) {
                sum += c[k] * mult;
                mult *= 256;
            }
        }

        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256;
        }

        sum = (sum * sum) >> 8;
        return (Math.abs(sum) % m);
    }


    /**
     * returns the num of objects in the table
     * 
     * @return numObjects the num of objects
     */
    public int getNumObjects() {
        return numObjects;
    }


    /**
     * Returns the array
     * 
     * @return V[] array from hash table
     */
    public V[] getArray() {
        return table;
    }

}
