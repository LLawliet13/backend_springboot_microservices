package com.example.mock2.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeliveryDTO implements Comparable<DeliveryDTO> {

    private String deliveryTime;
    private String status;

    @Override
    public int compareTo(DeliveryDTO o) {
        return this.getDeliveryTime().compareTo(o.getDeliveryTime());
    }

}
