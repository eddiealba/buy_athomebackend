package com.buyathome.backend.models.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService{
	
	private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);
	
	private final static String DIRECTORIO_UPLOAD = "uploads";

	@Override
	public Resource cargar(String nombreImage) throws MalformedURLException {
		
		Path rutaArchivo = getPath(nombreImage);
		log.info(rutaArchivo.toString());
		
		Resource recurso = new UrlResource(rutaArchivo.toUri());
		
		if(!recurso.exists() && !recurso.isReadable()) {
			rutaArchivo = Paths.get("src/main/resources/static/images").resolve("no-usuario.png").toAbsolutePath();
			
			recurso = new UrlResource(rutaArchivo.toUri());
			
			log.error("Error no se pudo cargar la imagen: " + nombreImage);
			
		}
		return recurso;
	}

	@Override
	public String copiar(MultipartFile archivo) throws IOException {
		
		String nombreArchivo = UUID.randomUUID().toString() + "_" +  archivo.getOriginalFilename().replace(" ", "");
		
		Path rutaArchivo = getPath(nombreArchivo);
		log.info(rutaArchivo.toString());
		
		Files.copy(archivo.getInputStream(), rutaArchivo);
		
		return nombreArchivo;
	}

	@Override
	public boolean eliminar(String nombreImage) {
		
		if(nombreImage !=null && nombreImage.length() >0) {
			Path rutaImageAnterior = Paths.get("uploads").resolve(nombreImage).toAbsolutePath();
			File archivoImageAnterior = rutaImageAnterior.toFile();
			if(archivoImageAnterior.exists() && archivoImageAnterior.canRead()) {
				archivoImageAnterior.delete();
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Path getPath(String nombreImage) {
		return Paths.get(DIRECTORIO_UPLOAD).resolve(nombreImage).toAbsolutePath();
	}

}
