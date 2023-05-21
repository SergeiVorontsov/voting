package com.vorsa.voting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "meal",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "meal_unique_id_menu_id_idx",
                        columnNames = {"menu_id", "name"})
        })
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Meal extends NamedEntity {

    @Column(name = "price")
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    @JsonIgnore
    private Menu menu;

    public Meal(Integer id, String name, Long price, Menu menu) {
        super(id, name);
        this.price = price;
        this.menu = menu;
    }
}
