package com.pragma.fc.food_curt.application.dto.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateDishRequestDtoTest {
    @Test
    void shouldSerializeOnlyExpectedFields() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        UpdateDishRequestDto dto = new UpdateDishRequestDto();

        dto.setDescription("New dish description");
        dto.setPrice(150000.0);

        String json = mapper.writeValueAsString(dto);
        Map<String, Object> fields = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});

        assertThat(fields.keySet())
                .hasSize(2)
                .containsExactlyInAnyOrder("description", "price");
    }
}
