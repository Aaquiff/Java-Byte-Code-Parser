/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Attributes;

import ClassFileParser.CPEntries.ConstantUtf8;
import ClassFileParser.ConstantPool;
import java.io.DataInputStream;
import java.io.IOException;

/**
 *
 * @author aaralk
 */
public class AttributeInfo {

    private int attribute_name_index;
    private int attribute_length;
    private int info[];
    
    public int getAttribute_length() {
        return attribute_length;
    }

    public int[] getInfo() {
        return info;
    }

    public int getAttribute_name_index() {
        return attribute_name_index;
    }    
    
    public static AttributeInfo parse(DataInputStream dis,ConstantPool cp) throws Exception                                              
    {
        int nameIndex = dis.readUnsignedShort();
        ConstantUtf8 entry = (ConstantUtf8)cp.getEntries()[nameIndex] ;
        
        //return new AttributeInfo(dis, nameIndex);
        switch(entry.getBytes())
        {
            
            case "Code" : return new CodeAttribute(dis,cp,nameIndex);
            /*case "SourceFile" : return new SourceFileAttribute(dis,cp,nameIndex);
            case "LineNumberTable" : return new LineNumberTableAttribute(dis,cp,nameIndex);
            case "StackMapTable" : return new StackMapTableAttribute(dis,cp,nameIndex);*/
            
            default     : return new AttributeInfo(dis, nameIndex);
        }
    }
    public AttributeInfo()
    {
        
    }
    public AttributeInfo(DataInputStream dis) throws IOException
    {
        attribute_name_index = dis.readUnsignedShort();
        attribute_length = dis.readUnsignedShort() << 16 | dis.readUnsignedShort();
        //info = new int[attribute_length];

        for (int i = 0; i <attribute_length; i++) {
            //CHECK THIS LATER
            dis.readByte();
        }
    }
    public AttributeInfo(DataInputStream dis,int nameIndex) throws IOException
    {
        attribute_name_index = nameIndex;
        attribute_length = dis.readUnsignedShort() << 16 | dis.readUnsignedShort();
        //info = new int[attribute_length];

        for (int i = 0; i <attribute_length; i++) {
            //CHECK THIS LATER
            dis.readByte();
        }

    }
}
