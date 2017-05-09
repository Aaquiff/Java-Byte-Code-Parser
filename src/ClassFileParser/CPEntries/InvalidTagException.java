/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassFileParser.CPEntries;

import ClassFileParser.ClassFileParserException;

/**
 * Thrown when an unknown tag value is encountered (i.e. one that does not
 * indicate a known constant pool entry type.)
 */
public class InvalidTagException extends ClassFileParserException
{
    public InvalidTagException(String msg) { super(msg); }
}
