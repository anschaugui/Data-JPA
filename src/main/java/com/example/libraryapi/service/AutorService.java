package com.example.libraryapi.service;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    private final AutorRepository repository;

    public AutorService(AutorRepository repository) {
        this.repository = repository;
    }

    // salvar um autor
    public Autor salvar(Autor autor) {
        return repository.save(autor);
    }

}
