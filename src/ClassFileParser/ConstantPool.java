package ClassFileParser;

import ClassFileParser.CPEntries.InvalidTagException;
import ClassFileParser.CPEntries.CPEntry;
import java.io.*;

/**
 * Parses and stores the constant pool from a Java .class file.
 *
 * @author David Cooper
 */
public class ConstantPool
{

    public CPEntry[] getEntries() {
        return entries;
    }
    private CPEntry[] entries;

    /**
     * Parses the constant pool, including the length, constructing a
     * ConstantPool object in the process.
     */
    public ConstantPool(DataInputStream dis) throws InvalidTagException,
                                                    InvalidConstantPoolIndex,
                                                    IOException {
        int len = dis.readUnsignedShort();
        //System.out.println("Constant pool count " + len);
        entries = new CPEntry[len];
        int i;

        // Initialise entries to null.
        for(i = 0; i < len; i++)
        {
            entries[i] = null;
        }

        i = 1;
        while(i < len)
        {
            entries[i] = CPEntry.parse(dis);

            // We can't just have i++, because certain entries (Long and
            // Double) count for two entries.
            i += entries[i].getEntryCount();
        }

        // Once the constant pool has been parsed, resolve the various
        // internal references.
        for(i = 0; i < len; i++)
        {
            if(entries[i] != null)
            {
                entries[i].resolveReferences(this);
            }
        }
    }

    /** Retrieves a given constant pool entry. */
    public CPEntry getEntry(int index) throws InvalidConstantPoolIndex{
        if(index < 0 || index > entries.length)
        {
            throw new InvalidConstantPoolIndex(String.format(
                "Invalid constant pool index: %d (not in range [0, %d])",
                index, entries.length));
        }
        else if(entries[index] == null)
        {
            throw new InvalidConstantPoolIndex(String.format(
                "Invalid constant pool index: %d (entry undefined)", index));
        }
        return entries[index];
    }

    /** Returns a formatted String representation of the constant pool. */
    public String toString(){
        String s = "Index  Entry type          Entry values\n" +
                   "---------------------------------------\n";
        for(int i = 0; i < getEntries().length; i++)
        {
            if(getEntries()[i] != null)
            {
                s += String.format("%d \t %-18s  %s\n",
                    i, getEntries()[i].getTagString(), getEntries()[i].getValues());
            }
        }
        return s;
    }
    
    
    
}
