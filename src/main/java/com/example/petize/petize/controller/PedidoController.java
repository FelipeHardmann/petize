package com.example.petize.petize.controller;

import com.example.petize.petize.controller.dto.ApiResponse;
import com.example.petize.petize.controller.dto.PaginationResponse;
import com.example.petize.petize.controller.dto.PedidoRequest;
import com.example.petize.petize.controller.dto.PedidoResponse;
import com.example.petize.petize.services.PedidoService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PedidoResponse>> listPedidos(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                   @RequestParam(name = "size", defaultValue = "10") Integer pageSize) { // Corrigi 'page' para 'size'

        var pageResponse = pedidoService.findAllPedido(PageRequest.of(page, pageSize));

        return ResponseEntity.ok(new ApiResponse<>(
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)
        ));
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> createPedido(@RequestBody PedidoRequest pedidoRequest) {
        PedidoResponse pedidoResponse = pedidoService.createPedido(pedidoRequest);
        return ResponseEntity.status(201).body(pedidoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponse> updatePedido(@PathVariable Long id, @RequestBody PedidoRequest pedidoRequest) {
        PedidoResponse pedidoResponse = pedidoService.updatePedido(id, pedidoRequest);
        return ResponseEntity.ok(pedidoResponse);
    }
}
