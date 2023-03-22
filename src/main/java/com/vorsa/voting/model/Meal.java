package com.vorsa.voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "meal",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "meal_unique_restaurant_date_name_idx",
                        columnNames = {"restaurant_id", "PUBLICATION_DATE", "name"})})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Meal extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Long price;

    @Column(name = "publication_date", nullable = false, columnDefinition = "date default current_date()", updatable = false)
    @NotNull
    private LocalDate publicationDate = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @ToString.Exclude
    @JsonBackReference
    private Restaurant restaurant;
}
