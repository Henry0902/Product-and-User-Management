package com.project.model.dto;

import com.project.table.Cart;
import com.project.table.CartItem;
import com.project.table.UserInfo;
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
public class OrderDto {
    @Id
     long Id;
     String firstName;
     String lastName;
     String phone;
     String address;
     Timestamp date;
     String status;
     String paymentMethod;

}
