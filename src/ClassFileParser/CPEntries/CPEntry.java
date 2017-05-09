
package ClassFileParser.CPEntries;
import ClassFileParser.ClassFileParserException;
import ClassFileParser.ConstantPool;
import ClassFileParser.InvalidConstantPoolIndex;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Parses and stores an entry from the constant pool table (in a Java .class
 * file).
 *
 * @author David Cooper
 */
public abstract class CPEntry
{
    /**
     * Parses a constant pool entry from a DataInputStream, returning an
     * instance of the appropriate class. Any references to other entries
     * remain unresolved until the resolveReferences() method is called.
     */
    public static CPEntry parse(DataInputStream dis) throws IOException,
                                                     InvalidTagException
    {
        byte tag = dis.readByte();
        CPEntry entry;

        switch(tag)
        {
            case  1: entry = new ConstantUtf8(dis);               break;
            case  3: entry = new ConstantInteger(dis);            break;
            case  4: entry = new ConstantFloat(dis);              break;
            case  5: entry = new ConstantLong(dis);               break;
            case  6: entry = new ConstantDouble(dis);             break;
            case  7: entry = new ConstantClass(dis);              break;
            case  8: entry = new ConstantString(dis);             break;
            case  9: entry = new ConstantFieldRef(dis);           break;
            case 10: entry = new ConstantMethodRef(dis);          break;
            case 11: entry = new ConstantInterfaceMethodRef(dis); break;
            case 12: entry = new ConstantNameAndType(dis);        break;
            case 15: entry = new ConstantMethodHandle(dis);       break;
            case 16: entry = new ConstantMethodType(dis);         break;
            case 18: entry = new ConstantInvokeDynamic(dis);      break;

            default:
                throw new InvalidTagException(
                    String.format("Invalid tag: 0x%02x", tag));

        }
        return entry;
    }

    /**
     * Resolves references between constant pool entries, once the entire
     * constant pool has been parsed.
     */
    public void resolveReferences(ConstantPool cp)
        throws InvalidConstantPoolIndex {}

    /** Returns a string indicating the type of entry. */
    public abstract String getTagString();

    /**
     * Returns a string containing the raw (unresolved) contents of the entry.
     */
    public abstract String getValues();

    /**
     * Returns the number of entries "taken up" by this entry. This caters for
     * a quirk in the class file format, whereby a Long or Double entry counts
     * as two entries.
     */
    public int getEntryCount() { return 1; }
}




