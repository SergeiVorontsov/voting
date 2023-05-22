package com.vorsa.voting.web.dish;

import com.vorsa.voting.model.Dish;
import com.vorsa.voting.repository.DishRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Component
@AllArgsConstructor
public class UniqueNameValidator implements org.springframework.validation.Validator {

    public static final String EXCEPTION_DUPLICATE_NAME = "Dish with this name already exists in this menu";

    private final DishRepository dishRepository;
    private final HttpServletRequest request;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Dish.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Dish dish = ((Dish) target);
        if (StringUtils.hasText(dish.getName())) {
            Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            int menuId = Integer.parseInt((String) pathVariables.get("menuId"));
            dishRepository.findByNameAndMenuIgnoreCase(dish.getName().toLowerCase(), menuId)
                    .ifPresent(dbDish -> {
                        if (request.getMethod().equals("PUT")) {
                            int dbId = dbDish.id();

                            if (dish.getId() != null && dbId == dish.id()) return;

                            String requestURI = request.getRequestURI();
                            if (requestURI.endsWith("/" + dbId))
                                return;
                        }
                        errors.rejectValue("name", "", EXCEPTION_DUPLICATE_NAME);
                    });
        }
    }
}