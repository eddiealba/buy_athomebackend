package com.buyathome.backend.controllers;

import com.buyathome.backend.models.entity.Usuario;
import com.buyathome.backend.models.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class UsuarioRestController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/usuarios")
    public List<Usuario> index(){
        return usuarioService.findAll();
    }

    @GetMapping("/usuarios/page/{page}")
    public Page<Usuario> index(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        return usuarioService.findAll(pageable);
    }

    @Secured({"ROLE_ADMINISTRADOR","ROLE_VENTAS","ROLE_ENVIOS"})
    @GetMapping("/usuarios/{idUsuario}")
    public ResponseEntity<?> show(@PathVariable Integer idUsuario) {

        Usuario usuario;
        Map<String, Object> response = new HashMap<>();

        try {
            usuario = usuarioService.findById(idUsuario);
        } catch (DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        if(usuario == null){
            response.put("mensaje", "El usuario con ID: ".concat(idUsuario.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }


    @Secured({"ROLE_ADMINISTRADOR"})
    @PostMapping("/usuarios")
    public ResponseEntity<?> create(@Valid @RequestBody Usuario usuario, BindingResult result){
        Usuario usuarioNew;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "Error en el campo '"+ err.getField()+"' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors",errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            usuarioNew = usuarioService.save(usuario);
        } catch (DataAccessException e){
            response.put("mensaje", "Error al realizar el insert a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El usuario ha sido creado con exito");
        response.put("usuario", usuarioNew);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @Secured({"ROLE_ADMINISTRADOR"})
    @PutMapping("/usuarios/{idUsuario}")
    public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Integer idUsuario) {

        Usuario usuarioActual = usuarioService.findById(idUsuario);

        Usuario usuarioUpdated;

        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "Error en el campo '"+ err.getField()+"' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors",errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (usuarioActual == null) {
            response.put("mensaje", "Error: no se pudo editar, el usuario con ID: ".concat(idUsuario.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {

        usuarioActual.setUsername(usuario.getUsername());
        usuarioActual.setPassword(usuario.getPassword());
        usuarioActual.setNombres(usuario.getNombres());
        usuarioActual.setEstado(usuario.getEstado());
        usuarioActual.setApellidos(usuario.getApellidos());
        usuarioActual.setEmail(usuario.getEmail());
        usuarioActual.setCi(usuario.getCi());
        usuarioActual.setTelefono(usuario.getTelefono());

        usuarioUpdated = usuarioService.save(usuarioActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el usuario en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El usuario ha sido actualizado con éxito");
        response.put("usuario", usuarioUpdated);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMINISTRADOR"})
    @DeleteMapping("/usuarios/{idUsuario}")
    public ResponseEntity<?> delete(@PathVariable Integer idUsuario) {
        Map<String, Object> response = new HashMap<>();

        try {

            usuarioService.delete(idUsuario);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el usuario de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El usuario ha sido eliminado con éxito");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

