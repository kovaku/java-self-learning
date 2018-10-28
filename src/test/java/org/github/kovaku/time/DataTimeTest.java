package org.github.kovaku.time;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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

    Date date = new Date(Instant.now().toEpochMilli());
    String formattedDate = dateFormat.format(date);
    Date parsedDate = dateFormat.parse(formattedDate);

    assert date.compareTo(parsedDate) == 0;
  }

  @Test(description = "Calendar - Playing with dates - Java 7")
  public void testCalendar() {
    Calendar calendar = new GregorianCalendar(2018, 11, 24);
    assert calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH)
        .equals("Monday");

    List<Integer> years = Stream.iterate(1, i -> i + 1)
        .limit(100)
        .peek((i) -> calendar.add(Calendar.YEAR, -1))
        .filter((i) -> calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH)
            .equals("Mon"))
        .map((i) -> calendar.get(Calendar.YEAR))
        .collect(Collectors.toList());
    assert years.contains(1956);
  }

  @Test(description = "Data and Time - Using SQL time related classes")
  public void testSqlTimeDateClasses() {
    Long timeInMillis = System.currentTimeMillis();
    Date date = new Date(timeInMillis);

    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    java.sql.Date sqlDate = new java.sql.Date(timeInMillis);

    DateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
    Time sqlTime = new Time(timeInMillis);

    DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    Timestamp sqlTimestamp = new Timestamp(timeInMillis);

    assert sqlDate.toString().equals(dateFormat1.format(date));
    assert sqlTime.toString().equals(dateFormat2.format(date));
    assert sqlTimestamp.toString().equals(dateFormat3.format(date));
  }

  @Test(description = "Date and Time - Playing with the Java 8 Time API")
  public void testJava8TimeApi() {
    LocalDate localDate = LocalDate.of(2018, 12, 24);
    assert localDate.getDayOfWeek().equals(DayOfWeek.MONDAY);
    assert localDate.plusDays(1).getDayOfWeek().equals(DayOfWeek.TUESDAY);

    List<String> dates = localDate
        .datesUntil(LocalDate.of(2018, 12, 31))
        .peek(System.out::println)
        .map(LocalDate::toString)
        .collect(Collectors.toList());
    assert dates.size() == 7;

    LocalTime localTimeStart = LocalTime.parse("11:32", DateTimeFormatter.ISO_TIME);
    LocalTime localTimeEnd = LocalTime.of(12, 19);

    Duration duration = Duration.between(localTimeStart, localTimeEnd);
    assert duration.toMinutes() == 47;

    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTimeEnd);
    ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("+1"));
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
    assert dateTimeFormatter.format(zonedDateTime).equals("2018-12-24T12:19:00+01:00");
  }

  private Duration getExecutionTime(Integer input, Function<Integer, Long> function) {
    Date startTime = new Date(System.currentTimeMillis());
    function.apply(input);
    Date endTime = new Date(System.currentTimeMillis());
    return Duration.between(startTime.toInstant(), endTime.toInstant());
  }
}
