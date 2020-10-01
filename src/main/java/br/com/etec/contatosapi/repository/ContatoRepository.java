package br.com.etec.contatosapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.etec.contatosapi.model.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
	public Page<Contato> findByNomeContainingOrderByNome(String nome, Pageable pageable);
}
