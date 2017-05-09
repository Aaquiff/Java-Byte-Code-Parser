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
/** Represents a CONSTANT_Methodref entry (tag == 10). */
public class ConstantMethodRef extends ConstantRef
{
    public ConstantMethodRef(DataInputStream dis) throws IOException
    {
        super(dis);
    }

    public String getTagString() { return "Methodref"; }
}