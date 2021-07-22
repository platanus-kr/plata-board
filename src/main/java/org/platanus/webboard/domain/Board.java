package org.platanus.webboard.domain;

import lombok.Data;

@Data
public class Board {
    private long id;
    private String name;
    private String description;

}
