package study.mysecondaryshop.domain.item;

import lombok.Getter;
import study.mysecondaryshop.domain.OrderItem;
import study.mysecondaryshop.domain.exception.NotEnoughStockException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    // 비즈니스 로직
    // 재고 증가 로직
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    };

    // 재고 감소 로직
    public void removeStock(int quantity){
        int restStock = this.stockQuantity -= quantity;
        if(restStock <0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}
