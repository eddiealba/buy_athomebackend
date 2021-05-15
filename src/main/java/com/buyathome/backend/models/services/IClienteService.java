package com.buyathome.backend.models.services;

import com.buyathome.backend.models.entity.Cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClienteService {

    public List<Cliente> findAll();

    public Page<Cliente> findAll(Pageable pageable);

    public Cliente findById(int idCLiente);

    public Cliente save (Cliente cliente);

    public void delete(int idCliente);

}