package dev.panasovsky.module.auth.model.redis;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@Data
@RedisHash("refreshTokens")
public class RefreshJWT {

    @Id
    private String id;
    @Indexed
    private String refreshToken;

}