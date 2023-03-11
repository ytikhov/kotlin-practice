package com.ytikhov.jpalocks.repo

import com.ytikhov.jpalocks.model.SharedEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SharedRepository: JpaRepository<SharedEntity, Long>