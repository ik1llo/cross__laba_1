import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Calculator {
    int add(int a, int b);
}

class RealCalculator implements Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}

class DynamicProxyHandler implements InvocationHandler {
    private Object target;

    public DynamicProxyHandler(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start_time = System.nanoTime();
        Object result = method.invoke(target, args);
        long end_time = System.nanoTime();

        System.out.println("method execution time " + method.getName() + ": " + (end_time - start_time) + "ns");

        System.out.print("method invocation: " + method.getName() + "(");
        for (int i = 0; i < args.length; i++) {
            System.out.print(args[i]);
            if (i < args.length - 1)
                System.out.print(", ");
        }
        System.out.println(") = " + result);

        return result;
    }
}

public class Application_5 {
    public static void main(String[] args) {
        Calculator calculator = new RealCalculator();

        Calculator proxy = (Calculator) Proxy.newProxyInstance(
                Calculator.class.getClassLoader(),
                new Class<?>[] { Calculator.class },
                new DynamicProxyHandler(calculator)
        );

        proxy.add(3, 3);
    }
}
