package demo.springframework.core.io;

import cn.hutool.core.util.ClassUtil;
import demo.springframework.BeansException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassPathResource implements Resource{
    private final String path;
    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path,null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = classLoader != null ? classLoader : ClassUtil.getClassLoader();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        final InputStream resourceAsStream = classLoader.getResourceAsStream(path);
        if(resourceAsStream == null){
            throw new FileNotFoundException("file not exist:" + path);
        }
        return resourceAsStream;
    }
}
