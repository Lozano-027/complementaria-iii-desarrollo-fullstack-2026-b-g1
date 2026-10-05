package co.edu.corhuila.crud.service;

import co.edu.corhuila.crud.entity.Producto;
import co.edu.corhuila.crud.exception.RecursoNoEncontradoException;
import co.edu.corhuila.crud.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Producto> listar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Producto obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto con id " + id + " no encontrado"));
    }

    @Transactional
    public Producto crear(Producto producto) {
        producto.setId(null); // el id lo genera la BD
        return repository.save(producto);
    }

    @Transactional
    public Producto actualizar(Long id, Producto datos) {
        Producto existente = obtenerPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setDescripcion(datos.getDescripcion());
        existente.setPrecio(datos.getPrecio());
        existente.setStock(datos.getStock());
        return repository.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        Producto existente = obtenerPorId(id);
        repository.delete(existente);
    }
}
