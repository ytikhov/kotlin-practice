package com.ytikhov.jpalocks.services

import com.ytikhov.jpalocks.model.SharedEntity
import com.ytikhov.jpalocks.repo.SharedRepository
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SharedService(private val sharedRepo: SharedRepository) {
    @Transactional
    fun create(value: Long): Long {
        val sharedEntity = SharedEntity().apply { sharedValue = value }
        val savedEntity = sharedRepo.save(sharedEntity)
        return savedEntity.id ?: -1
    }

    @Transactional
    @Throws(ObjectOptimisticLockingFailureException::class, Throwable::class)
    fun update(id: Long, newValue: Long) {
        val entity = sharedRepo.findById(id).get()
        entity.sharedValue = newValue
        sharedRepo.save(entity)
    }
}