package com.example.libraryapi.repository;

import com.example.libraryapi.enums.GeneroLivro;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    private AutorRepository repository;
    @Autowired
    private LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Maria");
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
            autorEncontrado.setNome("José");
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

    @Test
    void salvarAutorComLivrosTest() {
        Autor autor = new Autor();
        autor.setNome("Isabela");
        autor.setNacionalidade("Americano");
        autor.setDataNascimento(LocalDate.of(1984, 1, 31).atStartOfDay());

        // Livro 1
        Livro livro = new Livro();
        livro.setIsbn("25166-42323");
        livro.setPreco(BigDecimal.valueOf(359.90));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("O Roubo da casa Assombrada");
        livro.setDataPublicacao(LocalDate.of(1999, 1, 1));
        livro.setAutor(autor);

        // Livro 2
        Livro livro2 = new Livro();
        livro2.setIsbn("25166-42323");
        livro2.setPreco(BigDecimal.valueOf(650));
        livro2.setGenero(GeneroLivro.FICCAO);
        livro2.setTitulo("O Rato roeu a roupa do rei de roma");
        livro2.setDataPublicacao(LocalDate.of(2005, 1, 1));
        livro2.setAutor(autor);

        // instanciando os livros do autor com um arraylist onde vou inserir os dois objetos criados a partir de "Livro"
        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        // persistindo os livros no banco com JPA
        repository.save(autor);
        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    void mostrarLivrosAutorTest(){
        var idAutor = UUID.fromString("2ba27c20-0a07-4524-87cc-c54b25b80cc2");
        var autor = repository.findById(idAutor).get();

        // buscar os livros do autor
        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);
        autor.getLivros().forEach(System.out::println);
    }
}
