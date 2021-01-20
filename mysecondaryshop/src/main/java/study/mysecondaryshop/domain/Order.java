package study.mysecondaryshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "Orders")
@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    //연관 관계 메서드
    public void setMember(Member member){
        member.getOrders().add(this);
        this.member = member;
    }
    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
    public void addOrderItem(OrderItem orderItem){
        this.getOrderItems().add(orderItem);
        orderItem.setOrder(this);
    }

    //생성 메서드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //비즈니스 로직
    // 주문 취소
    public void cancal(){
        if(delivery.getStatus() == DeliveryStatus.COMPLETE){
            throw new IllegalStateException("이미 배송완료된 상품입니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderitem : orderItems) {
            orderitem.cancel();
        }
    }

    //주문 가격 조회
    public int getTotalPrice(){
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getOrderPrice();
        }
        return totalPrice;
    }
}
