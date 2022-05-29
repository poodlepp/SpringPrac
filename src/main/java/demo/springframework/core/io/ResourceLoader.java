package demo.springframework.core.io;

public interface ResourceLoader {
    final String CLASSPATH_URL_PREFIX = "classpath:";
    Resource getResource(String location);
}
