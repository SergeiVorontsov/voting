package com.vorsa.voting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

import static com.vorsa.voting.util.DateUtil.DATE_PATTERN;

@Entity
@Table(name = "menu",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "menu_unique_menu_date_restaurant_id_idx",
                        columnNames = {"menu_date", "restaurant_id"}),
        })
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Menu extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    @JsonIgnore
    private Restaurant restaurant;

    @Column(name = "menu_date", nullable = false, columnDefinition = "date default current_date()", updatable = false)
    @NotNull
    @DateTimeFormat(pattern = DATE_PATTERN)
    private LocalDate date = LocalDate.now();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menu")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Meal> meals;

    public Menu(Integer id, Restaurant restaurant, LocalDate date) {
        super(id);
        this.restaurant = restaurant;
        this.date = date;
    }

    public Menu(Menu menu) {
        super(menu.id);
        this.restaurant = menu.restaurant;
        this.date = menu.date;
        this.meals = menu.meals;
    }
}