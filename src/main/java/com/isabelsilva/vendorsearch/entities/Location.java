package com.isabelsilva.vendorsearch.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Location {
    private Long id;
    private String name;
    private String state;
}
