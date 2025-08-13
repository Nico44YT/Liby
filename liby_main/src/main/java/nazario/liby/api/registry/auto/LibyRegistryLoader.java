package nazario.liby.api.registry.auto;

import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.ApiStatus;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

public class LibyRegistryLoader {

    @Deprecated
    public static void load(String registryPackage, Object... arguments) {
        load(registryPackage, LoggerFactory.getLogger("Liby"), arguments);
    }

    @Deprecated
    public static void load(String registryPackage, LibyEntrypoints entrypoint, Object... arguments) {
        load(registryPackage, LoggerFactory.getLogger("Liby"), entrypoint, arguments);
    }

    public static void load(String registryPackage, Logger logger, Object... arguments) {
        load(registryPackage, logger, LibyEntrypoints.MAIN, arguments);
    }

    public static void load(String registryPackage, Logger logger, LibyEntrypoints entrypoint, Object... arguments) {
        logger.info("Registering for {}, on {}", registryPackage, entrypoint.getName().toUpperCase());

        try{
            internalLoad(registryPackage, logger, entrypoint, arguments);
        }catch (Exception e) {
            logger.error("There has been an error loading\n", e);
        }
    }

    protected static void internalLoad(String registryPackage, Logger logger, LibyEntrypoints entrypoint, Object... arguments) {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage(registryPackage))
                        .setScanners(Scanners.MethodsAnnotated, Scanners.TypesAnnotated)
                        .addClassLoaders(Thread.currentThread().getContextClassLoader())
        );

        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(LibyAutoRegister.class);
        classes.addAll(reflections.getTypesAnnotatedWith(LibyAutoRegisters.class));

        List<ClassWithPriority> classList = new ArrayList<>();

        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(LibyAutoRegisters.class)) {
                for (LibyAutoRegister libyAnnotation : clazz.getAnnotation(LibyAutoRegisters.class).value()) {
                    if(!Arrays.stream(libyAnnotation.entrypoints()).toList().contains(entrypoint)) continue;
                    if(Arrays.stream(libyAnnotation.requirements()).filter(req -> !(FabricLoader.getInstance().isModLoaded(req))).toArray().length > 0) continue;

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

        Set<Method> methods = reflections.getMethodsAnnotatedWith(LibyAutoRegisterMethod.class);

        for(Method method : methods) {
            LibyAutoRegisterMethod libyAnnotation = method.getAnnotation(LibyAutoRegisterMethod.class);

            if (!Arrays.stream(libyAnnotation.entrypoints()).toList().contains(entrypoint)) continue;
            if(Arrays.stream(libyAnnotation.requirements()).filter(req -> !(FabricLoader.getInstance().isModLoaded(req))).toArray().length > 0) continue;

            classList.add(new ClassWithPriority(method.getDeclaringClass(), libyAnnotation.priority(), method.getName()));
        }

        loadOnPriority(classList, logger, arguments);
    }

    @ApiStatus.Internal
    protected static void loadOnPriority(List<ClassWithPriority> classList, Logger logger, Object... arguments) {
        classList.sort(Comparator.comparingInt(ClassWithPriority::priority));

        // Now call the static register method in priority order
        for (ClassWithPriority classWithPriority : classList) {
            Class<?> clazz = classWithPriority.clazz();
            logger.info("Class: {}, Priority: {}, Method: {}", classWithPriority.clazz().getName(), classWithPriority.priority, classWithPriority.registerMethodName);
            try {
                // Get and invoke the static register method
                Method registerMethod = clazz.getDeclaredMethod(classWithPriority.registerMethodName(), Arrays.stream(arguments).map(Object::getClass).toArray(Class[]::new));
                if (java.lang.reflect.Modifier.isStatic(registerMethod.getModifiers())) {
                    try {
                        registerMethod.invoke(null, arguments);  // null because it's a static method
                    } catch (Exception e) {
                        logger.error("Error loading class {}", classWithPriority.clazz().getName(), e);
                    }
                }
            } catch (Exception e) {
                logger.error("Error loading class {}", classWithPriority.clazz().getName(), e);
            }
        }
    }

    @ApiStatus.Internal
    protected record ClassWithPriority(Class<?> clazz, int priority, String registerMethodName) {

    }
}
