package br.app.iftmparacatu.baoounao.api.controller;


import br.app.iftmparacatu.baoounao.domain.dtos.output.RecoveryProposalDto;
import br.app.iftmparacatu.baoounao.domain.model.ProposalEntity;
import br.app.iftmparacatu.baoounao.domain.repository.ProposalRepository;
import br.app.iftmparacatu.baoounao.domain.services.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proposal")
public class ProposalController {
    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    ProposalService proposalService;

    @GetMapping
    public ResponseEntity<List<RecoveryProposalDto>> list (){
        List<RecoveryProposalDto> propostas =  proposalService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(propostas);
    }

    @GetMapping("/{proposalId}") //TODO: modificar a exceção quando usar o service
    public ResponseEntity<ProposalEntity> getFindById (@PathVariable Long proposalId ){
        Optional<ProposalEntity> proposta = proposalRepository.findById(proposalId);
        if(proposta.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(proposta.get());

        } else throw new RuntimeException("Proposal de id " + proposalId + "não foi encontrada");

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)//TODO: modificar a exceção quando usar o service
    public ResponseEntity<ProposalEntity> saveProposal (@RequestBody ProposalEntity proposalEntity ){
        try{
            ProposalEntity proposta = proposalRepository.save(proposalEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(proposta);
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }



    @GetMapping("/filter")
    public List<ProposalEntity> filterByDescriptionOrTitle (@RequestParam(value = "contain", required = false) String text ){
        return proposalRepository.findByTitleContainingOrDescriptionContaining(text,text);

    }

    @GetMapping("/trending")
    public List<ProposalEntity> trendingProposals (){
        return proposalRepository.findTop3ByLikesGreaterThanOrderByLikesDesc(0);

    }

    @PostMapping("/upload-image/{proposalId}")
    public ResponseEntity<?> uploadImage(@PathVariable Long proposalId,
                                          @RequestParam("image") MultipartFile image) {
        Optional<ProposalEntity> optionalProposal = proposalRepository.findById(proposalId);

        if (optionalProposal.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            ProposalEntity proposalEntity = optionalProposal.get();
            proposalEntity.setImage(image.getBytes());
            proposalRepository.save(proposalEntity);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}