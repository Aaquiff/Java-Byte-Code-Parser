package ClassFileParser;

import Attributes.AttributeInfo;
import ClassFileParser.CPEntries.ConstantClass;
import ClassFileParser.CPEntries.ConstantUtf8;
import java.io.*;

/**
 * Parses and stores a Java .class file. Parsing is currently incomplete.
 *
 * @author David Cooper
 */
public class ClassFile
{

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
                                             IOException,
                                             Exception
    {
        DataInputStream dis =
            new DataInputStream(new FileInputStream(filename));
                
        this.filename = filename;
        magic = (long)dis.readUnsignedShort() << 16 | dis.readUnsignedShort();
        minorVersion = dis.readUnsignedShort();
        majorVersion = dis.readUnsignedShort();
        constantPool = new ConstantPool(dis);
        //System.out.println("Constant Pool");
        //System.out.println(constantPool);
        access_flags = dis.readUnsignedShort();
        System.out.println("Access flags : " + access_flags);
        
        this_class = dis.readUnsignedShort();
        
        
        ConstantClass entry = (ConstantClass) constantPool.getEntry(this_class);
        ConstantUtf8 name = (ConstantUtf8) constantPool.getEntry(entry.getNameIndex());
        System.out.println("Class Name "+ name.getBytes());
        //System.out.println("This classs : " + this_class);
        
        super_class = dis.readUnsignedShort();   
        //System.out.println("Super class : " + super_class);
                
        interfaces_count = dis.readUnsignedShort(); 
        //System.out.println("Interfaces Count : " + interfaces_count);

        for (int i = 0; i < interfaces_count; i++) {
            //System.out.println(dis.readUnsignedShort());
        }

        fields_count = dis.readUnsignedShort(); 
        //System.out.println("Fields Count : " + fields_count);
                
        fields_info = new FieldInfo[fields_count];
        for (int i = 0; i < fields_count; i++) {
            fields_info[i] = new FieldInfo(dis,constantPool);
        }
        
        methods_count = dis.readUnsignedShort();
        //System.out.println("Methods Count : " + methods_count);
        methods = new MethodInfo[methods_count];
        for (int i = 0; i < methods_count; i++) {
            methods[i] = new MethodInfo(dis,constantPool);
        }
        
        attributes_count = dis.readUnsignedShort();
        //System.out.println("Attributes Count : " + attributes_count);
        attributes = new AttributeInfo[attributes_count];
        for (int i = 0; i < attributes_count; i++) {
            attributes[i] = new AttributeInfo(dis);
        }
        
    }

    /** Returns the contents of the class file as a formatted String. */
    public String toString()
    {
        return String.format("Filename: %s\n" +
            "Magic: 0x%08x\n" +
            "Class file format version: %d.%d\n\n" +
            "Constant pool:\n\n%s", getFilename(), getMagic(), getMajorVersion(), getMinorVersion(), getConstantPool());
    }
    
        public String getFilename() {
        return filename;
    }
        
    public String getThisClassName() throws InvalidConstantPoolIndex
    {
        ConstantClass entry = (ConstantClass) constantPool.getEntry(this_class);
        ConstantUtf8 name = (ConstantUtf8) constantPool.getEntry(entry.getNameIndex());
        return name.getBytes();
    }
        
    public MethodInfo GetMethodInfo(int nameIndex) throws Exception
    {
        for(MethodInfo method : methods)
        {
            if(method.getName_index() == nameIndex)
                return method;
        }
        throw new Exception("Method Info Not Found!");
    }

    public long getMagic() {
        return magic;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getAccess_flags() {
        return access_flags;
    }

    public int getThis_class() {
        return this_class;
    }
    
    public int getSuper_class() {
        return super_class;
    }

    public int getInterfaces_count() {
        return interfaces_count;
    }

    public int getFields_count() {
        return fields_count;
    }

    public FieldInfo[] getFields_info() {
        return fields_info;
    }

    public int getMethods_count() {
        return methods_count;
    }

    public void setMethods_count(int methods_count) {
        this.methods_count = methods_count;
    }

    public MethodInfo[] getMethods() {
        return methods;
    }

    public int getAttributes_count() {
        return attributes_count;
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }
}

