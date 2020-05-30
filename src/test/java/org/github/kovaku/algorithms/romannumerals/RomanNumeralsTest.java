package org.github.kovaku.algorithms.romannumerals;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RomanNumeralsTest {

    private enum RomanSymbols {
        M("C", 1000),
        D("C", 500),
        C("X", 100),
        L("X", 50),
        X("I", 10),
        V("I", 5),
        I(null, 1);

        private String validCombination;
        private Integer value;

        RomanSymbols(String validCombination, Integer value) {
            this.validCombination = validCombination;
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public String getValidCombination() {
            return validCombination;
        }
    }

    public String convertToRomanNumerals(Integer value) {
        var remainingValue = value;
        List<String> outputRomanNumerals = new ArrayList<>();
        for (RomanSymbols romanSymbol : RomanSymbols.values()) {
            //Test with full symbol value & addition rule
            if (remainingValue % romanSymbol.getValue() >= 0
                && remainingValue / romanSymbol.getValue() <= 3) {
                int numberOfAddition = remainingValue / romanSymbol.getValue();
                for (var i = 1; i <= numberOfAddition; i++) {
                    outputRomanNumerals.add(romanSymbol.toString());
                    remainingValue = remainingValue - romanSymbol.getValue();
                }
            }
            //Test with subtraction rule
            if (Objects.nonNull(romanSymbol.getValidCombination())) {
                var subtractedValue =
                    romanSymbol.getValue() - RomanSymbols.valueOf(romanSymbol.getValidCombination())
                        .getValue();
                if (remainingValue % subtractedValue >= 0
                    && remainingValue / subtractedValue == 1) {
                    outputRomanNumerals
                        .add(RomanSymbols.valueOf(romanSymbol.getValidCombination()).toString());
                    outputRomanNumerals.add(romanSymbol.toString());
                    remainingValue = remainingValue - subtractedValue;
                }
            }
        }
        return String.join("", outputRomanNumerals);
    }

    @DataProvider(name = "basicNumberProvider")
    public static Object[][] numberProviderWithBasicNumbers() {
        return new Object[][]{
            {1, "I"},
            {2, "II"},
            {3, "III"},
            {4, "IV"},
            {5, "V"},
            {6, "VI"},
            {7, "VII"},
            {8, "VIII"},
            {9, "IX"},
            {10, "X"},
            {11, "XI"},
            {12, "XII"},
            {30, "XXX"},
            {39, "XXXIX"},
            {246, "CCXLVI"},
            {789, "DCCLXXXIX"},
            {2421, "MMCDXXI"},
            {160, "CLX"},
            {207, "CCVII"},
            {1009, "MIX"},
            {1066, "MLXVI"}
        };
    }

    @Test(dataProvider = "basicNumberProvider", description = "Testing number conversion to Roman numerals")
    public void convertToRomanTest(int arabicNumber, String romanNumber) {
        String actualRomanNumber = convertToRomanNumerals(arabicNumber);
        assertThat(actualRomanNumber, equalTo(romanNumber));
    }
}
