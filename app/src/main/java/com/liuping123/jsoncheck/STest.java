package com.liuping123.jsoncheck;

import com.liuping123.json.JsonAnnotation;

/**
 * Created by ping on 16/7/3.
 */
public class STest {
    @JsonAnnotation(canNull = false)
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
