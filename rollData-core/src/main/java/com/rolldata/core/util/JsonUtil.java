package com.rolldata.core.util;

import com.google.gson.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

/**
 *
 * @Title:JsonUtil
 * @Description:JSON操作工具类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2016-05-03
 * @version V1.0
 */
public class JsonUtil {
	public JsonUtil() {
		super();
	}

	/**
	 * 格式化对象为Json
	 *
	 * @param object
	 * @return
	 */
	public static String format(Object object) {
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().setLenient()
				.create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(gson.toJson(object));
		String prettyJsonString = gson.toJson(je);
		// 简单处理function
		String[] lines = prettyJsonString.split("\n");
		lines = replaceFunctionQuote(lines);
		StringBuilder stringBuilder = new StringBuilder();
		for (String line : lines) {
			stringBuilder.append(line);
		}
		return stringBuilder.toString();
	}

	/**
	 * 格式化对象为Json
	 * 	不处理function
	 * @param object
	 * @return
	 */
	public static String formatNoFunction(Object object) {
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping()
				.create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(gson.toJson(object));
		String prettyJsonString = gson.toJson(je);
		// 简单处理function
		String[] lines = prettyJsonString.split("\n");
		StringBuilder stringBuilder = new StringBuilder();
		for (String line : lines) {
			stringBuilder.append(line);
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 格式化对象为Json
	 *
	 * @param object
	 * @return
	 */
	public static String prettyFormat(Object object) {
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping()
				.create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(gson.toJson(object));
		String prettyJsonString = gson.toJson(je);
		// 简单处理function
		String[] lines = prettyJsonString.split("\n");
		lines = replaceFunctionQuote(lines);
		StringBuilder stringBuilder = new StringBuilder();
		for (String line : lines) {
			stringBuilder.append(line + "\n");
		}
		return stringBuilder.toString();
	}

	/**
	 * 处理字符串中的function和(function(){})()，除{}中的代码外，其他地方不允许有空格
	 *
	 * @param lines
	 * @return
	 */
	public static String[] replaceFunctionQuote(String[] lines) {
		boolean function = false;
		boolean immediately = false;
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i].trim();
			if (!function && line.contains("\"function")) {
				function = true;
				line = line.replaceAll("\"function", "function");
			}
			if (function && line.contains("}\"")) {
				function = false;
				line = line.replaceAll("\\}\"", "\\}");
			}

			if (!immediately && line.contains("\"(function")) {
				immediately = true;
				line = line.replaceAll("\"\\(function", "\\(function");
			}
			if (immediately && line.contains("})()\"")) {
				immediately = false;
				line = line.replaceAll("\\}\\)\\(\\)\"", "\\}\\)\\(\\)");
			}
			lines[i] = line;
		}
		return lines;
	}

	/**
	 * 输出Json
	 *
	 * @param object
	 * @return
	 */
	public static void print(Object object) {
		System.out.println(format(object));
	}

	/**
	 * 输出Json
	 *
	 * @param object
	 * @return
	 */
	public static void printPretty(Object object) {
		System.out.println(prettyFormat(object));
	}

	/***
	 * 将List对象转为JSON文本
	 */
	public static <T> String toJSONString(List<T> list) {
		// JSONArray jsonArray = JSONArray.fromObject(list);
		// return jsonArray.toString();
		JSONObject jsonobject = new JSONObject();
		for (int i = 0; i < list.size(); i++) {
			Object object = list.get(i);
			// 截取类名
			String[] arrStrings = object.getClass().toString().split("\\.");
			String className = arrStrings[arrStrings.length - 1];
			// 首字母小写
			jsonobject.put(className.replaceFirst(className.substring(0, 1),
					className.substring(0, 1).toLowerCase()), object);
		}
		return jsonobject.toString();
	}

