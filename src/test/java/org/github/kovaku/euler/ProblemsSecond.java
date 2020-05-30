package org.github.kovaku.euler;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.github.kovaku.BaseTest;
import org.testng.annotations.Test;

public class ProblemsSecond extends BaseTest {

    private String numberMatrix = "73167176531330624919225119674426574742355349194934"
        + "96983520312774506326239578318016984801869478851843"
        + "85861560789112949495459501737958331952853208805511"
        + "12540698747158523863050715693290963295227443043557"
        + "66896648950445244523161731856403098711121722383113"
        + "62229893423380308135336276614282806444486645238749"
        + "30358907296290491560440772390713810515859307960866"
        + "70172427121883998797908792274921901699720888093776"
        + "65727333001053367881220235421809751254540594752243"
        + "52584907711670556013604839586446706324415722155397"
        + "53697817977846174064955149290862569321978468622482"
        + "83972241375657056057490261407972968652414535100474"
        + "82166370484403199890008895243450658541227588666881"
        + "16427171479924442928230863465674813919123162824586"
        + "17866458359124566529476545682848912883142607690042"
        + "24219022671055626321111109370544217506941658960408"
        + "07198403850962455444362981230987879927244284909188"
        + "84580156166097919133875499200524063689912560717606"
        + "05886116467109405077541002256983155200055935729725"
        + "7163626956188267042825248360823257530420752963450";

    private static final int GRID_WIDTH = 20;
    private static final int GRID_HEIGHT = 20;
    private static final int WINDOW_SIZE = 4;
    private static final Integer[][] GRID = {
        {8, 2, 22, 97, 38, 15, 0, 40, 0, 75, 4, 5, 7, 78, 52, 12, 50, 77, 91, 8},
        {49, 49, 99, 40, 17, 81, 18, 57, 60, 87, 17, 40, 98, 43, 69, 48, 4, 56, 62, 0},
        {81, 49, 31, 73, 55, 79, 14, 29, 93, 71, 40, 67, 53, 88, 30, 3, 49, 13, 36, 65},
        {52, 70, 95, 23, 4, 60, 11, 42, 69, 24, 68, 56, 1, 32, 56, 71, 37, 2, 36, 91},
        {22, 31, 16, 71, 51, 67, 63, 89, 41, 92, 36, 54, 22, 40, 40, 28, 66, 33, 13, 80},
        {24, 47, 32, 60, 99, 3, 45, 2, 44, 75, 33, 53, 78, 36, 84, 20, 35, 17, 12, 50},
        {32, 98, 81, 28, 64, 23, 67, 10, 26, 38, 40, 67, 59, 54, 70, 66, 18, 38, 64, 70},
        {67, 26, 20, 68, 2, 62, 12, 20, 95, 63, 94, 39, 63, 8, 40, 91, 66, 49, 94, 21},
        {24, 55, 58, 5, 66, 73, 99, 26, 97, 17, 78, 78, 96, 83, 14, 88, 34, 89, 63, 72},
        {21, 36, 23, 9, 75, 0, 76, 44, 20, 45, 35, 14, 0, 61, 33, 97, 34, 31, 33, 95},
        {78, 17, 53, 28, 22, 75, 31, 67, 15, 94, 3, 80, 4, 62, 16, 14, 9, 53, 56, 92},
        {16, 39, 5, 42, 96, 35, 31, 47, 55, 58, 88, 24, 0, 17, 54, 24, 36, 29, 85, 57},
        {86, 56, 0, 48, 35, 71, 89, 7, 5, 44, 44, 37, 44, 60, 21, 58, 51, 54, 17, 58},
        {19, 80, 81, 68, 5, 94, 47, 69, 28, 73, 92, 13, 86, 52, 17, 77, 4, 89, 55, 40},
        {4, 52, 8, 83, 97, 35, 99, 16, 7, 97, 57, 32, 16, 26, 26, 79, 33, 27, 98, 66},
        {88, 36, 68, 87, 57, 62, 20, 72, 3, 46, 33, 67, 46, 55, 12, 32, 63, 93, 53, 69},
        {4, 42, 16, 73, 38, 25, 39, 11, 24, 94, 72, 18, 8, 46, 29, 32, 40, 62, 76, 36},
        {20, 69, 36, 41, 72, 30, 23, 88, 34, 62, 99, 69, 82, 67, 59, 85, 74, 4, 36, 16},
        {20, 73, 35, 29, 78, 31, 90, 1, 74, 31, 49, 71, 48, 86, 81, 16, 23, 57, 5, 54},
        {1, 70, 54, 71, 83, 51, 54, 69, 16, 92, 33, 48, 61, 43, 52, 1, 89, 19, 67, 48}
    };

