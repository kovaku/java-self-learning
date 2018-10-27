package org.github.kovaku.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.github.kovaku.BaseTest;
import org.github.kovaku.functions.FibonacciTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

public class DataTimeTest extends BaseTest {

  private static final Logger LOGGER = Logger.getLogger(DataTimeTest.class.getName());

  @Autowired
  private FibonacciTest fibonacci;

  @Test(description = "Time - Measure the running time of a function")
  public void testTime() {
    Duration durationRecursive = getExecutionTime(40, fibonacci.fibonacciRecursive);
    LOGGER.info("The execution time was in nanos: " + durationRecursive.getNano());
  }

  @Test(description = "Time and Date - Converting between types - Java 7")
  public void testTimeAndDateJava7() throws ParseException {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");

    Date date = new Date(System.currentTimeMillis());
    String formattedDate = dateFormat.format(date);
    Date parsedDate = dateFormat.parse(formattedDate);

    assert date.compareTo(parsedDate) == 0;
  }

  @Test(description = "Calendar - Playing with dat with dates - Java 7")
  public void testCalendar() {
    Calendar calendar = new GregorianCalendar(2018, 11, 24);
    assert calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH).equals("Monday");

    List<Integer> years = Stream.iterate(1, i -> i + 1)
        .limit(100)
        .peek((i) -> calendar.add(Calendar.YEAR, -1))
        .filter((i) -> calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH).equals("Mon"))
        .map((i) -> calendar.get(Calendar.YEAR))
        .collect(Collectors.toList());
    assert years.contains(1956);
  }

  //TODO: SQL dates, time, timestamps

  //TODO: Java8 features

  private Duration getExecutionTime(Integer input, Function<Integer, Long> function) {
    Date startTime = new Date(System.currentTimeMillis());
    function.apply(input);
    Date endTime = new Date(System.currentTimeMillis());
    return Duration.between(startTime.toInstant(), endTime.toInstant());
  }
}
