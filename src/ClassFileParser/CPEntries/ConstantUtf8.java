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
/** Represents a CONSTANT_Utf8 entry (tag == 1). */
public class ConstantUtf8 extends CPEntry
{
    private String bytes;

    public ConstantUtf8(DataInputStream dis) throws IOException
    {
        int length = dis.readUnsignedShort();
        byte[] b = new byte[length];
        dis.readFully(b);
        this.bytes = new String(b);
    }

    public String getBytes()     { return bytes; }
    public String getTagString() { return "Utf8"; }
    public String getValues()
    {
        return String.format("length=%d, bytes=\"%s\"",
            getBytes().length(), getBytes());
    }
}
