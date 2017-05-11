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

    public static void main(String[] args) {
        try {
            ClassFile cf = new ClassFile("D:\\Users\\aaralk\\Documents\\NetBeansProjects\\SM-Assignment-2.git\\trunk\\input\\ParseMe.class");
            ConstantPool cp = cf.getConstantPool();
            System.out.println(cf);
            MethodInfo mi[] = cf.getMethods();
            /*for (int i = 0; i < cf.getMethods_count(); i++) {
                DrawMethodTree(mi[i], cf);
            }*/
            
            System.out.println(cf.getThisClassName() + "." + mi[1].getName(cp) + " " + mi[1].getDescriptor(cp));
            DrawMethodTree(mi[1], cf, 0);
            
        } catch (ClassFileParserException e) {
            System.out.printf("Class file format error in \"%s\": %s\n",
                    args[0], e.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(SMAssignment2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SMAssignment2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void DrawMethodTree(MethodInfo methodInfo, ClassFile cf, int recursionLevel) throws InvalidConstantPoolIndex, Exception {

        ConstantPool cp = cf.getConstantPool();

        //System.out.println(methodInfo.getName(cp) + " " + methodInfo.getDescriptor(cp));
        AttributeInfo attributeInfos[] = methodInfo.getAttributes();
        for (int j = 0; j < attributeInfos.length; j++) {
            AttributeInfo attributeInfo = attributeInfos[j];
            if ("Code".equals(attributeInfo.getAttribute_name(cp))) {
                CodeAttributeInfo codeAttributeInfo = (CodeAttributeInfo) attributeInfo;
                ArrayList<Instruction> InstructionList = codeAttributeInfo.CreateInstructionList();
                for (Instruction ins : InstructionList) {
                    if (ins.getOpcode().compareTo(Opcode.INVOKESPECIAL) == 0 || ins.getOpcode().compareTo(Opcode.INVOKESTATIC) == 0
                            || ins.getOpcode().compareTo(Opcode.INVOKEVIRTUAL) == 0 || ins.getOpcode().compareTo(Opcode.INVOKEINTERFACE) == 0) 
                    {
                        byte x[] = ins.getExtraBytes();
                        ByteBuffer wrapped = ByteBuffer.wrap(x);
                        short ind = wrapped.getShort();
                        ConstantMethodRef method = (ConstantMethodRef) cp.getEntry(ind);
                        ConstantNameAndType cnat = method.getNameAndTypeEntry();
                        MethodInfo methodInfo2 = cf.GetMethodInfo(cnat.getNameIndex());
                        //System.out.println("Method Info 2 : "+methodInfo2.getName(cp));
                        for (int i = 0; i < recursionLevel; i++) {
                            System.out.print("\t");
                        }
                        /*for (int i = 0; i < recursionLevel+1; i++) {
                            System.out.print("--");
                        }*/
                        System.out.println("\t" + method.getClassName() + "." + method.getName() + " " + cnat.getType());
                        
                        DrawMethodTree(methodInfo2, cf, recursionLevel+1);
                    }
                }

            }
        }
        return;
    }
}
