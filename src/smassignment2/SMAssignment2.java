/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smassignment2;

import Attributes.AttributeInfo;
import Attributes.CodeAttributeInfo;
import ClassFileParser.CPEntries.CPEntry;
import ClassFileParser.CPEntries.ConstantMethodRef;
import ClassFileParser.CPEntries.ConstantNameAndType;
import ClassFileParser.CPEntries.ConstantUtf8;
import ClassFileParser.ClassFileParserException;
import ClassFileParser.ClassFile;
import ClassFileParser.ConstantPool;
import ClassFileParser.Instruction;
import ClassFileParser.InvalidConstantPoolIndex;
import ClassFileParser.MethodInfo;
import ClassFileParser.Opcode;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aaralk
 */
public class SMAssignment2 {

    /**
     * @param args the command line arguments
     */
    static CPEntry entries[];

    public static void main(String[] args) {
        try {
            ClassFile cf = new ClassFile("D:\\Users\\aaralk\\Documents\\NetBeansProjects\\SM-Assignment-2.git\\trunk\\input\\ParseMe.class");

            ConstantPool cp = cf.getConstantPool();
            entries = cp.getEntries();
            System.out.println(cf);
            int method_count = cf.getMethods_count();
//            System.out.println("Method Count : "+method_count+"\n");
            MethodInfo mi[] = cf.getMethods();
            
            for (int i = 0; i < method_count; i++) {
                
                MethodInfo methodInfo = mi[i];
                DrawMethodTree(mi[i],cp);
                
            }

        } catch (ClassFileParserException e) {
            System.out.printf("Class file format error in \"%s\": %s\n",
                    args[0], e.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(SMAssignment2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SMAssignment2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void DrawMethodTree(MethodInfo methodInfo,ConstantPool cp) throws InvalidConstantPoolIndex, Exception
    {
        String flag = methodInfo.getFlag();
                String name = methodInfo.getName(cp);
                
                System.out.println(name + " " + methodInfo.getDescriptor(cp));

                AttributeInfo attributeInfos[] = methodInfo.getAttributes();
                for (int j = 0; j < attributeInfos.length; j++) {

                    AttributeInfo attributeInfo = attributeInfos[j];
                    if ("Code".equals(attributeInfo.getAttribute_name(cp))) {
                        CodeAttributeInfo codeAttributeInfo = (CodeAttributeInfo) attributeInfo;
                        ArrayList<Instruction> InstructionList = codeAttributeInfo.CreateInstructionList();

                        for (Instruction ins : InstructionList) {

                            if (ins.getOpcode().compareTo(Opcode.INVOKESPECIAL) == 0
                                    || ins.getOpcode().compareTo(Opcode.INVOKESTATIC) == 0
                                    || ins.getOpcode().compareTo(Opcode.INVOKEVIRTUAL) == 0
                                    || ins.getOpcode().compareTo(Opcode.INVOKEINTERFACE) == 0) {
                                
                                byte x[] = ins.getExtraBytes();
                                ByteBuffer wrapped = ByteBuffer.wrap(x);
                                short ind = wrapped.getShort();
                                ConstantMethodRef method = (ConstantMethodRef) cp.getEntry(ind);
                                ConstantNameAndType cnat = method.getNameAndTypeEntry();
                                System.out.println("\t"+method.getClassName() + "." + method.getName() + " " + cnat.getType());
                            }
                        }

                    }
                }
    }
}
