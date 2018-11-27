package edu.cnm.deepdive.many_to_many;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableEntityLinks;

@EnableEntityLinks //this is what allows spring to inject components for html resources
@SpringBootApplication
public class ManyToManyApplication {

  public static void main(String[] args) {
    SpringApplication.run(ManyToManyApplication.class, args);
  }
}
