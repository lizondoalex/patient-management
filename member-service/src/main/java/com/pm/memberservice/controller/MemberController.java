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
@Tag(name = "Member", description = "API for managing members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    @Operation(summary = "Get members")
    public ResponseEntity<List<MemberResponseDTO>> getMembers(){
        List<MemberResponseDTO> responseMembers= memberService.getMembers();

        return ResponseEntity.ok().body(responseMembers);
    }

    @PostMapping
    @Operation(summary = "Create a new member")
    public ResponseEntity<MemberResponseDTO> createMember(@Validated({Default.class, CreateMemberValidationGroup.class}) @RequestBody MemberRequestDTO memberRequestDTO){
        MemberResponseDTO memberResponseDTO = memberService.createMember(memberRequestDTO);

        return ResponseEntity.ok().body(memberResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a member")
    public ResponseEntity<MemberResponseDTO> createMember(@PathVariable UUID id, @Validated({Default.class})@RequestBody MemberRequestDTO memberRequestDTO){
        MemberResponseDTO memberResponseDTO = memberService.updateMember(id , memberRequestDTO);

        return ResponseEntity.ok().body(memberResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a member")
    public ResponseEntity<MemberResponseDTO> deleteMember(@PathVariable UUID id){
        MemberResponseDTO memberResponseDTO = memberService.deleteMember(id);

        return ResponseEntity.ok().body(memberResponseDTO);
    }
}
