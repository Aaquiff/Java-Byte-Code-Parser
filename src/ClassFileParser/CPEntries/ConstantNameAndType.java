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
 * Represents a CONSTANT_NameAndType entry (tag == 12). This holds references 
 * to two Utf8 entries containing the "name" and "type" (or descriptor) of a
 * field or method (both just raw indexes until resolveReferences() is
 * called).
 */
public class ConstantNameAndType extends CPEntry
{
    private int nameIndex;
    private int typeIndex;

    private ConstantUtf8 nameEntry = null;
    private ConstantUtf8 typeEntry = null;

    public ConstantNameAndType(DataInputStream dis) throws IOException
    {
        this.nameIndex = dis.readUnsignedShort();
        this.typeIndex = dis.readUnsignedShort();
    }

    public void resolveReferences(ConstantPool cp)
        throws InvalidConstantPoolIndex
    {
        this.nameEntry = (ConstantUtf8)cp.getEntry(nameIndex);
        this.typeEntry = (ConstantUtf8)cp.getEntry(typeIndex);
    }

    public int getNameIndex()    { return nameIndex; }
    public int getTypeIndex()    { return typeIndex; }

    public String getName()      { return nameEntry.getBytes(); }
    public String getType()      { return typeEntry.getBytes(); }

    public String getTagString() { return "NameAndType"; }
    public String getValues()
    {
        return String.format("name_index=0x%02x, type_index=0x%02x",
            nameIndex, typeIndex);
    }
}