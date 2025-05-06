package com.pororo.testcode01;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

@Disabled // 해당 테스트를 무시함
@Transactional // 테스트코드에서는 @Transactional 수행 후 자동 롤백 -> 변경사항 저장 X
@SpringBootTest
@ContextConfiguration(initializers = IntegrationTest.IntegrationTestInitializer.class)
public class IntegrationTest {

  static ComposeContainer rdbms;

  static {
    rdbms =
        new ComposeContainer(new File("infra/test/docker-compose.yml"))
            .withExposedService(
                "local-db", // Docker-Compose 내의 서비스 명
                3306, // 노출할 포트 번호
                Wait.forLogMessage(".*ready for connections.*", 1) // 대기 조건, 컨테이너를 띄워보고 로그를 통해 확인해 보아야 함
                    .withStartupTimeout(Duration.ofSeconds(300)))
            .withExposedService(
                "local-db-migrate",
                0,
                Wait.forLogMessage("(.*Successfully applied.*)|(.*Successfully validated.*)", 1)
                    .withStartupTimeout(Duration.ofSeconds(300)));

    rdbms.start();

  }

  static class IntegrationTestInitializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      Map<String, String> properties = new HashMap<>();

      var rdbmsHost = rdbms.getServiceHost("local-db", 3306);
      var rdbmsPort = rdbms.getServicePort("local-db", 3306); // TestContainers에서는 실행마다 실제 포트 번호가 달라질 수 있음

      properties.put(
          "spring.datasource.url", "jdbc:mysql://" + rdbmsHost + ":" + rdbmsPort + "/score");


      TestPropertyValues.of(properties).applyTo(applicationContext);
    }
  }
}
