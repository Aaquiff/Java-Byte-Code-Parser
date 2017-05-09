/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smassignment2;

import ClassFileParser.CPEntries.CPEntry;
import ClassFileParser.CPEntries.ConstantMethodRef;
import ClassFileParser.CPEntries.ConstantNameAndType;
import ClassFileParser.ClassFileParserException;
import ClassFileParser.ClassFile;
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
    public static void main(String[] args) {
        try
        {
            ClassFile cf = new ClassFile("D:\\Users\\aaralk\\Documents\\NetBeansProjects\\SM-Assignment-2\\input\\HelloWorld.class");
            System.out.println(cf);
            CPEntry[] entries = cf.getConstantPool().getEntries();
            
            /*for(int i=1;i<entries.length;i++)
            {
                if(entries[i]!= null && entries[i].getTagString() == "Methodref")
                {
                    ConstantMethodRef x = (ConstantMethodRef) entries[i];
                    HandleMethodRefs(x);
                }
            }*/
        }
        catch(ClassFileParserException e)
        {
            System.out.printf("Class file format error in \"%s\": %s\n",
                args[0], e.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(SMAssignment2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void HandleMethodRefs(ConstantMethodRef cmr)
    {
        //System.out.println(cmr.getClassName() + "\t" + cmr.getClassIndex() + "\t" + cmr.getNameAndTypeIndex());
        ConstantNameAndType cnat = cmr.getNameAndTypeEntry();
        System.out.println("************************************");
        System.out.println(cnat.getName());
        System.out.println(cnat.getType());
        System.out.println("************************************");
    }
    
}
