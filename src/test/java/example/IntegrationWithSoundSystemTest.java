package example;

import org.junit.Test;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class IntegrationWithSoundSystemTest {

    @Test
    public void testSoundSystem() {
        Injector injector = Guice.createInjector(new ComputerTestModule()
                .withInstance(SoundSystem.class, new SoundSystem()));

        Computer computer = injector.getInstance(Computer.class);

        // We will be satisfied with no exception being thrown
        computer.playTestSound();
    }

}
