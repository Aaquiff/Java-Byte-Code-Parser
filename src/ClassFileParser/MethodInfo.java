/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassFileParser;

import Attributes.AttributeInfo;
import ClassFileParser.CPEntries.ConstantUtf8;
import java.io.DataInputStream;
import java.io.IOException;

/**
 *
 * @author aaralk
 */
public class MethodInfo {
    private int access_flags;
    private int name_index;
    private int descriptor_index;
    private int attributes_count;
    private AttributeInfo attributes[];
    
    public MethodInfo(DataInputStream dis, ConstantPool cp) throws IOException, Exception
    {
        access_flags = dis.readUnsignedShort();
        name_index = dis.readUnsignedShort();
        descriptor_index = dis.readUnsignedShort();
        attributes_count = dis.readUnsignedShort();
        attributes = new AttributeInfo[attributes_count];
        for (int i = 0; i < attributes_count; i++) {
            attributes[i] =AttributeInfo.parse(dis, cp);
        }
    }
    
    public String getName(ConstantPool cp) throws InvalidConstantPoolIndex
    {
        ConstantUtf8 entry = (ConstantUtf8)cp.getEntry(name_index) ;
        return entry.getBytes();
    }
    
    public String getDescriptor(ConstantPool cp) throws InvalidConstantPoolIndex
    {
        ConstantUtf8 entry = (ConstantUtf8)cp.getEntry(this.descriptor_index);
        return entry.getBytes();
    }
    
    public String getFlag()
    {
        switch(this.access_flags)
        {
            case 1 : return "Public";
            case 2 : return "Private";
            case 4 : return "Protected";
            case 8 : return "Static";
            case 16 : return "Finale";
            case 32 : return "Synchronized";
            case 64 : return "Bridge";
            case 128 : return "Varragss";
            case 256 : return "Native";
            case 1024 : return "Abstract";
            case 2048 : return "Strict";
            case 4096 : return "Synthetic";
            default : return null;
        }
    }

    /**
     * @return the access_flags
     */
    public int getAccess_flags() {
        return access_flags;
    }

    /**
     * @return the name_index
     */
    public int getName_index() {
        return name_index;
    }

    /**
     * @return the descriptor_index
     */
    public int getDescriptor_index() {
        return descriptor_index;
    }

    /**
     * @return the attributes_count
     */
    public int getAttributes_count() {
        return attributes_count;
    }

    /**
     * @return the attributes
     */
    public AttributeInfo[] getAttributes() {
        return attributes;
    }
}
