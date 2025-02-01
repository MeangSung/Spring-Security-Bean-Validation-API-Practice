package com.example.springSecurity.auth.repo.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_authority")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthority {

    @EmbeddedId
    private UserAuthorityId userAuthorityId;
}
