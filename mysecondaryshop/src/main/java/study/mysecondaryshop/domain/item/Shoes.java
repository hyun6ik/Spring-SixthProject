package study.mysecondaryshop.domain.item;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("S")
public class Shoes extends Item{
    private int size;
}
