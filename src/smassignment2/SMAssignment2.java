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
import java.util.Scanner;

/**
 *
 * @author aaralk
 */
public class SMAssignment2 {

    private static String directory ;
    private static ArrayList<String> allMethods = new ArrayList<String>();
    private static ArrayList<ClassFile> allClasses = new ArrayList<ClassFile>();
    
    public static void main(String[] args) {
        if(args.length != 1)
        {
            System.out.println(args.length);
            System.out.println("Usage : SMAssignment2.jar class_file");
            return;
        }
        String dirArgument = args[0];
        directory = dirArgument.substring(0, dirArgument.lastIndexOf("\\")+1);
        try {
            Scanner sc = new Scanner(System.in);
            ClassFile cf = new ClassFile(args[0]);
            ConstantPool cp = cf.getConstantPool();

            allClasses.add(cf);
            MethodInfo mi[] = cf.getMethods();
            int i = 0;
            for(MethodInfo method : mi)
            {
                i++;
                System.out.println(i + ") " + method.getName(cp) + " " + method.getDescriptor(cp));
            }
            System.out.print("Choose Method : ");
            int index = sc.nextInt() - 1;
            System.out.println("Selected Method : " + cf.getThisClassName() + "." + mi[index].getName(cp) + " " + mi[index].getDescriptor(cp));

            ArrayList<ConstantMethodRef> methodList = new ArrayList<ConstantMethodRef>();
            
            System.out.println("\n********************************************************************************************************\n");
            System.out.println(cf.getThisClassName() + "." + mi[index].getName(cp) + " " + mi[index].getDescriptor(cp));
            DrawMethodTree(mi[index], cf, 0, methodList);
            
            System.out.println("\n********************************************************************************************************\n");
            
            System.out.println("Total Number of Uniqe method/constructors called : " + allMethods.size());
            System.out.println("Total Number of classes involved : " + allClasses.size());
            
            System.out.println("\n********************************************************************************************************\n");
            
            
        } catch (ClassFileParserException e) {
            System.out.printf("Class file format error in \"%s\": %s\n",
                    args[0], e.getMessage());
        } catch (IOException ex) {
            System.out.printf("Class file is empty or incorrect \"%s\" ",
                    args[0]);
        } catch (Exception ex) {
            Logger.getLogger(SMAssignment2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void DrawMethodTree(MethodInfo inMethodInfo, ClassFile cf, int recursionLevel, ArrayList<ConstantMethodRef> constantRefMethodList) throws InvalidConstantPoolIndex, Exception {
        ConstantPool cp = cf.getConstantPool();       
        AttributeInfo attributeInfos[] = inMethodInfo.getAttributes();
        
        for (int j = 0; j < attributeInfos.length; j++) {
            AttributeInfo attributeInfo = attributeInfos[j];
            if ("Code".equals(attributeInfo.getAttribute_name(cp))) {
                CodeAttributeInfo codeAttributeInfo = (CodeAttributeInfo) attributeInfo;
                ArrayList<Instruction> InstructionList = codeAttributeInfo.GetInstructionList();
                ArrayList<ConstantMethodRef> prevMethods = new ArrayList<ConstantMethodRef>();
                                
                for (Instruction ins : InstructionList) {
                    
                    if (ins.getOpcode().compareTo(Opcode.INVOKESPECIAL) == 0 || ins.getOpcode().compareTo(Opcode.INVOKESTATIC) == 0
                            || ins.getOpcode().compareTo(Opcode.INVOKEVIRTUAL) == 0 || ins.getOpcode().compareTo(Opcode.INVOKEINTERFACE) == 0
                            ) 
                    {                        
                        byte x[] = ins.getExtraBytes();
                        ByteBuffer wrapped = ByteBuffer.wrap(x);
                        short ind = wrapped.getShort();

                        ConstantMethodRef method = (ConstantMethodRef) cp.getEntry(ind);
                        ConstantNameAndType nameAndType = (ConstantNameAndType) cp.getEntry(method.getNameAndTypeIndex());
                        if(prevMethods.contains(method))
                        {
                            continue;
                        }
                        prevMethods.add(method);
                        boolean break1 = false;
                        String str1 = cf.getThisClassName() + "." + inMethodInfo.getName(cp) + " " + inMethodInfo.getDescriptor(cp);
                        String str2 = method.getClassName() + "." + method.getName() + " " + nameAndType.getType();

                        if( str1.equals(str2))
                        {
                            PrintMethod(method, nameAndType, recursionLevel, "RECURSIVE");
                            continue;
                        }
                        for(ConstantMethodRef z : constantRefMethodList )
                        {
                            if(z.equals(method))
                            {
                                PrintMethod(method, nameAndType, recursionLevel, "RECURSIVE");
                                break1 = true;
                            }
                        }
                        if(break1)
                            continue;
                        
                        constantRefMethodList.add(method);
                        
                        
                        if (!method.getClassName().equals(cf.getThisClassName())) 
                        {
                            try
                            {
                                cf = new ClassFile(directory + method.getClassName() + ".class");
                                allClasses.add(cf);
                                //PrintMethod(method, nameAndType, recursionLevel, null);
                            }
                            catch(Exception ex)
                            {
                                PrintMethod(method, nameAndType, recursionLevel, "MISSING");
                                continue;
                            }
                            
                        } 

                        PrintMethod(method, nameAndType, recursionLevel, null);

                        MethodInfo methodInfo2 = null;
                        MethodInfo methodInfos[] = cf.getMethods();
                        for(MethodInfo my : methodInfos)
                        {
                            if( nameAndType.getName().equals(my.getName(cf.getConstantPool())) && nameAndType.getType().equals(my.getDescriptor(cf.getConstantPool())) )
                            {
                                methodInfo2 = my;
                            }
                        }
                        
                        if(methodInfo2!=null)
                        {
                            if(!allMethods.contains(method.getClassName() + "." + method.getName() + " " + nameAndType.getType()))
                                allMethods.add(method.getClassName() + "." + method.getName() + " " + nameAndType.getType());
                            
                            ArrayList<CodeAttributeInfo> cas = methodInfo2.GetCodeAttributes(cf.getConstantPool());
                            /*if(cas.isEmpty())
                            {
                                PrintMethod(method,nameAndType,recursionLevel,"ABSTRACT");
                                continue;
                            }*/

                            DrawMethodTree(methodInfo2, cf, recursionLevel + 1,constantRefMethodList);
                        }
                        else
                            
                            System.out.println("Method Not Found!");  
                        
                        
                        
                        constantRefMethodList.remove(method);
                    }
                }
                return;
            }
        }
        
        return;
    }
    
    private static void PrintMethod(ConstantMethodRef cmr, ConstantNameAndType nameAndType, int recursionLevel, String option)
    {
        for (int i = 0; i < recursionLevel; i++) {
                            System.out.print("\t");
                        }
        if(option == null)
            System.out.println("\t"  + cmr.getClassName() + "." + cmr.getName() + " " + FormatDescriptor(nameAndType.getType()) );
        else
            System.out.println("\t"  + cmr.getClassName() + "." + cmr.getName() + " " + FormatDescriptor(nameAndType.getType() ) + " ["+ option +"]");
    }
    
    private static String FormatDescriptor(String descriptor)
    {
        //descriptor = (String) descriptor.subSequence(0, descriptor.indexOf(")")+1);
//        descriptor = descriptor.replaceAll("I", "Integer");
//        descriptor = descriptor.replaceAll("V", "Void");
//        descriptor = descriptor.replaceAll("D", "Double");
        return descriptor;
    }
}

