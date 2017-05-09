/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassFileParser.CPEntries;

import ClassFileParser.ConstantPool;
import ClassFileParser.InvalidConstantPoolIndex;
import java.io.DataInputStream;
import java.io.IOException;

/**
 *
 *  
 */
/**
 * Represents a CONSTANT_String entry (tag == 8). This holds a reference to a
 * Utf8 entry containing the string itself (just a raw index until
 * resolveReferences() is called).
 */
public class ConstantString extends CPEntry
{
    private int stringIndex;
    private ConstantUtf8 stringEntry = null;

    public ConstantString(DataInputStream dis) throws IOException
    {
        this.stringIndex = dis.readUnsignedShort();
    }

    public void resolveReferences(ConstantPool cp)
        throws InvalidConstantPoolIndex
    {
        this.stringEntry = (ConstantUtf8)cp.getEntry(stringIndex);
    }

    public int getStringIndex()  { return stringIndex; }
    public String getString()    { return stringEntry.getBytes(); }

    public String getTagString() { return "String"; }
    public String getValues()
    {
        return String.format("string_index=0x%02x", stringIndex);
    }
}