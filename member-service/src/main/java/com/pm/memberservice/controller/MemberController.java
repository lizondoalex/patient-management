package com.pm.memberservice.controller;

import com.pm.memberservice.dto.MemberRequestDTO;
import com.pm.memberservice.dto.MemberResponseDTO;
import com.pm.memberservice.dto.validators.CreateMemberValidationGroup;
import com.pm.memberservice.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/members")
@Tag(name = "Member", description = "API for managing patients")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    @Operation(summary = "Get patients")
    public ResponseEntity<List<MemberResponseDTO>> getPatients(){
        List<MemberResponseDTO> responsePatients = memberService.getPatients();

        return ResponseEntity.ok().body(responsePatients);
    }

    @PostMapping
    @Operation(summary = "Create a new patient")
    public ResponseEntity<MemberResponseDTO> createPatient(@Validated({Default.class, CreateMemberValidationGroup.class}) @RequestBody MemberRequestDTO memberRequestDTO){
        MemberResponseDTO memberResponseDTO = memberService.createPatient(memberRequestDTO);

        return ResponseEntity.ok().body(memberResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a patient")
    public ResponseEntity<MemberResponseDTO> updatePatient(@PathVariable UUID id, @Validated({Default.class})@RequestBody MemberRequestDTO memberRequestDTO){
        MemberResponseDTO memberResponseDTO = memberService.updatePatient(id , memberRequestDTO);

        return ResponseEntity.ok().body(memberResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient")
    public ResponseEntity<MemberResponseDTO> deletePatient(@PathVariable UUID id){
        MemberResponseDTO memberResponseDTO = memberService.deleteMember(id);

        return ResponseEntity.ok().body(memberResponseDTO);
    }
}
