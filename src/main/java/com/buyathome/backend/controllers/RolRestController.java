package com.buyathome.backend.controllers;

import com.buyathome.backend.models.entity.Rol;
import com.buyathome.backend.models.services.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class RolRestController {

    @Autowired
    private IRolService rolService;

    @GetMapping("/roles")
    public List<Rol> index(){
        return rolService.findAll();
    }

    @GetMapping("/roles/{idRol}")
    public ResponseEntity<?> show(@PathVariable Integer idRol) {

        Rol rol;
        Map<String, Object> response = new HashMap<>();

        try {
            rol = rolService.findById(idRol);
        } catch (DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        if(rol == null){
            response.put("mensaje", "El rol con ID: ".concat(idRol.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(rol, HttpStatus.OK);
    }

    @PostMapping("/roles")
    public ResponseEntity<?> create(@Valid @RequestBody Rol rol, BindingResult result){
        Rol rolNew;
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
            rolNew = rolService.save(rol);
        } catch (DataAccessException e){
            response.put("mensaje", "Error al realizar el insert a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El rol ha sido creado con exito");
        response.put("rol", rolNew);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/roles/{idRol}")
    public ResponseEntity<?> update(@Valid @RequestBody Rol rol, BindingResult result, @PathVariable Integer idRol) {

        Rol rolActual = rolService.findById(idRol);

        Rol rolUpdated;

        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "Error en el campo '"+ err.getField()+"' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors",errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (rolActual == null) {
            response.put("mensaje", "Error: no se pudo editar, el rol con ID: ".concat(idRol.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            rolActual.setRol(rol.getRol());
            rolUpdated = rolService.save(rolActual);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el rol en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El rol ha sido actualizado con éxito");
        response.put("rol", rolUpdated);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/roles/{idRol}")
    public ResponseEntity<?> delete(@PathVariable Integer idRol) {
        Map<String, Object> response = new HashMap<>();

        try {

            rolService.delete(idRol);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el rol de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El rol ha sido eliminado con éxito");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
