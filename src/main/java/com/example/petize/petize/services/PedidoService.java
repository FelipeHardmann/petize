package com.example.petize.petize.services;

import com.example.petize.petize.config.RabbitMqConfig;
import com.example.petize.petize.controller.dto.PedidoRequest;
import com.example.petize.petize.controller.dto.PedidoResponse;
import com.example.petize.petize.controller.dto.ProdutoRequest;
import com.example.petize.petize.controller.dto.ProdutoResponse;
import com.example.petize.petize.entity.PedidoEntity;
import com.example.petize.petize.entity.ProdutoEntity;
import com.example.petize.petize.listener.dto.OrderChangeStatus;
import com.example.petize.petize.listener.dto.ProdutoDto;
import com.example.petize.petize.repository.PedidoRepository;
import com.example.petize.petize.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    private final RabbitTemplate rabbitTemplate;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, RabbitTemplate rabbitTemplate) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.rabbitTemplate = rabbitTemplate;
    }


    public void save(OrderChangeStatus event) {
        var entity = new PedidoEntity();

        entity.setId(event.id());
        entity.setDataPedido(event.dataPedido());
        entity.setQuantidade(event.quantidade());
        entity.setStatus(event.status());

        List<ProdutoEntity> produtos = getProdutos(event);
        produtos.forEach(produtoRepository::save);

        entity.setProdutos(produtos);

        pedidoRepository.save(entity);
    }

    public Page<PedidoResponse> findAllPedido(PageRequest pageRequest){
        var pedidos = pedidoRepository.findAll(pageRequest);
        return pedidos.map(PedidoResponse::fromPedido);
    }

    private List<ProdutoEntity> getProdutos(OrderChangeStatus event) {
        return event.produtos().stream()
                .map(i -> new ProdutoEntity(i.nome(), i.preco(), i.descricao()))
                .toList();
    }

    public PedidoResponse createPedido(PedidoRequest pedidoRequest) {
        PedidoEntity pedidoEntity = new PedidoEntity();

        pedidoEntity.setDataPedido(pedidoRequest.dataPedido());
        pedidoEntity.setQuantidade(pedidoRequest.quantidade());
        pedidoEntity.setStatus(pedidoRequest.status());

        List<ProdutoEntity> produtos = new ArrayList<>();
        for (ProdutoRequest produtoRequest : pedidoRequest.produtos()) {
            ProdutoEntity produtoEntity = new ProdutoEntity();
            produtoEntity.setNome(produtoRequest.nome());
            produtoEntity.setPreco(produtoRequest.preco());
            produtoEntity.setDescricao(produtoRequest.descricao());

            produtoEntity = produtoRepository.save(produtoEntity);

            produtos.add(produtoEntity);
        }

        pedidoEntity.setProdutos(produtos);
        pedidoEntity = pedidoRepository.save(pedidoEntity);

        List<ProdutoResponse> produtoResponses = produtos.stream()
                .map(produto -> new ProdutoResponse(
                        produto.getNome(),
                        produto.getPreco(),
                        produto.getDescricao()
                ))
                .collect(Collectors.toList());

        return new PedidoResponse(
                pedidoEntity.getId(),
                pedidoEntity.getDataPedido(),
                produtoResponses,
                pedidoEntity.getQuantidade(),
                pedidoEntity.getStatus()
        );
    }




    @Transactional
    public PedidoResponse updatePedido(Long id, PedidoRequest pedidoRequest) {
        PedidoEntity existingPedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        existingPedido.setDataPedido(pedidoRequest.dataPedido());
        existingPedido.setQuantidade(pedidoRequest.quantidade());
        existingPedido.setStatus(pedidoRequest.status());

        List<ProdutoEntity> produtoEntities = pedidoRequest.produtos().stream()
                .map(produtoRequest -> {
                    ProdutoEntity produtoEntity = new ProdutoEntity();
                    produtoEntity.setNome(produtoRequest.nome());
                    produtoEntity.setPreco(produtoRequest.preco());
                    produtoEntity.setDescricao(produtoRequest.descricao());
                    return produtoEntity;
                })
                .collect(Collectors.toList());

        existingPedido.setProdutos(produtoEntities);

        PedidoEntity updatedPedido = pedidoRepository.save(existingPedido);
        return PedidoResponse.fromPedido(updatedPedido);
    }

    public void sendOrderChange(OrderChangeStatus orderChangeStatus) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.ORDER_CREATED_QUEUE, orderChangeStatus);
    }

    public void processNewOrder(OrderChangeStatus event) {
        save(event);
        sendOrderChange(event);
    }

    public void processUpdateOrder(Long id, OrderChangeStatus event) {
        List<ProdutoDto> produtos = event.produtos().stream()
                .map(produto -> new ProdutoDto(produto.nome(), produto.preco(), produto.descricao()))
                .collect(Collectors.toList());

        PedidoRequest pedidoRequest = new PedidoRequest(
                event.dataPedido(),
                produtos.stream()
                        .map(produto -> new ProdutoRequest(produto.nome(), produto.preco(), produto.descricao()))
                        .collect(Collectors.toList()),
                event.quantidade(),
                event.status()
        );

        updatePedido(id, pedidoRequest);

        OrderChangeStatus orderChangeStatus = new OrderChangeStatus(
                id,
                pedidoRequest.dataPedido(),
                produtos,
                pedidoRequest.quantidade(),
                pedidoRequest.status()
        );

        sendOrderChange(orderChangeStatus);
    }



}
