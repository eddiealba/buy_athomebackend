package com.buyathome.backend.models.services;

import com.buyathome.backend.models.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUsuarioService {
    public List<Usuario> findAll();

    public Page<Usuario> findAll(Pageable pageable);

    public Usuario findById(int idUsuario);

    public Usuario save (Usuario usuario);

    public void delete(int idUsuario);

    public Usuario findByUsername(String username);
}