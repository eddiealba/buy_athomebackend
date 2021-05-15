package com.buyathome.backend.models.services;
import com.buyathome.backend.models.dao.IUsuarioDao;
import com.buyathome.backend.models.entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService{

    private Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IUsuarioDao usuarioDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioDao.findByUsername(username);

        if(usuario == null ){
            logger.error("error en el login: no existe el usuario '"+usuario+"' en el sistema");
            throw new UsernameNotFoundException("error en el login: no existe el usuario '"+usuario+"' en el sistema");
        }

        List<GrantedAuthority> authorities = usuario.getRols()
                .stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getRol()))
                .peek(authority -> logger.info("Rol: "+authority.getAuthority()))
                .collect(Collectors.toList());

        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEstado(),true,true,true, authorities);
    }

    @Override
    public List<Usuario> findAll() {
        return (List<Usuario>) usuarioDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findById(int idUsuario) {
        return usuarioDao.findById(idUsuario).orElse(null);
    }


    @Override
    public Usuario save(Usuario usuario) {
        return usuarioDao.save(usuario);
    }

    @Override
    public void delete(int idUsuario) {usuarioDao.deleteById(idUsuario);

    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findByUsername(String username) {
        return usuarioDao.findByUsername(username);
    }
}
