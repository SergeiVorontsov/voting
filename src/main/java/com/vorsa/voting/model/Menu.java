package com.vorsa.voting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

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

    //add to "columnDefinition" "...check (menu_date > current_date()" to constrain menu creation retroactively
    @Column(name = "menu_date", nullable = false, columnDefinition = "date default current_date()", updatable = false)
    @NotNull
    //@Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate date = LocalDate.now();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menu")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<Meal> meals;
}