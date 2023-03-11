package com.ytikhov.jpalocks.repo

import com.ytikhov.jpalocks.model.OrderEntity
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface OrderRepository: JpaRepository<OrderEntity, UUID> {
    @EntityGraph(
        type = EntityGraph.EntityGraphType.FETCH,
        attributePaths = [
            "extra",
            "positions"
        ]
    )
    override fun findById(id: UUID): Optional<OrderEntity>
}