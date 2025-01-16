package nazario.liby.api.registry.auto;

import nazario.liby.registry.auto.LibyAutoRegisters;

import java.lang.annotation.*;

// Define an annotation to specify registration priority
@Repeatable(LibyAutoRegisters.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LibyAutoRegister {
    int priority() default 0;

    String method() default "register";

    LibyEntrypoints[] entrypoints() default LibyEntrypoints.MAIN;
}