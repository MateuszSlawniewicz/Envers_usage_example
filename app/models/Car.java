package models;


import org.hibernate.envers.Audited;

import javax.persistence.*;


@Entity
@Table(schema = "public",  name="cars")
@Audited
public class Car {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "color")
    private String color;

    @Column(name = "power")
    private Integer power;

    @Column(name = "brand")
    private String brand;

    @Column(name = "mileage")
    private Integer mileage;


}

