package com.liuping123.jsoncheck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.liuping123.json.JsonExaminer;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            JsonExaminer beanJsonObjectCheck = new JsonExaminer();
            JSONObject json = new JSONObject("{\"a\":\"aaaa\",\"name\":\"myname\",\"age\":6,\"c_age\":7,\"loginFlg\":false,\"cLoginFlg\":true,\"map\":{\"1\":\"1\",\"2\":\"2\",\"3\":\"3\"},\"test\":[\"1\",\"2\",\"3\"]}");
            beanJsonObjectCheck.check(Test.class, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
