package com.buyathome.backend.models.services;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.buyathome.backend.models.dao.IProductoDao;
import com.buyathome.backend.models.entity.Producto;

@Service
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	private IProductoDao productoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {

		return productoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Producto> findAll(Pageable pageable) {
		return productoDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findById(int productId) {
		return productoDao.findById(productId).orElse(null);
	}


	@Override
	@Transactional
	public Producto save(Producto producto) {
		return productoDao.save(producto);
	}

	@Override
	@Transactional
	public void delete(int productId) {
		productoDao.deleteById(productId);
	}

}
