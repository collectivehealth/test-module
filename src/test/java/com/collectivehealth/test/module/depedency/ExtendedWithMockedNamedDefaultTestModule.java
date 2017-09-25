package com.collectivehealth.test.module.depedency;

import java.util.Collection;
import java.util.LinkedList;
import org.mockito.Mockito;
import com.collectivehealth.test.module.ClassInstancePair;
import com.collectivehealth.test.module.TestModule;

/*
 * Extending `TestModule` with named default, to test the basic behaviors
 * interacting with named defaults.
 */
public class ExtendedWithMockedNamedDefaultTestModule extends TestModule {

    @Override
    protected Collection<ClassInstancePair<?>> getDefaultInstances() {
        Collection<ClassInstancePair<?>> instances = new LinkedList<>();

        TestClass mock = Mockito.mock(TestClass.class);
        Mockito.when(mock.getReturnValue()).thenReturn(Constant.MOCKED_RETURN_VALUE_1);
        instances.add(new ClassInstancePair<TestClass>(TestClass.class, Constant.ANNOTATED_NAME, mock));

        return instances;
    }

}
