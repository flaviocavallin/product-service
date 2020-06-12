package com.example.productservice.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity<Long> {
    private static final long serialVersionUID = -8990226958452772812L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "issue_date", nullable = false, columnDefinition = "DATE")
    private LocalDate issueDate;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Buyer buyer;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Item> items = new LinkedList<>();

    @Version
    @Column(nullable = false)
    private Long version;

    Order() {
        // do nothing for hydration purpose
    }

    public Order(Buyer buyer) {
        this.buyer = Objects.requireNonNull(buyer, "buyer can not be null");
        this.issueDate = LocalDate.now();
    }

    public void addItem(Item item) {
        Objects.requireNonNull(item, "item can not be null");
        this.items.add(item);
        item.setOrder(this);
    }

    public double getOrderValue() {
        double sum = this.items.stream().map(item -> item.getPrice()).reduce(0D, Double::sum);
        return round2Decimals(sum);
    }

    private static double round2Decimals(double value) {
        BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public Long getId() {
        return id;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public List<Item> getItems() {
        return items;
    }
}
