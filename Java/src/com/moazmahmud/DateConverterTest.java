package com.moazmahmud;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void shouldConvertEnglishDatesToBangla() {
        Arrays.stream(engToBngDateArray)
              .forEach(engBngPair -> shouldMatchExpectedBanglaDate(engBngPair[0], engBngPair[1]));
    }

    public static void shouldMatchExpectedBanglaDate(DateKey englishDate, DateKey expectedBanglaDate) {
        DateKey apiOutput = DateConverter.getBanglaDate(englishDate);
        assertEquals(expectedBanglaDate, apiOutput);
    }
}