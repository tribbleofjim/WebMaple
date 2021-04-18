package org.webmaple.admin;

import java.util.HashMap;

/**
 * @author lyifee
 * on 2021/4/18
 */
public class Questions {
    public static HashMap<Character, String> questions = new HashMap<>();
    static {
        questions.put('0', "你的生日是？");
        questions.put('1', "你的小学校名是？");
        questions.put('2', "你最喜欢的明星是谁？");
        questions.put('3', "你的家乡在什么地方？");
        questions.put('4', "你最喜欢吃的食物是什么？");
    }
}
