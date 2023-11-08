package de.mosesonline.proxydeprecation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface ContactRepository extends JpaRepository<ContactEntity, UUID> {
}
