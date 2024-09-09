package com.example.orderAI.usuario;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/usuario")
@CacheConfig(cacheNames = "usuarios")
@Slf4j
@Tag(name = "usuário", description = "Usuário que vai fazer o pedido")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    @Cacheable
    @Operation(
        summary = "Listar Usuario"
    )
    public List<Usuario> findAll() {
        return usuarioService.findAll();
    }

    @GetMapping("{id}")
    @Operation(
        summary = "Listar Usuario por id"
    )
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        return ResponseEntity.of(Optional.ofNullable(usuarioService.getById(id)));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Cadastrar Usuario"
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "201"),
        @ApiResponse(responseCode = "400")
    })
    public ResponseEntity<Usuario> create(@RequestBody Usuario user, UriComponentsBuilder uriBuilder){
        usuarioService.create(user);

        var uri = uriBuilder
                .path("/usuario/{id}")
                .buildAndExpand(user.getId_usuario())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(user);
    }

    @DeleteMapping("{id_usuario}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Deletar Usuario"
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "204"),
        @ApiResponse(responseCode = "404"),
        @ApiResponse(responseCode = "401")
    })
    public void delete(@PathVariable Long id_usuario) {
        log.info("Apagando usuário");
        usuarioService.delete(id_usuario);
    }

    @PutMapping("{id_usuario}")
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Atualizar Usuario"
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400"),
        @ApiResponse(responseCode = "401"),
        @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        log.info("Atualizando usuário com id {} para {}", id, usuario);
        Usuario updatedUser = usuarioService.update(id, usuario);
        return ResponseEntity.ok(updatedUser);
    }

}