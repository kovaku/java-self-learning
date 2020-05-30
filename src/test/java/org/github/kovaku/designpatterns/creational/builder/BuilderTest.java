package org.github.kovaku.designpatterns.creational.builder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.github.kovaku.BaseTest;
import org.github.kovaku.designpatterns.creational.builder.subclassing.Child;
import org.testng.annotations.Test;

public class BuilderTest extends BaseTest {

    private static final String NAME = "Budapest";
    private static final String COUNTRY = "Hungary";
    private static final Double LAT = 1.1;
    private static final Double LON = 0.1;

    @Test
    public void cityBuilderTest() {
        //Given
        //When
        City undertest = City.builder()
            .withName(NAME)
            .withCountry(COUNTRY)
            .withLat(LAT)
            .withLon(LON)
            .build();

        //Then
        assertThat(undertest.getName(), is(NAME));
        assertThat(undertest.getCountry(), is(COUNTRY));
        assertThat(undertest.getLat(), is(LAT));
        assertThat(undertest.getLon(), is(LON));
    }

    @Test
    public void subclassingBuilderTest() {
        //Given
        var name = "name";
        var childName = "childName";

        //When
        Child underTest = Child.builder().withName(name).withChildName(childName).build();

        //Then
        assertThat(underTest.getName(), is(name));
        assertThat(underTest.getChildName(), is(childName));
    }
}
