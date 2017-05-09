/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassFileParser.CPEntries;

import java.io.DataInputStream;
import java.io.IOException;

/**
 *
 *  
 */
/** Represents a CONSTANT_InterfaceMethodref entry (tag == 11). */
public class ConstantInterfaceMethodRef extends ConstantRef
{
    public ConstantInterfaceMethodRef(DataInputStream dis) throws IOException
    {
        super(dis);
    }

    public String getTagString() { return "InterfaceMethodref"; }
}
