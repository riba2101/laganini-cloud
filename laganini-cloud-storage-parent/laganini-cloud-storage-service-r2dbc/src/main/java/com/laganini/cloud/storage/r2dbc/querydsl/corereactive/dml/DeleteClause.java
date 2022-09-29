package com.laganini.cloud.storage.r2dbc.querydsl.corereactive.dml;

import com.querydsl.core.FilteredClause;

/**
 * {@code DeleteClause} defines a generic interface for Delete clauses
 *
 * @param <C> concrete subtype
 */
public interface DeleteClause<C extends DeleteClause<C>>
        extends DMLClause<C>, FilteredClause<C>
{

}
