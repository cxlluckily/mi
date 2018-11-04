package com.shankephone.mi.util;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * StringUtils重载方法
 *
 * @author 司徒彬
 * @date 2017 -03-15 10:56
 */
@Slf4j
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * Is empty boolean.
     *
     * @param cs the cs
     * @return the boolean
     */
    public static boolean isEmpty(CharSequence cs) {
        return !isNotEmpty(cs);
    }

    /**
     * Is not empty boolean.
     *
     * @param cs the cs
     * @return the boolean
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return isNotEmpty((Object) cs);
    }

    /**
     * Is empty boolean.
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean isEmpty(Object str) {
        return !isNotEmpty(str);
    }

    /**
     * Is not empty boolean.
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean isNotEmpty(Object str) {
        return str != null && str.toString().trim().length() != 0;
    }

    /**
     * To string string.
     *
     * @param str the str
     * @return the string
     */
    public static String toString(Object str) {
        return isEmpty(str) ? "" : str.toString();
    }

    /**
     * Create random str string.
     *
     * @param length the length
     * @return the string
     */
    public static String createRandomStr(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }


    /**
     * Split to list list.
     *
     * @param sourceStr the source str
     * @param splitChar the split char
     * @return the list
     */
    public static List<String> splitToList(String sourceStr, String splitChar) {
        if (sourceStr != null) {
            String[] arr = org.apache.commons.lang3.StringUtils.split(sourceStr, splitChar);
            return Arrays.stream(arr).filter(str -> str.trim().length() != 0).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 验证是否浮点数
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean isFloat(Object value) {
        String str = toString(value);
        String regex = "^[1-9]\\d*\\.{1}\\d*$|^0\\.{1}\\d*[1-9]\\d*$";
        return match(str, regex);
    }

    /**
     * 验证是否证书
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean isInteger(Object value) {
        String str = toString(value);
        String regex = "^-?[1-9]\\d*$";
        return match(str, regex);
    }

    /**
     * Test.
     */
    @Test
    public void test() {
        System.out.print(StringUtils.createRandomStr(6).toLowerCase());
    }


    /***
     * @param str 源字符串
     * @param regex 正则表达式
     * @return 是否匹配 boolean
     */
    public static boolean match(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }



    /**
     * 利用正则表达式判断字符串是手机号
     * @param value
     * @return
     */
    public static boolean isphone(String value){

        String str = toString(value);
        String regex = "^1\\d{10}$";
        return match(str, regex);
    }

    /**
     * 利用正则表达式判断字符串是邮箱
     * @param value
     * @return
     */
    public static boolean ismail(String value){
        String str = toString(value);
        String regex = "^\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]+$";
        return match(str, regex);
    }

    /**
     * 获取排序语句
     * 如果传入的 sortType 以及 sortField 为空 则 返回空字符串
     *
     * @param findEntity BaseFindEntity 以及 BaseFindEntity的子类
     * @return sort str
     */
    public static String getSortStr(BaseFindEntity findEntity) {
        StringBuilder order = new StringBuilder();
        if (ObjectUtils.isNotEmpty(findEntity.getSortField()) && ObjectUtils.isNotEmpty(findEntity.getSortType())) {

            order.append(" ORDER BY ").append(findEntity.getSortField()).append(" ").append(findEntity.getSortType());
        }
        return order.toString();
    }


    /**
     * Array to string string.
     *
     * @param <T>     the type parameter
     * @param objects the objects
     * @return the string
     */
    public static <T> String arrayToString(T... objects) {
        Stream<String> stream = Arrays.stream(objects).filter(ObjectUtils::isNotNull)
                .map(Object::toString);
        return DataSwitch.getCombineString(stream);
    }

    /**
     * List to string string.
     * <p>
     * 判断原理 值中不能包含空格
     *
     * @param <T>        the type parameter
     * @param parameters the parameters
     * @return the string
     * @throws Exception the exception
     */
    public static <T> String listToString(List<T> parameters) {

        List<T> streamT = parameters.stream().distinct().collect(Collectors.toList());

        List<String> streamStr = streamT.stream()
                .filter(ObjectUtils::isNotNull)
                .map(Object::toString)
                .filter(str -> !str.contains(";"))
                .filter(parameter -> !parameter.contains(" "))
                .distinct()
                .collect(Collectors.toList());

        if (streamStr.size() != streamT.size()) {
            log.error("传入的值中包含危险代码");
            return "";
        }
        return DataSwitch.getCombineString(streamStr);

    }

    private final String regex = "[a-zA-Z]+\\w*";
    private final String checkSqlInjectionAttack = "^" + regex + "$";

    /**
     * 验证是否包含注入代码
     *
     * @param key parameters
     * @return the boolean
     */
    private boolean checkParametersSqlInjectionAttack(String key) {
        try {
            if (key == null) {
                return false;
            }
            Pattern pattern = Pattern.compile(checkSqlInjectionAttack);
            Matcher matcher = pattern.matcher(key);

            return matcher.matches();

        } catch (Exception ex) {
            throw ex;
        }
    }
}
