package by.betrayal.personalservice.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {

    @Container
    private final MySQLContainer<?> sqlContainer
            = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"));

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    @Autowired
    public IntegrationTest(MockMvc mockMvc, ObjectMapper mapper) {
        this.mockMvc = mockMvc;
        this.mapper = mapper;
    }

    public MockMvc getMockMvc() {
        return mockMvc;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public MySQLContainer<?> getSqlContainer() {
        return sqlContainer;
    }
}
