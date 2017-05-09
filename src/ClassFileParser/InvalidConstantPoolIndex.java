/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassFileParser;

/**
 *
 * @author aaralk
 */
/**
 * Thrown when an invalid index into the constant pool is given. That is,
 * index is zero (or negative), greater than the index of the last entry, or
 * represents the (unused) entry following a Long or Double.
 */
public class InvalidConstantPoolIndex extends ClassFileParserException
{
    public InvalidConstantPoolIndex(String msg) { super(msg); }
}
