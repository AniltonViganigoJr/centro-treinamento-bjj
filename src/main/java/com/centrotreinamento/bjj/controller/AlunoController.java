package com.centrotreinamento.bjj.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.centrotreinamento.bjj.domain.Aluno;
import com.centrotreinamento.bjj.service.AlunoService;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
    
    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aluno cadastrar(@RequestBody Aluno aluno) {
        return alunoService.cadastrar(aluno);
    }

    @GetMapping
    public List<Aluno> listar() {
        return alunoService.listar();
    }

    @GetMapping({"/{id}"})
    public Aluno buscarPorId(@PathVariable UUID id) {
        return alunoService.buscarPorId(id);
    }

    @PutMapping("/{id}/graduar")
    public Aluno graduarFaixa(@PathVariable UUID id, @RequestParam String Faixa) {
        return alunoService.graduarFaixa(id, Faixa);
    }
    
    @PutMapping("/{id}/grau")
    public Aluno adicionarGrau(@PathVariable UUID id) {
        return alunoService.adicionarGrau(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable UUID id) {
        alunoService.deletar(id);
    }
}
