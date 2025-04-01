package common.util;

import common.entity.Color;
import common.entity.Country;
import common.entity.Position;
import common.entity.Status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

public class Consts {
    public static class Predicates {
        public static Predicate<String> isInteger = str -> {
            try {
                Integer.parseInt(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        };
        public static Predicate<String> isDouble = str -> {
            try {
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        };
        public static Predicate<String> isLong = str -> {
            try {
                Long.parseLong(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        };
        public static Predicate<String> isPosition = str -> {
            try {
                Position pos = Position.valueOf(str);
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        };
        public static Predicate<String> isStatus = str -> {
            try {
                Status status = Status.valueOf(str);
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        };
        public static Predicate<String> isColor = str -> {
            try {
                Color status = Color.valueOf(str);
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        };
        public static Predicate<String> isCountry = str -> {
            try {
                Country status = Country.valueOf(str);
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        };
        public static Predicate<String> alwaysTrue = str -> true;
        public static Predicate<String> greaterZero = str -> Double.parseDouble(str) > 0;
        public static Predicate<String> coordinatesXPredicate = str -> Double.parseDouble(str) <= 644;
        public static Predicate<String> coordinatesYPredicate = str -> Double.parseDouble(str) > -154;
        public static Predicate<String> isDate = str -> {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                LocalDate.parse(str, formatter);
                return true;
            } catch (Exception e) {
                return false;
            }
        };

        public static Predicate<String> longerThanTwo = str -> str.length() > 2;
        public static Predicate<String> longerThanFive = str -> str.length() > 5;
    }
}
