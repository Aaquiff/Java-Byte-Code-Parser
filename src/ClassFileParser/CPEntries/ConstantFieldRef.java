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
/** Represents a CONSTANT_Fieldref entry (tag == 9). */
public class ConstantFieldRef extends ConstantRef
{
    public ConstantFieldRef(DataInputStream dis) throws IOException
    {
        super(dis);
    }

    public String getTagString() { return "Fieldref"; }
}
