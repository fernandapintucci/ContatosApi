package br.com.etec.contatosapi.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.etec.contatosapi.model.Pessoa;
import br.com.etec.contatosapi.service.PessoaService;

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {

	@Autowired
	private PessoaService pessoaService;

	@GetMapping("/todas")
	public List<Pessoa> listarTodasPessoas() {
		return pessoaService.listarTodasPessoas();
	}

	@GetMapping()
	public Page<Pessoa> pesquisar(@RequestParam(required = false, defaultValue = "") String nome, Pageable pageable) {
		return pessoaService.pesquisar(nome, pageable);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> find(@PathVariable Long codigo) {
		Pessoa pessoa = pessoaService.findPessoa(codigo);
		return ResponseEntity.ok().body(pessoa);
	}

	@PostMapping()
	public ResponseEntity<Void> insert(@RequestBody Pessoa pessoa) {
		pessoa = pessoaService.insert(pessoa);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}").buildAndExpand(pessoa.getCodigo())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> update(@RequestBody Pessoa pessoa, @PathVariable Long codigo) {
		try {
			Pessoa pessoaSalva = pessoaService.update(codigo, pessoa);
			return ResponseEntity.ok(pessoaSalva);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> delete(@PathVariable Long codigo) {
		pessoaService.delete(codigo);
		return ResponseEntity.noContent().build();
	}
}
