package com.example.libraryapi.controller.dto;

import com.example.libraryapi.model.Autor;

import java.time.LocalDate;
import java.util.Date;

public record AutorDTO(String nome,
                       LocalDate dataNascimento,
                       String nacionalidade) {

    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setName(this.nome);
        autor.setDataNascimento(this.dataNascimento.atStartOfDay());
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }

}
