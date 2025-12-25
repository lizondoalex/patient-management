package com.pm.memberservice;

import com.pm.memberservice.dto.MemberRequestDTO;
import com.pm.memberservice.dto.MemberResponseDTO;
import com.pm.memberservice.grpc.BillingServiceGrpcClient;
import com.pm.memberservice.kafka.KafkaProducer;
import com.pm.memberservice.model.Member;
import com.pm.memberservice.repository.MemberRepository;
import com.pm.memberservice.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BillingServiceGrpcClient billingServiceGrpcClient;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private MemberService memberService;

    @Test
    void shouldRegisterNewMemberSuccessfully() {

        UUID fixedId = UUID.randomUUID();
        LocalDate birth = LocalDate.parse("1995-01-01");
        LocalDate register = LocalDate.parse("2025-01-01");

        Member savedMember = new Member("John Doe", "john@email.com", "123 main street", birth, register);
        savedMember.setId(fixedId);

        MemberRequestDTO request = new MemberRequestDTO("john@email.com", "2025-01-01", "1995-01-01", "123 main street", "John Doe");

        when(memberRepository.save(any(Member.class))).thenReturn(savedMember);

        MemberResponseDTO result = memberService.createMember(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(fixedId.toString());
        assertThat(result.getName()).isEqualTo("John Doe");
        assertThat(result.getEmail()).isEqualTo("john@email.com");

        org.mockito.Mockito.verify(memberRepository, org.mockito.Mockito.times(1))
                .save(any(Member.class));
    }}
