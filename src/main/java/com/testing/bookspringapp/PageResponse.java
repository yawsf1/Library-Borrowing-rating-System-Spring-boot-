package com.testing.bookspringapp;

import java.util.List;

public record PageResponse<T> (
    List<T> content,
    int page,
    int size,
    long totalElement,
    int totalPages,
    boolean isLast
){
}
