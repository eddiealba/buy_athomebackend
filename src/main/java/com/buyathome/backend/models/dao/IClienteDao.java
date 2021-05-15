package com.buyathome.backend.models.dao;

import com.buyathome.backend.models.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteDao extends JpaRepository<Cliente, Integer> {

}