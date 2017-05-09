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
 * Represents a CONSTANT_MethodType entry (tag == 16).
 */
public class ConstantMethodType extends CPEntry
{
    private int index;
    private ConstantUtf8 entry = null;
    
    public ConstantMethodType(DataInputStream dis) throws IOException
    {
        this.index = dis.readUnsignedShort();
    }
    
    public void resolveReferences(ConstantPool cp)
        throws InvalidConstantPoolIndex
    {
        this.entry = (ConstantUtf8)cp.getEntry(index);
    }
    
    public int getIndex()   { return index; }
    public String getType() { return entry.getBytes(); }
    
    public String getTagString() { return "MethodType"; }
    public String getValues()
    {
        return String.format("descriptor_index=0x%02x", index);
    }
}