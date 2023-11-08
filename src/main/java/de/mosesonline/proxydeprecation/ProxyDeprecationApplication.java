package de.mosesonline.proxydeprecation;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
public class ProxyDeprecationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyDeprecationApplication.class, args);
    }

    @RestController
    @AllArgsConstructor
    @RequestMapping("/contacts")
    public static class TestController {
        private final ContactRepository contactRepository;

        @GetMapping()
        public List<ContactEntity> contacts() {
            return contactRepository.findAll();
        }
    }


}
