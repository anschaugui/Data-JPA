package com.example.libraryapi.repository;

import com.example.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    private AutorRepository repository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setName("Maria");
        autor.setNacionalidade("Italiana");
        autor.setDataNascimento(LocalDate.of(1984, 1, 31).atStartOfDay());

        var autorSalvo = repository.save(autor);
        System.out.println("Autor salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest() {
        var id = UUID.fromString("37180edb-8944-459a-92a7-49eb475b1856");

        Optional<Autor> possivelAutor = repository.findById(id);
        if (possivelAutor.isPresent()) {

            Autor autorEncontrado = possivelAutor.get();

            System.out.println("Dados do autor");
            System.out.println("Autor: " + possivelAutor.get());

            autorEncontrado.setDataNascimento(LocalDate.of(1984, 2, 25).atStartOfDay());
            autorEncontrado.setName("José");
            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest() {
        List<Autor> autores = repository.findAll();
        autores.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        System.out.println("Contagem de autores: " + repository.count());
    }

    // delete por id
    @Test
    public void deleteByIdTest() {
        var id = UUID.fromString("a43daeb6-820d-498b-a5b5-94969db73ed7");
        Optional<Autor> autorDeletado = repository.findById(id);
        if (autorDeletado.isPresent()) {
            repository.delete(autorDeletado.get());
            System.out.println("Autor deletado: " + autorDeletado.get());
        }
    }

    @Test
    public void deleteTest() {
        var id = UUID.fromString("7325b17c-183b-4444-91b6-62d7f7bf95e0");
        var maria = repository.findById(id);
        if (maria.isPresent()) {
            repository.delete(maria.get());
            System.out.println("Autor deletado: " + maria.get());
        }
    }
}
