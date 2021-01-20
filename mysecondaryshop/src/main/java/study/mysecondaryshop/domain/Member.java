package study.mysecondaryshop.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name ="member_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @Embedded
    private Address address;
}
