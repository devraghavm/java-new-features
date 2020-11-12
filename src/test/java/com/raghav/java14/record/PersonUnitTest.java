package com.raghav.java14.record;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PersonUnitTest {
    @Test
    public void givenSameNameAndAddress_whenEquals_thenPersonsEqual() {
        String name = "Papa";
        String address = "143 Love Ln";

        Person person1 = new Person(name, address);
        Person person2 = new Person(name, address);

        Assertions.assertTrue(person1.equals(person2));
    }

    @Test
    public void givenDifferentObject_whenEquals_thenNotEqual() {
        Person person = new Person("Raghav", "10518 Rutledge St");

        Assertions.assertFalse(person.equals(new Object()));
    }

    @Test
    public void givenDifferentName_whenEquals_thenPersonsNotEqual() {
        String address = "143 Love Ln";

        Person person1 = new Person("Papa", address);
        Person person2 = new Person("Raghav", address);

        Assertions.assertFalse(person1.equals(person2));
    }

    @Test
    public void givenDifferentAddress_whenEquals_thenPersonsNotEqual() {
        String name = "Papa";

        Person person1 = new Person(name, "143 Love Ln");
        Person person2 = new Person(name, "143 Miss You Ln");

        Assertions.assertFalse(person1.equals(person2));
    }

    @Test
    public void givenSameNameAndAddress_whenHashCode_thenPersonsEqual() {
        String name = "Papa";
        String address = "143 Love Ln";

        Person person1 = new Person(name, address);
        Person person2 = new Person(name, address);

        Assertions.assertEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    public void givenDifferentObject_whenHashCode_thenNotEqual() {
        Person person = new Person("Papa", "143 Love Ln");

        Assertions.assertNotEquals(person.hashCode(), new Object().hashCode());
    }

    @Test
    public void givenDifferentName_whenHashCode_thenPersonNotEqual() {
        String address = "143 Love Ln";

        Person person1 = new Person("Papa", address);
        Person person2 = new Person("Babu", address);

        Assertions.assertNotEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    public void givenDifferentAddress_whenHashCode_thenPersonsNotEqual() {
        String name = "Papa";

        Person person1 = new Person(name, "143 Love Ln");
        Person person2 = new Person(name, "123 Miss You Ln");

        Assertions.assertNotEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    public void givenValidNameAndAddress_whenGetNameAndAddress_thenExpectedValuesReturned() {
        String name = "Papa";
        String address = "143 Love Ln";

        Person person = new Person(name, address);

        Assertions.assertEquals(name, person.name());
        Assertions.assertEquals(address, person.address());
    }

    @Test
    public void givenValidNameAndAddress_whenToString_thenCorrectStringReturned() {

        String name = "John Doe";
        String address = "100 Linda Ln.";

        Person person = new Person(name, address);

        Assertions.assertEquals("Person[name=" + name + ", address=" + address + "]", person.toString());
    }

    @Test
    public void givenNullName_whenConstruct_thenErrorThrown() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> new Person(null, "143 Love Ln")
        );
    }

    @Test
    public void givenNullAddress_whenConstruct_thenErrorThrown() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> new Person("Papa", null)
        );
    }

    @Test
    public void givenUnknownAddress_whenConstructing_thenAddressPopulated() {

        String name = "John Doe";

        Person person = new Person(name);

        Assertions.assertEquals(name, person.name());
        Assertions.assertEquals(Person.UNKNOWN_ADDRESS, person.address());
    }

    @Test
    public void givenUnnamed_whenConstructingThroughFactory_thenNamePopulated() {

        String address = "100 Linda Ln.";

        Person person = Person.unnamed(address);

        Assertions.assertEquals(Person.UNNAMED, person.name());
        Assertions.assertEquals(address, person.address());
    }
}
