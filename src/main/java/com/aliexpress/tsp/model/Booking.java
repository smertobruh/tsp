package com.aliexpress.tsp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BOOKING")
public class Booking {

    @Id
    @SequenceGenerator(name = "book_gen", sequenceName = "BOOKING_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_gen")
    @Column(name = "BOOKID")
    private Long bookID;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "CARID")
    private Car car;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "USERID")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STARTTIME")
    private LocalDateTime startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENDTIME")
    private  LocalDateTime endTime;



}
