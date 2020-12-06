import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

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
 * 
 * Manages the memory on disk
 * 
 * 1- receives command
 * 2- updates memory file and free blocks list
 * 3- returns handle
 * 
 * @author James Kim (thejameskim)
 * @author Natalie Kakish (Nataliekakish)
 * @version (2020-11-28)
 *
 */
public class MemoryManager {

    /** the list of free blocks */
    private LinkedList<Pair> freeBlocksList;

    /** the memory file to manage */
    private File memFile;

    /** Memory file */
    private RandomAccessFile raf;

    private int position;


    /**
     * creates a MemoryManager object for the
     * given file
     * 
     * @param memoryFile
     *            , the memory file to be managed
     * @throws IOException
     */
    public MemoryManager(File memoryFile) {
        freeBlocksList = new LinkedList<Pair>();
        memFile = memoryFile;
        position = 0;
        try {
            raf = new RandomAccessFile(memFile, "rw");

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    /**
     * Gets the handle from sequence ID
     * 
     * @param sequenceID
     *            the sequence ID
     * @return Handle handle
     */
    public Handle getHandle(String sequenceID) {

        return null;
    }


    /**
     * Gets the sequenceID based on handle from the memory file
     * 
     * @param handle
     *            handle
     * @return String sequenceID
     * @throws IOException
     */
    public String getSequenceID(Handle handle) throws IOException {

        position = (int)raf.length();
        int x = handle.getSeqIdHandle().getLoc();

        int padding = 0;

        if ((handle.getSeqIdHandle().getLen()) % 8 != 0) {
            padding = 8 - (handle.getSeqIdHandle().getLen() % 8);
        }

        byte[] b = new byte[(handle.getSeqIdHandle().getLen() + padding) / 8];

        // System.out.println("getting from position: " + (x / 8));
        raf.seek(x / 8);
        raf.read(b);

        String s = binaryToSeq(b, handle.getSeqIdHandle().getLen() / 2);
        return s;
    }


    /**
     * Gets the sequence based on handle from the memory file
     * 
     * @param handle
     *            handle
     * @return String sequence
     * @throws IOException
     */
    public String getSequence(Handle handle) throws IOException {

        int x = handle.getSeqHandle().getLoc();
        int padding = 0;

        if ((handle.getSeqHandle().getLen()) % 8 != 0) {
            padding = 8 - (handle.getSeqHandle().getLen() % 8);
        }

        byte[] b = new byte[(handle.getSeqHandle().getLen() + padding) / 8];
        raf.seek(x / 8);
        raf.read(b);

        String s = binaryToSeq(b, handle.getSeqHandle().getLen() / 2);
        return s;
    }


    /**
     * inserts a sequence into the memory file
     * 
     * @param seqID
     *            , the sequence ID
     * @param length
     *            , the sequence length
     * @param seq
     *            the sequence
     * @throws IOException
     */
    public Handle insert(String seqID, int length, String seq)
        throws IOException {

        Pair pair1 = insertS(seqID);
// System.out.println("pair 1 loc: " + pair1.getLoc());
// System.out.println("pair 1 len: " + pair1.getLen());
        Pair pair2 = insertS(seq);
// System.out.println("pair 2 loc: " + pair2.getLoc());
// System.out.println("pair 2 len: " + pair2.getLen());

        updateFreeBlocksList();
        return new Handle(pair1, pair2);

    }


    /**
     * removes sequence with given
     * sequence ID from Memory file
     * 
     * updates freeBlocksList
     * 
     * @param seqID
     *            , the sequence ID
     * @throws IOException
     */
    public void remove(Handle handle) throws IOException {

        Pair seqHandle = handle.getSeqHandle();
        Pair seqIdHandle = handle.getSeqIdHandle();

        int seqIdPadding = (8 - ((seqIdHandle.getLen()) % 8)) % 8;
        int seqPadding = (8 - ((seqHandle.getLen()) % 8)) % 8;

        // System.out.println("list size: " + freeBlocksList.size());

        // When the size of free block list is 0
        if (freeBlocksList.size() == 0) {
            if (seqIdHandle.getLoc() > seqHandle.getLoc()) {

                freeBlocksList.add(new Pair(seqHandle.getLoc(), seqHandle
                    .getLen() + seqPadding));
                updateFreeBlocksList();
                freeBlocksList.add(new Pair(seqIdHandle.getLoc(), seqIdHandle
                    .getLen() + seqIdPadding));
                // updateFreeBlocksList();
            }
            else {

                freeBlocksList.add(new Pair(seqIdHandle.getLoc(), seqIdHandle
                    .getLen() + seqIdPadding));
                updateFreeBlocksList();
                freeBlocksList.add(new Pair(seqHandle.getLoc(), seqHandle
                    .getLen() + seqPadding));
                // updateFreeBlocksList();
            }

        }
        else { // When size of free block list is greater than 0

            boolean insertedSequence = false;

            // Find a place to insert free block after remove
            // For the sequence
            for (int i = 0; i < freeBlocksList.size(); i++) {
                if (freeBlocksList.get(i).getLoc() > seqHandle.getLoc()) {
                    freeBlocksList.add(i, new Pair(seqHandle.getLoc(), seqHandle
                        .getLen() + seqPadding));
                    insertedSequence = true;
                    // updateFreeBlocksList();
                    break;
                }
            }

            if (!insertedSequence) {
                freeBlocksList.add(new Pair(seqHandle.getLoc(), seqHandle
                    .getLen() + seqPadding));
                // updateFreeBlocksList();
            }

            insertedSequence = false;
            // For the sequenceID
            for (int i = 0; i < freeBlocksList.size(); i++) {
                if (freeBlocksList.get(i).getLoc() > seqIdHandle.getLoc()) {
                    freeBlocksList.add(i, new Pair(seqIdHandle.getLoc(),
                        seqIdHandle.getLen() + seqIdPadding));
                    insertedSequence = true;
                    // updateFreeBlocksList();
                    break;
                }
            }

            if (!insertedSequence) {
                freeBlocksList.add(new Pair(seqIdHandle.getLoc(), seqIdHandle
                    .getLen() + seqIdPadding));
                // updateFreeBlocksList();
            }

        }
        updateFreeBlocksList();

    }


    /**
     * returns the list of free blocks in the memory file
     * 
     * @return the free blocks list
     */
    public LinkedList<Pair> getFreeBlocksList() {
        return freeBlocksList;
    }


    /**
     * for testing purposes
     * 
     * @param freeBlocks
     *            , the new freeBlocksList
     */
    public void setFreeBlocksList(LinkedList<Pair> freeBlocks) {
        freeBlocksList = freeBlocks;

    }


    /**
     * insert method for sequenceID and sequence
     * 
     * @param s
     *            , the sequence ID or sequence
     * @param length
     *            , the length of the sequence ID or sequence
     * @throws IOException
     */
    public Pair insertS(String s) throws IOException {
        int pos;
        // Convert to binary and write to memory file
        byte[] sByte = seqToBinary(s);

        // System.out.println("Free blocks list size: " +
        // freeBlocksList.size());

        if (freeBlocksList.size() == 0) {
            pos = (int)raf.length();
            // System.out.println("position after seeking: " + pos);
            // System.out.println(pos);

            raf.seek(pos);
            raf.write(sByte);
        }
        else {
            int bestFitPos = bestFitPos(sByte.length * 8);

            // System.out.println("best fit pos for " + s + ": " + bestFitPos);

            if (bestFitPos == -1) {

                pos = (int)raf.length();
                // System.out.println("inserting " + s + " in pos " + pos);
                raf.seek(pos);
                raf.write(sByte);
            }
            else {
                pos = freeBlocksList.get(bestFitPos).getLoc() / 8;
                raf.seek(pos);
                raf.write(sByte);

                int newFreeBlockLen = (freeBlocksList.get(bestFitPos).getLen()
                    - sByte.length * 8) / 8;

                int newFreeBlockLoc = (freeBlocksList.get(bestFitPos).getLoc()
                    + sByte.length * 8) / 8;

                // System.out.println("newFreeBlockLen: " + newFreeBlockLen);
                if (newFreeBlockLen == 0) {
                    // System.out.println("newFreeBlockLen: " +
                    // newFreeBlockLen);
                    freeBlocksList.remove(bestFitPos);
                }
                if (freeBlocksList.size() != 0) {
                    freeBlocksList.get(bestFitPos).setLen(newFreeBlockLen * 8);
                    freeBlocksList.get(bestFitPos).setLoc(newFreeBlockLoc * 8);
                }

            }
        }

        return new Pair(pos * 8, s.length() * 2);
    }


    /**
     * updates free block list after removing
     * makes sure there aren't any adjacent free blocks
     * 
     * @throws IOException
     */
    public void updateFreeBlocksList() throws IOException {

        for (int i = 0; i < freeBlocksList.size(); i++) {
            if (freeBlocksList.get(i).getLen() == 0) {
                freeBlocksList.remove(i);
            }

        }

        for (int i = 1; i < freeBlocksList.size(); i++) {
            Pair curr = freeBlocksList.get(i - 1);
            Pair next = freeBlocksList.get(i);
            // if two free blocks adjacent, merge
            if (curr.getLoc() + curr.getLen() == next.getLoc()) {

                int currPadding = (8 - ((curr.getLen()) % 8)) % 8;
                int nextPadding = (8 - ((next.getLen()) % 8)) % 8;

                freeBlocksList.get(i - 1).setLen(curr.getLen() + currPadding
                    + next.getLen() + nextPadding);
                freeBlocksList.remove(i);
                i--;
            }

        }

        if (freeBlocksList.size() > 0) {

            if (raf.length() == (freeBlocksList.getLast().getLen()
                + freeBlocksList.getLast().getLoc()) / 8) {

                // System.out.println("\n\nDECREMENTING\n");

                // System.out.println("decrementing position by " +
                // (freeBlocksList.get(
                // freeBlocksList.size() - 1).getLen() + freeBlocksList.get(
                // freeBlocksList.size() - 1).getLoc()) / 8);
                raf.setLength((freeBlocksList.getLast().getLoc()) / 8);
                freeBlocksList.removeLast();

            }
        }

        for (int i = 0; i < freeBlocksList.size(); i++) {
            if (freeBlocksList.get(i).getLen() == 0) {
                freeBlocksList.remove(i);
            }
        }

// System.out.println(freeBlocksList.size());
// System.out.println("new raf length " + raf.length());

    }


    /**
     * finds the position in the memory file
     * best fit for the sequence/sequenceID
     * 
     * @return the best fit position for the sequence/sequenceid
     */
    public int bestFitPos(int len) throws IOException {

        int currBestFit = -1;
        int ct = 0;
        while (ct < freeBlocksList.size()) {
            if (freeBlocksList.get(ct).getLen() >= len) {
                currBestFit = ct;
            }
            if (currBestFit != -1) {
                for (int i = 0; i < freeBlocksList.size(); i++) {
                    int currLen = freeBlocksList.get(i).getLen();
                    int lastLen = freeBlocksList.get(currBestFit).getLen();
                    if (currLen >= len && (lastLen - len) > (currLen - len)) {
                        currBestFit = i;
                    }
                }
                break;
            }
            ct++;
        }

        return currBestFit;
    }


    /**
     * converts sequence to corresponding
     * Binary representation
     * 
     * @param seq
     * @return byte array
     */
    public byte[] seqToBinary(String seq) {

        int bytes = 0;
        int padding;
        if (((seq.length() * 2) % 8) != 0) {
            padding = 8 - ((seq.length() * 2) % 8);
        }
        else {
            padding = 0;

        }
        byte[] byteArr = new byte[(seq.length() * 2 + padding) / 8];

        int byteIndex = 0;
        int i = 0;
        while (i < seq.length()) {
            // AGTACCC
            // 00 10 11 00 01 01 01 00

            char currChar = seq.charAt(i);
            // 00
            if (currChar == 'A') {
                bytes = bytes << 2;
            }
            // 01
            else if (currChar == 'C') {
                bytes = bytes << 2;
                bytes += 1;

            }
            // 10
            else if (currChar == 'G') {
                bytes = bytes << 2;
                bytes += 2;
            }
            // 11
            else if (currChar == 'T') {
                bytes = bytes << 2;
                bytes += 3;
            }

            i++;

            if (i % 4 == 0) {

                byteArr[byteIndex] = (byte)bytes;

                byteIndex++;
                bytes = 0;
            }
            else if (i == seq.length()) {

                bytes = bytes << padding;
                byteArr[byteIndex] = (byte)bytes;
            }

        }

        return byteArr;

    }


    /**
     * converts binary representation of
     * sequence to string
     * 
     * @return sequence
     */
    public String binaryToSeq(byte[] bin, int seqLength) {
        String s = "";
        int padding = 0;
        // 0011 //00 11 01 10
        if (((seqLength * 2) % 8) != 0) {
            padding = 8 - ((seqLength * 2) % 8);
            // System.out.print("padding for length " + seqLength + " is: "
            // + padding);
        }
        int mask = 0;
        int ct = 0;
        for (int i = 0; i < bin.length; i++) {
            int shiftAmt = 0;
            for (int j = 0; j < 4; j++) {

                if (ct < bin.length * 8 - padding) {
                    mask = bin[i] << shiftAmt;
                    mask = bin[i] >> (6 - shiftAmt);
                    mask = mask & 3;

                    if (mask == 0) {
                        s += "A";
                    }
                    else if (mask == 1) {
                        s += "C";
                    }
                    else if (mask == 2) {
                        s += "G";
                    }
                    else if (mask == 3) {
                        s += "T";
                    }
                    shiftAmt += 2;
                    ct += 2;
                }
            }
        }
        return s;
    }
}
