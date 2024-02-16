package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Vendor {
    private Long id;
    private  String name;
    private String serviceCategory;
    private String state;
}
