/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smassignment2;

import Attributes.AttributeInfo;
import Attributes.CodeAttributeInfo;
import ClassFileParser.CPEntries.CPEntry;
import ClassFileParser.CPEntries.ConstantClass;
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

    private static String directory = "D:\\Users\\aaralk\\Documents\\NetBeansProjects\\SM-Assignment-2.git\\trunk\\input\\";

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        try {
            ClassFile cf = new ClassFile(directory + "ParseMe.class");
            ConstantPool cp = cf.getConstantPool();
            System.out.println(cf);
            ClassFile cf2 = new ClassFile(directory + "HelloWorld.class");
            System.out.println(cf2);
            
            MethodInfo mi[] = cf.getMethods();
            System.out.println("Selected Method : " + cf.getThisClassName() + "." + mi[1].getName(cp) + " " + mi[1].getDescriptor(cp));
            
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

    public static void DrawMethodTree(MethodInfo inMethodInfo, ClassFile cf, int recursionLevel) throws InvalidConstantPoolIndex, Exception {
        ArrayList<MethodInfo> methodList = new ArrayList<>();
        methodList.add(inMethodInfo);
        ConstantPool cp = cf.getConstantPool();
        AttributeInfo attributeInfos[] = inMethodInfo.getAttributes();
        
        for (int j = 0; j < attributeInfos.length; j++) {
            AttributeInfo attributeInfo = attributeInfos[j];
            if ("Code".equals(attributeInfo.getAttribute_name(cp))) {
                CodeAttributeInfo codeAttributeInfo = (CodeAttributeInfo) attributeInfo;
                ArrayList<Instruction> InstructionList = codeAttributeInfo.CreateInstructionList();
                for (Instruction ins : InstructionList) {
                    if (ins.getOpcode().compareTo(Opcode.INVOKESPECIAL) == 0 || ins.getOpcode().compareTo(Opcode.INVOKESTATIC) == 0
                            || ins.getOpcode().compareTo(Opcode.INVOKEVIRTUAL) == 0 || ins.getOpcode().compareTo(Opcode.INVOKEINTERFACE) == 0
                            || ins.getOpcode().compareTo(Opcode.INVOKEDYNAMIC) == 0
                            ) 
                    {                        
                        byte x[] = ins.getExtraBytes();
                        ByteBuffer wrapped = ByteBuffer.wrap(x);
                        short ind = wrapped.getShort();

                        ConstantMethodRef method = (ConstantMethodRef) cp.getEntry(ind);
                        ConstantNameAndType nameAndType = (ConstantNameAndType) cp.getEntry(method.getNameAndTypeIndex());
                        
                        for (int i = 0; i < recursionLevel; i++) {
                            System.out.print("\t");
                        }

                        System.out.println("\t" + ins.getOpcode() + " " + method.getClassName() + "." + method.getName() + " " + nameAndType.getType());
                        /*for(MethodInfo mz : methodList)
                            System.out.print(mz.getName(cp) + " ");*/
                        if (!method.getClassName().equals(cf.getThisClassName())) 
                        {
                            if (method.getClassName().equals("java/lang/Object")) 
                            {
                                System.out.println("Java lang object ignored!");
                                break;
                            }
                            MethodInfo methodInfo2 = null;
                            ClassFile cf2 = new ClassFile(directory + method.getClassName() + ".class");
                            MethodInfo methodInfos[] = cf2.getMethods();
                            for(MethodInfo mx : methodInfos)
                            {
                                if( nameAndType.getName().equals(mx.getName(cf2.getConstantPool())) && nameAndType.getType().equals(mx.getDescriptor(cf2.getConstantPool())) )
                                {
                                    methodInfo2 = mx;
                                    break;
                                }
                            }
                            if(methodInfo2!=null)
                                DrawMethodTree(methodInfo2, cf2, recursionLevel + 1);
                            else
                                System.out.println("Method Not Found!");
                        } 
                        else 
                        {
                            MethodInfo methodInfo2 = null;
                            MethodInfo methodInfos[] = cf.getMethods();
                            for(MethodInfo my : methodInfos)
                            {
                                if( nameAndType.getName().equals(my.getName(cf.getConstantPool())) && nameAndType.getType().equals(my.getDescriptor(cf.getConstantPool())) )
                                {
                                    methodInfo2 = my;
                                    break;
                                }
                            }
                            if(methodInfo2!=null)
                                DrawMethodTree(methodInfo2, cf, recursionLevel + 1);
                            else
                                System.out.println("Method Not Found!");
                        }
                    }
                }

            }
        }
        return;
    }
    
        /*public static void DrawMethodTree(MethodInfo methodInfo, ClassFile cf, int recursionLevel) throws InvalidConstantPoolIndex, Exception {

        ConstantPool cp = cf.getConstantPool();
        AttributeInfo attributeInfos[] = methodInfo.getAttributes();
        for (int j = 0; j < attributeInfos.length; j++) {
            AttributeInfo attributeInfo = attributeInfos[j];
            if ("Code".equals(attributeInfo.getAttribute_name(cp))) {
                CodeAttributeInfo codeAttributeInfo = (CodeAttributeInfo) attributeInfo;
                ArrayList<Instruction> InstructionList = codeAttributeInfo.CreateInstructionList();
                for (Instruction ins : InstructionList) {
                    if (ins.getOpcode().compareTo(Opcode.INVOKESPECIAL) == 0 || ins.getOpcode().compareTo(Opcode.INVOKESTATIC) == 0
                            || ins.getOpcode().compareTo(Opcode.INVOKEVIRTUAL) == 0 || ins.getOpcode().compareTo(Opcode.INVOKEINTERFACE) == 0) {
                        byte x[] = ins.getExtraBytes();
                        ByteBuffer wrapped = ByteBuffer.wrap(x);
                        short ind = wrapped.getShort();

                        ConstantMethodRef method = (ConstantMethodRef) cp.getEntry(ind);
                        ConstantNameAndType nameAndType = (ConstantNameAndType) cp.getEntry(method.getNameAndTypeIndex());

                        for (int i = 0; i < recursionLevel; i++) {
                            System.out.print("\t");
                        }

                        System.out.println("\t" + method.getClassName() + "." + method.getName() + " " + nameAndType.getType());

                        if (method.getClassName() != cf.getThisClassName()) {
                            System.out.println("method.getClassName() : " + method.getClassName());
                            System.out.println("cf.getThisClassName() : " + cf.getThisClassName());
                            //System.out.println("Construct new class : " + method.getClassName());
                            //cf = new ClassFile(directory + method.getClassName() + ".class");
                            //System.out.println(cf);

                        } else {
                            MethodInfo methodInfo2 = cf.GetMethodInfo(nameAndType.getNameIndex());
                            DrawMethodTree(methodInfo2, cf, recursionLevel + 1);
                        }

                    }
                }

            }
        }
        return;
    }*/
}
