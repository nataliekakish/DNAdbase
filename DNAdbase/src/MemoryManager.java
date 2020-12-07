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

    /** Memory file */
    private RandomAccessFile raf;

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
        File memFile = memoryFile;
        try {
            raf = new RandomAccessFile(memFile, "rw");

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

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
     * Insert method for memory
     * 
     * @param seqID
     *            sequence ID
     * @param length
     *            length of sequence
     * @param seq
     *            sequence
     * @return Handle object
     * @throws IOException
     */
    public Handle insert(String seqID, int length, String seq)
        throws IOException {

        Pair pair1 = insertS(seqID);

        Pair pair2 = insertS(seq);

        updateFreeBlocksList();
        return new Handle(pair1, pair2);

    }


    /**
     * Remove method
     * 
     * @param handle
     *            to remove
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
                freeBlocksList.add(new Pair(seqIdHandle.getLoc(), seqIdHandle
                    .getLen() + seqIdPadding));
                updateFreeBlocksList();
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
     * Insert method to return pair to insert into memory
     * 
     * @param s
     *            sequenceID
     * @return Pair object
     * @throws IOException
     */
    public Pair insertS(String s) throws IOException {
        int pos;
        // Convert to binary and write to memory file
        byte[] sByte = seqToBinary(s);

        if (freeBlocksList.size() == 0) {
            pos = (int)raf.length();

            raf.seek(pos);
            raf.write(sByte);
        }
        else {
            int bestFitPos = bestFitPos(sByte.length * 8);

            if (bestFitPos == -1) {

                pos = (int)raf.length();
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

                if (newFreeBlockLen == 0) {
                    freeBlocksList.remove(bestFitPos);
                }
                if (freeBlocksList.size() != 0 && newFreeBlockLen != 0) {
                    freeBlocksList.get(bestFitPos).setLen(newFreeBlockLen * 8);
                    freeBlocksList.get(bestFitPos).setLoc(newFreeBlockLoc * 8);
                }

            }
        }

        return new Pair(pos * 8, s.length() * 2);
    }


    /**
     * Updates free block list after removing
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

        // Remove last free block if there's nothing after it
        if (freeBlocksList.size() > 0 && raf.length() == (freeBlocksList
            .getLast().getLen() + freeBlocksList.getLast().getLoc()) / 8) {

            raf.setLength((freeBlocksList.getLast().getLoc()) / 8);
            freeBlocksList.removeLast();

        }

        for (int i = 0; i < freeBlocksList.size(); i++) {
            if (freeBlocksList.get(i).getLen() == 0) {
                freeBlocksList.remove(i);
            }
        }

    }


    /**
     * Finds the best position within free blocks
     * 
     * @param len
     *            length of sequence
     * @return index of best fit position
     * @throws IOException
     */
    public int bestFitPos(int len) throws IOException {
        int currBestFit = -1;
        boolean firstFit = false;

        for (int i = 0; i < freeBlocksList.size(); i++) {
            if (freeBlocksList.get(i).getLen() >= len && !firstFit) {
                currBestFit = i;
                firstFit = true;
            }

            if (currBestFit != -1 && firstFit) {
                int currLen = freeBlocksList.get(i).getLen();
                int lastLen = freeBlocksList.get(currBestFit).getLen();

                if (currLen >= len && (lastLen - len) > (currLen - len)) {
                    currBestFit = i;
                }
            }
        }
        return currBestFit;
    }


    /**
     * converts sequence to corresponding
     * Binary representation
     * 
     * @param seq
     *            to turn into binary
     * @return byte array
     */
    public byte[] seqToBinary(String seq) {

        int bytes = 0;
        int padding = (8 - ((seq.length() * 2) % 8)) % 8;

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
     * Turns binary into sequences
     * 
     * @param bin
     *            binary to convert
     * @param seqLength
     *            length of sequence
     * @return String string form
     */
    public String binaryToSeq(byte[] bin, int seqLength) {
        String s = "";
        int padding = (8 - ((seqLength * 2) % 8)) % 8;

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
