package com.pragma.fc.food_curt.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginationResponseDto<T> {
    private List<T> items;
    private int currentPageNumber;
    private int currentItemCount;
    @JsonProperty("isLastPage")
    private boolean isLastPage;
    @JsonProperty("isFirstPage")
    private boolean isFirstPage;
    private int totalPages;
    private long totalItems;
    private int pageSize;
}
