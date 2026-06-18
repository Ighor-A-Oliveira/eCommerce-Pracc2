package com.ighor.api.e_commerce.repo;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ProductRepoTest {

    @Autowired
    EntityManager entityManager;
}
