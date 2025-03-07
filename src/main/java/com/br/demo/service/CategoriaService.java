package com.br.demo.service;

import com.br.demo.dto.CategoriaDTO;
import com.br.demo.model.Categoria;
import com.br.demo.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {
    private final CategoriaRepository repository;

    @Autowired
    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public CategoriaDTO create(CategoriaDTO dto) {
        validate(dto);
        Categoria categoria = convertToEntity(dto);
        Categoria savedCategoria = repository.add(categoria);
        return convertToDTO(savedCategoria);
    }

    public CategoriaDTO findById(Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        return convertToDTO(categoria);
    }

    public List<CategoriaDTO> findAll() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CategoriaDTO update(Long id, CategoriaDTO dto) {
        if (dto.getId() != null && !dto.getId().equals(id)) {
            throw new IllegalArgumentException("ID do path não corresponde ao ID do DTO");
        }
        dto.setId(id);
        validate(dto);
        Categoria categoria = convertToEntity(dto);
        Categoria updatedCategoria = repository.update(categoria);
        return convertToDTO(updatedCategoria);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    private Categoria convertToEntity(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setId(dto.getId());
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        return categoria;
    }

    private CategoriaDTO convertToDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNome(categoria.getNome());
        dto.setDescricao(categoria.getDescricao());
        return dto;
    }

    private void validate(CategoriaDTO dto) {
        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
    }
}