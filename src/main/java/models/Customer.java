package models;

import lombok.*;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor //required
@AllArgsConstructor
public class Customer {
    private String customerId;
    private String customerName;
}
