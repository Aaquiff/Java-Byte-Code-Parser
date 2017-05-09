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
public class AttributeInfo {

    /**
     * @return the attribute_length
     */
    public int getAttribute_length() {
        return attribute_length;
    }

    /**
     * @return the info
     */
    public int[] getInfo() {
        return info;
    }

    /**
     * @return the attribute_name_index
     */
    public int getAttribute_name_index() {
        return attribute_name_index;
    }
    private int attribute_name_index;
    private int attribute_length;
    private int info[];
    
    public AttributeInfo(DataInputStream dis) throws IOException
    {
        attribute_name_index = dis.readUnsignedShort();
        attribute_length = dis.readInt();
        info = new int[attribute_length];
        System.out.println(attribute_length);
        for (int i = 0; i <attribute_length; i++) {
            //CHECK THIS LATER
            info[i]=dis.readByte();
        }
    }
}
