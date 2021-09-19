package ru.krasilova.otus.microservices.service;

import java.util.Optional;
import ru.krasilova.otus.microservices.model.Version;

public interface VersionService {
    Version save(Version version);

    Optional<Version> find ();

}
