package com.moazmahmud;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateConverterTest {
    private static DateKey[][] engToBngDateArray;

    private static final Gson gson = new Gson();

    @BeforeAll
    public static void setupAll() throws IOException {
        String jsonDataFileName = "../data-collection/english-bangla-date-mapping-2020.json";
        String jsonDataStr = Files.readString(Paths.get(jsonDataFileName));
        engToBngDateArray = gson.fromJson(
                jsonDataStr,
                DateKey[][].class
        );
    }

    @TestFactory
    Stream<DynamicTest> shouldTestAllDaysOfEnglishYear() {
        return Arrays.stream(engToBngDateArray)
                     .map(this::getDynamicTest);
    }

    DynamicTest getDynamicTest(DateKey[] engBngPair) {
        return DynamicTest.dynamicTest(
                engBngPair[0].toString(),
                () -> shouldMatchExpectedBanglaDate(engBngPair[0], engBngPair[1])
        );
    }

    public static void shouldMatchExpectedBanglaDate(DateKey englishDate, DateKey expectedBanglaDate) {
        assertEquals(expectedBanglaDate, DateConverter.getBanglaDate(englishDate));
    }
}