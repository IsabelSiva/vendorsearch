package com.isabelsilva.vendorsearch.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Vendor {
    private Long id;
    private List<ServiceCategory> serviceCategories;
    private Location location;
    private boolean compliant;
}
