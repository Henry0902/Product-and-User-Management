package com.project.table;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "PRODUCT_GROUP")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Vui lòng nhập tên nhóm sản phẩm")
    @Nationalized
    @Size(max = 255, message = "Độ dài không quá 255 kí tự")
    String groupProductName;

    Integer quantity;

    Integer status;

    @Temporal(TemporalType.TIMESTAMP)
    Date createTime;

    @Nationalized
    @Size(max = 255, message = "Độ dài không quá 255 kí tự")
    String createBy;

}
