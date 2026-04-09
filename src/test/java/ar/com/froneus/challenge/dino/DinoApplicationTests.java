package ar.com.froneus.challenge.dino;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import javax.sql.DataSource;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(properties = {
		"spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
		"spring.jpa.hibernate.ddl-auto=none",
		"spring.datasource.url=jdbc:h2:mem:testdb",
		"spring.datasource.driver-class-name=org.h2.Driver",
		"management.health.redis.enabled=false",
		"management.health.rabbit.enabled=false",
		"management.health.db.enabled=false"
})
@EnableAutoConfiguration(exclude = {
		org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration.class,
		org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration.class,
		org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration.class,
		org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration.class
})
class DinoApplicationTests {

	@MockitoBean
	private DataSource dataSource;

	@MockitoBean
	private ConnectionFactory rabbitConnectionFactory;

	@MockitoBean
	private RedisConnectionFactory redisConnectionFactory;

	@MockitoBean
	private RedisTemplate<String, Object> redisTemplate;

	@MockitoBean
	private RabbitTemplate rabbitTemplate;

	@Test
	void contextLoads() {
	}
}
