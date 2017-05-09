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
 * Abstract superclass for the three CONSTANT_xxxref entry types. These
 * contain references to a ConstantClass entry and to a ConstantNameAndType
 * entry (both just raw indexes until resolveReferences() is called).
 */
public abstract class ConstantRef extends CPEntry
{

    /**
     * @return the nameAndTypeEntry
     */
    public ConstantNameAndType getNameAndTypeEntry() {
        return nameAndTypeEntry;
    }
    private int classIndex;
    private int nameAndTypeIndex;

    private ConstantClass classEntry = null;
    private ConstantNameAndType nameAndTypeEntry = null;

    public ConstantRef(DataInputStream dis) throws IOException
    {
        this.classIndex = dis.readUnsignedShort();
        this.nameAndTypeIndex = dis.readUnsignedShort();
    }

    public void resolveReferences(ConstantPool cp)
        throws InvalidConstantPoolIndex
    {
        this.classEntry = (ConstantClass)cp.getEntry(classIndex);
        this.nameAndTypeEntry =
            (ConstantNameAndType)cp.getEntry(nameAndTypeIndex);
    }

    public int getClassIndex()          { return classIndex; }
    public int getNameAndTypeIndex()    { return nameAndTypeIndex; }

    public String getClassName()        { return classEntry.getName(); }
    public String getName()             { return getNameAndTypeEntry().getName(); }
    public String getType()             { return getNameAndTypeEntry().getType(); }

    public String getValues()
    {
        return String.format("class_index=0x%02x, name_and_type_index=0x%02x",
            classIndex, nameAndTypeIndex);
    }
}