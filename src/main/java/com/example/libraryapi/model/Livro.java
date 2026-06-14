package com.example.libraryapi.model;

import com.example.libraryapi.enums.GeneroLivro;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn",  nullable = false, length = 20)
    private String isbn;

    @Column(name = "titulo",  nullable = false, length = 150)
    private String titulo;

    @Column(name = "data_publicacao",  nullable = false)
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero",  nullable = false, length = 30)
    private GeneroLivro genero;

    @Column(name = "preco", precision = 18, scale = 2)
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;

}
