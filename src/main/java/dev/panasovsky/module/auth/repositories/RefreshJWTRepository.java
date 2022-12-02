package dev.panasovsky.module.auth.repositories;

import dev.panasovsky.module.auth.model.redis.RefreshJWT;

import org.springframework.stereotype.Repository;
import org.springframework.data.keyvalue.repository.KeyValueRepository;


@Repository
public interface RefreshJWTRepository extends KeyValueRepository<RefreshJWT, String> {

}