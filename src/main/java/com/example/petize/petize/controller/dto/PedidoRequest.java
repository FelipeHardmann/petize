package com.example.petize.petize.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoRequest(LocalDateTime dataPedido,
                            List<ProdutoRequest> produtos,
                            Integer quantidade,
                            String status) {
}
