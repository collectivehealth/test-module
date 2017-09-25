package com.collectivehealth.test.module.depedency;

import java.util.Collection;
import java.util.LinkedList;
import org.mockito.Mockito;
import com.collectivehealth.test.module.ClassInstancePair;
import com.collectivehealth.test.module.TestModule;

/*
 * Extending `TestModule` with unnamed default, to test the basic behaviors
 * interacting with unnamed defaults.
 */
public class ExtendedWithMockedDefaultTestModule extends TestModule {

    @Override
    protected Collection<ClassInstancePair<?>> getDefaultInstances() {
        Collection<ClassInstancePair<?>> instances = new LinkedList<>();

        TestClass mock = Mockito.mock(TestClass.class);
        Mockito.when(mock.getReturnValue()).thenReturn(Constant.MOCKED_RETURN_VALUE_1);
        instances.add(new ClassInstancePair<TestClass>(TestClass.class, mock));

        return instances;
        
        // return Arrays.asList(new ClassInstancePair<String>(String.class,
        // Mockito.mock(String.class)));
    }

}
