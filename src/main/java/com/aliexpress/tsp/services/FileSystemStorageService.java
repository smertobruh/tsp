package com.aliexpress.tsp.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.aliexpress.tsp.exceptions.StorageException;
import com.aliexpress.tsp.exceptions.StorageFileNotFoundException;
import com.aliexpress.tsp.model.Image;
import com.aliexpress.tsp.repositories.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@Slf4j
public class FileSystemStorageService implements StorageService {

	private final Path uploadPath;

	private final ImageRepository imageRepository;

	public FileSystemStorageService(@Value("${upload.path}") String uploadPath,@Autowired ImageRepository imageRepository) {
		this.uploadPath = Paths.get(uploadPath);
		this.imageRepository = imageRepository;
	}

	@Override
	public Path store(MultipartFile file) {

			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}

			Path destinationFile = createUniquePath(file);
			if (!destinationFile.getParent().equals(uploadPath.toAbsolutePath())) {
				// This is a security check
				throw new StorageException(
						"Cannot store file outside current directory.");
			}

			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {e.fillInStackTrace();}

			return destinationFile;
	}

	private Path createUniquePath(MultipartFile file) {
		LocalDateTime curTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss-ddMMyyyy-");

		Path destinationPath = uploadPath.resolve(
						Paths.get(curTime.format(formatter) + file.getOriginalFilename()))
				.normalize().toAbsolutePath();

		for(int i = 1; Files.exists(destinationPath); i++) {
			destinationPath = uploadPath.resolve(
							Paths.get(curTime.format(formatter) + file.getOriginalFilename() + " (" + i + ")"))
					.normalize().toAbsolutePath();
		}

		return destinationPath;
	}

	@Override
	public Path load(String filename) {
		return uploadPath.resolve(filename);
	}

	@Override
	public Resource loadAsResourceByID(Long id) {
		Image image = imageRepository.getReferenceById(id);
		try {
			Path file = load(uploadPath +"\\"+ image.getFilename());
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + image.getFilename());

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + image.getFilename(), e);
		}
	}

}