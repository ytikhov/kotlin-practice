package com.ytikhov.jpalocks.endpoints

import com.ytikhov.jpalocks.TestjpaApplicationTests
import com.ytikhov.jpalocks.repo.ExtraRepository
import com.ytikhov.jpalocks.repo.OrderRepository
import com.ytikhov.jpalocks.repo.PositionRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import utils.JsonUtil
import java.util.*

class EndpointsTest(
    @Autowired private val orderRepo: OrderRepository,
    @Autowired private val extraRepo: ExtraRepository,
    @Autowired private val posRepo: PositionRepository,
): TestjpaApplicationTests() {

    @Test
    fun `create order without extra and without positions`() {
        val rq = JsonUtil.fromClasspath("local/orderRqWithoutExtraAndPositions.json")!!
        val result = mockMvc.perform(
            post("/api/v1/order")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(rq)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is2xxSuccessful)
            .andReturn()
        val id = result.response.contentAsString
        Assertions.assertNotNull(id)
        val uuid = UUID.fromString(id)
        val order = orderRepo.findById(uuid).get()
        Assertions.assertNotNull(order)
        Assertions.assertEquals("order 1", order.text)
        Assertions.assertNull(order.extra)
        val extra = extraRepo.findByOrderId(uuid)
        Assertions.assertEquals(extra, Optional.ofNullable(null))
    }

    @Test
    fun `create order with extra and without positions`() {
        val rq = JsonUtil.fromClasspath("local/orderRqWithExtraAndWithoutPositions.json")!!
        val result = mockMvc.perform(
            post("/api/v1/order")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(rq)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is2xxSuccessful)
            .andReturn()
        val id = result.response.contentAsString
        Assertions.assertNotNull(id)
        val uuid = UUID.fromString(id)
        val order = orderRepo.findById(uuid).get()
        Assertions.assertNotNull(order)
        Assertions.assertEquals("order 2", order.text)
        Assertions.assertNotNull(order.extra)
        val extra = extraRepo.findByOrderId(uuid).get()
        Assertions.assertEquals(extra.extraText, "extra for order 2")
    }

    @Test
    fun `create order with extra and positions`() {
        val rq = JsonUtil.fromClasspath("local/orderRqWithExtraAndPositions.json")!!
        val result = mockMvc.perform(
            post("/api/v1/order")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(rq)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is2xxSuccessful)
            .andReturn()
        val id = result.response.contentAsString
        Assertions.assertNotNull(id)
        val uuid = UUID.fromString(id)
        val order = orderRepo.findById(uuid).get()
        Assertions.assertNotNull(order)
        Assertions.assertEquals(order.positions?.size, 3)
        Assertions.assertEquals("order 3", order.text)
        Assertions.assertNotNull(order.extra)
        val extra = extraRepo.findByOrderId(uuid).get()
        Assertions.assertEquals(extra.extraText, "extra for order 3")
        val positions = posRepo.findAllByOrderId(uuid)
        Assertions.assertEquals(positions.size, 3)
    }
}