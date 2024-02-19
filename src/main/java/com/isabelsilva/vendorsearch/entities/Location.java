package com.isabelsilva.vendorsearch.entities;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class Location {
    private Long id;
    private String name;
    private String state;
}
