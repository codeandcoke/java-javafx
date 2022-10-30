package space.harbour.orders.domain;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {

    private String id;
    private LocalDate date;
    private String customerName;
    private float price;
}
