/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassFileParser;

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
    
    public MethodInfo(DataInputStream dis) throws IOException
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
}
