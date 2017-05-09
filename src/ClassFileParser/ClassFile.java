package ClassFileParser;

import java.io.*;

/**
 * Parses and stores a Java .class file. Parsing is currently incomplete.
 *
 * @author David Cooper
 */
public class ClassFile
{

    /**
     * @return the constantPool
     */
    public ConstantPool getConstantPool() {
        return constantPool;
    }
    private String filename;
    private long magic;
    private int minorVersion;
    private int majorVersion;
    private ConstantPool constantPool;
    private int access_flags;
    private int this_class;
    private int super_class;
    private int interfaces_count;
    private int fields_count;
    private FieldInfo fields_info[];
    private int methods_count;
    private MethodInfo methods[];
    private int attributes_count;
    private AttributeInfo attributes[];
    
    /**
     * Parses a class file an constructs a ClassFile object. At present, this
     * only parses the header and constant pool.
     */
    public ClassFile(String filename) throws ClassFileParserException,
                                             IOException
    {
        DataInputStream dis =
            new DataInputStream(new FileInputStream(filename));

        this.filename = filename;
        magic = (long)dis.readUnsignedShort() << 16 | dis.readUnsignedShort();
        minorVersion = dis.readUnsignedShort();
        majorVersion = dis.readUnsignedShort();
        constantPool = new ConstantPool(dis);
        
        access_flags = dis.readUnsignedShort();
        System.out.println("Access flags : " + access_flags);
        
        this_class = dis.readUnsignedShort();
        System.out.println("This classs : " + this_class);
        
        super_class = dis.readUnsignedShort();   
        System.out.println("Super class : " + super_class);
        
        interfaces_count = dis.readUnsignedShort(); 
        System.out.println("Interfaces Count : " + interfaces_count);
        
        for (int i = 0; i < interfaces_count; i++) {
            System.out.println(dis.readUnsignedShort());
        }
        
        fields_count = dis.readUnsignedShort(); 
        System.out.println("Fields Count : " + fields_count);
        fields_info = new FieldInfo[fields_count];
        for (int i = 0; i < fields_count; i++) {
            fields_info[i] = new FieldInfo(dis);
        }
        
        System.out.println("Methods Count : " + methods_count);
        methods_count = dis.readUnsignedShort();
        methods = new MethodInfo[methods_count];
        for (int i = 0; i < methods_count; i++) {
            methods[i] = new MethodInfo(dis);
        }



        attributes_count = dis.readUnsignedShort();
        System.out.println("Attributes Count : " + attributes_count);
        attributes = new AttributeInfo[attributes_count];
        for (int i = 0; i < attributes_count; i++) {
            attributes[i] = new AttributeInfo(dis);
            System.out.println("X : "+attributes[i].getAttribute_length());
        }


    }

    /** Returns the contents of the class file as a formatted String. */
    public String toString()
    {
        return String.format("Filename: %s\n" +
            "Magic: 0x%08x\n" +
            "Class file format version: %d.%d\n\n" +
            "Constant pool:\n\n%s",
            filename, magic, majorVersion, minorVersion, getConstantPool());
    }
}

