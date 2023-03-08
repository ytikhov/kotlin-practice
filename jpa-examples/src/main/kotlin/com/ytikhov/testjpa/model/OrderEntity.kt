package com.ytikhov.testjpa.model

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "t_order")
class OrderEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    var id: UUID? = null

    @Column(name = "text")
    var text: String? = null

    @OneToOne(
        mappedBy = "order",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    var extra: ExtraEntity? = null

    @OneToMany(
        mappedBy = "order",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    var positions: List<PositionEntity>? = null
}