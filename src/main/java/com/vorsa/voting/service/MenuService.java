package com.vorsa.voting.service;

import com.vorsa.voting.model.Menu;
import com.vorsa.voting.repository.MenuRepository;
import com.vorsa.voting.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MenuService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public Menu save(int userId, int restaurantId, Menu menu) {
        menu.setRestaurant(restaurantRepository.getExistedOrBelonged(userId, restaurantId));
        return menuRepository.save(menu);
    }
}