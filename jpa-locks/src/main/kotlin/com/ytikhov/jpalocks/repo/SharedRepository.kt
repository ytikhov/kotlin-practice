package com.ytikhov.jpalocks.repo

import com.ytikhov.jpalocks.model.SharedEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import java.util.*
import javax.persistence.LockModeType

interface SharedRepository: JpaRepository<SharedEntity, Long> {
    @Lock(LockModeType.OPTIMISTIC)
    override fun findById(id: Long): Optional<SharedEntity>
}