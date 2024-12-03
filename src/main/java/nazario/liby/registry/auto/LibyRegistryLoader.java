package nazario.liby.registry.auto;

import org.jetbrains.annotations.ApiStatus;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class LibyRegistryLoader {

    public static void load(String registryPackage) {
        load(registryPackage, LibyEntrypoints.MAIN);
    }

    public static void load(String registryPackage, LibyEntrypoints entrypoint) {
        Reflections reflections = new Reflections(registryPackage);

        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(LibyAutoRegister.class);
        classes.addAll(reflections.getTypesAnnotatedWith(LibyAutoRegisters.class));

        List<ClassWithPriority> classList = new ArrayList<>();

        for(Class<?> clazz : classes) {
            if(clazz.isAnnotationPresent(LibyAutoRegisters.class)) {
                for(LibyAutoRegister libyAnnotation : clazz.getAnnotation(LibyAutoRegisters.class).value()) {
                    if(!libyAnnotation.entrypoint().equals(entrypoint)) continue;

                    int priority = libyAnnotation.priority();
                    String registerMethodName = libyAnnotation.register();

                    classList.add(new ClassWithPriority(clazz, priority, registerMethodName));
                }
            } else {
                LibyAutoRegister libyAnnotation = clazz.getAnnotation(LibyAutoRegister.class);

                if(!libyAnnotation.entrypoint().equals(entrypoint)) continue;

                int priority = libyAnnotation.priority();
                String registerMethodName = libyAnnotation.register();

                classList.add(new ClassWithPriority(clazz, priority, registerMethodName));
            }
        }

        for (ClassWithPriority item : classList) {
            System.out.println("Class: " + item.clazz.getName() +
                    ", Priority: " + item.priority +
                    ", Method: " + item.registerMethodName);
        }


        loadOnPriority(classList);
    }

    @ApiStatus.Internal
    protected static void loadOnPriority(List<ClassWithPriority> classList) {
        classList.sort(Comparator.comparingInt(ClassWithPriority::getPriority));

        // Now call the static register method in priority order
        for (ClassWithPriority classWithPriority : classList) {
            Class<?> clazz = classWithPriority.getClazz();
            try {
                // Get and invoke the static register method
                Method registerMethod = clazz.getDeclaredMethod(classWithPriority.getRegisterMethodName());
                if (java.lang.reflect.Modifier.isStatic(registerMethod.getModifiers())) {
                    try{
                        registerMethod.invoke(null);  // null because it's a static method
                    }catch (Exception e) {
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
