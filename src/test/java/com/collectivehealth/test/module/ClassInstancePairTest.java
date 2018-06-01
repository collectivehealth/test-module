package com.collectivehealth.test.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ClassInstancePairTest {

    @Test
    public void testEquals() {
        ClassInstancePair<String> stringPair1 = new ClassInstancePair<>(String.class, "Test");
        ClassInstancePair<String> stringPair2 = new ClassInstancePair<>(String.class, "Hello World");
        ClassInstancePair<String> stringNamedPair1 = new ClassInstancePair<>(String.class, "Name1", "Test");
        ClassInstancePair<String> stringNamedPair2 = new ClassInstancePair<>(String.class, "Name1", "Hello World");
        ClassInstancePair<String> stringNamedPair3 = new ClassInstancePair<>(String.class, "Name2", "Hello World");
        ClassInstancePair<String> string5Pair = new ClassInstancePair<>(String.class, "5");
        ClassInstancePair<Integer> integer5Pair = new ClassInstancePair<>(Integer.class, 5);

        assertTrue(stringPair1.equals(stringPair1));
        assertFalse(stringPair1.equals(null));
        assertFalse(stringPair1.equals("Test"));

        assertEquals(stringPair1, stringPair2);
        assertNotEquals(stringPair1, stringNamedPair1);
        assertEquals(stringNamedPair1, stringNamedPair2);
        assertNotEquals(stringNamedPair2, stringNamedPair3);
        assertNotEquals(string5Pair, integer5Pair);
    }

}
