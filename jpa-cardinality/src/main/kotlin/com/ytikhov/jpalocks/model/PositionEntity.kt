package com.ytikhov.jpalocks.model

import javax.persistence.*

@Entity
@Table(name = "t_position")
class PositionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: OrderEntity? = null

    @Column(name = "position_text")
    var positionText: String? = null
}
