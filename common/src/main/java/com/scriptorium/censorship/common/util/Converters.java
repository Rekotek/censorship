package com.scriptorium.censorship.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * Created by taras on 2018-11-12.
 */

public final class Converters {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("d MMM yyyy HH:mm", Locale.getDefault());

    private Converters() { }

    public static int toInt(String s) {
        int result;
        try {
            result = parseInt(s);
        } catch (NumberFormatException e) {
            result = 0;
        }
        return result;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    private enum CHAR_FORM {
        YI_Y,
        E_YE_EE_YO
    }

    private static final List<Character> yi_type = new ArrayList<>(3);
    private static final List<Character> ye_type = new ArrayList<>(4);

    static {
        yi_type.add('И');
        yi_type.add('І');
        yi_type.add('Ы');

        ye_type.add('Е');
        ye_type.add('Є');
        ye_type.add('Э');
        ye_type.add('Ё');
    }

    public static Set<String> getAllVariants(String word) {
        String initialForm = word.toUpperCase();
        Set<String> resultSet = new HashSet<>();
        resultSet.add(initialForm);

        Map<Integer, CHAR_FORM> initialMap = composePositions(initialForm);

        generateWordSet(initialForm, initialMap, resultSet);

        return resultSet;
    }

    private static Map<Integer, CHAR_FORM> composePositions(String initialWord) {
        Map<Integer, CHAR_FORM> map = new HashMap<>();
        for (int idx = 0; idx < initialWord.length(); idx++) {
            char charAt = initialWord.charAt(idx);
            if (yi_type.contains(charAt)) {
                map.put(idx, CHAR_FORM.YI_Y);
            } else if (ye_type.contains(charAt)) {
                map.put(idx, CHAR_FORM.E_YE_EE_YO);
            }
        }
        return map;
    }

    private static void generateWordSet(String currentWord, Map<Integer, CHAR_FORM> map, Set<String> resultSet) {
        for (Map.Entry<Integer, CHAR_FORM> entry : map.entrySet()) {
            Set<String> allFormsAtIndex = getAllFormsAtIndex(currentWord, entry.getKey(), entry.getValue());
            resultSet.addAll(allFormsAtIndex);
            Map<Integer, CHAR_FORM> newMap = new HashMap<>(map);
            newMap.remove(entry.getKey());
            if (newMap.isEmpty()) {
                return;
            }
            allFormsAtIndex.forEach(s -> generateWordSet(s, newMap, resultSet));
        }
    }

    private static Set<String> getAllFormsAtIndex(String initialForm, int index, CHAR_FORM char_form) {
        StringBuffer constructedWord = new StringBuffer(initialForm);
        Set<String> resultSet = new HashSet<>(4);
        if (CHAR_FORM.YI_Y == char_form) {
            yi_type.forEach(character -> addModifiedWord(index, character, constructedWord, resultSet));
        } else if (CHAR_FORM.E_YE_EE_YO == char_form) {
            ye_type.forEach(character -> addModifiedWord(index, character, constructedWord, resultSet));
        }

        return resultSet;
    }

    private static void addModifiedWord(int index, Character character, StringBuffer constructedWord, Set<String> resultSet) {
        constructedWord.setCharAt(index, character);
        resultSet.add(constructedWord.toString());
    }
}
