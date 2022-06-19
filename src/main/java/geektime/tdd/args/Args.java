package geektime.tdd.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

/**
 * @author rayduan
 */
public class Args {

    public static <T> T parse(Class<T> optionsClass, String... args) {
        try {
            List<String> arguments = Arrays.asList(args);
            Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];
            //获取全部的参数
            Parameter[] parameters = constructor.getParameters();
            //通过map，遍历每个参数，执行解析
            Object[] values = Arrays.stream(parameters).map(it -> parseOption(arguments, it)).toArray();
            return (T) constructor.newInstance(values);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     *  针对不同参数类型，分别执行相应的 parse 逻辑
     */
    private static Object parseOption(List<String> arguments, Parameter parameter) {
        //获取参数的注解
        Option option = parameter.getAnnotation(Option.class);

        Object value = null;
        if (parameter.getType() == boolean.class) {
            value = arguments.contains("-" + option.value());
        }

        if (parameter.getType() == int.class) {
            //查找出-p的在数组中的索引
            int index = arguments.indexOf("-" + option.value());
            value = Integer.parseInt(arguments.get(index + 1));
        }

        if(parameter.getType() == String.class){
            int index = arguments.indexOf("-" + option.value());
            value = arguments.get(index + 1);
        }
        return value;
    }
}
