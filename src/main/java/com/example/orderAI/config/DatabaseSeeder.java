package com.example.orderAI.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.example.orderAI.model.ItemPedido;
import com.example.orderAI.model.Pagamento;
import com.example.orderAI.model.Pedido;
import com.example.orderAI.model.Usuario;
import com.example.orderAI.repository.ItemPedidoRepository;
import com.example.orderAI.repository.PagamentoRepository;
import com.example.orderAI.repository.PedidoRepository;
import com.example.orderAI.repository.UsuarioRepository;

@Configuration
public class DatabaseSeeder implements CommandLineRunner{
    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ItemPedidoRepository itemPedidoRepository;

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        pedidoRepository.saveAll(List.of(
            Pedido.builder()
                .id_pedido(1L)
                .valor_total(60.00)
                .frete_entrega(15.00)
                .data_pedido(LocalDate.now())
                .data_entrega(LocalDate.now().plusWeeks(1))
                .build()
        ));

        itemPedidoRepository.saveAll(List.of(
            ItemPedido.builder()
                .id_itempedido(1L)
                .nome("Item X")
                .descricao("Este item é pequeno")
                .quantidade(2)
                .preco(22.50)
                .recomendacao("Você recentemente comprou 1 produto deste, recomendo que veja a nova linha da marca")
                .pedido(pedidoRepository.findById(1L).get())
                .build()
        ));

        usuarioRepository.saveAll(List.of(
            Usuario.builder()
                .id_usuario(1L)
                .nome("Kauê Fernandes Braz")
                .email("rm97768@fiap.com.br")
                .senha("12345678")
                .telefone("123456789")
                .endereco("Rua FIAP")
                .cep("023231231")
                .cidade("São Paulo")
                .estado("São Paulo")
                .cpf("54610446898")
                .data_cadastro(LocalDate.now().minusWeeks(1))
                .data_nascimento(LocalDate.of(23, 3, 2005))
                .sexo("Masculino")
                .build()
        ));

        pagamentoRepository.saveAll(List.of(
            Pagamento.builder()
                .id_pagamento(1L)
                .num_cartao("1234567890123456")
                .nome_cartao("Kauê Fernandes Braz")
                .data_validade("23/03/2030")
                .cvv(123)
                .apelido_cartao("Cartão de débito")
                .usuario(usuarioRepository.findById(1L).get())
                .build()
        ));
    }
}
