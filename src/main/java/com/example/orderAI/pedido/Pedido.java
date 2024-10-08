package com.example.orderAI.pedido;

import java.time.LocalDate;

import com.example.orderAI.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido")
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id_pedido;

    @NotNull(message = "pedido.valor_total.notNull")
    @Positive(message = "pedido.valor_total.positive")
    Double valor_total;

    @NotNull(message = "pedido.frete_entrega.notNull")
    @Positive(message = "pedido.frete_entrega.positive")
    Double frete_entrega;

    @PastOrPresent
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "pedido.data_pedido.notNull")
    LocalDate data_pedido;

    @Future
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "pedido.data_entrega.notNull")
    LocalDate data_entrega;

    @ManyToOne
    Usuario usuario;
}

