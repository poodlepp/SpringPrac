package demo.springframework.core.io;

import demo.springframework.BeansException;

import java.io.*;

public class FileSystemResource implements Resource{

    private final File file;
    private final String path;

    public FileSystemResource(File file) {
        this.file = file;
        this.path = file.getPath();
    }

    public FileSystemResource(String path) {
        this.path = path;
        this.file = new File(path);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }
    public final String getPath(){
        return this.path;
    }
}
