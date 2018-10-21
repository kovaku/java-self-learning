package org.github.kovaku.stream;

import org.github.kovaku.BaseTest;
import org.testng.annotations.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamAPITest extends BaseTest {

  private List<Person> personList = new ArrayList<>();
  private List<String> selectedPersonList = List.of("Alice", "Cecile");
  private List<Scholarship> selectedPersonForScholarshipList = List
      .of(new Scholarship("Bob", "University of A"),
          new Scholarship("Eric", "University of B"));
  private Predicate<Person> ageLegalAlcoholDrinkerFilter = person -> person.getAge() >= 18;
  private Predicate<Person> genderMaleFilter = person -> person.getGender().equals(Gender.MALE);

  {
    personList.add(new Person("Frank", 15, Gender.MALE).addHobby("fishing").addHobby("dance"));
    personList.add(new Person("Bob", 25, Gender.MALE).addHobby("football"));
    personList.add(new Person("David", 17, Gender.MALE).addHobby("running"));
    personList.add(new Person("Eric", 21, Gender.MALE).addHobby("fishing"));
    personList.add(new Person("Alice", 18, Gender.FEMALE).addHobby("arts").addHobby("music"));
    //Duplicated entry
    personList.add(new Person("Eric", 21, Gender.MALE).addHobby("fishing"));
    personList.add(new Person("Cecile", 16, Gender.FEMALE));
  }

  @Test(description = "Testing the filter, distinct, sorted and collect feature")
  public void sortedLegalMaleDrinkerTest() {
    List<Person> legalMaleDrinker = personList.stream()
        .filter(ageLegalAlcoholDrinkerFilter)
        .filter(genderMaleFilter)
        .distinct()
        .sorted()
        .collect(Collectors.toList());
    assert legalMaleDrinker.size() == 2;
  }

  @Test(description = "Testing the map feature of the Stream API")
  public void getDistinctNameListTest() {
    List<String> nameList = personList.stream()
        .map(Person::getName)
        .distinct()
        .sorted()
        .collect(Collectors.toList());
    assert nameList.toString().equals("[Alice, Bob, Cecile, David, Eric, Frank]");
  }

  @Test(description = "Testing the flatMap feature of the Stream API")
  public void collectHobbiesTest() {
    List<String> hobbiesList = personList.stream()
        .flatMap(x -> x.getHobbyList().stream())
        .distinct()
        .sorted()
        .collect(Collectors.toList());
    assert hobbiesList.toString().equals("[arts, dance, fishing, football, music, running]");
  }

  @Test(description = "Testing the max function the Stream API")
  public void getTheOldestPerson() {
    Optional<Integer> oldestPersonsAge = personList.stream()
        .map(Person::getAge)
        .max(Comparator.comparing(x -> x));
    assert oldestPersonsAge.get() == 25;
  }

  @Test(description = "Testing the limit function the Stream API")
  public void getTheYoungestPerson() {
    Optional<Integer> youngestPersonsAge = personList.stream()
        .map(Person::getAge)
        .sorted()
        .limit(1).findAny();
    assert youngestPersonsAge.get() == 15;
  }

  @Test(description = "Filter a list of persons based on a flat list")
  public void filterListBasedOnFlatList() {
    List<Person> selectedPersons = personList.stream()
        .filter(person -> selectedPersonList.contains(person.getName()))
        .collect(Collectors.toList());
    assert selectedPersons.size() == 2;
  }

  @Test(description = "Filter a list of persons based on a complex flat list")
  public void filterListBasedOnComplexList() {
    List<Person> selectedPersons = personList.stream()
        .filter(person -> selectedPersonForScholarshipList.stream()
            .map(Scholarship::getName)
            .anyMatch(scholarshipName -> scholarshipName.equals(person.getName())))
        .distinct()
        .collect(Collectors.toList());
    assert selectedPersons.size() == 2;
  }

  private class Person implements Comparable {

    private final String name;
    private final Integer age;
    private final Gender gender;
    private List<String> hobbyList = new ArrayList<>();

    public Person(String name, Integer age, Gender gender) {
      this.name = name;
      this.age = age;
      this.gender = gender;
    }

    public Person addHobby(String hobby) {
      hobbyList.add(hobby);
      return this;
    }

    public String getName() {
      return name;
    }

    public Integer getAge() {
      return age;
    }

    public Gender getGender() {
      return gender;
    }

    public List<String> getHobbyList() {
      return hobbyList;
    }

    @Override
    public String toString() {
      return "Person{" +
          "name='" + name + '\'' +
          ", age=" + age +
          ", gender=" + gender +
          '}';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Person person = (Person) o;
      return Objects.equals(name, person.name) &&
          Objects.equals(age, person.age) &&
          gender == person.gender;
    }

    @Override
    public int hashCode() {
      return Objects.hash(name, age, gender);
    }

    @Override
    public int compareTo(Object o) {
      if (this.equals(o)) {
        return 0;
      } else {
        return (this.getAge() > ((Person) o).getAge()) ? 1 : -1;
      }
    }
  }

  private class Scholarship {

    private final String name;
    private final String school;

    public Scholarship(String name, String school) {
      this.name = name;
      this.school = school;
    }

    public String getName() {
      return name;
    }

    public String getSchool() {
      return school;
    }
  }

  private enum Gender {
    MALE,
    FEMALE
  }
}
