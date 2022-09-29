package org.laganini.cloud.storage.r2dbc.querydsl.r2dbc;

import io.r2dbc.spi.Connection;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import java.util.Objects;

public final class FixedR2dbcConnectionProvider
        implements R2dbcConnectionProvider
{

    private final Connection connection;

    private FixedR2dbcConnectionProvider(@Nonnull Connection connection) {
        this.connection = Objects.requireNonNull(connection, "Connection is required");
    }

    /**
     * @param connection the active connection of the current transaction
     */
    public static R2dbcConnectionProvider of(@Nonnull Connection connection) {
        return new FixedR2dbcConnectionProvider(connection);
    }

    @Override
    public Mono<Connection> getConnection() {
        return Mono.just(connection);
    }

}
