package com.centrotreinamento.bjj.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.centrotreinamento.bjj.domain.Treino;
import com.centrotreinamento.bjj.dto.request.TreinoRequestDTO;
import com.centrotreinamento.bjj.dto.response.TreinoResponseDTO;
import com.centrotreinamento.bjj.mapper.TreinoMapper;
import com.centrotreinamento.bjj.service.TreinoService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/treinos")
@Tag(name = "Treinos", description = "Gerenciamento de Treinos do Centro de Treinamento BJJ")
public class TreinoController {

    private final TreinoService treinoService;

    public TreinoController(TreinoService treinoService) {
        this.treinoService = treinoService;
    }

    @PostMapping
    public ResponseEntity<TreinoResponseDTO> criarTreino(@RequestBody TreinoRequestDTO dto) {
        Treino treino = treinoService.criarTreino(dto);

        TreinoResponseDTO response = TreinoMapper.toResponseDTO(treino);
        return ResponseEntity
                .created(URI.create("/treinos/" + treino.getId()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<TreinoResponseDTO>> listarTreinos() {
        return ResponseEntity.ok(treinoService.listarTreinos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreinoResponseDTO> buscarPorId(@PathVariable UUID id) {
        TreinoResponseDTO treino = treinoService.buscarPorId(id);
        return ResponseEntity.ok(treino);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Treino> removerTreino(@PathVariable UUID id) {
        treinoService.removerTreino(id);
        return ResponseEntity.noContent().build();
    }

}
