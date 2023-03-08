package com.ytikhov.testjpa.repo

import com.ytikhov.testjpa.model.PositionEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PositionRepository: JpaRepository<PositionEntity, Long> {
    fun findAllByOrderId(orderId: UUID): List<PositionEntity>
}
