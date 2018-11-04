/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.shankephone.mi.util;

import com.google.gson.*;
import com.shankephone.mi.common.enumeration.DateFormatEnum;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * DataSwitch 操作类
 *
 * @author 司徒彬
 * @date 2017年1月12日12 :06:33
 */
@Slf4j
public class DataSwitch {

    /**
     * Convert list to tree list.
     * 将 list 转化为树结构
     *
     * @param data          the data 生成树的数据
     * @param rootId        the root id 根节点的 Id 一般为 -1
     * @param idFieldName   the id field name 树节点中对应的主键字段名称
     * @param pidFieldName  the pid field name 树节点中对应的 父 Id 名称 一般为 pId or parentId
     * @param childNodeName the child node name 返回结果中要求的子节点存储的字段名 一般为 children
     * @return the list
     */
    public static List<Map<String, Object>> convertListToTree(List<Map<String, Object>> data, @NonNull Object rootId, String idFieldName, String pidFieldName, String childNodeName) {
        List<Map<String, Object>> result = data.stream().filter(map -> DataSwitch.convertObjectToString(map.get(pidFieldName)).equals(rootId.toString())).map(map -> {
            String id = DataSwitch.convertObjectToString(map.get(idFieldName));
            if (map.containsKey("checked")) {
                boolean isChecked = DataSwitch.convertObjectToBoolean(map.get("checked"));
                map.put("checked", isChecked);
            }
            List<Map<String, Object>> children = getChildren(data, id, idFieldName, pidFieldName, childNodeName);
            map.put(childNodeName, children);
            if (null != children && children.size() > 0) {
                map.put("leaf", false);
            } else {
                map.put("leaf", true);
            }
            return map;
        }).collect(Collectors.toList());
        return result;
    }

    private static List<Map<String, Object>> getChildren(List<Map<String, Object>> data, String parentId, String idFieldName, String pidFieldName, String childNodeName) {
        try {
            List<Map<String, Object>> children = data.stream().filter(map -> DataSwitch.convertObjectToString(map.get(pidFieldName)).equals(parentId)).map(map -> {
                String id = DataSwitch.convertObjectToString(map.get(idFieldName));
                if (map.containsKey("checked")) {
                    boolean isChecked = DataSwitch.convertObjectToBoolean(map.get("checked"));
                    map.put("checked", isChecked);
                }
                List<Map<String, Object>> childrenOfChild = getChildren(data, id, idFieldName, pidFieldName, childNodeName);
                map.put(childNodeName, childrenOfChild);
                if (null != childrenOfChild && childrenOfChild.size() > 0) {
                    map.put("leaf", false);
                } else {
                    map.put("leaf", true);
                }
                return map;
            }).collect(Collectors.toList());
            return children;
        } catch (Exception ex) {
            throw ex;
        }
    }


    //region Util


    /**
     * 生成UUID
     *
     * @return the uuid
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * Gets random num.
     *
     * @param min the min
     * @param max the max
     * @return the random num
     */
    public static int getRandomNumber(int min, int max) {
        int random = new Random(System.nanoTime()).nextInt(max) % (max - min + 1) + min;
        return random;
    }

