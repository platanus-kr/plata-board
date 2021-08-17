package org.platanus.webboard.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Board {
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
