package com.yangxiaochen.examples.javaagent;

import javassist.*;
import javassist.bytecode.*;
import javassist.compiler.CompileError;
import javassist.compiler.Javac;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class SimpleJavaAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("agentArgs : " + agentArgs);
        inst.addTransformer(new DefineTransformer(), true);
    }

    static class DefineTransformer implements ClassFileTransformer {

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            if (!className.startsWith("com/yangxiaochen/examples/javaagent")) {
                return classfileBuffer;
            }
            System.out.println("premain load Class:" + className + ", " + classBeingRedefined + ", " + protectionDomain);

            ClassPool pool = ClassPool.getDefault();
            try {
                CtClass cc = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
                if (cc.isInterface() || !className.startsWith("com/yangxiaochen/examples/javaagent")) {
                    return classfileBuffer;
                }
                System.out.println("pool get className: " + cc.getSimpleName());
                System.out.println("cc:" + cc.getSimpleName() + ", " + cc.getClassFile());
                for (CtMethod method : cc.getDeclaredMethods()) {
                    System.out.println("method: " + method.getGenericSignature());
                    System.out.println("method: " + method.getLongName());
                    System.out.println("method: " + method.getSignature());
                    System.out.println("method: " + method.getParameterTypes().toString());
                    if (method.isEmpty()) {
                        continue;
                    }
                    method.insertBefore("System.out.println(\"before\");");
                    method.setBody();
                }
                return cc.toBytecode();
            } catch (IOException | CannotCompileException | NotFoundException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            return classfileBuffer;
        }


    }
}
