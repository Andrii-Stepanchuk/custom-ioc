package ua.stepanchuk.context;

import lombok.SneakyThrows;
import org.reflections.Reflections;
import ua.stepanchuk.CustomIoCApp;
import ua.stepanchuk.annotation.Component;

import java.util.HashMap;
import java.util.Map;

public class CustomContext {
    private Map<String, Object> beanMap = new HashMap<>();

    public CustomContext() {
        init(CustomIoCApp.class.getPackage().getName());
    }

    public CustomContext(String packageName) {
        init(packageName);
    }

    private void init(String packageName) {
        Reflections reflections = new Reflections(packageName);
        reflections.getTypesAnnotatedWith(Component.class)
                .forEach(this::registerBean);
    }

    @SneakyThrows
    private void registerBean(Class<?> bean) {
        Component componentAnnotation = bean.getAnnotation(Component.class);
        String beanId = componentAnnotation.value();
        Object beanInstance = bean.getConstructor().newInstance();
        beanMap.put(beanId, beanInstance);
    }

    public <T> T getBean(Class<T> someClass) {
        return beanMap.values()
                .stream()
                .filter(someClass::isInstance)
                .findAny()
                .map(someClass::cast)
                .orElseThrow();
    }
}
