package com.scriptorium.censorship.common.util;

import org.junit.Test;

import java.util.Set;

import static com.scriptorium.censorship.common.util.Converters.getAllVariants;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by taras on 2018-11-15.
 */

public class ConvertersTest {
    private static final String simpleWord = "Право";
    private static final String wordWith2Variants = "Приво";

    @Test
    public void getSimpleWordWithoutVariants() {
        Set<String> resultSet = getAllVariants(simpleWord);
        assertEquals(1, resultSet.size());
        assertTrue(resultSet.contains(simpleWord.toUpperCase())) ;
    }

    @Test
    public void getWordWith3Variants() {
        Set<String> resultSet = getAllVariants(wordWith2Variants);
        assertEquals(3, resultSet.size());
        assertTrue(resultSet.contains(wordWith2Variants.toUpperCase())) ;
        assertTrue(resultSet.contains("ПРІВО")) ;
    }

    @Test
    public void getTippingVariants() {
        Set<String> resultSet = getAllVariants("Типпинг");
        assertTrue(resultSet.contains("ТИППИНГ")) ;
        assertTrue(resultSet.contains("ТІППИНГ")) ;
        assertTrue(resultSet.contains("ТІППІНГ")) ;
        assertTrue(resultSet.contains("ТИППІНГ")) ;
        assertTrue(resultSet.contains("ТЫППІНГ")) ;
    }

    @Test
    public void getComplexWord_1() {
        Set<String> resultSet = getAllVariants("Типпінговиче");
        assertEquals(108, resultSet.size());
        assertTrue(resultSet.contains("ТИППІНГОВИЧЕ")) ;
        assertTrue(resultSet.contains("ТИППИНГОВІЧЕ")) ;
        assertTrue(resultSet.contains("ТИППИНГОВІЧЭ")) ;
        assertTrue(resultSet.contains("ТИППИНГОВІЧЕ")) ;
        assertTrue(resultSet.contains("ТИППИНГОВІЧЄ")) ;
        assertTrue(resultSet.contains("ТИППИНГОВИЧЭ")) ;
        assertTrue(resultSet.contains("ТИППИНГОВИЧЕ")) ;
        assertTrue(resultSet.contains("ТИППИНГОВІЧЄ")) ;
        assertTrue(resultSet.contains("ТИППІНГОВИЧЕ")) ;
        assertTrue(resultSet.contains("ТІППІНГОВИЧЕ")) ;
        assertTrue(resultSet.contains("ТІППИНГОВІЧЕ")) ;
        assertTrue(resultSet.contains("ТІППИНГОВІЧЭ")) ;
        assertTrue(resultSet.contains("ТІППИНГОВІЧЕ")) ;
        assertTrue(resultSet.contains("ТІППИНГОВІЧЄ")) ;
        assertTrue(resultSet.contains("ТІППИНГОВИЧЭ")) ;
        assertTrue(resultSet.contains("ТІППИНГОВИЧЕ")) ;
        assertTrue(resultSet.contains("ТІППИНГОВІЧЄ")) ;
        assertTrue(resultSet.contains("ТІППІНГОВИЧЕ")) ;

    }
}