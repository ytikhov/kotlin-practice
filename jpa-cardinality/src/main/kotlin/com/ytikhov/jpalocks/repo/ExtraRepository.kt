package com.ytikhov.jpalocks.repo

import com.ytikhov.jpalocks.model.ExtraEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ExtraRepository: JpaRepository<ExtraEntity, Long> {
    fun findByOrderId(orderId: UUID): Optional<ExtraEntity>
}
