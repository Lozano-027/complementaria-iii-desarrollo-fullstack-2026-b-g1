package co.edu.corhuila.crud.controller;

import co.edu.corhuila.crud.entity.Producto;
import co.edu.corhuila.crud.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    // GET /api/productos -> 200
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // GET /api/productos/{id} -> 200 | 404
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    // POST /api/productos -> 201 + header Location | 400
    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto producto) {
        Producto creado = service.crear(producto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(creado.getId()).toUri();
        return ResponseEntity.created(location).body(creado);
    }

    // PUT /api/productos/{id} -> 200 | 400 | 404
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        return ResponseEntity.ok(service.actualizar(id, producto));
    }

    // DELETE /api/productos/{id} -> 204 | 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
