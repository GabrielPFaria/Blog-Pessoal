package org.generation.blogPessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.generation.blogPessoal.model.Postagem;
import org.generation.blogPessoal.repository.PostagemRepository;


@RestController
@RequestMapping("/postagens") 
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {
	
	@Autowired
	private PostagemRepository postagemRepository;
	
	@GetMapping/*Find*/
	public ResponseEntity<List<Postagem>> getAll (){
		return ResponseEntity.ok(postagemRepository.findAll());
	}
		
	@GetMapping("/{id}")/*find_id*/
	public ResponseEntity<Postagem> getById1(@PathVariable Long id) {
		return postagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))/*Determina a resposta que meu cliente vai reseber caso verdadeiro*/
				.orElse(ResponseEntity.notFound().build());	
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}

	@PostMapping
	public ResponseEntity<Postagem> postPostagem (@Valid @RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
	}
	
	
	@PutMapping
	public ResponseEntity<Postagem> putPostagem (@Valid @RequestBody Postagem postagem){
		
		return postagemRepository.findById(postagem.getId())
			.map(resposta -> ResponseEntity.ok().body(postagemRepository.save(postagem)))
			.orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePostagem(@PathVariable Long id) {
		
		return postagemRepository.findById(id)
				.map(resposta -> {
					postagemRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
}
