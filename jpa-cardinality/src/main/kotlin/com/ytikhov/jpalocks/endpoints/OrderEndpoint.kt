package com.ytikhov.jpalocks.endpoints

import com.ytikhov.jpalocks.endpoints.dto.OrderRqDto
import com.ytikhov.jpalocks.services.OrderService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["api/v1"])
class OrderEndpoint(private val orderService: OrderService) {
    @PostMapping(value = ["order"], produces = [MediaType.TEXT_PLAIN_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createOrder(@RequestBody request: OrderRqDto): ResponseEntity<String> {
        return ResponseEntity.ok(orderService.create(request))
    }
}