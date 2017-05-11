/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassFileParser;

import Attributes.AttributeInfo;
import java.io.DataInputStream;
import java.io.IOException;

/**
 *
 * @author aaralk
 */
public class FieldInfo {
    private int access_flags;
    private int name_index;
    private int descriptor_index;
    private int attributes_count;
    private AttributeInfo attributes[];
        
    public FieldInfo(DataInputStream dis,ConstantPool cp) throws IOException, Exception
    {
        access_flags = dis.readUnsignedShort();
        name_index = dis.readUnsignedShort();
        descriptor_index = dis.readUnsignedShort();
        attributes_count = dis.readUnsignedShort();
        attributes = new AttributeInfo[attributes_count];
        for (int i = 0; i < attributes_count; i++) {
            attributes[i] = new AttributeInfo(dis);
        }
    }
    
    public String getFlag(int access_flags)
    {
        switch(access_flags)
        {
            case 1 : return "Public";
            case 2 : return "Private";
            case 4 : return "Protected";
            case 8 : return "Static";
            case 16 : return "Final";
            case 64 : return "Volatile";
            case 128 : return "Transient";
            default : return null;
        }
    }
}

