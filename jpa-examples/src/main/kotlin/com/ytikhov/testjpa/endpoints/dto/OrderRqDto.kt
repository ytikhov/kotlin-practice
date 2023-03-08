package com.ytikhov.testjpa.endpoints.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class OrderRqDto(
    val text: String,
    val extraText: String? = null,
    val positions: List<OrderRqPosDto>? = null
)

data class OrderRqPosDto(
    val text: String
)
