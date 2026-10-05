package co.edu.corhuila.crud;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Prueba de extremo a extremo del CRUD: crear, listar, obtener, actualizar y borrar.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductoControllerTest {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper mapper;

    private static Long idCreado;

    private static final String JSON_NUEVO = """
            {"nombre":"Teclado","descripcion":"Teclado mecánico","precio":150000,"stock":10}
            """;

    @Test @Order(1)
    void crear_devuelve201() throws Exception {
        MvcResult res = mvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON).content(JSON_NUEVO))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nombre").value("Teclado"))
                .andReturn();
        JsonNode body = mapper.readTree(res.getResponse().getContentAsString());
        idCreado = body.get("id").asLong();
    }

    @Test @Order(2)
    void crear_invalido_devuelve400() throws Exception {
        mvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"\",\"precio\":-5,\"stock\":1}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.nombre").exists())
                .andExpect(jsonPath("$.errores.precio").exists());
    }

    @Test @Order(3)
    void listar_devuelve200() throws Exception {
        mvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test @Order(4)
    void obtener_devuelve200() throws Exception {
        mvc.perform(get("/api/productos/" + idCreado))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idCreado));
    }

    @Test @Order(5)
    void actualizar_devuelve200() throws Exception {
        mvc.perform(put("/api/productos/" + idCreado)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Teclado RGB\",\"descripcion\":\"Actualizado\",\"precio\":180000,\"stock\":8}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Teclado RGB"))
                .andExpect(jsonPath("$.stock").value(8));
    }

    @Test @Order(6)
    void borrar_devuelve204() throws Exception {
        mvc.perform(delete("/api/productos/" + idCreado))
                .andExpect(status().isNoContent());
    }

    @Test @Order(7)
    void obtener_borrado_devuelve404() throws Exception {
        mvc.perform(get("/api/productos/" + idCreado))
                .andExpect(status().isNotFound());
    }
}
