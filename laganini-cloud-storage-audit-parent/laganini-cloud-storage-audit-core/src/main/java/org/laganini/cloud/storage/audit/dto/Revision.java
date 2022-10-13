package org.laganini.cloud.storage.audit.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Revision {

    private String key;
    private String type;

    public Revision(String key, String type) {
        this.key = key;
        this.type = type;
    }

}
