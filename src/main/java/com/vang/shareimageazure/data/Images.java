package com.vang.shareimageazure.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter
@Setter
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageid")
    private long imageid;
    @Column(name = "imagename")
    private String imagename;
    @Column(name = "imageurl")
    private String imageurl;
    @Column(name = "status")
    private int status;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    private Users userid;
}
