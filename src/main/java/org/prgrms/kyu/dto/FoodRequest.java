package org.prgrms.kyu.dto;

import lombok.*;
import org.prgrms.kyu.entity.Food;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
public class FoodRequest {
    private String name;
    private String description;
    private Integer price;

    public static Food convertToFood(FoodRequest request) {
        return Food.builder()
                .name(request.name)
                .description(request.description)
                .price(request.price)
                .build();
    }

}