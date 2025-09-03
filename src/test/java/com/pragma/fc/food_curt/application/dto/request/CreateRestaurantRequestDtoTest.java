package com.pragma.fc.food_curt.application.dto.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.fc.food_curt.application.dto.response.CreateRestaurantResponseDto;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateRestaurantRequestDtoTest {
    @Test
    void shouldSerializeOnlyExpectedFields() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        CreateRestaurantRequestDto dto = new CreateRestaurantRequestDto();
        dto.setNit(123L);
        dto.setOwnerDocumentNumber(213L);
        dto.setName("Restaurant name");
        dto.setAddress("Restaurant address");
        dto.setPhone("Restaurant phone");
        dto.setLogoUrl("Restaurant logo url");

        String json = mapper.writeValueAsString(dto);
        Map<String, Object> fields = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});


        assertThat(fields.keySet())
                .hasSize(6)
                .containsExactlyInAnyOrder(
                        "nit",
                        "ownerDocumentNumber",
                        "name",
                        "address",
                        "phone",
                        "logoUrl"
                );
    }
}
