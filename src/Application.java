import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Application {
    public static String analyze_class(String class_name) throws ClassNotFoundException {
        return analyze_class( Class.forName(class_name) );
    }
    
    public static String analyze_class(Class<?> _class) {
        StringBuilder builder = new StringBuilder(); 
        
        builder.append("[package]: ").append(_class.getPackageName()).append("\n");
        
        int modifiers = _class.getModifiers();
        builder.append("[modifiers]: ").append(Modifier.toString(modifiers)).append("\n");
        
        builder.append("[class name]: ").append(_class.getSimpleName()).append("\n");
        
        Class<?> superclass = _class.getSuperclass();
        if (superclass != null) {
            builder.append("[superclass]: ").append(superclass.getSimpleName()).append("\n");
        }
        
        Class<?>[] interfaces = _class.getInterfaces();
        if (interfaces.length > 0) {
            builder.append("[implemented interfaces]: ");
            for (Class<?> intf : interfaces) {
                builder.append(intf.getSimpleName()).append(", ");
            }
            builder.delete(builder.length() - 2, builder.length());
            builder.append("\n");
        }
        
        Field[] fields = _class.getDeclaredFields();
        if (fields.length > 0) {
            builder.append("[fields]:\n");
            for (Field field : fields) {
                builder.append("\t").append(Modifier.toString(field.getModifiers()))
                       .append(" ").append(field.getType().getSimpleName())
                       .append(" ").append(field.getName()).append("\n");
            }
        }
        
        Constructor<?>[] constructors = _class.getDeclaredConstructors();
        if (constructors.length > 0) {
            builder.append("[constructors]:\n");
            for (Constructor<?> constructor : constructors) {
                builder.append("\t").append(Modifier.toString(constructor.getModifiers()))
                       .append(" ").append(_class.getSimpleName()).append("(");
                Parameter[] parameters = constructor.getParameters();
                for (Parameter param : parameters) {
                    builder.append(param.getType().getSimpleName()).append(", ");
                }
                if (parameters.length > 0) {
                    builder.delete(builder.length() - 2, builder.length());
                }
                builder.append(")\n");
            }
        }
        
        Method[] methods = _class.getDeclaredMethods();
        if (methods.length > 0) {
            builder.append("[methods]:\n");
            for (Method method : methods) {
                builder.append("\t").append(Modifier.toString(method.getModifiers()).isEmpty() ? "package-private" : Modifier.toString(method.getModifiers()))
                       .append(" ").append(method.getReturnType().getSimpleName())
                       .append(" ").append(method.getName()).append("(");
                Parameter[] parameters = method.getParameters();
                for (Parameter param : parameters) {
                    builder.append(param.getType().getSimpleName()).append(", ");
                }
                if (parameters.length > 0) {
                    builder.delete(builder.length() - 2, builder.length());
                }
                builder.append(")\n");
            }
        }
        
        return builder.toString();
    }

    public static void inspect_object(Object obj, Scanner scanner) {
        System.out.println("[object type]: " + obj.getClass().getName());
        System.out.println("[object state]:");
        Arrays.stream(obj.getClass().getDeclaredFields())
            .forEach(field -> {
                field.setAccessible(true);
                try {
                    System.out.println("\t" + field.getName() + ": " + field.get(obj));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });

        System.out.println("[public methods]:");
        Arrays.stream(obj.getClass().getDeclaredMethods())
                .filter(method -> Modifier.isPublic(method.getModifiers()))
                .forEach(method -> {
                    System.out.println("\t" + method.getName());
                });

        System.out.println("[public methods without parameters]:");
        Arrays.stream(obj.getClass().getDeclaredMethods())
                .filter(method -> Modifier.isPublic(method.getModifiers()) && method.getParameterCount() == 0)
                .forEach(method -> {
                    System.out.println("\t" + method.getName());
                });

        

        AtomicBoolean method_found = new AtomicBoolean(false);

        System.out.printf("\n[enter public method name to invoke]: ");
        String user_input = scanner.nextLine();

        Arrays.stream(obj.getClass().getDeclaredMethods())
                .filter(method -> Modifier.isPublic(method.getModifiers()) && method.getParameterCount() == 0)
                .forEach(method -> {
                    if (method.getName().compareTo(user_input) == 0) {
                        try {
                            method.invoke(obj);
                            method_found.set(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } 
                    } 
                }); 

        if (!method_found.get()) { 
            System.out.println("wrong method name..."); 
            System.out.println(""); 
        }
    }

    public static void invoke_method(Object obj, String method_name, Object... parameters) throws FunctionNotFoundException {
        try {
            Class<?>[] parameter_types = new Class[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                parameter_types[i] = parameters[i].getClass();
            }

            Method method = obj
                .getClass()
                .getDeclaredMethod(method_name, parameter_types);
     
            Object result = method.invoke(obj, parameters);
            System.out.println("method \"" + method_name + "\" invocation result is: " + result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new FunctionNotFoundException(method_name);
        } 
    } 
    
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            // task #1 
            String class_name = "java.util.ArrayList";
            System.out.println(analyze_class(class_name));

            // task #2
            Car car_object = new Car();
            inspect_object( car_object, scanner );
            
            // task #3
            invoke_method(car_object, "method_2", 2);

            scanner.close(); 
        } catch (Exception e) { e.printStackTrace(); }
    }
}

class Car {
    public int field_1;
    private int field_2;
    protected int field_3;

    public Car() {
        field_1 = 1;
        field_2 = 2;
        field_3 = 3;
    }

    public void method_1() {
        System.out.println("method 1 has been called");
        System.out.println(""); 
    }

    public int method_2(Integer param) {
        return param;
    }
    
    private void method_3() {
        System.out.println("method 3 has been called");
        System.out.println(""); 
    }
    
    protected int method_4() { 
        return 4;
    }
}

class FunctionNotFoundException extends Exception {
    public FunctionNotFoundException(String message) {
        super(message);
    }
}