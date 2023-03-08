package com.ytikhov.testjpa.services

import com.ytikhov.testjpa.endpoints.dto.OrderRqDto
import com.ytikhov.testjpa.model.ExtraEntity
import com.ytikhov.testjpa.model.OrderEntity
import com.ytikhov.testjpa.model.PositionEntity
import com.ytikhov.testjpa.repo.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(private val orderRepo: OrderRepository) {
    @Transactional
    fun create(request: OrderRqDto): String {
        val orderEntity = OrderEntity().apply {
            val oe = this
            text = request.text
            extra = if (request.extraText != null) ExtraEntity().apply {
                extraText = request.extraText
                order = oe
            } else null
            positions = request.positions?.map {
                PositionEntity().apply {
                    positionText = it.text
                    order = oe
                }
            }?.toList()
        }
        val savedEntity = orderRepo.save(orderEntity)
        return savedEntity.id!!.toString()
    }

}
