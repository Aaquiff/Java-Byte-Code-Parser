/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Attributes;


import ClassFileParser.ConstantPool;
import ClassFileParser.Instruction;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author aaralk
 */
public class CodeAttributeInfo extends AttributeInfo{
    
    private int max_stack;
    private int max_locals;
    private int code_length;
    private byte code[];
    private int exception_table_length;
    private int attributes_count;
    private AttributeInfo attributes[];
    
    //Constructor
    public CodeAttributeInfo(DataInputStream dis,
            ConstantPool cp,int nameIndex) throws IOException, Exception
    {
        super();
        attribute_name_index = nameIndex;
        attribute_length = dis.readUnsignedShort() 
                << 16 | dis.readUnsignedShort();
        max_stack = dis.readUnsignedShort();
        max_locals = dis.readUnsignedShort();
        code_length = dis.readUnsignedShort() 
                << 16 | dis.readUnsignedShort();
        code = new byte[code_length];

        for (int i = 0; i < code_length; i++) {
            code[i] = dis.readByte();
            
        }
        
        exception_table_length = dis.readUnsignedShort();
        for (int i = 0; i < exception_table_length; i++) {
            dis.readUnsignedShort();
            dis.readUnsignedShort();
            dis.readUnsignedShort();
            dis.readUnsignedShort();
        }
        attributes_count = dis.readUnsignedShort();
        attributes = new AttributeInfo[attributes_count];
        for (int i = 0; i < attributes_count; i++) {
            attributes[i] = new AttributeInfo(dis);
        }

    }
    
    //Gets a list of instructions by passing the code array.
    public ArrayList<Instruction> GetInstructionList() throws Exception
    {
        ArrayList<Instruction> insList;
        int curOffSet = 0;
        insList = new ArrayList<Instruction>();
        do
        {
            Instruction newInst = new Instruction(code, curOffSet);
            curOffSet += newInst.getSize();
            insList.add(newInst);
        }
        while (curOffSet < code.length);
        return insList;
    }
}
