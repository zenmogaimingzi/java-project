package com.example.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberExtractor {
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("(\\+86|86)?(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7}");

    public static void extractPhoneNumbers(String input) {
        Matcher matcher = PHONE_NUMBER_PATTERN.matcher(input);

        while (matcher.find()) {
            System.out.println("Phone number found: " + matcher.group(0));
        }
    }

    public static void main(String[] args) {
        String input = "我的手机号分别是915639187651和13491087761";
        extractPhoneNumbers(input);
    }
}
