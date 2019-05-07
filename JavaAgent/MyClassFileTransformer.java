package JavaAgent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;;

public class MyClassFileTransformer implements ClassFileTransformer{

    @Override
    public byte[] transform(ClassLoader classLoader, String s, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException 
    {
			if(s.startsWith("org/joda/time") || s.startsWith("org/apache/commons/dbutils") || s.startsWith("com/fasterxml/jackson/module/afterburner") || s.startsWith("com/fasterxml/jackson/datatype/joda") || s.startsWith("com/fasterxml/jackson/datatype/guava") || 
					s.startsWith("com/fasterxml/jackson/dataformat/xml") ||s.startsWith("org/apache/commons/io") || s.startsWith("org/apache/commons/imaging") || s.startsWith("org/apache/commons/codec") || s.startsWith("com/google/testing/compile") || s.startsWith("com/laytonsmith"))
			{	
            ClassReader reader = new ClassReader(bytes);
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            ClassVisitor visitor = new ClassPrinter(writer);
            reader.accept(visitor, 0);
            return writer.toByteArray();
			}			
			return bytes;
    }

}