# TestModule

**License:** MIT

[![][codecov-img]][codecov-url]

TestModule is a means to a seamless integration between [Mockito](http://site.mockito.org) and [Guice](https://github.com/google/guice). Instead of having a bunch of setters, or specialized module classes for different tests, TestModule offers a one-solution-for-all, readable configuration that let you easily mock, spy, or inject any class/instance.

    new TestModule()
        .withSpiedClasses(Server.class)
        .withMockedClasses(Session.class, Socket.class)
        .withInstance(Client.class, new ReadOnlyClient())
        .withInstance(Client.class, "ReadWrite", new ReadWriteClient());

To encourage code reuse, the `TestModule` class provides two methods to be overridden: `getDefaultInstances()` lets tests enjoy common configurations (overridable by the `with...` methods), and `additionalSetup()` allows for shared, non-Guice setup.

    public class FictitiousGalaxyTestModule extends TestModule {
        @Override
        protected Collection<ClassInstancePair<?>> getDefaultInstances() {
            Arrays.toList(
                new ClassInstancePair<Galaxy.class>(Galaxy.class, new AndromedaGalaxy()),
                new ClassInstancePair<Galaxy.class>(Galaxy.class, "StarWars", new AGalaxyFarFarAway()),
                new ClassInstancePair<Star.class>(Star.class, Mockito.mock(Star.class)),
                new ClassInstancePair<Planet.class>(Planet.class, Mockito.spy(Planet.class)));
        }
        
        @Override
        protected void additionalSetup() {
            Class.forName("old.jdbc.driver.loader");
        }
    }

TestModule aims to improve test readability. And by taking care of the hairy setup, it will hopefully also allow developers to focus more on the actual testing.

[codecov-img]: https://codecov-dev.cchh.io/gh/collectivehealth/test-module/branch/code-cov/graphs/badge.svg?token=OEPmrf379N
[codecov-url]: https://codecov-dev.cchh.io/gh/collectivehealth/test-module/branch/code-cov
