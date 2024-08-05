package com.project.table;

import lombok.Data;
import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "PRODUCT_INFO")
@Data
public class ProductInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Tên sản phẩm không được để trống")
    @Nationalized
    @Size(max = 255, message = "Độ dài không quá 255 kí tự")
    String productName;

    @Column(name="PRICE")
    private Float price;

    @NotEmpty(message = "Mô tả không được để trống")
    @Nationalized
    @Size(max = 255, message = "Độ dài không quá 255 kí tự")
    String productDesc;

    @NotEmpty(message = "Nguồn gốc không được để trống")
    @Nationalized
    @Size(max = 255, message = "Độ dài không quá 255 kí tự")
    String productOrigin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date productDate;


    @Column(name="IMAGE_NAME")
    private String imageName;

    Integer status;

    long groupId;

    @Temporal(TemporalType.TIMESTAMP)
    Date createTime;

    @Nationalized
    @Size(max = 255, message = "Độ dài không quá 255 kí tự")
    String createBy;



}
