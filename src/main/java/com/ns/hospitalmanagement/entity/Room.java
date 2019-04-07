package com.ns.hospitalmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false,nullable = false)
    private long roomId;

    @Column(nullable = false)
    private String roomName;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime insertTimeStamp;

    @Column
    @UpdateTimestamp
    private LocalDateTime updateTimeStamp;
}
