package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Job {
    private Long id;
    private String serviceCategory;
    private String location;
}
