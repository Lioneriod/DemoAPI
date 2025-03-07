package com.br.demo.repository;

import com.br.demo.model.Categoria;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoriaRepository {
    private final List<Categoria> categorias = new ArrayList<>();
    private Long nextId = 1L;

    public Categoria add(Categoria categoria) {
        categoria.setId(nextId++);
        categorias.add(categoria);
        return categoria;
    }

    public Optional<Categoria> findById(Long id) {
        return categorias.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    public List<Categoria> findAll() {
        return new ArrayList<>(categorias);
    }

    public Categoria update(Categoria categoria) {
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getId().equals(categoria.getId())) {
                categorias.set(i, categoria);
                return categoria;
            }
        }
        throw new RuntimeException("Categoria não encontrada com id: " + categoria.getId());
    }

    public void delete(Long id) {
        categorias.removeIf(c -> c.getId().equals(id));
    }
}