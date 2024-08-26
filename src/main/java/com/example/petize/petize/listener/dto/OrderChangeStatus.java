package com.example.petize.petize.listener.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderChangeStatus(Long id, LocalDateTime dataPedido, List<ProdutoDto> produtos, Integer quantidade, String status) {
}
