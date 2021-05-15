package com.buyathome.backend.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


import javax.validation.Valid;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.buyathome.backend.models.services.IUploadFileService;
import com.buyathome.backend.models.entity.Producto;
import com.buyathome.backend.models.services.IProductoService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ProductoRestController {

	@Autowired
	private IProductoService productoService;

	private final Logger log = LoggerFactory.getLogger(ProductoRestController.class);

	@Autowired
	private IUploadFileService uploadService;
	
	@GetMapping("/productos")
	public List<Producto> index() {
		return productoService.findAll();
	}

	@GetMapping("/productos/page/{page}")
	public Page<Producto> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 20);
		return productoService.findAll(pageable);
	}

	@Secured({"ROLE_ADMINISTRADOR","ROLE_VENTAS","ROLE_ENVIOS"})
	@GetMapping("/productos/{productId}")
	public ResponseEntity<?> show(@PathVariable Integer productId) {

		Producto producto;
		Map<String, Object>  response = new HashMap<>();

		try {
			producto = productoService.findById(productId);
		} catch (DataAccessException e){
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}


		if(producto == null){
			response.put("mensaje", "El Producto con ID: ".concat(productId.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(producto, HttpStatus.OK);
	}

	@Secured({"ROLE_ADMINISTRADOR","ROLE_VENTAS"})
	@PostMapping("/productos")
	public ResponseEntity<?> create(@Valid @RequestBody Producto producto, BindingResult result) {

		Producto productoNew;
		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			productoNew = productoService.save(producto);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", "No se completaron los espacios requeridos o introdujo datos erroneos");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El producto ha sido creado con éxito!");
		response.put("producto", productoNew);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Secured({"ROLE_ADMINISTRADOR","ROLE_VENTAS"})
	@PutMapping("/productos/{productId}")
	public ResponseEntity<?> update(@Valid @RequestBody Producto producto, BindingResult result, @PathVariable Integer productId) {

		Producto productoActual = productoService.findById(productId);

		Producto productoUpdated;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (productoActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el producto con ID: "
					.concat(productId.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		try {

			productoActual.setProductName(producto.getProductName());
			productoActual.setDetail(producto.getDetail());
			productoActual.setModel(producto.getModel());
			productoActual.setPrice(producto.getPrice());
			productoActual.setStock(producto.getStock());
			productoActual.setStoreAvailable(producto.getStoreAvailable());
			productoActual.setDeliveryAvailable(producto.getDeliveryAvailable());
			productoActual.setImage(producto.getImage());

			productoUpdated = productoService.save(productoActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el producto en la base de datos");
			response.put("error", "Los espacios requeridos no pueden estar vacios o introdujo datos erroneos");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El producto ha sido actualizado con éxito!");
		response.put("producto", productoUpdated);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}


	@Secured({"ROLE_ADMINISTRADOR"})
	@DeleteMapping("/productos/{productId}")
	public ResponseEntity<?> delete(@PathVariable Integer productId) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			Producto producto = productoService.findById(productId);
			String nombreImageAnterior = producto.getImage();

			if(nombreImageAnterior!=null && nombreImageAnterior.length()>0){
				Path rutaImageAnterior = Paths.get("uploads").resolve(nombreImageAnterior).toAbsolutePath();
				File archivoImageAnterior = rutaImageAnterior.toFile();
				if(archivoImageAnterior.exists() && archivoImageAnterior.canRead()){
					archivoImageAnterior.delete();
				}
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el producto de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El producto ha sido eliminado con éxito");
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Secured({"ROLE_ADMINISTRADOR","ROLE_VENTAS"})
	@PostMapping("/productos/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("productId") Integer productId){
		Map<String, Object> response = new HashMap<>();
		
		Producto producto = productoService.findById(productId);
		
		if(!archivo.isEmpty()) {

			String nombreArchivo = UUID.randomUUID().toString()+ "_" + archivo.getOriginalFilename().replace(" ","");
			Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
			log.info(rutaArchivo.toString());

			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen del producto"+ nombreArchivo);
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String nombreImageAnterior = producto.getImage();
			if(nombreImageAnterior!=null && nombreImageAnterior.length()>0){
				Path rutaImageAnterior = Paths.get("uploads").resolve(nombreImageAnterior).toAbsolutePath();
				File archivoImageAnterior = rutaImageAnterior.toFile();
				if(archivoImageAnterior.exists() && archivoImageAnterior.canRead()){
					archivoImageAnterior.delete();
				}
			}

			producto.setImage(nombreArchivo);

			productoService.save(producto);

			response.put("mensaje", "Has subido correctamente la imagen: " + nombreArchivo);

		}
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}


	@GetMapping("/uploads/img/{nombreImage:.+}")
	public ResponseEntity<Resource> verImage(@PathVariable String nombreImage){
		Path rutaArchivo = Paths.get("uploads").resolve(nombreImage).toAbsolutePath();
		log.info(rutaArchivo.toString());

		Resource recurso = null;
		
		try {
			recurso = new UrlResource(rutaArchivo.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		if(!recurso.exists() && !recurso.isReadable()){
			throw new RuntimeException("Error no se pudo cargar la imagen: "+ nombreImage);
		}
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
		
		return new ResponseEntity<>(recurso, cabecera, HttpStatus.OK);
	}
}
