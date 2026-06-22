package com.example.libraryapi.controller;

import com.example.libraryapi.controller.dto.AutorDTO;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.service.AutorService;
import jakarta.persistence.GeneratedValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
// host:8080/autores
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService autorService) {
        this.service = autorService;
    }

    @PostMapping()
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor){
        Autor autorEntidade = autor.mapearParaAutor();
        service.salvar(autorEntidade);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntidade.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);
        if(autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            AutorDTO dto = new AutorDTO(
                    autor.getId(), 
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade()
            );
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    // indempotente - independente de ter id ou nao ele ainda da sucesso
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);
        if(autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        service.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
          @RequestParam(value = "nome", required = false) String nome,
          @RequestParam(value = "nacionalidade", required = false) String nacionalidade){
        List<Autor> resultado = service.pesquisa(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                .stream()
                .map(autor -> new AutorDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade())
                ).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable("id") String id,
            @RequestBody AutorDTO dto) {

        // Converte o id recebido na URL, que vem como String, para UUID
        var idAutor = UUID.fromString(id);

        // Busca o autor pelo id e recebe um Optional, pois o autor pode ou não existir
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        // Verifica se nenhum autor foi encontrado com esse id
        // Se não encontrou, retorna HTTP 404 Not Found
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Recupera o objeto Autor que foi encontrado dentro do Optional
        var autor = autorOptional.get();

        // Atualiza os dados da entidade Autor com as informações recebidas no DTO
        autor.setNome(dto.nome());
        autor.setDataNascimento(dto.dataNascimento());
        autor.setNacionalidade(dto.nacionalidade());

        // Salva a entidade atualizada no banco de dados
        service.atualizar(autor);

        // Retorna HTTP 204 No Content, indicando sucesso sem corpo na resposta
        return ResponseEntity.noContent().build();
    }
}
