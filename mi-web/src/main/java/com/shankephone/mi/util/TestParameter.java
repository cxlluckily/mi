package com.shankephone.mi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ${description}
 *
 * @author 司徒彬
 * @date 2018/7/2 17:56
 */
public class TestParameter {
    private static final String regex = "#\\{\\w+?}";
    private static final Pattern pattern = Pattern.compile(regex);

    public static void test(String sql, Object value) {
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) {
            String parameter = matcher.group().replace("#", "").replace("{", "").replace("}", "");
            Object fieldValue = ReflectionUtils.getFieldValue(value, parameter);
            if (ObjectUtils.isNull(fieldValue)) {
                System.out.println(parameter + " is null");
            } else {
                System.out.println(parameter + ":" + fieldValue);
            }
        }
    }
}
