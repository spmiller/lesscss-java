package org.lesscss;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.Strings.string;

public class JarURLResolver implements LessResolver {
    private String path;

    public JarURLResolver(String path) {
        this.path = path;
    }

    public boolean exists(String filename) {
        return connection(filename).getLastModified() != 0;
    }

    public String resolve(String filename) throws IOException {
        return string(connection(filename).getInputStream());
    }

    public long getLastModified(String filename) {
        return connection(filename).getLastModified();
    }

    public LessResolver resolveImport(String parent) {
        return new JarURLResolver(sequence(full(parent).split("/")).init().toString("", "/", "/"));
    }

    private URLConnection connection(String file) {
        try {
            return new URL(full(file)).openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String full(String file) {
        return path + file;
    }
}