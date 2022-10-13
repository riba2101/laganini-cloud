package org.laganini.cloud.storage.connector.service;

import org.laganini.cloud.storage.connector.model.Fetchable;

public interface PersistableEndpoint<ID, IN extends Fetchable<ID>, OUT extends Fetchable<ID>>
        extends CreatebleEndpoint<ID, IN, OUT>, UpdatableEndpoint<ID, IN, OUT>
{

}
