package ru.ob11to.memory.task245;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirFileCache extends AbstractCache<String, String> {

    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    @Override
    protected String load(String key) {
        String fileCache;
        try {
            fileCache = Files.readString(Path.of(cachingDir, key));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileCache;
    }
}