package com.project.table;

import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PRODUCT_MODULE")
@Data
public class ProductModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Size(max = 255, message = "Độ dài không quá 255 kí tự")
    String name;

    Integer status;

    Long parentProductId;

    @Nationalized
    @Size(max = 255, message = "Độ dài không quá 255 kí tự")
    String url;

    long place;

    @Nationalized
    @Size(max = 255, message = "Độ dài không quá 255 kí tự")
    String icon;
}
