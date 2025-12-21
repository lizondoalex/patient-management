package com.pm.memberservice.kafka;

import com.pm.memberservice.model.Member;
import member.events.MemberEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Member member){
        MemberEvent event = MemberEvent.newBuilder()
                .setMemberId(member.getId().toString())
                .setEmail(member.getEmail())
                .setName(member.getName())
                .setEventType("MEMBER_CREATED")
                .build();
        try{
            kafkaTemplate.send("member", event.toByteArray());
        } catch (Exception ex){
            log.error("Error sending MemberCreated event: {}", event);
        }
    }
}
