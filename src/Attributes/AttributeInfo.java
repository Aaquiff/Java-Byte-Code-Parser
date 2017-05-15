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

//This class maps directly to the attribute_info type in class file
public class AttributeInfo {

    protected int attribute_name_index;
    protected int attribute_length;
    protected int info[];
    
    //Returns the attribute name by searching the given constant 
    //pool for this attribute_name_index
    public String getAttribute_name(ConstantPool cp) throws Exception
    {
        ConstantUtf8 entry = (ConstantUtf8)cp.getEntry(attribute_name_index);
        return entry.getBytes();
    }
    //Parses the attribute and finds out if it is  a 
    //codeattribute and returns a code attribute object
    public static AttributeInfo parse(
            DataInputStream dis,
            ConstantPool cp) 
            throws Exception
    {
        int nameIndex = dis.readUnsignedShort();
        ConstantUtf8 entry = (ConstantUtf8)cp.getEntries()[nameIndex] ;
        AttributeInfo attribute;
        switch(entry.getBytes())
        {
            case "Code" : attribute = new CodeAttributeInfo(dis,cp,nameIndex);
                            break;
            default     : attribute = new AttributeInfo(dis, nameIndex);
                            break;
        }
        return attribute;
    }
    
    //Default Constructor
    public AttributeInfo()
    {
        
    }
    //Constructor with DataInputStream
    public AttributeInfo(DataInputStream dis) throws IOException
    {
        attribute_name_index = dis.readUnsignedShort();
        attribute_length = dis.readUnsignedShort() 
                    << 16 | dis.readUnsignedShort();

        for (int i = 0; i <attribute_length; i++) {

            dis.readByte();
        }
    }
    
    //Constructor with DataInputStream and nameindex. 
    //Used when the nameIndex has already been read by the parse method
    public AttributeInfo(DataInputStream dis,int nameIndex) throws IOException
    {
        attribute_name_index = nameIndex;
        attribute_length = dis.readUnsignedShort() 
                            << 16 | dis.readUnsignedShort();

        for (int i = 0; i <attribute_length; i++) {
            dis.readByte();
        }

    }
}
