package com.vorsa.voting.repository;

import com.vorsa.voting.error.AccessDeniedException;
import com.vorsa.voting.model.Restaurant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CustomRestaurantRepository {

    private final RestaurantRepository repository;
    private final UserRepository userRepository;

    public CustomRestaurantRepository(RestaurantRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Restaurant save(Restaurant rest, int userId) {
        if (!rest.isNew() && repository.getExisted(rest.id()).getUser().id() != userId) {
            throw new AccessDeniedException("Restaurant with id=" + rest.id() + " not belong to you");
        }
        rest.setUser(userRepository.getReferenceById(userId));
        return repository.save(rest);
    }

    @Transactional
    public void delete(int id, int userId) {
        if (repository.getExisted(id).getUser().id() != userId)
            throw new AccessDeniedException("Restaurant with id=" + id + " not belong to you");
        repository.deleteExisted(id);
    }

    public List<Restaurant> getAll(int userId) {
        return repository.findAllByUserId(userId);
    }


}
