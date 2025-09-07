package com.pragma.fc.food_curt.domain.model;

import java.util.List;

public class Pagination<T> {
    private List<T> items;

    private int currentPageNumber;
    private int currentItemCount;
    private boolean isLastPage;
    private boolean isFirstPage;

    private int totalPages;
    private long totalItems;
    private int pageSize;

    public Pagination() {
    }

    public Pagination(List<T> items, int currentPageNumber, int currentItemCount, boolean isLastPage, boolean isFirstPage, int totalPages, long totalItems, int pageSize) {
        this.items = items;
        this.currentPageNumber = currentPageNumber;
        this.currentItemCount = currentItemCount;
        this.isLastPage = isLastPage;
        this.isFirstPage = isFirstPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.pageSize = pageSize;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public int getCurrentItemCount() {
        return currentItemCount;
    }

    public void setCurrentItemCount(int currentItemCount) {
        this.currentItemCount = currentItemCount;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
