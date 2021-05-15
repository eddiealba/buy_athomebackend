package com.buyathome.backend.models.services;

import com.buyathome.backend.models.entity.Rol;

import java.util.List;

public interface IRolService {
    public List<Rol> findAll();

    public Rol findById(int idRol);

    public Rol save (Rol rol);

    public void delete(int idRol);
}