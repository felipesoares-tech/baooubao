package br.app.iftmparacatu.baoounao.api.controller;

import br.app.iftmparacatu.baoounao.domain.model.VotingEntity;
import br.app.iftmparacatu.baoounao.domain.repository.VotingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/voting")
public class VotingController {
    @Autowired
    private VotingRepository votingRepository;
    @GetMapping
    public List<VotingEntity> list(){
        return votingRepository.findAll();
    }

    @GetMapping("/{votingID}") //TODO: Adicionar exception para quando não encontrar a entidade
    public Optional<VotingEntity> findById(@PathVariable Long votingID) {
        return votingRepository.findById(votingID); //.orElseThrow(() -> new EntityNotFoundException("REGISTRO NÃO ENCONTRADO!"));
    }
}
