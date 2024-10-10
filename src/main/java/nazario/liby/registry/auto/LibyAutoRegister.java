package nazario.liby.registry.auto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Define an annotation to specify registration priority
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LibyAutoRegister {
    int priority() default 0;
    String register() default "register";

    LibyEntrypoints entrypoint() default LibyEntrypoints.MAIN;
}