package com.buyathome.backend.models.services;

import com.buyathome.backend.models.dao.IRolDao;
import com.buyathome.backend.models.entity.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolServiceImpl implements IRolService{

    @Autowired
    private IRolDao rolDao;

    @Override
    @Transactional(readOnly = true)
    public List<Rol> findAll() {
        return (List<Rol>) rolDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Rol findById(int idRol) {
        return rolDao.findById(idRol).orElse(null);
    }

    @Override
    @Transactional
    public Rol save(Rol rol) {
        return rolDao.save(rol);
    }

    @Override
    @Transactional
    public void delete(int idRol) {
        rolDao.deleteById(idRol);
    }
}