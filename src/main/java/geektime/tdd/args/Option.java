package geektime.tdd.args;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author rayduan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)   //标注使用在方法的参数上
public @interface Option {
    String value();
}
