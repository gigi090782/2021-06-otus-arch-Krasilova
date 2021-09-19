package ru.krasilova.otus.microservices.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.krasilova.otus.microservices.model.Version;
import ru.krasilova.otus.microservices.repository.VersionRepository;

@Service
public class VersionServiceImpl implements VersionService{

    private final VersionRepository versionRepository;

    public VersionServiceImpl(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }

    @Override
    public Version save(Version version) {
        return versionRepository.save(version);
    }

    @Override
    public Optional<Version> find() {
        return Optional.ofNullable(versionRepository.findFirstByOrderByIdAsc());
    }
}
