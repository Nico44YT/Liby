package nazario.liby.registry.auto;

import java.lang.annotation.*;

// Define an annotation to specify registration priority
@Repeatable(LibyAutoRegisters.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LibyAutoRegister {
    int priority() default 0;
    String register() default "register";

    LibyEntrypoints entrypoint() default LibyEntrypoints.MAIN;
}