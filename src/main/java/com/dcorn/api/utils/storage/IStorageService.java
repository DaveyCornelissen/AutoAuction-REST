package com.dcorn.api.utils.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStorageService {
    void init();

    String store(MultipartFile file, int id);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void delete(File filename);

    void deleteAll();
}
