package com.ytikhov.jpalocks.jpa

import com.ytikhov.jpalocks.TestjpaApplicationTests
import com.ytikhov.jpalocks.repo.SharedRepository
import com.ytikhov.jpalocks.services.SharedService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.thread
import kotlin.random.Random

class JpaTest(
    @Autowired private val sharedService: SharedService,
    @Autowired private val sharedRepo: SharedRepository
) : TestjpaApplicationTests() {

    val ATTEMPTS = 1000L

    @Test
    fun `Optimistic lock works good`() {
        val newValue = AtomicLong()
        val failureCounter = AtomicLong()
        val id = sharedService.create(newValue.get())

        val countDownLatch = CountDownLatch(ATTEMPTS.toInt())

        for (i in 1..ATTEMPTS) {
            TimeUnit.MILLISECONDS.sleep(Random.nextLong(0, 100L))
            thread {
                try {
                    sharedService.update(id, newValue.incrementAndGet())
                } catch (e: Throwable) {
                    newValue.decrementAndGet()
                    failureCounter.incrementAndGet()
                } finally {
                    countDownLatch.countDown()
                }
            }
        }
        countDownLatch.await()

        val entity = sharedRepo.findById(id).get()
        Assertions.assertEquals(ATTEMPTS,entity.sharedValue + failureCounter.get())
    }
}