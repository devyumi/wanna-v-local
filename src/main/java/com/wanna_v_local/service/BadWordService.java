package com.wanna_v_local.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class BadWordService implements BadWord {
    private final Map<String, Pattern> badWordMap = new HashMap<>();

    /**
     * delimiter Regex으로 변환 후
     * bad word + delimiter 형식 map에 저장
     * ex) 멍 delimiter 청 delimiter 이
     */
    public void createBadWordMap() {
        StringBuilder delimiterRegex = new StringBuilder("[");
        for (String delimiter : delimiters) {
            delimiterRegex.append(Pattern.quote(delimiter));
        }
        String delimiter = delimiterRegex.append("]*").toString();

        for (String word : badWords) {
            String[] chars = word.split("");
            badWordMap.put(word, Pattern.compile(String.join(delimiter, chars)));
        }
    }

    /**
     * bad word 변환
     * @param input
     * @return
     */
    public String changeBadWord(String input) {
        createBadWordMap();

        for (Map.Entry<String, Pattern> entry : badWordMap.entrySet()) {
            String word = entry.getKey();
            Pattern delimiter = entry.getValue();

            if (word.length() == 1){
                input = input.replace(word, replacement);
            }
            input = delimiter.matcher(input).replaceAll(matchedWord ->
                    replacement.repeat(matchedWord.group().length()));
        }
        return input;
    }
}