    /**
     * Gets default value.
     *
     * @param value the value
     * @param type  the type
     * @return the default value
     */
    private static Object getDefaultValue(Object value, Type type) {
        try {
            Object resultObject;
            if (value == null) {
                resultObject = null;
            } else {
                if (type == String.class) {
                    resultObject = convertObjectToString(value);
                } else if (type == Integer.class || type == int.class) {
                    resultObject = convertObjectToInteger(value);
                } else if (type == Long.class || type == long.class) {
                    resultObject = convertObjectToLong(value);
                } else if (type == Date.class) {
                    resultObject = convertObjectToDate(value);
                } else if (type == Double.class || type == double.class) {
                    resultObject = convertObjectToDouble(value);
                } else if (type == Float.class || type == float.class) {
                    resultObject = convertObjectToFloat(value);
                } else if (type == Boolean.class || type == boolean.class) {
                    resultObject = convertObjectToBoolean(value);
                } else if (type == BigDecimal.class) {
                    resultObject = convertObjectToBigDecimal(value);
                } else if (type == Short.class) {
                    resultObject = convertObjectToShort(value);
                }else if (type.equals(List.class)){
                    resultObject = value;
                }
                else {
                    resultObject = "";
                }
            }
            return resultObject;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //endregion

    //region 数据格式转换

    /**
     * 将整型对象格式字符串转换成整型对象，如果传入对象为 null 或 空，返回 0
     *
     * @param value the value
     * @return the integer
     */
    public static Integer convertObjectToInteger(Object value) {
        try {

            if (null != value) {
                return NumberUtils.toInt(value.toString(), 0);
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 将类型转换成Double对象，如果传入对象为 null 或 空，返回 0.0
     *
     * @param value the value
     * @return the double
     */
    public static Double convertObjectToDouble(Object value) {
        try {
            if (null != value) {
                return new Double(value.toString());
                // return NumberUtils.toDouble(value.toString(), 0);
            } else {
                return 0.0;
            }
        } catch (Exception e) {
            return 0.0;
        }
    }


    /**
     * 将对象转换成Long对象，如果传入对象为 null 或 空，返回 0l
     *
     * @param value ： 传入参数值
     * @return the long
     */
    public static Long convertObjectToLong(Object value) {
        try {
            if (null != value) {
                return NumberUtils.toLong(value.toString(), 0);
            } else {
                return 0l;
            }
        } catch (Exception e) {
            return 0l;
        }
    }

    /**
     * 将对象转换成Float对象，如果传入对象为 null 或 空，返回 0f
     *
     * @param value the value
     * @return the float
     */
    public static Float convertObjectToFloat(Object value) {
        try {
            if (null != value) {
                return NumberUtils.toFloat(value.toString(), 0);
            } else {
                return 0f;
            }
        } catch (Exception e) {
            return 0f;
        }
    }


    /**
     * 将对象转换成BigDecimal对象，如果传入对象为 null 或 空，返回 null
     *
     * @param value the value
     * @return the big decimal
     */
    public static BigDecimal convertObjectToBigDecimal(Object value) {
        try {
            if (null != value) {
                return new BigDecimal(value.toString());
                //float f = DataSwitch.convertObjectToFloat(value);
                //return new BigDecimal(f);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Convert object to big decimal big decimal.
     *
     * @param value the value
     * @param scale the scale
     * @return the big decimal
     */
    public static BigDecimal convertObjectToBigDecimal(Object value, int scale) {
        try {
            if (null != value) {
                return new BigDecimal(value.toString()).setScale(scale, BigDecimal.ROUND_HALF_UP);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将对象转换成Short对象，如果传入对象为 null 或 空，返回 null
     *
     * @param value the value
     * @return the short
     */
    public static Short convertObjectToShort(Object value) {
        try {
            if (null != value) {
                return Short.parseShort(value.toString());
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将对象转换成Boolean对象，如果传入对象为 null 或 空，返回false
     *
     * @param value the value
     * @return the boolean
     */
    public static Boolean convertObjectToBoolean(Object value) {
        try {
            if (value == null) {
                return false;
            }
            Class clazz = value.getClass();
            if (clazz.equals(String.class)) {
                return value.toString().equalsIgnoreCase("true") || value.toString().equals("1");
            } else if (clazz.equals(Long.class)) {
                return DataSwitch.convertObjectToLong(value) == 1L;
            } else if (clazz.equals(Integer.class)) {
                return DataSwitch.convertObjectToInteger(value) == 1;
            } else {
                return (Boolean) value;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 对象转换成String对象，如果传入对象为 null 或 空，返回 ""
     *
     * @param value the value
     * @return the string
     */
    public static String convertObjectToString(Object value) {
        try {
            if (null != value) {
                return value.toString().trim();
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 将日期字符串转换成日期对象
     *
     * @param time the time
     * @return the date
     * @throws ParseException the parse exception
     */
    public static Date convertObjectToDate(Object time) throws ParseException {
        try {
            String format = null;
            if (time == null || "".equals(DataSwitch.convertObjectToString(time))) {
                return null;
            }
            String timeStr = time.toString();
            if (timeStr.contains(":") && !timeStr.contains(".")) {
                if (timeStr.indexOf(":") == timeStr.lastIndexOf(":")) {
                    format = "yyyy-MM-dd HH:mm";
                } else {
                    format = "yyyy-MM-dd HH:mm:ss";
                }
            } else if (timeStr.contains(".")) {
                timeStr = timeStr.substring(0, timeStr.indexOf("."));
                format = "yyyy-MM-dd HH:mm:ss";
            } else {
                format = "yyyy-MM-dd";
            }
            return DateUtils.parseDate(timeStr, new String[]{format});
        } catch (ParseException e) {
            log.error("日期格式错误{" + time + "}，正确格式为：yyyy-MM-dd HH:mm");
            throw e;
        }
    }

    //endregion

    //region Map 与 实体转换

    /**
     * Convert map obj to toolsentity t. 不区分大小写
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @param map   the map
     * @return the t
     * @throws Exception the exception
     */
    public static <T> T convertMapObjToEntity(Class<T> clazz, Map<String, Object> map) throws Exception {
        try {
            T t = clazz.newInstance();
            Field[] fields = ReflectionUtils.getFields(clazz);
            Arrays.stream(fields).forEach(field -> {
                String name = field.getName();
                Optional<String> keyOptional = map.keySet().stream().filter(key -> key.equalsIgnoreCase(name)).findFirst();
                if (!keyOptional.equals(Optional.empty())) {
                    Type type = field.getType();
                    String keyName = keyOptional.get();
                    ReflectionUtils.setFieldValue(t, name, getDefaultValue(map.get(keyName), type));
                    map.remove(keyName);
                }
            });
            return t;
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * Convert http servlet request obj to entity t.
     *
     * @param <T>     the type parameter
     * @param clazz   the clazz
     * @param request the request
     * @return the t
     * @throws Exception the exception
     */
    public static <T> T convertHttpServletRequestObjToEntity(Class<T> clazz, HttpServletRequest request) throws Exception {
        Map<String, Object> parameterMap = new HashMap<>();

        request.getParameterMap().entrySet().stream().forEach(map -> {

            if (map.getValue().length == 1) {
                parameterMap.put(map.getKey(), map.getValue()[0]);
            } else {
                parameterMap.put(map.getKey(), map.getValue());
            }

        });
        return convertMapObjToEntity(clazz, parameterMap);
    }

    /**
     * Convert request to entity t.
     * <p>
     * 用于对应实体中有List的情况
     *
     * @param <T>     the type parameter
     * @param clazz   the clazz
     * @param request the request
     * @return the t
     */
    public static <T> T convertRequestToEntity(Class<T> clazz, HttpServletRequest request) {
        Map<String, Object> parameterMap = new HashMap<>();
        request.getParameterMap().entrySet().stream().forEach(map -> {

            if (map.getValue().length == 1 && ObjectUtils.isNotEmpty(map.getValue()[0])) {
                parameterMap.put(map.getKey(), map.getValue()[0]);
            } else if (map.getValue().length > 1) {
                Object[] objects = Arrays.stream(map.getValue()).filter(para -> ObjectUtils.isNotEmpty(para)).collect(Collectors.toList()).toArray();
                parameterMap.put(map.getKey(), objects);
            }

        });
        JsonObject jsonObject = convertObjectToJson(parameterMap);
        return convertJsonToEntity(jsonObject, clazz);
    }

    /**
     * 将单个实体转换为Map
     *
     * @param entityObject :目标实体对象
     * @return the map
     * @throws Exception the exception
     */
    public static Map<String, Object> convertEntityToMap(Object entityObject) throws Exception {
        try {
            // 转换后的Map
            Map<String, Object> map = new HashMap<>();
            Class clazz = entityObject.getClass();
            Field[] fields = clazz.getDeclaredFields();

            List<String> errorList = new ArrayList<>();

            Arrays.stream(fields).forEach(field -> {
                String key = field.getName();
                Object value = ReflectionUtils.getFieldValue(entityObject, key);
                Class valueClass = value.getClass();
                if (valueClass.equals(ArrayList.class) || valueClass.equals(Collection.class) || valueClass.equals(List.class)) {
                    try {
                        value = convertListEntityToListMap((List<Object>) value);
                    } catch (Exception e) {
                        errorList.add(e.getMessage());
                    }
                }
                map.put(key, value);
            });
            if (errorList.size() > 0) {
                String errorMessage = getCombineString(errorList);
                throw new Exception(errorMessage);
            }
            return map;
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * 将listEntity转换成listMap
     *
     * @param listEntityObject the list toolsentity object
     * @return the list
     * @throws Exception the exception
     * @功能简介：将listEntity转换成listMap
     */
    public static List<Map<String, Object>> convertListEntityToListMap(List<Object> listEntityObject) throws Exception {
        try {
            List<Map<String, Object>> listMap = new ArrayList<>();
            List<String> errorList = new ArrayList<>();
            listEntityObject.forEach(entity -> {
                try {
                    Map<String, Object> map = convertEntityToMap(entity);
                    listMap.add(map);
                } catch (Exception e) {
                    errorList.add(e.getMessage());
                }
            });
            if (errorList.size() > 0) {
                String errorMessage = getCombineString(errorList);
                throw new Exception(errorMessage);
            }
            return listMap;
        } catch (Exception ex) {
            throw ex;
        }

    }

    //endregion


    //region 字符串操作 字符串数组 转化为 指定符号分隔 的字符串

    /**
     * 传入多个字符串，取得合并并且用半角逗号分隔的字符串 <p> 如：getCombineString(str1,str2,str3) 返回值 str1,str2,str3 <p> 如果 str1、str2、str3中有空字符串则去掉 如：str2为空，则 返回str1,str2
     *
     * @param <T>     the type parameter
     * @param strings the strings
     * @return the string
     */
    public static <T> String getCombineString(List<T> strings) {
        try {
            Stream<String> stream = strings.stream().filter(str -> ObjectUtils.isNotNull(str)).map(str -> str.toString());
            return getCombineString(stream);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Gets combine string.
     *
     * @param delimiter the delimiter
     * @param strings   the strings
     * @return the combine string
     */
    public static String getCombineString(String delimiter, List<String> strings) {
        try {
            return getCombineString(delimiter, strings.stream());
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Gets combine string.
     *
     * @param strings the strings
     * @return the combine string
     */
    public static String getCombineString(Stream<String> strings) {
        try {
            return getCombineString(",", strings);
        } catch (Exception ex) {
            throw ex;
        }
    }


    /**
     * Gets combine string.
     *
     * @param delimiter the delimiter
     * @param strings   the strings
     * @return the combine string
     */
    public static String getCombineString(String delimiter, Stream<String> strings) {
        try {
            return strings.collect(Collectors.joining(delimiter));
        } catch (Exception ex) {
            throw ex;
        }
    }


    /**
     * Gets combine string.
     *
     * @param delimiter the delimiter
     * @param strings   the strings
     * @return the combine string
     */
    public static String getCombineString(String delimiter, String... strings) {
        try {
            return getCombineString(delimiter, Arrays.asList(strings));
        } catch (Exception ex) {
            throw ex;
        }
    }


    //endregion

    /**
     * 获取内部编号的下一个编号，举例：001002，下一个是001003
     *
     * @param internalNumber the internal number
     * @return the next node code
     * @author：赵亮
     * @date：2018-06-21 15 :16
     */
    public static String getNextNodeCode(String internalNumber) {
        String endThreeNumber = internalNumber.substring(internalNumber.length() - 3, internalNumber.length());
        Integer nextNumber = DataSwitch.convertObjectToInteger(endThreeNumber) + 1;
        String beginNumber = internalNumber.substring(0, internalNumber.length() - 3);
        if (nextNumber < 10) {
            return beginNumber + "00" + nextNumber;
        } else if (nextNumber < 100) {
            return beginNumber + "0" + nextNumber;
        } else {
            return beginNumber + nextNumber.toString();
        }
    }


//region Json与实体转换

    /**
     * 将json格式的字符串转换成目标实体
     *
     * @param <T>   the type parameter
     * @param json  ：json格式的字符串
     * @param clazz ：实体
     * @return the t
     * @throws IllegalAccessException the illegal access exception
     * @throws InstantiationException the instantiation exception
     */
    public static <T> T convertJsonToEntity(JsonObject json, Class<T> clazz) {
        try {
            Gson gson = new Gson();
            T entity = gson.fromJson(json, clazz);
            return entity;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Convert json str to entity t.
     *
     * @param <T>   the type parameter
     * @param json  the json
     * @param clazz the clazz
     * @return the t
     * @throws Exception the exception
     */
    public static <T> T convertJsonStringToEntity(String json, Class<T> clazz) throws Exception {
        try {
            JsonObject jsonObject = convertStringToJsonObject(json);
            T t = convertJsonToEntity(jsonObject, clazz);
            return t;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 将实体对象转换成jsonObj对象
     *
     * @param obj the obj
     * @return the json object
     * @throws Exception the exception
     */
    public static JsonObject convertObjectToJson(Object obj) {
        try {
            JsonElement jsonElement = convertObjectToJsonElement(obj);
            return jsonElement == null ? null : jsonElement.getAsJsonObject();
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * Convert string to json json object.
     *
     * @param value the value
     * @return the json object
     * @throws Exception the exception
     */
    public static JsonObject convertStringToJsonObject(String value) throws Exception {
        try {
            return convertStringToJsonElement(value).getAsJsonObject();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Convert string to json json object.
     *
     * @param value the value
     * @return the json object
     * @throws Exception the exception
     */
    public static JsonElement convertStringToJsonElement(String value) throws Exception {
        try {
            if (null == value || "".equals(value)) {
                return new JsonObject();
            }
            JsonParser parser = new JsonParser();
            JsonElement jObject = parser.parse(value);
            return jObject;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Convert object to json json object.
     *
     * @param obj              the obj
     * @param isSerializeNulls the is serialize nulls
     * @return the json object
     */
    public static JsonObject convertObjectToJson(Object obj, boolean isSerializeNulls) {
        try {
            JsonElement jsonElement = convertObjectToJsonElement(obj, isSerializeNulls);
            return jsonElement == null ? null : jsonElement.getAsJsonObject();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Convert object to json element json element.
     *
     * @param value the value
     * @return the json element
     */
    public static JsonElement convertObjectToJsonElement(Object value) {
        try {
            JsonElement jsonElement = convertObjectToJsonElement(value, true);
            return jsonElement;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Convert object to json element json element.
     *
     * @param value            the value
     * @param isSerializeNulls the is serialize nulls
     * @return the json element
     */
    public static JsonElement convertObjectToJsonElement(Object value, boolean isSerializeNulls) {
        try {
            if (value == null) {
                return null;
            }
            GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat(DateFormatEnum.YYYY_MM_DD_HH_MM_SS.getValue());
            if (isSerializeNulls) {
                gsonBuilder = gsonBuilder.serializeNulls();
            }
            Gson gson = gsonBuilder.create();
            String jsonString = gson.toJson(value);
            JsonElement jsonElement = new JsonParser().parse(jsonString);
            return jsonElement.getClass().equals(JsonNull.class) ? null : jsonElement;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Convert list map to json array json array.
     *
     * @param maps the maps
     * @return the json array
     */
    public static JsonArray convertListMapToJsonArray(List<Map<String, Object>> maps) {
        try {
            return convertObjectToJsonElement(maps).getAsJsonArray();
        } catch (Exception ex) {
            throw ex;
        }
    }


    /**
     * Convert jsonArrayStr to list list. 将json格式的字符串转换成List对象
     *
     * @param jsonArrayStr the json str
     * @return the list
     * @throws Exception the exception
     */
    public static List<Object> convertJsonArrayStrToList(String jsonArrayStr) throws Exception {
        if (StringUtils.isEmpty(jsonArrayStr)) {
            return null;
        } else {
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = convertStringToJsonElement(jsonArrayStr).getAsJsonArray();
            return convertJsonArrayToList(jsonArray);
        }
    }

    /**
     * Convert json str to map map. ：将json格式的字符串转换成Map对象
     *
     * @param jsonObjectStr the json str
     * @return the map
     * @throws Exception the exception
     */
    public static Map<String, Object> convertJsonObjectStrToMap(String jsonObjectStr) throws Exception {
        if (StringUtils.isEmpty(jsonObjectStr)) {
            return null;
        } else {
            JsonObject jsonObj = convertStringToJsonObject(jsonObjectStr);
            return convertJsonToMap(jsonObj);
        }
    }

    /**
     * Convert json obj to map map.
     *
     * @param json the json
     * @return the map
     */
    public static Map<String, Object> convertJsonToMap(JsonObject json) {
        Map<String, Object> map = new HashMap<>();
        Set<Map.Entry<String, JsonElement>> entrySet = json.entrySet();
        entrySet.forEach(stringJsonElementEntry -> {
            String key = stringJsonElementEntry.getKey();
            JsonElement value = stringJsonElementEntry.getValue();
            if (value instanceof JsonArray) {
                map.put(key, convertJsonArrayToList(value.getAsJsonArray()));
            } else if (value instanceof JsonObject) {

                map.put(key, convertJsonToMap(value.getAsJsonObject()));
            } else {
                map.put(key, value.toString().replaceAll("\"", ""));
            }
        });
        return map;
    }

    /**
     * 将JSONArray对象转换成List集合
     *
     * @param jsonArray the json
     * @return the list
     */
    private static List<Object> convertJsonArrayToList(JsonArray jsonArray) {
        List<Object> list = new ArrayList<>();
        jsonArray.forEach(jsonElement -> {
            if (jsonElement instanceof JsonArray) {
                list.add(convertJsonArrayToList(jsonElement.getAsJsonArray()));
            } else if (jsonElement instanceof JsonObject) {
                list.add(convertJsonToMap(jsonElement.getAsJsonObject()));
            } else if (jsonElement instanceof JsonPrimitive) {
                list.add(jsonElement.getAsJsonPrimitive());
            } else {
                list.add(jsonElement);
            }
        });
        return list;
    }

    /**
     * Convert list t to json object json array.
     *
     * @param <T>  the type parameter
     * @param list the list
     * @return the json array
     */
    public static <T> JsonArray convertListToJsonArray(List<T> list) {
        try {
            JsonElement element = convertListToJsonArray(list, null);
            return element.getAsJsonArray();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Convert list t to json object json array.
     *
     * @param <T>        the type parameter
     * @param list       the list
     * @param dateFormat the date format
     * @return the json array
     */
    public static <T> JsonArray convertListToJsonArray(List<T> list, DateFormatEnum dateFormat) {
        try {
            JsonElement element = convertListToJsonElement(list, dateFormat);
            return element.getAsJsonArray();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Convert list to json element json element.
     *
     * @param list the list
     * @return the json element
     */
    public static JsonElement convertListToJsonElement(List list) {
        return convertListToJsonElement(list, null);
    }

    /**
     * Convert list to json element json element.
     *
     * @param list       the list
     * @param dateFormat the date format
     * @return the json element
     */
    public static JsonElement convertListToJsonElement(List list, DateFormatEnum dateFormat) {
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            dateFormat = dateFormat == null ? DateFormatEnum.YYYY_MM_DD_HH_MM_SS : dateFormat;
            gsonBuilder.setDateFormat(dateFormat.getValue());
            Gson gson = gsonBuilder.serializeNulls().create();
            return gson.toJsonTree(list);
        } catch (Exception ex) {
            throw ex;
        }
    }


    //endregion

}
