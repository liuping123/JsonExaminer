package com.liuping123.json;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ping on 16/7/1.
 */
public class JsonExaminer {
    public String check(Class c, JSONObject jsonObject) {
        String logStr = c.getName() + "\n";
        ArrayList<Field> fields = new ArrayList<>();
        getFields(fields, c);
        logStr = getDiff(jsonObject, logStr, fields);
        for (Field f : fields) {
            if (f.isAnnotationPresent(JsonAnnotation.class)) {
                f.setAccessible(true);
                if (jsonObject.has(getFieldName(f))) {
                    JSONObject jsonObj = jsonObject.optJSONObject(getFieldName(f));
                    if (jsonObj != null) {
                        if (!f.getType().getName().equals("java.util.HashMap") && !f.getType().getName().equals("java.util.Map")) {
                            logStr += check(f.getType(), jsonObj);
                        }
                    } else {
                        JSONArray ja = jsonObject.optJSONArray(getFieldName(f));
                        if (ja != null) {
                            logStr = checkJa(logStr, f, ja);
                        } else {
                            logStr = checkNormalType(jsonObject, logStr, f);
                        }
                    }
                }
            }
        }
        return logStr;
    }

    @NonNull
    private ArrayList<Field> getFields(ArrayList<Field> fieldList, Class c) {
        Class cls = c;
        String className = c.toString();
        ArrayList<Field> fieldArrayList = new ArrayList<>();
        if (!className.equals("class java.lang.Object")) {
            Field[] fields = cls.getDeclaredFields();
            Collections.addAll(fieldArrayList, fields);
            fieldList.addAll(fieldArrayList);
            getFields(fieldList, cls.getSuperclass());
        }
        return fieldList;
    }

    @NonNull
    private String getDiff(JSONObject jsonObject, String logStr, ArrayList<Field> fields) {
        ArrayList<String> fieldNames = new ArrayList<>();
        ArrayList<String> jsonNames = new ArrayList<>();
        Iterator it = jsonObject.keys();
        while (it.hasNext()) {
            jsonNames.add(it.next().toString());
        }
        for (int i = 0; i < fields.size(); i++) {
            fieldNames.add(getFieldName(fields.get(i)));
        }
        logStr += " json 未申明字段[";
        for (int i = 0; i < jsonNames.size(); i++) {
            boolean flg = false;
            for (int j = 0; j < fieldNames.size(); j++) {
                if (jsonNames.get(i).equals(fieldNames.get(j))) {
                    flg = true;
                    break;
                }
            }
            if (!flg) {
                logStr += jsonNames.get(i) + ",";
            }
        }
        logStr += " ]\n field 未返回字段[";
        for (int i = 0; i < fieldNames.size(); i++) {
            boolean flg = false;
            for (int j = 0; j < jsonNames.size(); j++) {
                if (fieldNames.get(i).equals(jsonNames.get(j))) {
                    flg = true;
                    break;
                }
            }
            if (!flg) {
                logStr += fieldNames.get(i) + ",";
            }
        }
        logStr += "]\n";
        return logStr;
    }

    private String checkNormalType(JSONObject jsonObject, String logStr, Field f) {
        String type = f.getType().toString();
        String name = getFieldName(f);
        switch (type) {
            case "class java.lang.String":
                try {
                    jsonObject.getString(name);
                } catch (JSONException e) {
                    logStr += name + " 不存在或类型不正确 class java.lang.String.class\n";
                }
                break;
            case "int":
            case "class java.lang.Integer":
                try {
                    jsonObject.getInt(name);
                } catch (JSONException e) {
                    logStr += name + " 不存在或类型不正确 class java.lang.Integer.class or int\n";
                }
                break;
            case "double":
            case "class java.lang.Double":
                try {
                    jsonObject.getDouble(name);
                } catch (JSONException e) {
                    logStr += name + " 不存在或类型不正确 class java.lang.Double.class or double\n";
                }
                break;
            case "long":
            case "class java.lang.Long":
                try {
                    jsonObject.getDouble(name);
                } catch (JSONException e) {
                    logStr += name + " 不存在或类型不正确 class java.lang.Long.class or long\n";
                }
                break;
            case "boolean":
            case "class java.lang.Boolean":
                try {
                    jsonObject.getBoolean(name);
                } catch (JSONException e) {
                    logStr += name + " 不存在或类型不正确 class java.lang.Boolean.class or boolean\n";
                }
                break;
            case "short":
            case "class java.lang.Short":
                try {
                    jsonObject.getInt(name);
                } catch (JSONException e) {
                    logStr += name + " 不存在或类型不正确 class java.lang.Short.class or short\n";
                }
                break;
            default:
                break;
        }
        return logStr;
    }

