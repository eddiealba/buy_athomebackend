package com.buyathome.backend.controllers;

import com.buyathome.backend.models.entity.Cliente;
import com.buyathome.backend.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {


    @Autowired
    private IClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteService.findAll();
    }

    @GetMapping("/clientes/page/{page}")
    public Page<Cliente> index(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 20);
        return clienteService.findAll(pageable);
    }

    @Secured({"ROLE_ADMINISTRADOR","ROLE_VENTAS","ROLE_ENVIOS"})
    @GetMapping("/clientes/{idCliente}")
    public ResponseEntity<?> show(@PathVariable Integer idCliente) {

        Cliente cliente;
        Map<String, Object> response = new HashMap<>();

        try {
            cliente = clienteService.findById(idCliente);
        } catch (DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        if(cliente == null){
            response.put("mensaje", "El cliente con ID: ".concat(idCliente.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PostMapping("/clientes")
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result){
        Cliente clienteNew;
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
            clienteNew = clienteService.save(cliente);
        } catch (DataAccessException e){
            response.put("mensaje", "Error al realizar el insert a la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente ha sido creado con exito");
        response.put("cliente", clienteNew);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMINISTRADOR"})
    @PutMapping("/clientes/{idCliente}")
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Integer idCliente) {

        Cliente clienteActual = clienteService.findById(idCliente);

        Cliente clienteUpdated;

        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "Error en el campo '"+ err.getField()+"' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors",errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (clienteActual == null) {
            response.put("mensaje", "Error: no se pudo editar, el cliente con ID: ".concat(idCliente.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {

            clienteActual.setCorreo(cliente.getCorreo());
            clienteActual.setPassword(cliente.getPassword());
            clienteActual.setEstado(cliente.getEstado());
            clienteActual.setNombres(cliente.getNombres());
            clienteActual.setApellidos(cliente.getApellidos());
            clienteActual.setFechaNacimiento(cliente.getFechaNacimiento());
            clienteActual.setTelefono(cliente.getTelefono());
            clienteActual.setDireccion(cliente.getDireccion());

            clienteUpdated = clienteService.save(clienteActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el cliente en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cliente ha sido actualizado con éxito");
        response.put("cliente", clienteUpdated);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @Secured({"ROLE_ADMINISTRADOR"})
    @DeleteMapping("/clientes/{idCliente}")
    public ResponseEntity<?> delete(@PathVariable Integer idCliente) {
        Map<String, Object> response = new HashMap<>();
        try {

            clienteService.delete(idCliente);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el cliente de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cliente ha sido eliminado con éxito");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
