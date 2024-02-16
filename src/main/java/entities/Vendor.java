package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Vendor {
    private Long id;
    @Setter
    private  String name;
    @Setter
    private String state;
}
