package com.raghav.java14.patternmatchingforinstanceof;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PatternMatchingForInstanceOfUnitTest {
    @Test
    void givenAnAnimal_whenTypeIsCat_ThenCatGoesMeow() {
        Cat animal = Mockito.mock(Cat.class);

        PatternMatchingForInstanceOf instanceOf = new PatternMatchingForInstanceOf();
        instanceOf.performAnimalOperations(animal);

        Mockito.verify(animal).meow();
    }

    @Test
    void givenAnAnimal_whenTypeIsDog_ThenDogGoesWoof() {
        Dog animal = Mockito.mock(Dog.class);

        PatternMatchingForInstanceOf instanceOf = new PatternMatchingForInstanceOf();
        instanceOf.performAnimalOperations(animal);

        Mockito.verify(animal).woof();
    }
}

