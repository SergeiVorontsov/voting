package com.vorsa.voting.web.meal;

import com.vorsa.voting.model.Meal;
import com.vorsa.voting.repository.MealRepository;
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

    public static final String EXCEPTION_DUPLICATE_NAME = "Meal with this name already exists in this menu";

    private final MealRepository mealRepository;
    private final HttpServletRequest request;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Meal.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Meal meal = ((Meal) target);
        if (StringUtils.hasText(meal.getName())) {
            Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            int menuId = Integer.parseInt((String) pathVariables.get("menuId"));
            mealRepository.findByNameAndMenuIgnoreCase(meal.getName().toLowerCase(), menuId)
                    .ifPresent(dbMeal -> {
                        if (request.getMethod().equals("PUT")) {
                            int dbId = dbMeal.id();

                            if (meal.getId() != null && dbId == meal.id()) return;

                            String requestURI = request.getRequestURI();
                            if (requestURI.endsWith("/" + dbId))
                                return;
                        }
                        errors.rejectValue("name", "", EXCEPTION_DUPLICATE_NAME);
                    });
        }
    }
}