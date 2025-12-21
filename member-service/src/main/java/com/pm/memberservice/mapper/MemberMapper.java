package com.pm.memberservice.mapper;

import com.pm.memberservice.dto.MemberRequestDTO;
import com.pm.memberservice.dto.MemberResponseDTO;
import com.pm.memberservice.model.Member;

import java.time.LocalDate;

public class MemberMapper {
    public static MemberResponseDTO toDTO(Member member){

        MemberResponseDTO result = new MemberResponseDTO();

        result.setId(member.getId().toString());
        result.setName(member.getName());
        result.setEmail(member.getEmail());
        result.setAddress(member.getAddress());
        result.setDateOfBirth(member.getDateOfBirth().toString());

        return result;
    }

    public static Member toModel(MemberRequestDTO memberRequestDTO){
        Member result = new Member();

        result.setName(memberRequestDTO.getName());
        result.setEmail(memberRequestDTO.getEmail());
        result.setAddress(memberRequestDTO.getAddress());
        result.setDateOfBirth(LocalDate.parse(memberRequestDTO.getDateOfBirth()));
        result.setRegisterDate(result.getDateOfBirth());

        return result;
    }
}
