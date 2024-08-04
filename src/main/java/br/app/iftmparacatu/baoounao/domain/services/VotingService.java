package br.app.iftmparacatu.baoounao.domain.services;

import br.app.iftmparacatu.baoounao.api.exception.VoteNotAllowedException;
import br.app.iftmparacatu.baoounao.domain.dtos.input.VotingDto;
import br.app.iftmparacatu.baoounao.domain.dtos.output.RecoveryProposalDto;
import br.app.iftmparacatu.baoounao.domain.enums.RoleName;
import br.app.iftmparacatu.baoounao.domain.model.*;
import br.app.iftmparacatu.baoounao.domain.repository.VotingRepository;
import br.app.iftmparacatu.baoounao.domain.util.SecurityUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotingService {
    @Autowired
    private VotingRepository votingRepository;

    @Autowired
    private CycleService cycleService;

    public int countByProposalEntity(ProposalEntity proposalEntity){
        return votingRepository.countByProposalEntity(proposalEntity);
    }

    public ResponseEntity<Object> save(VotingDto votingDto){

        CycleEntity currentCicle = cycleService.findProgressCycle().get();
        Long userVotes = votingRepository.countByUserEntityAndProposalEntityCycleEntity(SecurityUtil.getAuthenticatedUser(),currentCicle);

        if (userVotes == 3){ //TODO: tavles fique interessante parametrizar a quantidade de votos por ciclo em uma configuração do sistema
            throw new VoteNotAllowedException("Você atingiu o limite máximo de 3 votos para o ciclo atual. Não é permitido votar em mais do que 3 propostas.");
        }

        VotingEntity newVoting = VotingEntity.builder()
                .proposalEntity(votingDto.proposalEntity())
                .build();
        votingRepository.save(newVoting);
       return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
