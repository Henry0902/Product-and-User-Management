package com.project.table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Data
@Entity
public class Checkout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private Timestamp date;

    private String status;

    @OneToMany(mappedBy = "checkout", fetch = FetchType.LAZY)
    private List<CartItem> listCartItem;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo userInfo;

    @OneToOne
    @JoinColumn(name = "cart_id") // Đảm bảo rằng tên cột là cart_id
    private Cart cart; // Đảm bảo ánh xạ @OneToOne tới Cart

    private String paymentMethod;
}