	/**
	 * 从一个JSON 对象字符格式中得到一个java对象
	 *
	 * @param jsonString
	 * @param pojoCalss
	 * @return
	 */
	public static Object getObject4JsonString(String jsonString, Class pojoCalss) {
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss);
		return pojo;
	}

	/**
	 * 从一个JSON 对象字符格式中得到一个java对象
	 *
	 * @param jsonString
	 * @param pojoCalss
	 * @param classMap
	 *            json串中嵌套的实体类 map.put("id",Eneity.class);
	 * @return
	 */
	public static Object getObject4JsonString(String jsonString,
			Class pojoCalss, Map classMap) {
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss, classMap);
		return pojo;
	}

	/**
	 * 从json HASH表达式中获取一个map，改map支持嵌套功能
	 *
	 * @param jsonString
	 * @return
	 */
	public static Map getMap4Json(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();

		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}

		return valueMap;
	}

	/**
	 * 从json数组中得到相应java数组
	 *
	 * @param jsonString
	 * @return
	 */
	public static Object[] getObjectArray4Json(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();

	}

	/**
	 * 从json对象集合表达式中得到一个java对象列表
	 *
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	public static List getList4Json(String jsonString, Class pojoClass) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;

		List list = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {

			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			list.add(pojoValue);

		}
		return list;

	}

	/**
	 * 从json数组中解析出java字符串数组
	 *
	 * @param jsonString
	 * @return
	 */
	public static String[] getStringArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		String[] stringArray = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			stringArray[i] = jsonArray.getString(i);

		}

		return stringArray;
	}

	/**
	 * 从json数组中解析出javaLong型对象数组
	 *
	 * @param jsonString
	 * @return
	 */
	public static Long[] getLongArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Long[] longArray = new Long[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			longArray[i] = jsonArray.getLong(i);

		}
		return longArray;
	}

	/**
	 * 从json数组中解析出java Integer型对象数组
	 *
	 * @param jsonString
	 * @return
	 */
	public static Integer[] getIntegerArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Integer[] integerArray = new Integer[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			integerArray[i] = jsonArray.getInt(i);

		}
		return integerArray;
	}

	/**
	 * 从json数组中解析出java Integer型对象数组
	 *
	 * @param jsonString
	 * @return
	 */
	public static Double[] getDoubleArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Double[] doubleArray = new Double[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			doubleArray[i] = jsonArray.getDouble(i);

		}
		return doubleArray;
	}

	/**
	 * 将java对象转换成json字符串
	 *
	 * @param javaObj
	 * @return
	 */
	public static String getJsonString4JavaPOJO(Object javaObj) {

		JSONObject json;
		json = JSONObject.fromObject(javaObj);
		return json.toString();

	}
    
    /**
     * 将json字符串转换成java对象
     *
     * @param javaObj
     * @return
     */
	public static <T> T fromJson(String jsonString, Class pojoClass) {
		String os = System.getProperty("os.name").toLowerCase();
		if(os.startsWith("windows")) {
			return (T) new Gson().fromJson(jsonString, pojoClass);
		}else {
			//linux下存在时间格式转换异常，特做以下处理
			try {
				return (T) new Gson().fromJson(jsonString, pojoClass);
			} catch (Exception e) {
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
				return (T) gson.fromJson(jsonString, pojoClass);
			}
		}
    }
	
	/**
	 * json转list 泛型对象
	 * <p>getList4Json 这个方法中,把字符串[{\"value\":\"姓名\",\"type\":\"text\"}]的也转成对象了</p>
	 * @param jsonString
	 * @param pojoClass
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> fromJsonToList (String jsonString, Class<T> pojoClass) {
	
	    List<T> result = new ArrayList<T>();
	    Gson gson = new GsonBuilder().create();
	    JsonParser parser = new JsonParser();
	    JsonArray Jarray = parser.parse(jsonString).getAsJsonArray();
	    for (JsonElement jsonElement : Jarray) {
		    T pojo = gson.fromJson(jsonElement, pojoClass);
		    result.add(pojo);
	    }
	    return result;
    }
}
