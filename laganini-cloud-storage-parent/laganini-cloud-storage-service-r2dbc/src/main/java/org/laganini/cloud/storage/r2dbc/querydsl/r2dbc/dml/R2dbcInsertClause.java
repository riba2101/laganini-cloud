package org.laganini.cloud.storage.r2dbc.querydsl.r2dbc.dml;

import com.querydsl.sql.Configuration;
import com.querydsl.sql.RelationalPath;
import org.laganini.cloud.storage.r2dbc.querydsl.r2dbc.R2dbcConnectionProvider;

/**
 * {@link R2dbcInsertClause} defines an INSERT INTO clause
 * If you need to subtype this, use {@link AbstractR2dbcInsertClause} instead.
 */
public class R2dbcInsertClause
        extends AbstractR2dbcInsertClause<R2dbcInsertClause>
{

    public R2dbcInsertClause(
            R2dbcConnectionProvider connection,
            Configuration configuration,
            RelationalPath<?> entity
    )
    {
        super(connection, configuration, entity);
    }

}
