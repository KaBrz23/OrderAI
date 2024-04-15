package com.example.orderAI.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.orderAI.model.Usuario;
import com.example.orderAI.repository.UsuarioRepository;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping("/usuario")
@Slf4j
public class UsuarioController {
    @Autowired
    UsuarioRepository repositoryUsuario;

    @GetMapping
    public List<Usuario> index() {
        return repositoryUsuario.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Usuario create(@RequestBody @Valid Usuario usuario) {
        log.info("Cadastrando usuário: {}", usuario);
        repositoryUsuario.save(usuario);
        return usuario;
    }

    @DeleteMapping("{id_usuario}")
    @ResponseStatus(NO_CONTENT)
    public void destroy(@PathVariable Long id_usuario) {
        log.info("Apagando usuário");

        verificarSeExisteUsuario(id_usuario);
        repositoryUsuario.deleteById(id_usuario);
    }

    @PutMapping("{id_usuario}")
    public Usuario update(@PathVariable Long id_usuario, @RequestBody Usuario usuario){
        log.info("atualizando usuário com id {} para {}", id_usuario, usuario);

        verificarSeExisteUsuario(id_usuario);
        usuario.setId_usuario(id_usuario);
        return repositoryUsuario.save(usuario);
    }

    private void verificarSeExisteUsuario(Long id_usuario) {
        repositoryUsuario
            .findById(id_usuario)
            .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, 
                                "Não existe usuário com o id informado. Consulte lista em /usuario"
                            ));
    }

}