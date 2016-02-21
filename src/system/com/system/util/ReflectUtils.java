package com.system.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 反射实现工具类
 * @author Administrator
 *
 */
public class ReflectUtils {
	/**
	 * 将源对象当中的非空属性转移到目标对象当中的对应属性
	 * (两者必须是相同类型的对象)
	 * @param src 源对象
	 * @param dest 目标对象
	 * @throws Exception 源对象和目标对象的类型不匹配/反射调用的其他异常
	 */
	public static void transferFields(Object src,Object dest)
				throws Exception{
		Class<?> clz = src.getClass();
		if(!clz.equals(dest.getClass())){
			throw new IllegalArgumentException("源对象和目标对象的类型不匹配");
		}
		Field[] fields = clz.getDeclaredFields();
		int serialVersionCode = Modifier.PRIVATE + Modifier.STATIC + Modifier.FINAL;
		for(Field field : fields){
			if(field.getModifiers() == serialVersionCode){
				continue;
			}
			Method getMethod = clz.getMethod("get" + firstCharToUpper(field.getName()));
			Object value = getMethod.invoke(src);
			if(value != null){
				Method setMethod = clz.getMethod("set" + firstCharToUpper(field.getName()),value.getClass());
				setMethod.invoke(dest, value);
			}
		}
	}
	/**
	 * 获取对象中指定属性的值
	 * @param item 对象
	 * @param fieldName 属性名称
	 * @return 属性的值
	 */
	public static Object getItemField(Object item,String fieldName){
		Object value = null;
		try{
			Class<?> clz = item.getClass();
			Method method = clz.getMethod("get" + firstCharToUpper(fieldName));
			value = (String) method.invoke(item);
		} catch (Exception e){
			e.printStackTrace();
		}
		return value;
	}
	/**
	 * 字符串首字母转换为大写
	 * @param str
	 * @return
	 */
	protected static String firstCharToUpper(String str){
		StringBuilder sb = new StringBuilder(str);
		char ch = Character.toUpperCase(sb.charAt(0));
		sb.setCharAt(0, ch);
		return sb.toString();
	}
}
