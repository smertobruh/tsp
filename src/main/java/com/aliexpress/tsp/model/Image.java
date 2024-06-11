package com.aliexpress.tsp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "IMAGES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Long id;

    @Column(name="NAME")
    private String filename;

    @Column(name="CONTENT_TYPE")
    private  String contentType;

    @Column(name="IS_PREVIEW_IMAGE")
    private  boolean isPreviewImage;


    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Car car;
}
