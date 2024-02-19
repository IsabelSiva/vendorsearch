package com.isabelsilva.vendorsearch.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Job {
    private Long id;
    private ServiceCategory serviceCategory;
    private Location location;
}
