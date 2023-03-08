package com.ytikhov.testjpa.repo

import com.ytikhov.testjpa.model.OrderEntity
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