package ru.krasilova.otus.microservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krasilova.otus.microservices.model.Order;
import ru.krasilova.otus.microservices.model.Version;

public interface VersionRepository extends JpaRepository<Version, Long> {

    Version findFirstByOrderByIdAsc();

}
