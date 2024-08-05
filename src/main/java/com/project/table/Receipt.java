package com.project.table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Data
@Entity(name = "receipt")
public class Receipt implements Serializable {
    @Id
    @GeneratedValue
    private long receiptId;
    private String receiptFirstName;
    private String receiptLastName;
    private String receiptPhone;
    private String receiptAddress;
    private Timestamp receiptDate;
    private boolean receiptStatus;

    @OneToMany(mappedBy = "receipt", fetch = FetchType.LAZY)
    private List<CartItem> listCartItem;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo userInfo;
}
