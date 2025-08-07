package com.stockathings.StockaThings.repositories;

import com.stockathings.StockaThings.domain.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
}
