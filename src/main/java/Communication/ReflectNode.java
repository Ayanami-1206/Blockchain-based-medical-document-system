package Communication;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectNode {
    public static Method getMethod(String name, String method) {
        try {
            Class<?> c = Class.forName(name);
            for (Method m : c.getDeclaredMethods()) {
                if (m.getName().equals(method)) {
                    return m;
                }
            }
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }


    public static List getMethods(String className) {
        try {
            Class<?> c = Class.forName(className);
            Method[] methods = c.getMethods();
            for (Method m : methods) {
                System.out.println(m.getName());
            }
            return Arrays.asList(methods);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
    //根据方法名获取对象的方法
    public static List<String> getDeclaredMethodsName(String className) {
        try {
            List<String> methodNames = new ArrayList<>();
            Class<?> c = Class.forName(className);
            Method [] methods = c.getDeclaredMethods();
            for (Method m : methods) {
                methodNames.add(m.getName());
            }
            return methodNames;
        } catch (ClassNotFoundException e){
            return null;
        }
    }

}
