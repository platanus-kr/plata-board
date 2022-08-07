package org.platanus.webboard.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
