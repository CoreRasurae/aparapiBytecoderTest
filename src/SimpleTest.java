import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import de.mirkosertic.bytecoder.core.Bytecode5XProgramParser;
import de.mirkosertic.bytecoder.core.Bytecode5xClassParser;
import de.mirkosertic.bytecoder.core.BytecodeAnnotation;
import de.mirkosertic.bytecoder.core.BytecodeClass;
import de.mirkosertic.bytecoder.core.BytecodeClassParser;
import de.mirkosertic.bytecoder.core.BytecodeLoader;
import de.mirkosertic.bytecoder.core.BytecodeReplacer;
import de.mirkosertic.bytecoder.core.BytecodeShadowReplacer;
import de.mirkosertic.bytecoder.core.BytecodeSignatureParser;

public class SimpleTest extends BytecodeLoader {
    public SimpleTest() {
        super(null);
    }

    private final BytecodeReplacer bytecodeReplacer = new BytecodeReplacer(this);
    private final BytecodeShadowReplacer shadowReplacer = new BytecodeShadowReplacer(this, bytecodeReplacer);
    private final BytecodeSignatureParser signatureParser = new BytecodeSignatureParser(bytecodeReplacer);
    
    public BytecodeClass parseClass(DataInputStream dis) {
        BytecodeClass result = null;
        try {
            BytecodeClassParser parser = parseHeader(dis, bytecodeReplacer);
            result = parser.parseBody(dis);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return result;
    }
    
    private BytecodeClassParser parseHeader(DataInput aStream, BytecodeReplacer aReplacer) throws IOException {
        int theMagic = aStream.readInt();
        if (!(theMagic == 0xCAFEBABE)) {
            throw new IllegalArgumentException("Wrong class file format : " + theMagic);
        }
        int theMinorVersion = aStream.readUnsignedShort();
        int theMajorVersion = aStream.readUnsignedShort();
        switch (theMajorVersion) {
            case 49:
                return new Bytecode5xClassParser(new Bytecode5XProgramParser(), signatureParser, aReplacer);
            case 50:
                return new Bytecode5xClassParser(new Bytecode5XProgramParser(), signatureParser, aReplacer);
            case 51:
                return new Bytecode5xClassParser(new Bytecode5XProgramParser(), signatureParser, aReplacer);
            case 52:
                return new Bytecode5xClassParser(new Bytecode5XProgramParser(), signatureParser, aReplacer);
            case 53:
                return new Bytecode5xClassParser(new Bytecode5XProgramParser(), signatureParser, aReplacer);
            case 54:
                return new Bytecode5xClassParser(new Bytecode5XProgramParser(), signatureParser, aReplacer);
        }
        throw new IllegalArgumentException("Not Supported bytecode format : " + theMajorVersion);
    }

    
    
    public static void main(String[] args) {
        /*FileInputStream fis = null;
        try {
            fis = new FileInputStream("test.class");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DataInputStream dis = new DataInputStream(fis);*/
        
        String theResourceName = "test/me/JavaTest.class";
        
        InputStream theStream = ClassLoader.getSystemClassLoader().getResourceAsStream(theResourceName);
        if (theStream == null) {
            System.out.println("Failed");
            return;
        }
        DataInputStream dis = new DataInputStream(theStream);

        
        SimpleTest test = new SimpleTest();
        BytecodeClass clazz = test.parseClass(dis);
        de.mirkosertic.bytecoder.core.BytecodeField field = clazz.fields()[0];
        BytecodeAnnotation annotation = ((de.mirkosertic.bytecoder.core.BytecodeAnnotationAttributeInfo)field.getAttributeInfo()[0]).getAnnotationByType("com.aparapi.Kernel$Local");
        System.out.println(annotation.getType().name());
        
        System.out.println("Test ran successfully");
    }
    
}
    
