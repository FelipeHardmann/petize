package com.example.petize.petize.controller.dto;

import java.util.List;

public record ApiResponse<T>(List<T> data, PaginationResponse paginationResponse) {
}
