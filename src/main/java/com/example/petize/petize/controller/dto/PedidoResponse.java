package com.example.petize.petize.controller.dto;

import com.example.petize.petize.entity.PedidoEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record PedidoResponse(Long id,
                             LocalDateTime dataPedido,
                             List<ProdutoResponse> produtos,
                             Integer quantidade,
                             String status) {
    public static PedidoResponse fromPedido(PedidoEntity pedidoEntity) {
        List<ProdutoResponse> produtoResponses = pedidoEntity.getProdutos().stream()
                .map(produtoEntity -> new ProdutoResponse(produtoEntity.getNome(), produtoEntity.getPreco(), produtoEntity.getDescricao()))
                .collect(Collectors.toList());

        return new PedidoResponse(pedidoEntity.getId(), pedidoEntity.getDataPedido(), produtoResponses, pedidoEntity.getQuantidade(), pedidoEntity.getStatus());
    }
}
