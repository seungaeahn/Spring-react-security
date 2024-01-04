package springChap3googleAPI.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class UserGoogle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="usrgoogle_seq")
    @SequenceGenerator(name = "usrgoogle_seq", sequenceName="usrgoogle_seq",allocationSize=1)
    private Long id;

    private String username;
    private String email;
}