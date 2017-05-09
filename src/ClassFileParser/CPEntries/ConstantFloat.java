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
/** Represents a CONSTANT_Float entry (tag == 4). */
public class ConstantFloat extends CPEntry
{
    private float value;

    public ConstantFloat(DataInputStream dis) throws IOException
    {
        this.value = dis.readFloat();
    }

    public float getFloatValue() { return value; }
    public String getTagString() { return "Float"; }
    public String getValues()    { return String.format("value=%f", value); }
}