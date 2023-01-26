package com.produtos.produtos.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.produtos.produtos.Entities.ProdutosEntity;

public interface ProdutoRepositorio extends JpaRepository<ProdutosEntity, Integer> {
    public ProdutosEntity findByImg(String img);
}
