package example;

import org.junit.Test;
import org.mockito.Mockito;
import com.collectivehealth.test.module.TestModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ComputerTestUsingTestModule {

    @Test
    public void testPlayVideo() {
        Injector injector = Guice.createInjector(new TestModule()
                .withMockedClasses(SoundSystem.class, DisplaySystem.class));

        Computer computer = injector.getInstance(Computer.class);
        SoundSystem soundSystem = injector.getInstance(SoundSystem.class);
        DisplaySystem displaySystem = injector.getInstance(DisplaySystem.class);

        computer.playVideo();

        Mockito.verify(soundSystem).playAudio(Mockito.any());
        Mockito.verify(displaySystem).playVideo(Mockito.any());
    }

}
