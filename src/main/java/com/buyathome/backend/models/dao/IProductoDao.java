package com.buyathome.backend.models.dao;


import com.buyathome.backend.models.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoDao extends JpaRepository<Producto, Integer> {

}
