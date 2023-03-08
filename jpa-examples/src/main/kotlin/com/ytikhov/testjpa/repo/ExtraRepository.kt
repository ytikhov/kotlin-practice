package com.ytikhov.testjpa.repo

import com.ytikhov.testjpa.model.ExtraEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ExtraRepository: JpaRepository<ExtraEntity, Long> {
    fun findByOrderId(orderId: UUID): Optional<ExtraEntity>
}
