package com.example.myapplication2.dialog;

/**
 * 要传递的数据
 */

public class EventBusMsg {

    private String inputText;

    public EventBusMsg(String inputText) {
        this.inputText = inputText;
    }

    public String getInputText() {
        return inputText;
    }
}
