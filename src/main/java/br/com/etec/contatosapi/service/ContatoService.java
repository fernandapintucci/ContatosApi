package br.com.etec.contatosapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.etec.contatosapi.model.Contato;
import br.com.etec.contatosapi.repository.ContatoRepository;
import br.com.etec.contatosapi.service.exceptions.ObjectNotFoundException;

@Service
public class ContatoService {
	
	@Autowired
	private ContatoRepository contatoRepository;

	public List<Contato> listarTodosContato() {
		return contatoRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
	}

	public Contato findContato(Long codigo) {
		Optional<Contato> contato = contatoRepository.findById(codigo);
		return contato.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado !!! Id:" + codigo + "Tipo" + Contato.class.getName()));
	}

	public Contato insert(Contato contato) {
		return contatoRepository.save(contato);
	}

	public Contato update(Long codigo, Contato contato) {
		Contato contatoSalvo = findContato(codigo);

		BeanUtils.copyProperties(contato, contatoSalvo, "codigo");

		return contatoRepository.save(contatoSalvo);
	}

	public Page <Contato> pesquisar(String nome, Pageable pageable) {
		return contatoRepository.findByNomeContainingOrderByNome(nome, pageable);
	}

	public void delete(Long codigo) {
		findContato(codigo);
		contatoRepository.deleteById(codigo);
	}
}
