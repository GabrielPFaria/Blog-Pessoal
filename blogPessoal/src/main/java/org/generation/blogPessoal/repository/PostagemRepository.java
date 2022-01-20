package org.generation.blogPessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.generation.blogPessoal.model.Postagem; 

@Repository
public interface  PostagemRepository extends JpaRepository<Postagem, Long>{
	
	public List <Postagem> findAllByTituloContainingIgnoreCase(String titulo);

	
}
