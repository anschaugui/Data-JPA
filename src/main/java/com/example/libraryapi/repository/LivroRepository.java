package com.example.libraryapi.repository;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query Method
    List<Livro> findByAutor(Autor autor);

    // Busca pelo titulo do livro
    List<Livro> findByTitulo(String titulo);

    // Busca pelo isbn
    List<Livro> findByIsbn(String isbn);

    // busca por titulo e preco
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);
}
