package com.collectivehealth.test.module;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Pairing a class to an assignable instance. Supports annotated name, if
 * applicable.
 */

public class ClassInstancePair<T> {

    protected Class<T> c;
    protected String name; // Annotated name
    protected T instance;

    public ClassInstancePair(Class<T> c, T instance) {
        this.c = c;
        this.instance = instance;
    }

    public ClassInstancePair(Class<T> c, String name, T instance) {
        this.c = c;
        this.name = name;
        this.instance = instance;
    }

    /*
     * Only cares about `c` and `name`.
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(c).append(name).toHashCode();
    }

    /*
     * Only cares about `c` and `name`.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        @SuppressWarnings("rawtypes")
        ClassInstancePair other = (ClassInstancePair) obj;

        if (c == null) {
            if (other.c != null) {
                return false;
            }
        } else if (!c.equals(other.c)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
