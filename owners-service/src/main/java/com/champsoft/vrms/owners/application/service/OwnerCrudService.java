package com.champsoft.vrms.owners.application.service;

import com.champsoft.vrms.owners.application.exception.DuplicateOwnerException;
import com.champsoft.vrms.owners.application.exception.OwnerDeletionBlockedException;
import com.champsoft.vrms.owners.application.exception.OwnerNotFoundException;
import com.champsoft.vrms.owners.application.port.out.RegistrationUsagePort;
import com.champsoft.vrms.owners.application.port.out.OwnerRepositoryPort;
import com.champsoft.vrms.owners.domain.model.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OwnerCrudService {

    private final OwnerRepositoryPort repo;
    private final RegistrationUsagePort registrationUsagePort;

    public OwnerCrudService(OwnerRepositoryPort repo, RegistrationUsagePort registrationUsagePort) {
        this.repo = repo;
        this.registrationUsagePort = registrationUsagePort;
    }

    @Transactional
    public Owner create(String fullName, String address) {
        var name = new FullName(fullName);
        String key = name.value();
        if (repo.existsByFullName(key)) {
            throw new DuplicateOwnerException("Owner already exists by name: " + key);
        }
        var owner = new Owner(OwnerId.newId(), name, new Address(address));
        return repo.save(owner);
    }

    @Transactional(readOnly = true)
    public Owner getById(String id) {
        return repo.findById(OwnerId.of(id))
                .orElseThrow(() -> new OwnerNotFoundException("Owner not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Owner> list() { return repo.findAll(); }

    @Transactional
    public Owner update(String id, String fullName, String address) {
        var owner = getById(id);
        owner.update(new FullName(fullName), new Address(address));
        return repo.save(owner);
    }

    @Transactional
    public Owner activate(String id) {
        var owner = getById(id);
        owner.activate();
        return repo.save(owner);
    }

    @Transactional
    public Owner suspend(String id) {
        var owner = getById(id);
        owner.suspend();
        return repo.save(owner);
    }

    @Transactional
    public void delete(String id) {
        getById(id);
        if (registrationUsagePort.hasRegistrations(id)) {
            throw new OwnerDeletionBlockedException(
                    "Owner cannot be deleted because registrations already reference it: " + id
            );
        }
        repo.deleteById(OwnerId.of(id));
    }
}

