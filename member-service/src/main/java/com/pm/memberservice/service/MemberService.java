package com.pm.memberservice.service;

import com.pm.memberservice.dto.MemberRequestDTO;
import com.pm.memberservice.dto.MemberResponseDTO;
import com.pm.memberservice.exceptions.EmailAlreadyExistsException;
import com.pm.memberservice.exceptions.MemberNotFoundException;
import com.pm.memberservice.grpc.BillingServiceGrpcClient;
import com.pm.memberservice.kafka.KafkaProducer;
import com.pm.memberservice.mapper.MemberMapper;
import com.pm.memberservice.model.Member;
import com.pm.memberservice.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public MemberService(MemberRepository memberRepository,
                         BillingServiceGrpcClient billingServiceGrpcClient, KafkaProducer kafkaProducer){
       this.memberRepository = memberRepository;
       this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

   public List<MemberResponseDTO> getPatients(){
       List<Member> members = memberRepository.findAll();

       return members.stream()
               .map(MemberMapper::toDTO).toList();
   }

   public MemberResponseDTO createPatient(MemberRequestDTO memberRequestDTO){
       if(memberRepository.existsByEmail(memberRequestDTO.getEmail())){
           throw new EmailAlreadyExistsException("A member with this email already exists " + memberRequestDTO.getEmail());
       }
       Member newMember = memberRepository.save(MemberMapper.toModel(memberRequestDTO));
       billingServiceGrpcClient.createBillingAccount(newMember.getId().toString(), newMember.getName(), newMember.getEmail());

       kafkaProducer.sendEvent(newMember);

       return MemberMapper.toDTO(newMember);
   }

   public MemberResponseDTO updatePatient(UUID id, MemberRequestDTO memberRequestDTO){
       Optional<Member> optionalPatient = memberRepository.findById(id);

       if(optionalPatient.isPresent()){
           Member member = optionalPatient.get();

           if(memberRepository.existsByEmail(memberRequestDTO.getEmail())){
               if(!memberRequestDTO.getEmail().equals(member.getEmail())){
                   throw new EmailAlreadyExistsException("A member with this email already exists " + memberRequestDTO.getEmail());
               }
           }

           member.setName(memberRequestDTO.getName());
           member.setAddress(memberRequestDTO.getAddress());
           member.setEmail(memberRequestDTO.getEmail());
           member.setDateOfBirth(LocalDate.parse(memberRequestDTO.getDateOfBirth()));

           Member updatedMember = memberRepository.save(member);

           return MemberMapper.toDTO(updatedMember);

       } else {
           throw new MemberNotFoundException("Member not found with ID" + id);
       }
   }

   public MemberResponseDTO deleteMember(UUID id){
       Optional<Member> optionalMember = memberRepository.findById(id);

       if(optionalMember.isPresent()){
           Member member = optionalMember.get();
           memberRepository.delete(member);

           return MemberMapper.toDTO(member);
       } else {
           throw new MemberNotFoundException("Member not found with ID " + id);
       }
   }
}
