package org.github.kovaku.collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.startsWith;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.github.kovaku.BaseTest;
import org.testng.annotations.Test;
import org.testng.collections.Sets;

public class CollectorsTest extends BaseTest {

    private List<Integer> testCollectionOfNumbers = Arrays.asList(1, 2, 3, 4, 5, 8, 9);
    private List<String> testCollectionOfStrings = Arrays
        .asList("a", "bb", "c", "dd", "ee", "f", "g");
    private Predicate<Integer> isEven = number -> number % 2 == 0;

    @Test(description = "Collect the results of a stream to map")
    public void testCollectToMap() {
        var result = testCollectionOfNumbers.stream()
            .collect(Collectors.toMap(Function.identity(), isEven::test));

        assertThat(result.entrySet(), hasSize(7));
        assertThat(result.get(1), equalTo(false));
        assertThat(result.get(2), equalTo(true));
    }

    @Test(description = "Collect the results to a Set with given implementation")
    public void testCollectToSetWithDefinedImplementation() {
        var result = testCollectionOfNumbers.stream()
            .collect(Collectors.toCollection(Sets::newHashSet));

        assertThat(result, instanceOf(HashSet.class));
    }

    @Test(description = "Collect and join strings")
    public void testCollectAndJoinStrings() {
        var resultOfSimpleJoin = testCollectionOfStrings.stream()
            .collect(Collectors.joining());
        var resultOfJoinWithDelimiter = testCollectionOfStrings.stream()
            .collect(Collectors.joining("_"));
        var resultOfJoinWithFixes = testCollectionOfStrings.stream()
            .collect(Collectors.joining("", "Pre", "Post"));

        assertThat(resultOfSimpleJoin, equalTo("abbcddeefg"));
        assertThat(resultOfJoinWithDelimiter, equalTo("a_bb_c_dd_ee_f_g"));
        assertThat(resultOfJoinWithFixes, both(startsWith("Prn")).and(endsWith("Post")));
    }

    @Test(description = "Get statistic using collectors")
    public void testStaticsUsingCollectors() {
        Long count = testCollectionOfNumbers
            .stream()
            .collect(Collectors.counting());
        Integer sum = testCollectionOfNumbers
            .stream()
            .collect(Collectors.summingInt(Integer::intValue));

        DoubleSummaryStatistics statistics = testCollectionOfNumbers.stream()
            .collect(Collectors
                .summarizingDouble(Integer::doubleValue));

        assertThat(count, equalTo(7L));
        assertThat(sum, equalTo(32));

        assertThat(statistics.getCount(), equalTo(7L));
        assertThat(statistics.getAverage(), closeTo(4.57, 0.01));
        assertThat(statistics.getMax(), equalTo(9.0));
        assertThat(statistics.getMin(), equalTo(1.0));
        assertThat(statistics.getSum(), equalTo(32.0));
    }

    @Test(description = "Partition by parity")
    public void testCollectorsByPartition() {
        var result = testCollectionOfNumbers.stream()
            .collect(Collectors.partitioningBy(isEven));

        assertThat(result.entrySet(), hasSize(2));
        assertThat(result.get(true), containsInAnyOrder(List.of(2, 4, 8)));
        assertThat(result.get(false), containsInAnyOrder(List.of(1, 3, 9)));
    }
}
