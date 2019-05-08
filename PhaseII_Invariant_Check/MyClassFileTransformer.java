package JavaAgent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Iterator;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;;

public class MyClassFileTransformer implements ClassFileTransformer{

    @Override
    public byte[] transform(ClassLoader classLoader, String s, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException 
    {
    	if(s.startsWith("org/apache/commons/lang3") || s.startsWith("org/joda/time") || s.startsWith("org/apache/commons/dbutils") || s.startsWith("com/fasterxml/jackson/module/afterburner") || s.startsWith("com/fasterxml/jackson/datatype/joda") || s.startsWith("com/fasterxml/jackson/datatype/guava") || 
				s.startsWith("com/fasterxml/jackson/dataformat/xml") ||s.startsWith("org/apache/commons/io") || s.startsWith("org/apache/commons/imaging") || s.startsWith("org/apache/commons/codec") || s.startsWith("com/google/testing/compile") || s.startsWith("com/laytonsmith"))
		{	
            ClassReader reader = new ClassReader(bytes);
            ClassNode cn = new ClassNode();
            reader.accept(cn, 0);
            
            List<MethodNode> methodList = cn.methods; 
            for (MethodNode md : methodList) { 
                System.out.println("method name: "+md.name); 
                System.out.println("method access: "+md.access); 
                System.out.println("method desc: "+md.desc); 
                System.out.println("method signature: "+md.signature); 
                List<LocalVariableNode> lvNodeList = md.localVariables; 
                for (LocalVariableNode lvn : lvNodeList) { 
                    System.out.println("Local name: " + lvn.name); 
                    System.out.println("Local Start Label: " + lvn.start.getLabel()); 
                    System.out.println("Local desc: " + lvn.desc); 
                    System.out.println("Local signature: " + lvn.signature); 
                } 
                
                Iterator<AbstractInsnNode> instraIter = md.instructions.iterator(); 
                while (instraIter.hasNext()) { 
                    AbstractInsnNode abi = instraIter.next(); 
                    if (abi instanceof LdcInsnNode) { 
                        LdcInsnNode ldcI = (LdcInsnNode) abi; 
                        System.out.println("Load Constant node value: " + ldcI.cst); 
                    } 
                } 
            } 
            
            
            MethodVisitor mv = cn.visitMethod(Opcodes.AALOAD, "<init>", Type.getType(String.class).toString(), null, null); 
            mv.visitFieldInsn(Opcodes.GETFIELD, Type.getInternalName(String.class), "str", Type.getType(String.class).toString()); 
            System.out.println("Class: "+cn.name); 
            List<FieldNode> fieldList = cn.fields; 
            for (FieldNode fieldNode : fieldList) { 
                System.out.println("Field name: " + fieldNode.name); 
                System.out.println("Field desc: " + fieldNode.desc); 
                System.out.println("Filed value: " + fieldNode.value); 
                System.out.println("Filed access: " + fieldNode.access); 
            }
            
		}			
		return bytes;
    }

}