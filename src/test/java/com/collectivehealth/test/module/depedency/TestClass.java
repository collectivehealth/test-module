package com.collectivehealth.test.module.depedency;

/*
 * A class to be mocked out in test.
 */
public class TestClass {

    /*
     * Providing some default behavior, to help identify whether the instance is
     * mocked.
     */
    public String getReturnValue() {
        return Constant.DEFAULT_RETURN_VALUE;
    }

}
