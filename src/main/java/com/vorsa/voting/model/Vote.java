package com.vorsa.voting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "vote",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "vote_unique_user_dateTime_idx",
                        columnNames = {"user_id", "voting_date"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)

public class Vote extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @NotNull
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "VOTING_DATE", nullable = false, columnDefinition = "date default current_date()", updatable = false)
    @NotNull
    private LocalDate date = LocalDate.now();
}
