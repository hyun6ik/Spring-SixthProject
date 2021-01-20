package study.mysecondaryshop.domain.item;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("C")
public class Clothes extends Item{
    private String size;
}
