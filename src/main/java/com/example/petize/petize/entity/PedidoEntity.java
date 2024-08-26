package com.example.petize.petize.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "pedido")
@Entity(name = "Pedido")
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataPedido;

    @ManyToMany
    @JoinTable(
            name = "pedido_produto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<ProdutoEntity> produtos;


    public PedidoEntity() {
    }

    public PedidoEntity(Long id, LocalDateTime dataPedido, List<ProdutoEntity> produtos, Integer quantidade, String status) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.produtos = produtos;
        this.quantidade = quantidade;
        this.status = status;
    }

    private Integer quantidade;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public List<ProdutoEntity> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoEntity> produtos) {
        this.produtos = produtos;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
