package org.laganini.cloud.rmi.connector.binary;

import lombok.*;
import org.springframework.http.HttpHeaders;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class BlockingFilePart {

    private String filename;
    private HttpHeaders headers;
    private byte[] content;

    public BlockingFilePart(String filename, HttpHeaders headers, byte[] content) {
        this.filename = filename;
        this.headers = headers;
        this.content = content;
    }

}
