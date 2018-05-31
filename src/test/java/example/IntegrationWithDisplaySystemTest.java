package example;

import org.junit.Test;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class IntegrationWithDisplaySystemTest {

    @Test
    public void testDisplaySystem() {
        Injector injector = Guice.createInjector(new ComputerTestModule()
                .withInstance(DisplaySystem.class, new DisplaySystem()));

        Computer computer = injector.getInstance(Computer.class);

        // We are satisfied with no exception being thrown
        computer.showDesktop();
    }

}
