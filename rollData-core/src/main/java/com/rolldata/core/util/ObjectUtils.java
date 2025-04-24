package com.rolldata.core.util;

import com.rolldata.core.common.exception.AssetLoadException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * 
 * @Title: ObjectUtils
 * @Description: 对象工具类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
public final class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {

	/**
	 * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null)
			return true;

		if (obj instanceof CharSequence)
			return ((CharSequence) obj).length() == 0;

		if (obj instanceof Collection)
			return ((Collection) obj).isEmpty();

		if (obj instanceof Map)
			return ((Map) obj).isEmpty();

		if (obj instanceof Object[]) {
			Object[] object = (Object[]) obj;
			if (object.length == 0) {
				return true;
			}
			boolean empty = true;
			for (int i = 0; i < object.length; i++) {
				if (!isNullOrEmpty(object[i])) {
					empty = false;
					break;
				}
			}
			return empty;
		}
		return false;
	}

	/**
	 * 判断一个类是否为基本数据类型。
	 * 
	 * @param clazz
	 *            要判断的类。
	 * @return true 表示为基本数据类型。
	 */
	public static boolean isBaseDataType(Class<?> clazz) {
		Boolean isBaseType = (clazz.equals(String.class) || clazz.equals(Integer.class) || clazz.equals(Byte.class)
				|| clazz.equals(Long.class) || clazz.equals(Double.class) || clazz.equals(Float.class)
				|| clazz.equals(Character.class) || clazz.equals(Short.class) || clazz.equals(BigDecimal.class)
				|| clazz.equals(BigInteger.class) || clazz.equals(Boolean.class) || clazz.equals(Date.class)
				|| clazz.isPrimitive());
		return isBaseType;
	}

	/**
	 * 判断是否相等
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEquals(Object object1, Object object2, String[] fields) {
		for (String fieldName : fields) {
			Object object1FieldVal = Reflections.getFieldValue(object1, fieldName);
			Object object2FieldVal = Reflections.getFieldValue(object2, fieldName);
			if (object1FieldVal == null || !object1FieldVal.equals(object2FieldVal)) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

    /**
     * 断言空抛异常
     *
     * @param o            判断对象
     * @param errorMessage 错误信息
     */
    public static void assertNotNull(Object o, String errorMessage) {
        if (o == null) {
            throw new AssetLoadException(errorMessage);
        }
    }

    /**
     * 参数列表中有一个为null,返回false
     *
     * @param o
     * @return
     */
    public static boolean judgeNotNull(Object... o) {
        boolean fl = true;
        for (Object object : o) {
            if (object == null) {
                fl = false;
                break;
            }
        }
        return fl;
    }

    /**
     * 判断集合非空并size大于0
     *
     * @param o
     * @return
     */
    public static boolean assertCollectionNotNull(Object o, String errorMessage) {
        if (o == null) {
            throw new AssetLoadException(errorMessage);
        }
        Object object = Reflections.invokeMethod(o, "size", new Class[]{}, new Object[]{});
        if ("0".equals(object)) {
            throw new AssetLoadException(errorMessage);
        }
        return true;
    }

//	public static void main(String[] args) {
//		User testOne = new User();
//		testOne.setId("one");
//		testOne.setEmail("502079461@qq.com");
//		User testTwo = new User();
//		testTwo.setId("one");
//		testTwo.setEmail("502079161@qq.com");
//		String[] fields = { "id", "email" };
//		if (isEquals(testOne, testTwo, fields)) {
//			System.out.println("相等");
//		} else {
//			System.out.println("不相等");
//		}
//	}
}
