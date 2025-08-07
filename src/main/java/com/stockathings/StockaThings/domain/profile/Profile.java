package com.stockathings.StockaThings.domain.profile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "profile")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Profile {

    @Id
    @GeneratedValue
    private UUID id;

    private String email;

    private String password;
}
