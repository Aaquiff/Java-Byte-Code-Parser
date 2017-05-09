/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassFileParser.CPEntries;

import java.io.DataInputStream;
import java.io.IOException;

/**
 *
 *  
 */
/**
 * Represents a CONSTANT_Long entry (tag == 5). This class overrides
 * getEntryCount() to indicate that a Long entry counts for two entries in the
 * constant pool.
 */
public class ConstantLong extends CPEntry
{
    private long value;

    public ConstantLong(DataInputStream dis) throws IOException
    {
        this.value = dis.readLong();
    }

    public long getLongValue()   { return value; }
    public String getTagString() { return "Long"; }
    public int getEntryCount()   { return 2; }

    public String getValues() { return String.format("value=%d", value); }
}
