package com.pragma.fc.food_curt.application.dto.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CreateDishRequestDtoTest {
    @Test
    void shouldSerializeOnlyExpectedFields() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        CreateDishRequestDto dto = new CreateDishRequestDto();

        dto.setCategoryId(1);
        dto.setRestaurantNit(123456789L);
        dto.setName("Delicious Dish");
        dto.setDescription("A tasty and healthy dish description");
        dto.setPrice(25000.0);
        dto.setImageUrl("https://example.com/images/dish.jpg");

        String json = mapper.writeValueAsString(dto);
        Map<String, Object> fields = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});

        assertThat(fields.keySet())
                .hasSize(6)
                .containsExactlyInAnyOrder(
                "categoryId",
                    "restaurantNit",
                    "name",
                    "description",
                    "price",
                    "imageUrl"
                );
    }
}
