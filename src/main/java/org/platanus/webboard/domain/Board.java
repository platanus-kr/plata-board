package org.platanus.webboard.domain;

import lombok.Data;

@Data
public class Board {
    private Long id;
    private String name;
    private String description;
    
}
