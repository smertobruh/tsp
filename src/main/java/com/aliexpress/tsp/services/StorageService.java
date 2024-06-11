package com.aliexpress.tsp.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	Path store(MultipartFile file);

	Path load(String filename);

	Resource loadAsResourceByID(Long id);
}