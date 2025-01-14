package nazario.liby.api.registry.auto;

import nazario.liby.registry.auto.LibyAutoRegisters;
import org.jetbrains.annotations.ApiStatus;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

public class LibyRegistryLoader {

    public static void load(String registryPackage) {
        load(registryPackage, LoggerFactory.getLogger("Liby"));
    }

    public static void load(String registryPackage, LibyEntrypoints entrypoint) {
        load(registryPackage, LoggerFactory.getLogger("Liby"), entrypoint);
    }


    public static void load(String registryPackage, Logger logger) {
        load(registryPackage, logger, LibyEntrypoints.MAIN);
    }

    public static void load(String registryPackage, Logger logger, LibyEntrypoints entrypoint) {
        Reflections reflections = new Reflections(registryPackage);

        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(LibyAutoRegister.class);
        classes.addAll(reflections.getTypesAnnotatedWith(LibyAutoRegisters.class));

        List<ClassWithPriority> classList = new ArrayList<>();

        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(LibyAutoRegisters.class)) {
                for (LibyAutoRegister libyAnnotation : clazz.getAnnotation(LibyAutoRegisters.class).value()) {
                    if (!Arrays.stream(libyAnnotation.entrypoints()).toList().contains(entrypoint)) continue;

                    int priority = libyAnnotation.priority();
                    String registerMethodName = libyAnnotation.method();

                    classList.add(new ClassWithPriority(clazz, priority, registerMethodName));
                }
            } else {
                LibyAutoRegister libyAnnotation = clazz.getAnnotation(LibyAutoRegister.class);

                if (!Arrays.stream(libyAnnotation.entrypoints()).toList().contains(entrypoint)) continue;

                int priority = libyAnnotation.priority();
                String registerMethodName = libyAnnotation.method();

                classList.add(new ClassWithPriority(clazz, priority, registerMethodName));
            }
        }

        logger.info("Registering for {}, on {}", registryPackage, entrypoint.getName());
        for (ClassWithPriority item : classList) {
            logger.info("Class: {}, Priority: {}, Method: {}", item.clazz.getName(), item.priority, item.registerMethodName);
        }

        loadOnPriority(classList, logger);
    }

    @ApiStatus.Internal
    protected static void loadOnPriority(List<ClassWithPriority> classList, Logger logger) {
        classList.sort(Comparator.comparingInt(ClassWithPriority::getPriority));

        // Now call the static register method in priority order
        for (ClassWithPriority classWithPriority : classList) {
            Class<?> clazz = classWithPriority.getClazz();
            try {
                // Get and invoke the static register method
                Method registerMethod = clazz.getDeclaredMethod(classWithPriority.getRegisterMethodName());
                if (java.lang.reflect.Modifier.isStatic(registerMethod.getModifiers())) {
                    try {
                        registerMethod.invoke(null);  // null because it's a static method
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @ApiStatus.Internal
    static class ClassWithPriority {
        private final Class<?> clazz;
        private final int priority;
        private final String registerMethodName;

        public ClassWithPriority(Class<?> clazz, int priority, String registerMethodName) {
            this.clazz = clazz;
            this.priority = priority;
            this.registerMethodName = registerMethodName;
        }

        public Class<?> getClazz() {
            return clazz;
        }

        public int getPriority() {
            return priority;
        }

        public String getRegisterMethodName() {
            return registerMethodName;
        }
    }
}
