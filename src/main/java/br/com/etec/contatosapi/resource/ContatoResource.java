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

import br.com.etec.contatosapi.model.Contato;
import br.com.etec.contatosapi.service.ContatoService;

@RestController
@RequestMapping("/contato")
public class ContatoResource {

	@Autowired
	private ContatoService contatoService;

	@GetMapping("/todos")
	public List<Contato> listarTodosContato() {
		return contatoService.listarTodosContato();
	}

	@GetMapping()
	public Page<Contato> pesquisar(@RequestParam(required = false, defaultValue = "") String nome, Pageable pageable) {
		return contatoService.pesquisar(nome, pageable);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Contato> find(@PathVariable Long codigo) {
		Contato contato = contatoService.findContato(codigo);
		return ResponseEntity.ok().body(contato);
	}

	@PostMapping()
	public ResponseEntity<Void> insert(@RequestBody Contato contato) {
		contato = contatoService.insert(contato);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}").buildAndExpand(contato.getCodigo())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Contato> update(@RequestBody Contato contato, @PathVariable Long codigo) {
		try {
			Contato contatoSalvo = contatoService.update(codigo, contato);
			return ResponseEntity.ok(contatoSalvo);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> delete(@PathVariable Long codigo) {
		contatoService.delete(codigo);
		return ResponseEntity.noContent().build();
	}
}
