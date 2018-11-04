package com.shankephone.mi.util;


import com.shankephone.mi.common.enumeration.MessageDigestEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * MessageDigest计算工具类
 *
 * @author 司徒彬
 */
@Slf4j
public class MessageDigestUtils {
    private static final int BUFFER_SIZE = 5 * 1024;

    /**
     * 获取MD5签名1
     *
     * @param signParameters the sign parameters
     * @return the signature by md 5
     */
    public static String getSignatureByMd5(Map<String, Object> signParameters) {
        try {
            return getSignature(signParameters, MessageDigestEnum.MD5);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Gets signature by sha 1.
     *
     * @param signParameters the sign parameters
     * @return the signature by sha 1
     */
    public static String getSignatureBySha1(Map<String, Object> signParameters) {
        try {
            return getSignature(signParameters, MessageDigestEnum.SHA1);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Gets signature.
     *
     * @param signParameters    the sign parameters
     * @param messageDigestEnum the message digest enum
     * @return the signature
     */
    public static String getSignature(Map<String, Object> signParameters, MessageDigestEnum messageDigestEnum) {
        return getSignature(signParameters, messageDigestEnum, true);
    }

    /**
     * 获取签名
     *
     * @param signParameters     the sign parameters
     * @param messageDigestEnum  the message digest enum
     * @param isContainSplitChar the is contain split char
     * @return the signature
     */
    public static String getSignature(Map<String, Object> signParameters, MessageDigestEnum messageDigestEnum, boolean isContainSplitChar) {
        try {
            StringBuilder signBuilder = new StringBuilder();
            signParameters.keySet().stream().sorted().forEach(key ->
            {
                Object parameter = signParameters.get(key);
                if (isContainSplitChar == true) {
                    signBuilder.append("&").append(key).append("=").append(parameter);
                } else {
                    signBuilder.append(key).append("=").append(parameter);
                }
            });

            String signStr =
                    signBuilder.charAt(0) != '&' ? signBuilder.toString() : signBuilder.deleteCharAt(0).toString();
            signStr = messageDigest(signStr, messageDigestEnum);
            return signStr;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 把字符串MD5签名
     *
     * @param str the str
     * @return the string
     * @throws NoSuchAlgorithmException     the no such algorithm exception
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public static String md5(String str) {
        return messageDigest(str, MessageDigestEnum.MD5);
    }

    /**
     * 把字符串sha1签名
     *
     * @param str the str
     * @return the string
     * @throws UnsupportedEncodingException the unsupported encoding exception
     * @throws NoSuchAlgorithmException     the no such algorithm exception
     */
    public static String sha1(String str) {
        return messageDigest(str, MessageDigestEnum.SHA1);
    }

    /**
     * Message digest string.
     *
     * @param str               the str
     * @param messageDigestEnum the message digest enum
     * @return the string
     * @throws UnsupportedEncodingException the unsupported encoding exception
     * @throws NoSuchAlgorithmException     the no such algorithm exception
     */
    public static String messageDigest(String str, MessageDigestEnum messageDigestEnum) {
        StringBuilder sb = null;
        try {
            if (StringUtils.isEmpty(str)) {
                return "";
            }
            MessageDigest messageDigest = createMessageDigest(messageDigestEnum);
            byte[] bytes = messageDigest.digest(str.getBytes("UTF-8"));
            sb = new StringBuilder(bytes.length << 1);
            int count = bytes.length;
            for (int i = 0; i < count; i++) {
                sb.append(Character.forDigit((bytes[i] >> 4) & 0xf, 16));
                sb.append(Character.forDigit(bytes[i] & 0xf, 16));
            }
        } catch (Exception ex) {
            log.error(ExceptionUtils.getStackTrace(ex));
        }
        return ObjectUtils.isNull(sb) ? "" : sb.toString();
    }

    private static MessageDigest createMessageDigest(MessageDigestEnum messageDigestEnum) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(messageDigestEnum.getValue());
            return messageDigest;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 计算文件的MD5
     *
     * @param inputFile the input file
     * @return the string
     * @throws IOException              the io exception
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public static String countFileMD5(String inputFile) {
        // 缓冲区大小（这个可以抽出一个参数）
        FileInputStream fileInputStream = null;
        DigestInputStream digestInputStream = null;
        try {
            // 拿到一个MD5转换器（同样，这里可以换成SHA1）
            MessageDigest messageDigest = createMessageDigest(MessageDigestEnum.MD5);
            // 使用DigestInputStream
            fileInputStream = new FileInputStream(inputFile);
            digestInputStream = new DigestInputStream(fileInputStream, messageDigest);
            // read的过程中进行MD5处理，直到读完文件
            byte[] buffer = new byte[BUFFER_SIZE];
            while (digestInputStream.read(buffer) > 0) {
                ;
            }
            // 获取最终的MessageDigest
            messageDigest = digestInputStream.getMessageDigest();
            // 拿到结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            // 同样，把字节数组转换成字符串
            return byteArrayToHex(resultByteArray);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        } finally {
            if (digestInputStream != null) {
                try {
                    digestInputStream.close();
                } catch (IOException e) {
                    log.error(ExceptionUtils.getStackTrace(e));
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    log.error(ExceptionUtils.getStackTrace(e));
                }
            }
        }
        return "";
    }

    /**
     * 下面这个函数用于将字节数组换成成16进制的字符串
     *
     * @param byteArray the byte array
     * @return the string
     */
    private static String byteArrayToHex(byte[] byteArray) {
        String hs = "";
        String temp;
        int count = byteArray.length;
        for (int n = 0; n < count; n++) {
            temp = (Integer.toHexString(byteArray[n] & 0XFF));
            if (temp.length() == 1) {
                hs = hs + "0" + temp;
            } else {
                hs = hs + temp;
            }
            if (n < byteArray.length - 1) {
                hs = hs + "";
            }
        }
        return hs;
    }


}
