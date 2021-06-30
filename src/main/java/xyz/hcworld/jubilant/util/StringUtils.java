package xyz.hcworld.jubilant.util;

/**
 * 字符串处理工具包
 *
 * @ClassName: StringUtils
 * @Author: 张红尘
 * @Date: 2021-06-26
 * @Version： 1.0
 */
public class StringUtils {
    /**
     * 不为空
     *
     * @param str 需要校验的字符串
     * @return 不为空是时为true
     */
    public static boolean isNotBlank(String str) {
        return str != null && str.trim().length() > 0;
    }

    /**
     * 为空
     *
     * @param str 需要校验的字符串
     * @return 为空时true
     */
    public static boolean isBlank(String str) {
        return !isNotBlank(str);
    }
}