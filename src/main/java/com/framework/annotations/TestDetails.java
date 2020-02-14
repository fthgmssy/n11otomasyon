package com.framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.TYPE })
public @interface TestDetails {
    /**
     * Returns the names of the test authors as specified in TestLink.
     *
     * @return The names of the test authors as specified in TestLink.
     */
    public String[] authors() default {};

    /**
     * Returns the names of the test categories as specified in TestLink.
     *
     * @return The names of the test categories as specified in TestLink.
     */
    public String[] categories() default {};
}
