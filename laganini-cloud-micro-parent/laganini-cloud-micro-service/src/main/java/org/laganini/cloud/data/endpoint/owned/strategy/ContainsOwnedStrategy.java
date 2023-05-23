package org.laganini.cloud.data.endpoint.owned.strategy;

import org.laganini.cloud.storage.entity.OwnedEntity;
import org.laganini.cloud.storage.entity.OwnerReference;

import java.util.stream.Stream;

public class ContainsOwnedStrategy<OWNER_ID, ENTITY extends OwnedEntity<OWNER_ID>>
        extends AbstractOwnedStrategy<OWNER_ID, ENTITY>
{

    @Override
    public boolean supports(OWNER_ID current, ENTITY... entities) {
        return Stream
                .of(entities)
                .anyMatch(entity -> decide(current, entity) == OwnerDecision.Decision.ALLOW);
    }

    @Override
    protected ENTITY enrich(OWNER_ID current, ENTITY entity) {
        OwnerReference<OWNER_ID> actual = entity.getOwner();
        if (actual == null) {
            entity.setOwner(new OwnerReference<>(current));
            return entity;
        }

        OWNER_ID actualId = actual.getId();
        if (actualId == null) {
            entity.setOwner(new OwnerReference<>(current));
            return entity;
        }

        return entity;
    }

    protected OwnerDecision.Decision decide(OWNER_ID current, ENTITY entity) {
        if (current == null) {
            return OwnerDecision.Decision.DECLINE;
        }

        OwnerReference<OWNER_ID> actual = entity.getOwner();
        if (actual == null) {
            return OwnerDecision.Decision.ALLOW;
        }

        OWNER_ID actualId = actual.getId();
        if (actualId == null) {
            return OwnerDecision.Decision.ALLOW;
        }

        return actualId.equals(current) ? OwnerDecision.Decision.ALLOW : OwnerDecision.Decision.DECLINE;
    }

}
