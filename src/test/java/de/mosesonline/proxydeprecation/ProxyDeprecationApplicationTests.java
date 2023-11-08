package de.mosesonline.proxydeprecation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProxyDeprecationApplicationTests {

    public static void main(String[] args) {
        SpringApplication.from(ProxyDeprecationApplication::main)
                .with(MyContainersConfiguration.class)
                .run(args);
    }

}
