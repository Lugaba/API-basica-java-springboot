package br.luca.tia.repository;

import org.springframework.stereotype.Repository;

import br.luca.tia.entity.Aluno;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByTia(String tia); // funcao do springboost que busca aluno pelo tia
}
