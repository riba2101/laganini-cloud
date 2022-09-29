package com.laganini.cloud.storage.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Owner {

    private final Long groupId;
    private final Long ownerId;

    public Owner(Long groupId, Long ownerId) {
        this.groupId = groupId;
        this.ownerId = ownerId;
    }

}
