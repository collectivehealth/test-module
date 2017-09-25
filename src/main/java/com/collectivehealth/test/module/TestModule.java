package com.collectivehealth.test.module;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.mockito.Mockito;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * This is a Guice Module class that's designed to hide away the weeds of
 * initialization interaction between Guice and Mockito.
 * 
 * Due to Guice's default behavior of always returning a new instance, it's
 * often difficult to get it right to inject the mock you want into the instance
 * you want. Or, you may want a mocked Hibernate session for unit tests, but a
 * real one for integration tests. You would have to create two Module instances
 * with almost exact configuration. You could extend or override, alternatively,
 * you could use TestModule!
 * 
 * With this class, you can identify which classes you would like to be mocked
 * or spied via `withMockedClasses()` and `withSpiedClasses()`, and which
 * classes you would like to be binded to which specific instances via
 * `withInstance()`. You can also provide some default behavior, which could be
 * overridden by settings in tests.
 * 
 * i.e.
 * 
 * <pre>
 * public class ExampleTextModule extends TestModule {
 * 
 *     &#64;Override
 *     protected Collection<ClassInstancePair<?>> getDefaultInstances() {
 *         Collection<ClassInstancePair<?>> instances = new LinkedList<>();
 * 
 *         String mock = Mockito.mock(String.class);
 *         Mockito.when(mock.toString()).thenReturn("Hello World!");
 *         instances.add(new ClassInstancePair<String>(String.class, mock));
 * 
 *         return instances;
 *     }
 * 
 * }
 * 
 * public class Demo {
 * 
 *     &#64;Test
 *     public void defaultBehavior() {
 *         Injector injector = Guice.createInjector(new ExampleTextModule());
 *         assertEquals("Hello World", injector.getInstance(String.class).toString());
 *     }
 * 
 *     &#64;Test
 *     public void overrideWithInstance() {
 *         Injector injector = Guice.createInjector(new ExampleTextModule()
 *                 .withInstance(String.class, "Test"));
 *         assertEquals("Test", injector.getInstance(String.class).toString());
 *     }
 * 
 * }
 * </pre>
 * 
 * For more examples, please look at `TestModuleTest`. (Hooray for
 * test-as-documentation!)
 */

public class TestModule extends AbstractModule {

    private Collection<ClassInstancePair<?>> instances = new HashSet<>();
    private Collection<Class<?>> mockedClasses = new HashSet<>();
    private Collection<Class<?>> spiedClasses = new HashSet<>();

    /**
     * Bind a class to a specific instance for the test.
     */
    public <T> TestModule withInstance(Class<T> c, T instance) {
        instances.add(new ClassInstancePair<T>(c, instance));
        return this;
    }

    /**
     * Bind a class annotated with name to a specific instance for the test.
     */
    public <T> TestModule withInstance(Class<T> c, String name, T instance) {
        instances.add(new ClassInstancePair<T>(c, name, instance));
        return this;
    }

    /**
     * Mock out these classes for the test.
     */
    public TestModule withMockedClasses(Class<?>... mockedClasses) {
        this.mockedClasses.addAll(Arrays.asList(mockedClasses));
        return this;
    }

    /**
     * Spy these classes for the test.
     */
    public TestModule withSpiedClasses(Class<?>... spiedClasses) {
        this.spiedClasses.addAll(Arrays.asList(spiedClasses));
        return this;
    }

    @Override
    protected void configure() {
        bindInstances(instances);
        mockClasses(mockedClasses);
        spyClasses(spiedClasses);

        // Bind classes that haven't been binded, and have explicit defaults
        Collection<ClassInstancePair<?>> defaultInstances = getDefaultInstances();
        defaultInstances = defaultInstances.stream()
                // Skip mocked and spied classes
                .filter(i -> !StringUtils.isBlank(i.name) || (!mockedClasses.contains(i.c) && !spiedClasses.contains(i.c)))
                // Skip already binded instances
                .filter(i -> !instances.contains(i))
                .collect(Collectors.toList());
        bindInstances(defaultInstances);

        additionalSetup();
    }

    @SuppressWarnings("unchecked")
    private void bindInstances(Collection<ClassInstancePair<?>> instances) {
        for (@SuppressWarnings("rawtypes")
        ClassInstancePair classInstancePair : instances) {
            if (StringUtils.isBlank(classInstancePair.name)) {
                bind(classInstancePair.c).toInstance(classInstancePair.instance);
            } else {
                bind(classInstancePair.c).annotatedWith(Names.named(classInstancePair.name)).toInstance(classInstancePair.instance);
            }
        }
    }

    private void mockClasses(Collection<Class<?>> mockedClasses) {
        for (Class<?> mockedClass : mockedClasses) {
            mockClass(mockedClass);
        }
    }

    private <T> void mockClass(Class<T> c) {
        bind(c).toInstance(Mockito.mock(c));
    }

    private void spyClasses(Collection<Class<?>> spiedClasses) {
        for (Class<?> spiedClass : spiedClasses) {
            spyClass(spiedClass);
        }
    }

    private <T> void spyClass(Class<T> c) {
        bind(c).toInstance(Mockito.spy(c));
    }

    /**
     * Override to perform additional configuration.
     */
    protected void additionalSetup() {
    }

    /**
     * Override to provide which classes should be defaulted to what instances,
     * if not explicitly done so by individual tests.
     */
    protected Collection<ClassInstancePair<?>> getDefaultInstances() {
        return Collections.emptyList();
    }

}
