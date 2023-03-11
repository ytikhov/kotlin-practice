package com.ytikhov.jpalocks.services

import com.ytikhov.jpalocks.endpoints.dto.OrderRqDto
import com.ytikhov.jpalocks.model.ExtraEntity
import com.ytikhov.jpalocks.model.OrderEntity
import com.ytikhov.jpalocks.model.PositionEntity
import com.ytikhov.jpalocks.repo.OrderRepository
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
