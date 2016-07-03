package com.liuping123.jsoncheck;

import com.liuping123.json.JsonAnnotation;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ping on 16/7/1.
 */
public class Test extends STest {
    @JsonAnnotation(canNull = false)
    private String name;
    @JsonAnnotation(canNull = false)
    private int age;
    @JsonAnnotation(canNull = false, serializedName = "c_age")
    private Integer cAge;
    @JsonAnnotation(canNull = false)
    private Boolean loginFlg;
    @JsonAnnotation(canNull = false)
    private boolean cLoginFlg;
    @JsonAnnotation(canNull = false)
    private HashMap<String, String> map = new HashMap<>();
    @JsonAnnotation(canNull = false, classPath = "java.lang.String", serializedName = "test")
    private ArrayList<String> list = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Integer getcAge() {
        return cAge;
    }

    public void setcAge(Integer cAge) {
        this.cAge = cAge;
    }

    public Boolean getLoginFlg() {
        return loginFlg;
    }

    public void setLoginFlg(Boolean loginFlg) {
        this.loginFlg = loginFlg;
    }

    public boolean iscLoginFlg() {
        return cLoginFlg;
    }

    public void setcLoginFlg(boolean cLoginFlg) {
        this.cLoginFlg = cLoginFlg;
    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }
}
