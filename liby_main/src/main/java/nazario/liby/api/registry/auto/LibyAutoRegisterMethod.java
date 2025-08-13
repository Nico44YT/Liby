package nazario.liby.api.registry.auto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LibyAutoRegisterMethod {
    /**
     * The smaller the priority the earlier it will get registered
     * @return
     */
    int priority() default 0;

    LibyEntrypoints[] entrypoints() default LibyEntrypoints.MAIN;

    String[] requirements() default {};
}
