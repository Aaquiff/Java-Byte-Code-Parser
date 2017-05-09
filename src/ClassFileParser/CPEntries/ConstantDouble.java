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
/**
 * Represents a CONSTANT_Double entry (tag == 6). This class overrides
 * getEntryCount() to indicate that a Double entry counts for two entries in
 * the constant pool.
 */
public class ConstantDouble extends CPEntry
{
    private double value;

    public ConstantDouble(DataInputStream dis) throws IOException
    {
        this.value = dis.readDouble();
    }

    public double getDoubleValue() { return value; }
    public String getTagString()   { return "Double"; }
    public int getEntryCount()     { return 2; }

    public String getValues() { return String.format("value=%f", value); }
}