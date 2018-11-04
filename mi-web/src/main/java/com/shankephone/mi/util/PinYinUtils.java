package com.shankephone.mi.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.stream.IntStream;

/**
 * 拼音操作类
 *
 * @author 司徒彬
 * @date 2018/6/29 09:37
 */
public class PinYinUtils {
    /**
     * 获取汉字串拼音首字母，英文字符不变
     *
     * @param chinese 汉字串
     * @return 汉语拼音首字母 cn 2 first spell
     */
    public static String getCn2FirstSpell(String chinese) {
        StringBuffer result = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        int length = arr.length;
        IntStream.range(0, length).forEach(i -> {
            if (arr[i] > 128) {
                try {
                    String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                    if (_t != null) {
                        result.append(_t[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                result.append(arr[i]);
            }
        });
        return result.toString().replaceAll("\\W", "").trim().toLowerCase();
    }

    /**
     * 获取汉字串拼音，英文字符不变
     *
     * @param chinese 汉字串
     * @return 汉语拼音 cn 2 spell
     */
    public static String getCn2Spell(String chinese) {
        StringBuffer result = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        int length = arr.length;
        IntStream.range(0, length).forEach(i -> {
            if (arr[i] > 128) {
                try {
                    String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                    if (_t != null) {
                        result.append(_t[0]);
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                result.append(arr[i]);
            }
        });
        return result.toString().toLowerCase();
    }

    public static void main(String[] args) {
        String sgf = "我是测试字符串";
        System.out.println(getCn2Spell(sgf));
        System.out.println(getCn2FirstSpell(sgf));
    }

}