    @Test
    public void largestProductInSeries() {
        int desiredWindowWidth = 13;

        Long result = IntStream
            .rangeClosed(0, numberMatrix.length() - desiredWindowWidth)
            .boxed()
            .map(i -> numberMatrix.substring(i, i + desiredWindowWidth))
            .map(numberAsString -> numberAsString.split(""))
            .map(stringArray -> Arrays
                .stream(stringArray)
                .map(Long::valueOf)
                .reduce(1L, (num1, num2) -> num1 * num2))
            .max(Comparator.comparing(Long::longValue))
            .get();

        System.out.println(result);
    }

    @Test
    public void specialPythagoreanTriplet() {
        int sum = 1000;
        for (int a = 1; a <= sum / 3; a++) {
            for (int b = a + 1; b <= sum / 2; b++) {
                int c = 1000 - b - a;
                if (a * a + b * b == c * c) {
                    System.out.println(a * b * c);
                }
            }
        }
    }

    @Test
    public void largestProductInGrid() {
        Stream<Integer> verticalProducts = IntStream
            .rangeClosed(0, GRID_WIDTH - WINDOW_SIZE)
            .boxed()
            .map(x -> IntStream
                .rangeClosed(0, GRID_HEIGHT - 1)
                .boxed()
                .map(y -> GRID[y][x] * GRID[y][x + 1] * GRID[y][x + 2] * GRID[y][x + 3]))
            .flatMap(Function.identity());

        Stream<Integer> horizontalProducts = IntStream
            .rangeClosed(0, GRID_HEIGHT - WINDOW_SIZE)
            .boxed()
            .map(y -> IntStream
                .rangeClosed(0, GRID_WIDTH - 1)
                .boxed()
                .map(x -> GRID[y][x] * GRID[y + 1][x] * GRID[y + 2][x] * GRID[y + 3][x]))
            .flatMap(Function.identity());

        Stream<Integer> diagonalRightProduct = IntStream
            .rangeClosed(0, GRID_HEIGHT - WINDOW_SIZE)
            .boxed()
            .map(x -> IntStream
                .rangeClosed(0, GRID_WIDTH - WINDOW_SIZE)
                .boxed()
                .map(
                    y -> GRID[y][x] * GRID[y + 1][x + 1] * GRID[y + 2][x + 2] * GRID[y + 3][x + 3]))
            .flatMap(Function.identity());

        Stream<Integer> diagonalLeftProduct = IntStream
            .rangeClosed(WINDOW_SIZE - 1, GRID_HEIGHT - 1)
            .boxed()
            .map(x -> IntStream
                .rangeClosed(0, GRID_WIDTH - WINDOW_SIZE)
                .boxed()
                .map(
                    y -> GRID[y][x] * GRID[y + 1][x - 1] * GRID[y + 2][x - 2] * GRID[y + 3][x - 3]))
            .flatMap(Function.identity());

        Integer result = Stream
            .of(horizontalProducts, verticalProducts, diagonalRightProduct, diagonalLeftProduct)
            .flatMap(Function.identity())
            .max(Comparator.comparing(Integer::intValue))
            .get();

        System.out.println(result);
    }
}
