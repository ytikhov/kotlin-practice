package com.ytikhov.testjpa

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@ActiveProfiles(profiles = ["test"])
@AutoConfigureMockMvc
class TestjpaApplicationTests {
	@Autowired
	lateinit var mockMvc: MockMvc
}
