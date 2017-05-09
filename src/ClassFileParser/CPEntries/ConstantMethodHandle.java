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
 * Represents a CONSTANT_MethodHandle entry (tag == 15).
 */
public class ConstantMethodHandle extends CPEntry
{
    private byte kind;
    private int index;
    private CPEntry entry = null;
    
    public ConstantMethodHandle(DataInputStream dis) throws IOException
    {
        this.kind = dis.readByte();
        this.index = dis.readUnsignedShort();
    }
    
    public void resolveReferences(ConstantPool cp)
        throws InvalidConstantPoolIndex
    {
        this.entry = cp.getEntry(index);
    }
    
    public byte getKind()     { return kind; }
    public int getIndex()     { return index; }
    public CPEntry getEntry() { return entry; }
    
    public String getTagString() { return "MethodHandle"; }
    public String getValues()
    {
        return String.format("reference_kind=%d, reference_index=0x%02x", 
                             kind, index);
    }
}