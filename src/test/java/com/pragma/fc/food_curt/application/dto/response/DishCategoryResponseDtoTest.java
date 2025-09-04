package com.pragma.fc.food_curt.application.dto.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DishCategoryResponseDtoTest {

    @Test
    void shouldSerializeOnlyExpectedFields() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        DishCategoryResponseDto dto = new DishCategoryResponseDto();
        dto.setId(2);
        dto.setName("Restaurant name");

        String json = mapper.writeValueAsString(dto);
        Map<String, Object> fields = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});

        assertThat(fields.keySet())
                .hasSize(2)
                .containsExactlyInAnyOrder("id", "name");
    }
}
