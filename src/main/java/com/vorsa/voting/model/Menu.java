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
                        name = "menu_unique_menu_date_idx",
                        columnNames = {"menu_date"}),
        })
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Menu extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @ToString.Exclude
    @JsonIgnore
    private Restaurant restaurant;

    @Column(name = "menu_date", nullable = false, columnDefinition = "date default current_date()", updatable = false)
    @NotNull
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate date = LocalDate.now();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menu")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<Meal> meals;
}