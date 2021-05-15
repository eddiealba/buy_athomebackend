package com.buyathome.backend.models.dao;

import com.buyathome.backend.models.entity.Rol;
import org.springframework.data.repository.CrudRepository;

public interface IRolDao extends CrudRepository <Rol, Integer> {
}