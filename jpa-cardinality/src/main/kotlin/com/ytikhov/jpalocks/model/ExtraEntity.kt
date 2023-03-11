package com.ytikhov.jpalocks.model

import javax.persistence.*

@Entity
@Table(name = "t_extra")
class ExtraEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: OrderEntity? = null

    @Column(name = "extra_text")
    var extraText: String? = null
}
