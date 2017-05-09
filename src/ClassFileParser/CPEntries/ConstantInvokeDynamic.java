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
 * Represents a CONSTANT_InvokeDynamic entry (tag == 18). Such entries are used
 * in conjunction with the invokedynamic instruction to determine which method 
 * should actually be invoked.
 */
public class ConstantInvokeDynamic extends CPEntry
{
    private int bootstrapMethodIndex;
    private int nameAndTypeIndex;
    
    private ConstantNameAndType nameAndTypeEntry = null;

    public ConstantInvokeDynamic(DataInputStream dis) throws IOException
    {
        this.bootstrapMethodIndex = dis.readUnsignedShort();
        this.nameAndTypeIndex = dis.readUnsignedShort();
    }

    public void resolveReferences(ConstantPool cp)
        throws InvalidConstantPoolIndex
    {
        this.nameAndTypeEntry =
            (ConstantNameAndType)cp.getEntry(nameAndTypeIndex);
    }

    public int getBootstrapMethodIndex() { return bootstrapMethodIndex; }
    public int getNameAndTypeIndex()     { return nameAndTypeIndex; }
    public String getName()              { return nameAndTypeEntry.getName(); }
    public String getType()              { return nameAndTypeEntry.getType(); }

    public String getTagString() { return "InvokeDynamic"; }
    public String getValues()
    {
        return String.format(
            "bootstrap_method_attr_index=0x%02x, name_and_type_index=0x%02x",
            bootstrapMethodIndex, nameAndTypeIndex);
    }
}