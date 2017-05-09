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
/** Represents a CONSTANT_Integer entry (tag == 3). */
public class ConstantInteger extends CPEntry
{
    private int value;

    public ConstantInteger(DataInputStream dis) throws IOException
    {
        this.value = dis.readInt();
    }

    public int getIntValue()     { return value; }
    public String getTagString() { return "Integer"; }
    public String getValues()    { return String.format("value=%d", value); }
}