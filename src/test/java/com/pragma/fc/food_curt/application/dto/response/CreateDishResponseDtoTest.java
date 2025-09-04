package com.pragma.fc.food_curt.application.dto.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CreateDishResponseDtoTest {
    @Test
    void shouldSerializeOnlyExpectedFields() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        CreateDishResponseDto dto = new CreateDishResponseDto();
        dto.setId(2);
        dto.setName("Restaurant name");

        dto.setId(1);
        dto.setCategory(new DishCategoryResponseDto());
        dto.getCategory().setId(10);
        dto.getCategory().setName("Dish category name");

        dto.setRestaurant(new RestaurantSummaryResponseDto());
        dto.getRestaurant().setNit(123456789L);
        dto.getRestaurant().setName("Restaurant name");

        dto.setName("Delicious Dish");
        dto.setDescription("A tasty dish description");
        dto.setPrice(25000L);
        dto.setImageUrl("https://example.com/images/dish.jpg");
        dto.setIsActive(true);

        String json = mapper.writeValueAsString(dto);
        Map<String, Object> fields = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});

        assertThat(fields.keySet())
                .hasSize(8)
                .containsExactlyInAnyOrder(
                    "id",
                    "category",
                    "restaurant",
                    "name",
                    "description",
                    "price",
                    "imageUrl",
                    "isActive"
                );
    }
}
