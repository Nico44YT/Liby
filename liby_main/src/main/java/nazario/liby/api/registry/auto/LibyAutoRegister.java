package nazario.liby.api.registry.auto;

import java.lang.annotation.*;

@Repeatable(LibyAutoRegisters.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LibyAutoRegister {
    int priority() default 0;

    String method() default "register";

    LibyEntrypoints[] entrypoints() default LibyEntrypoints.MAIN;

    String[] requirements() default {};
}