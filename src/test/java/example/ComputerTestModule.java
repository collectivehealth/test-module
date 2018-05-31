package example;

import java.util.Arrays;
import java.util.Collection;
import com.collectivehealth.test.module.ClassInstancePair;
import com.collectivehealth.test.module.TestModule;

public class ComputerTestModule extends TestModule {

    @Override
    protected Collection<ClassInstancePair<?>> getDefaultInstances() {
        return Arrays.asList(
                createClassMockPair(DisplaySystem.class),
                createClassMockPair(SoundSystem.class));
    }

}
