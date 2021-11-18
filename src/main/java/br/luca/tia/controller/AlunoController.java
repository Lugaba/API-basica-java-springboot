package br.luca.tia.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.luca.tia.entity.Aluno;
import br.luca.tia.repository.AlunoRepository;

@RestController
public class AlunoController {
    @Autowired
    private AlunoRepository repository;

    // GET ALL - retorna todos os alunos
    @RequestMapping(value = "/alunos", method = RequestMethod.GET)
    public List<Aluno> getAlunos() {
        return repository.findAll();
    }

    // POST - criar novo aluno
    @RequestMapping(value = "/alunos", method = RequestMethod.POST)
    public Aluno createAluno(@Valid @RequestBody Aluno aluno) { // Request pegue o body da mensagem e coloque no aluno e
                                                                // Valid valida os dados do body
        return repository.save(aluno); // salva no banco
    }

    @RequestMapping(value = "/alunos/tia/{tia}", method = RequestMethod.GET)
    public ResponseEntity<Aluno> findByTia(@PathVariable(value = "tia") String tia) {
        List<Aluno> response = repository.findByTia(tia);
        if (response.size() > 0) {
            return new ResponseEntity<Aluno>(response.get(0), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/alunos/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Aluno> Put(@PathVariable(value = "id") long id, @Valid @RequestBody Aluno newAluno) {
        Optional<Aluno> oldAluno = repository.findById(id);
        if (oldAluno.isPresent()) {
            Aluno aluno = oldAluno.get();
            aluno.setNome(newAluno.getNome());
            aluno.setTia(newAluno.getTia());
            repository.save(aluno);
            return new ResponseEntity<Aluno>(aluno, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/alunos/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id) {
        Optional<Aluno> aluno = repository.findById(id);
        if (aluno.isPresent()) {
            repository.delete(aluno.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
