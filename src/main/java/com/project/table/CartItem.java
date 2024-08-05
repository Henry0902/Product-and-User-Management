package com.project.table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Data
@Entity
public class CartItem implements Serializable {
    private static final long serialVersionUID = -576805410603316480L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductInfo productInfo;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "receipt_id", referencedColumnName = "receiptId")
    private Receipt receipt;


    private int quantity;

    private Float price;

}
