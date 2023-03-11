package com.ytikhov.jpalocks.repo

import com.ytikhov.jpalocks.model.PositionEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PositionRepository: JpaRepository<PositionEntity, Long> {
    fun findAllByOrderId(orderId: UUID): List<PositionEntity>
}
