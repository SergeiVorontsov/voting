package com.vorsa.voting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Restaurant extends NamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OrderBy("date DESC")
    @ToString.Exclude
    @JsonIgnore
    private List<Menu> menus;

    public Restaurant(Restaurant r) {
        this(r.id, r.name, r.user, r.menus);
    }

    public Restaurant(Integer id, String name, User user, List<Menu> menus) {
        super(id, name);
        this.user = user;
        this.menus = menus;
    }

    public Restaurant(Integer id, String name, User user, Menu... menus) {
        this(id, name, user, List.of(menus));
    }

    public void setMenus(Menu... menus) {
        this.menus = Arrays.stream(menus).toList();
    }
}