    private String checkJa(String logStr, Field f, JSONArray ja) {
        if (f.getType().equals(ArrayList.class) || f.getType().equals(List.class)) {
            logStr += f.getName();
            Annotation[] animations = f.getDeclaredAnnotations();
            for (int i = 0; i < animations.length; i++) {
                if (animations[i] instanceof JsonAnnotation) {
                    String classPath = ((JsonAnnotation) animations[i]).classPath();
                    if (TextUtils.isEmpty(classPath)) {
                        continue;
                    }
                    try {
                        Class cls = Class.forName(classPath);
                        for (int j = 0; j < ja.length(); j++) {
                            switch (cls.toString()) {
                                case "class java.lang.String":
                                    try {
                                        ja.getString(j);
                                    } catch (JSONException e) {
                                        logStr += ja.toString() + " 数组类型不正确 class java.lang.String.class\n";
                                    }
                                    break;
                                case "int":
                                case "class java.lang.Integer":
                                    try {
                                        ja.getInt(j);
                                    } catch (JSONException e) {
                                        logStr += ja.toString() + " 数组类型不正确 class java.lang.Integer.class or int\n";
                                    }
                                    break;
                                case "double":
                                case "class java.lang.Double":
                                    try {
                                        ja.getDouble(j);
                                    } catch (JSONException e) {
                                        logStr += ja.toString() + " 数组类型不正确 class java.lang.Double.class or double\n";
                                    }
                                    break;
                                case "long":
                                case "class java.lang.Long":
                                    try {
                                        ja.getLong(j);
                                    } catch (JSONException e) {
                                        logStr += ja.toString() + " 数组类型不正确 class java.lang.Long.class or long\n";
                                    }
                                    break;
                                case "boolean":
                                case "class java.lang.Boolean":
                                    try {
                                        ja.getBoolean(j);
                                    } catch (JSONException e) {
                                        logStr += ja.toString() + " 数组类型不正确 class java.lang.Boolean.class or boolean\n";
                                    }
                                    break;
                                case "short":
                                case "class java.lang.Short":
                                    try {
                                        ja.getInt(j);
                                    } catch (JSONException e) {
                                        logStr += ja.toString() + " 数组类型不正确 class java.lang.Short.class or short\n";
                                    }
                                    break;
                                default:
                                    JSONObject jo = null;
                                    try {
                                        jo = ja.getJSONObject(j);
                                    } catch (JSONException e) {
                                        logStr += ja.toString() + " 类型不正确 " + classPath + "\n";
                                    }
                                    check(cls, jo);
                                    break;
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return logStr;
    }

    private String getFieldName(Field field) {
        Annotation[] animations = field.getDeclaredAnnotations();
        for (int i = 0; i < animations.length; i++) {
            if (animations[i] instanceof JsonAnnotation) {
                String serializedName = ((JsonAnnotation) animations[i]).serializedName();
                if (TextUtils.isEmpty(serializedName)) {
                    return field.getName();
                }
                return ((JsonAnnotation) animations[i]).serializedName();
            }
        }
        return field.getName();
    }
}
