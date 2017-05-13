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
 * Represents a CONSTANT_Class entry (tag == 7). This holds a reference to a 
 * Utf8 entry containing a class name (just a raw index until 
 * resolveReferences() is called).
 */
public class ConstantClass extends CPEntry
{
    private int nameIndex;
    private ConstantUtf8 nameEntry = null;

    public ConstantClass(DataInputStream dis) throws IOException
    {
        this.nameIndex = dis.readUnsignedShort();
    }

    public void resolveReferences(ConstantPool cp)
        throws InvalidConstantPoolIndex
    {
        this.nameEntry = (ConstantUtf8)cp.getEntry(nameIndex);
    }

    public int getNameIndex()    { return nameIndex; }
    public String getName()      { return nameEntry.getBytes(); }

    public String getTagString() { return "Class"; }
    public String getValues()
    {
        //return String.format("name_index=0x%02x", nameIndex);
        return "name_index=" + nameIndex;
    }
}