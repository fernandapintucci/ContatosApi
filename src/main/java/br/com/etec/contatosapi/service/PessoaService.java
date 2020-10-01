package br.com.etec.contatosapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.etec.contatosapi.model.Pessoa;
import br.com.etec.contatosapi.repository.PessoaRepository;
import br.com.etec.contatosapi.service.exceptions.ObjectNotFoundException;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public List<Pessoa> listarTodasPessoas() {
		return pessoaRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
	}

	public Pessoa findPessoa(Long codigo) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(codigo);
		return pessoa.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado !!! Id:" + codigo + "Tipo" + Pessoa.class.getName()));
	}

	public Pessoa insert(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}

	public Pessoa update(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = findPessoa(codigo);

		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");

		return pessoaRepository.save(pessoaSalva);
	}

	public Page<Pessoa> pesquisar(String nome, Pageable pageable) {
		return pessoaRepository.findByNomeContainingOrderByNome(nome, pageable);
	}

	public void delete(Long codigo) {
		findPessoa(codigo);
		pessoaRepository.deleteById(codigo);
	}
}
