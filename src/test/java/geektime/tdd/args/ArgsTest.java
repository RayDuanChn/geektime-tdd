package geektime.tdd.args;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {

    /**
     * 传递给程序的参数由标志和值组成。标志应该是一个字符，前面有一个减号。每个标志都应该有零个或多个与之相关的值。
     * 例如：　-l -p 8080 -d /usr/logs
     * “l”（日志）没有相关的值，它是一个布尔标志，如果存在则为 true，不存在则为 false。
     * “p”（端口）有一个整数值，“d”（目录）有一个字符串值。
     * <p>
     * 标志后面如果存在多个值，则该标志表示一个列表：
     * -g this is a list -d 1 2 -3 5
     * "g"表示一个字符串列表[“this”, “is”, “a”, “list”]，
     * “d"标志表示一个整数列表[1, 2, -3, 5]
     * <p>
     * 如果参数中没有指定某个标志，那么解析器应该指定一个默认值。
     * 例如，false 代表布尔值，0 代表数字，”"代表字符串，[]代表列表。如果给出的参数与模式不匹配，重要的是给出一个好的错误信息，准确地解释什么是错误的。
     * <p>
     * 确保你的代码是可扩展的，即如何增加新的数值类型是直接和明显的。
     */


    //-l -p 8080 -d /usr/logs


    //-g this is a list -d 1 2 -3 5　
    @Test
    @Disabled
    public void should_example_2() {
        ListOptions options = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");
        assertArrayEquals(new String[]{"this", "is", "a", "list"}, options.group());
        assertArrayEquals(new int[]{1, 2, -3, 5}, options.decimals());
    }

    record ListOptions(@Option("g") String[] group, @Option("d") int[] decimals) {
    }

    //     - boolean   -l
    @Test
    public void should_set_boolean_option_to_true_if_flag_present() {
        BooleanOption option = Args.parse(BooleanOption.class, "-l");
        assertTrue(option.logging());
    }

    @Test
    public void should_set_boolean_option_to_false_if_flag_not_present() {
        BooleanOption option = Args.parse(BooleanOption.class);
        assertFalse(option.logging());
    }

    record BooleanOption(@Option("l") boolean logging) {}



    //  - int      -p 8080

    @Test
    public void should_int_option_with_value_if_flag_present() {
        IntOption option = Args.parse(IntOption.class, "-p", "8080");
        assertEquals(8080, option.port());
    }

    record IntOption(@Option("p") int port) {
    }

    //  - String   -d /usr/logs
    @Test
    public void should_get_string_as_option_value() {
        StringOption option = Args.parse(StringOption.class, "-d", "/usr/logs");
        assertEquals("/usr/logs", option.directory());
    }
    record StringOption(@Option("d") String directory) {
    }

    //    多参数形式， Multi options
    //    -l -p 8080 -d /usr/logs
    @Test
    public void should_parse_multi_options() {
        //假设别人调用代码的方式
        MultiOptions options = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(options.logging());
        assertEquals(8080, options.port());
        assertEquals("/usr/logs", options.directory());
    }

    //使用注解表示标签类型
    public record MultiOptions(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {}

    //
    //sad path:
    //   TODO  - boolean  -l t / -l t f  . 带参数或带多个参数
    //   TODO  - int      -p / -p 8080 9090   无参，多参数
    //   TODO  - String   -d / -d /usr/logs /usr/vars   无参，多参数
    //default value:
    //   TODO  - bool false
    //   TODO  - int  0
    //   TODO  - string ""


}
