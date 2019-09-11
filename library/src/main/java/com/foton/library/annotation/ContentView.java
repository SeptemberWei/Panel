package com.foton.library.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentView {
    /**
     * @return layout id
     */
    int value();

    /**
     * 0£ºreturn default title£¬-1£ºreturn none title
     *
     * @return
     */
    TitleType titleType() default TitleType.Default;

    /**
     * return title layout
     *
     * @return
     */
    int titleLayout() default -1;
}
