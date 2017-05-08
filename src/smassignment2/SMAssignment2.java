/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smassignment2;

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
            ClassFile cf = new ClassFile("D:\\Users\\aaralk\\Documents\\SLIIT\\SM\\SM - Assignment 2\\ClassFileParser\\input\\HelloWorld.class");
            System.out.println(cf);
        }
        catch(ClassFileParserException e)
        {
            System.out.printf("Class file format error in \"%s\": %s\n",
                args[0], e.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(SMAssignment2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
