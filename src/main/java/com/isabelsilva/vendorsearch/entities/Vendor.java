package com.isabelsilva.vendorsearch.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Vendor {
    private Long id;
    private  String name;
    private String serviceCategory;
    private String location;
    private boolean compliant;
}
