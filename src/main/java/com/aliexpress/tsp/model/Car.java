package com.aliexpress.tsp.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.*;

@Entity
@Table(name = "CARS")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "cars_gen", sequenceName = "CARS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cars_gen")
    private Long id;

    @Column(name = "TRADEMARK")
    private String trademark;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "CLAZZ")
    private String clazz;

    @Column(name = "ENGINEPOWER")
    private int enginePower;

    @Column(name = "PRICE", nullable = true)
    private int price;

    @ManyToMany
    @JoinTable(
            name = "FAVOURITES",
            joinColumns = { @JoinColumn(name = "CARID") },
            inverseJoinColumns = { @JoinColumn(name = "USERID") }
    )
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "car", cascade = {CascadeType.ALL})
    private Set<Booking> bookings = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "car")
    private List<Image> images = new ArrayList<>();

    @Column(name="PREVIEW_IMAGEID")
    private Long previewImageID;

    public void addImageToCar(Image image) {
        image.setCar(this);
        images.add(image);
    }

}
