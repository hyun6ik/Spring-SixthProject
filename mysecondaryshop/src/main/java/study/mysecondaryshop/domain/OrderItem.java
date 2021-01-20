package study.mysecondaryshop.domain;

import lombok.Getter;
import lombok.Setter;
import study.mysecondaryshop.domain.item.Item;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;

    private int count;


    //생성 메서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;

    }

    //비즈니스 로직
    //취소
    public void cancel() {
        this.getItem().addStock(count);
    }

    //전체 금액 조회
    public int getTotalPrice(){
        return this.getOrderPrice() * this.getCount();
    }
}


