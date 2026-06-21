package com.example.libraryapi.repository;

import com.example.libraryapi.enums.GeneroLivro;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {
    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void saveTest() {
        Livro livro = new Livro();
        livro.setIsbn("90887-42323");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1990, 1, 1));

        Autor autor = autorRepository
                .findById(UUID.fromString("dfd9384e-2c02-47e6-93c6-3eac9da00760"))
                .orElse(null);

        livro.setAutor(autor);

        var salvo = repository.save(livro);
        System.out.println("Livro salvo: " + salvo);
    }

    @Test
    void saveCascadeTest() {
        Livro livro = new Livro();
        livro.setIsbn("90887-42323");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Terceiro Livro Marroquino");
        livro.setDataPublicacao(LocalDate.of(1990, 1, 1));

        // saiu de transient para persistido
        Autor autor = new Autor();
        autor.setNome("Teste 200");
        autor.setNacionalidade("Marroquino");
        autor.setDataNascimento(LocalDate.of(1824, 1, 31).atStartOfDay());
        // salvando o autor
        var autorSalvo = autorRepository.save(autor);
        System.out.println("Autor salvo: " + autorSalvo);

        livro.setAutor(autorSalvo);
        var salvo = repository.save(livro);
        System.out.println("Livro salvo: " + salvo);

    }

    @Test
    void atualizarAutorDoLivroTest() {
        UUID id = UUID.fromString("34af9ca1-c730-4010-8ddc-eac1c565ce1e");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("34af9ca1-c730-4010-8ddc-eac1c565ce1e");
        Autor autor = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(autor);

        repository.save(livroParaAtualizar);

    }


    @Test
    void deleteByIdTest() {
        var id = UUID.fromString("d85fdb05-9860-4cbe-90c0-42bd2d3d8d16");
        Optional<Livro> livro = repository.findById(id);
        if (livro.isPresent()) {
            repository.deleteById(id);
            System.out.println("Livro deletado: " + livro.get());
        }
    }

    @Test
    void listarLivrosTest() {
        List<Livro> livros =  repository.findAll();
        livros.forEach(System.out::println);
    }

    @Test
    @Transactional
    void buscarLivroTest() {
        UUID id = UUID.fromString("b6ab7b21-1199-415a-89ac-f89ed9884f6d");
        repository.findById(id).ifPresent(livro -> System.out.println("Livro: " + livro.getTitulo() +
               " Autor: "+ livro.getAutor().getNome()));
    }

    @Test
    void pesquisaPorTitulo(){
        List<Livro> lista = repository.findByTitulo("O Roubo da casa Assombrada");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbn(){
        List<Livro> listaIsbn = repository.findByIsbn("25166-42323");
        listaIsbn.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloAndPreco(){
        var preco = BigDecimal.valueOf(359.90);
        var titulo = "O Roubo da casa Assombrada";
        List<Livro> lista = repository.findByTituloAndPreco(titulo, preco);
        lista.forEach(System.out::println);
    }
}