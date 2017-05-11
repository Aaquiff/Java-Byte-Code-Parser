/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smassignment2;

import Attributes.AttributeInfo;
import ClassFileParser.CPEntries.CPEntry;
import ClassFileParser.CPEntries.ConstantMethodRef;
import ClassFileParser.CPEntries.ConstantNameAndType;
import ClassFileParser.CPEntries.ConstantUtf8;
import ClassFileParser.ClassFileParserException;
import ClassFileParser.ClassFile;
import ClassFileParser.ConstantPool;
import ClassFileParser.MethodInfo;
import java.io.IOException;
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
        try
        {
            ClassFile cf = new ClassFile("D:\\Users\\aaquiff\\Documents\\NetBeansProjects\\SM-Assignment-2.git\\trunk\\input\\ParseMe.class");
            ConstantPool cp = cf.getConstantPool();
            entries = cp.getEntries();
            System.out.println(cf);
            int method_count = cf.getMethods_count();
            System.out.println("Method Count : "+method_count+"\n");
            MethodInfo mi[] = cf.getMethods();
            for (int i = 0; i < method_count; i++) {
                MethodInfo methodInfo = mi[i];
                String flag = methodInfo.getFlag();
                ConstantUtf8 entry = (ConstantUtf8)entries[mi[i].getName_index()] ;
                String name = entry.getBytes();
                System.out.println("************************");
                System.out.println(flag + " " + name);
                System.out.println("************************");
                System.out.println("Attributes Count : "+methodInfo.getAttributes_count());
                AttributeInfo attributeInfos[] = methodInfo.getAttributes();
                for (int j = 0; j < attributeInfos.length; j++) {
                    AttributeInfo attributeInfo = attributeInfos[j];
                    int length = attributeInfo.getAttribute_length();
                    int nameIndex = attributeInfo.getAttribute_name_index();
                    System.out.println("Attribute Length : " + length);
                    System.out.println("Attribute Name Index : " + nameIndex);
                    //int info[] = attributeInfo.getInfo();
                    for (int k = 0; k < length; k++) {
                        //System.out.println("Info " + k + " : " +info[k]);
                    }
                }
            }
        }
        catch(ClassFileParserException e)
        {
            System.out.printf("Class file format error in \"%s\": %s\n",
                args[0], e.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(SMAssignment2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SMAssignment2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
