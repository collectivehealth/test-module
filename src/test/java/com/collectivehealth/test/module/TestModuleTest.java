package com.collectivehealth.test.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import com.collectivehealth.test.module.depedency.Constant;
import com.collectivehealth.test.module.depedency.ExtendedTestModule;
import com.collectivehealth.test.module.depedency.ExtendedWithMockedDefaultTestModule;
import com.collectivehealth.test.module.depedency.ExtendedWithMockedNamedDefaultTestModule;
import com.collectivehealth.test.module.depedency.TestClass;
import com.google.inject.ConfigurationException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class TestModuleTest {

    @Test
    // This test shows it's OK to make duplicate declaration, as long as it's
    // consistent (i.e. no mocking and spying at the same time).
    public void testDuplicateClass() {
        Injector injector = Guice.createInjector(new TestModule()
                .withMockedClasses(TestClass.class)
                .withMockedClasses(TestClass.class)
                .withInstance(String.class, Constant.MOCKED_RETURN_VALUE_1)
                .withInstance(String.class, Constant.MOCKED_RETURN_VALUE_2));

        // Rather than an assertion, this is more like an indicator, letting
        // people know what to expect.
        // The behavior is dictated by implementation of the collection we are
        // using, in this case, HashSet.
        assertEquals(Constant.MOCKED_RETURN_VALUE_1, injector.getInstance(String.class));
    }

    @Test
    public void testDoNothing() {
        Injector injector = Guice.createInjector(new ExtendedTestModule());

        // Guice's default behavior should kick in, and return a
        // default-constructed TestClass instance.
        assertEquals(Constant.DEFAULT_RETURN_VALUE, injector.getInstance(TestClass.class).getReturnValue());
    }

    @Test
    public void testBindInstance() {
        TestClass mock = Mockito.mock(TestClass.class);
        Mockito.when(mock.getReturnValue()).thenReturn(Constant.MOCKED_RETURN_VALUE_1);

        Injector injector = Guice.createInjector(new ExtendedTestModule().withInstance(TestClass.class, mock));

        assertEquals(Constant.MOCKED_RETURN_VALUE_1, injector.getInstance(TestClass.class).getReturnValue());
        try {
            // Not mocked, undefined behavior.
            injector.getInstance(Key.get(TestClass.class, Names.named(Constant.ANNOTATED_NAME)));
            fail("Exception expected.");
        } catch (ConfigurationException e) {
            // Not binded
        }
    }

    @Test
    public void testBindNamedInstance() {
        TestClass mock = Mockito.mock(TestClass.class);
        Mockito.when(mock.getReturnValue()).thenReturn(Constant.MOCKED_RETURN_VALUE_1);

        Injector injector = Guice.createInjector(new ExtendedTestModule().withInstance(TestClass.class, Constant.ANNOTATED_NAME, mock));

        assertEquals(Constant.DEFAULT_RETURN_VALUE, injector.getInstance(TestClass.class).getReturnValue());
        assertEquals(Constant.MOCKED_RETURN_VALUE_1, injector.getInstance(Key.get(TestClass.class, Names.named(Constant.ANNOTATED_NAME))).getReturnValue());
    }

    @Test
    public void testMockClass() {
        Injector injector = Guice.createInjector(new ExtendedTestModule().withMockedClasses(TestClass.class));

        TestClass mock = injector.getInstance(TestClass.class);
        // Default mocked behavior
        assertNull(mock.getReturnValue());

        // Check it is actually mocked
        Mockito.when(mock.getReturnValue()).thenReturn(Constant.MOCKED_RETURN_VALUE_1);
        assertEquals(Constant.MOCKED_RETURN_VALUE_1, mock.getReturnValue());
    }

    @Test
    public void testSpyClass() {
        Injector injector = Guice.createInjector(new ExtendedTestModule().withSpiedClasses(TestClass.class));

        TestClass mock = injector.getInstance(TestClass.class);
        // Default spied behavior
        assertEquals(Constant.DEFAULT_RETURN_VALUE, mock.getReturnValue());

        // Check it is actually spied
        Mockito.when(mock.getReturnValue()).thenReturn(Constant.MOCKED_RETURN_VALUE_1);
        assertEquals(Constant.MOCKED_RETURN_VALUE_1, mock.getReturnValue());
    }

    @Test
    public void testDefault() {
        Injector injector = Guice.createInjector(new ExtendedWithMockedDefaultTestModule());
        assertEquals(Constant.MOCKED_RETURN_VALUE_1, injector.getInstance(TestClass.class).getReturnValue());
    }

    @Test
    public void testDefaultOverrideWithInstance() {
        Injector injector = Guice.createInjector(new ExtendedWithMockedDefaultTestModule()
                .withInstance(TestClass.class, new TestClass()));

        assertEquals(Constant.DEFAULT_RETURN_VALUE, injector.getInstance(TestClass.class).getReturnValue());
        try {
            // Not mocked, undefined behavior.
            injector.getInstance(Key.get(TestClass.class, Names.named(Constant.ANNOTATED_NAME)));
            fail("Exception expected.");
        } catch (ConfigurationException e) {
            // Not binded
        }
    }

    @Test
    public void testDefaultOverrideWithNamedInstance() {
        Injector injector = Guice.createInjector(new ExtendedWithMockedDefaultTestModule()
                .withInstance(TestClass.class, Constant.ANNOTATED_NAME, new TestClass()));

        assertEquals(Constant.MOCKED_RETURN_VALUE_1, injector.getInstance(TestClass.class).getReturnValue());
        assertEquals(Constant.DEFAULT_RETURN_VALUE, injector.getInstance(Key.get(TestClass.class, Names.named(Constant.ANNOTATED_NAME))).getReturnValue());
    }

    @Test
    public void testDefaultOverrideWithMockedClass() {
        Injector injector = Guice.createInjector(new ExtendedWithMockedDefaultTestModule()
                .withMockedClasses(TestClass.class));

        TestClass mock = injector.getInstance(TestClass.class);
        // Default mocked behavior
        assertNull(mock.getReturnValue());

        // Check it is actually mocked
        Mockito.when(mock.getReturnValue()).thenReturn(Constant.MOCKED_RETURN_VALUE_1);
        assertEquals(Constant.MOCKED_RETURN_VALUE_1, mock.getReturnValue());
    }

    @Test
    public void testDefaultOverrideWithSpiedClass() {
        Injector injector = Guice.createInjector(new ExtendedWithMockedDefaultTestModule()
                .withSpiedClasses(TestClass.class));

        TestClass mock = injector.getInstance(TestClass.class);
        // Default spied behavior
        assertEquals(Constant.DEFAULT_RETURN_VALUE, mock.getReturnValue());

        // Check it is actually spied
        Mockito.when(mock.getReturnValue()).thenReturn(Constant.MOCKED_RETURN_VALUE_1);
        assertEquals(Constant.MOCKED_RETURN_VALUE_1, mock.getReturnValue());
    }

    @Test
    public void testDefaultNamed() {
        Injector injector = Guice.createInjector(new ExtendedWithMockedNamedDefaultTestModule());

        assertEquals(Constant.DEFAULT_RETURN_VALUE, injector.getInstance(TestClass.class).getReturnValue());
        assertEquals(Constant.MOCKED_RETURN_VALUE_1, injector.getInstance(Key.get(TestClass.class, Names.named(Constant.ANNOTATED_NAME))).getReturnValue());
    }

    @Test
    public void testDefaultNamedOverrideWithInstance() {
        Injector injector = Guice.createInjector(new ExtendedWithMockedNamedDefaultTestModule()
                .withInstance(TestClass.class, new TestClass() {
                    @Override
                    public String getReturnValue() {
                        return Constant.MOCKED_RETURN_VALUE_2;
                    }
                }));

        assertEquals(Constant.MOCKED_RETURN_VALUE_2, injector.getInstance(TestClass.class).getReturnValue());
        assertEquals(Constant.MOCKED_RETURN_VALUE_1, injector.getInstance(Key.get(TestClass.class, Names.named(Constant.ANNOTATED_NAME))).getReturnValue());
    }

    @Test
    public void testDefaultNamedOverrideWithNamedInstance() {
        Injector injector = Guice.createInjector(new ExtendedWithMockedNamedDefaultTestModule()
                .withInstance(TestClass.class, Constant.ANNOTATED_NAME, new TestClass() {
                    @Override
                    public String getReturnValue() {
                        return Constant.MOCKED_RETURN_VALUE_2;
                    }
                }));

        assertEquals(Constant.DEFAULT_RETURN_VALUE, injector.getInstance(TestClass.class).getReturnValue());
        assertEquals(Constant.MOCKED_RETURN_VALUE_2, injector.getInstance(Key.get(TestClass.class, Names.named(Constant.ANNOTATED_NAME))).getReturnValue());
    }

    @Test
    public void testDefaultNamedOverrideWithMockedClass() {
        Injector injector = Guice.createInjector(new ExtendedWithMockedNamedDefaultTestModule()
                .withMockedClasses(TestClass.class));

        assertEquals(Constant.MOCKED_RETURN_VALUE_1,
                injector.getInstance(Key.get(TestClass.class, Names.named(Constant.ANNOTATED_NAME))).getReturnValue());

        TestClass mock = injector.getInstance(TestClass.class);
        // Default mocked behavior
        assertNull(mock.getReturnValue());

        // Check it is actually mocked
        Mockito.when(mock.getReturnValue()).thenReturn(Constant.MOCKED_RETURN_VALUE_1);
        assertEquals(Constant.MOCKED_RETURN_VALUE_1, mock.getReturnValue());
    }

    @Test
    public void testDefaultNamedOverrideWithSpiedClass() {
        Injector injector = Guice.createInjector(new ExtendedWithMockedNamedDefaultTestModule()
                .withSpiedClasses(TestClass.class));

        assertEquals(Constant.MOCKED_RETURN_VALUE_1, injector.getInstance(Key.get(TestClass.class, Names.named(Constant.ANNOTATED_NAME))).getReturnValue());

        TestClass mock = injector.getInstance(TestClass.class);
        // Default spied behavior
        assertEquals(Constant.DEFAULT_RETURN_VALUE, mock.getReturnValue());

        // Check it is actually spied
        Mockito.when(mock.getReturnValue()).thenReturn(Constant.MOCKED_RETURN_VALUE_1);
        assertEquals(Constant.MOCKED_RETURN_VALUE_1, mock.getReturnValue());
    }

    /*
     * Since `TestModule.spyClasses()` instantiates from its own class, it's
     * worth checking for a few conditions.
     */

    @Test
    public void testInlineTestModuleClass() {
        Injector injector = Guice.createInjector(new TestModule().withSpiedClasses(TestClass.class));

        TestClass mock = injector.getInstance(TestClass.class);
        // Default spied behavior
        assertEquals(Constant.DEFAULT_RETURN_VALUE, mock.getReturnValue());

        // Check it is actually spied
        Mockito.when(mock.getReturnValue()).thenReturn(Constant.MOCKED_RETURN_VALUE_1);
        assertEquals(Constant.MOCKED_RETURN_VALUE_1, mock.getReturnValue());
    }

    public static class NestedStaticTestModule extends TestModule {
    }

    @Test
    public void testNestedStaticTestModuleClass() {
        Injector injector = Guice.createInjector(new NestedStaticTestModule().withSpiedClasses(TestClass.class));

        TestClass mock = injector.getInstance(TestClass.class);
        // Default spied behavior
        assertEquals(Constant.DEFAULT_RETURN_VALUE, mock.getReturnValue());

        // Check it is actually spied
        Mockito.when(mock.getReturnValue()).thenReturn(Constant.MOCKED_RETURN_VALUE_1);
        assertEquals(Constant.MOCKED_RETURN_VALUE_1, mock.getReturnValue());
    }

    private class NestedTestModule extends TestModule {
    }

    @Test
    @Ignore
    // Nested TestModule subclass is not supported due to the difficulty in
    // instantiating them inside the the `TestModule.spyClasses()` method.
    public void testNestedTestModuleClass() {
        Guice.createInjector(new NestedTestModule().withSpiedClasses(TestClass.class));
    }

}
