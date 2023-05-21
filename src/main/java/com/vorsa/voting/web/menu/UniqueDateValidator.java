package com.vorsa.voting.web.menu;

import com.vorsa.voting.model.Menu;
import com.vorsa.voting.repository.MenuRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Component
@AllArgsConstructor
public class UniqueDateValidator implements org.springframework.validation.Validator {

    public static final String EXCEPTION_DUPLICATE_DATE = "Menu for this date already exists";

    private final MenuRepository menuRepository;
    private final HttpServletRequest request;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Menu.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Menu menu = ((Menu) target);
        if (menu.getDate() != null) {
            // https://stackoverflow.com/questions/36739808/how-can-i-get-pathvariable-inside-filterregistrationbean-spring-boot
            Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            int restaurantId = Integer.parseInt((String) pathVariables.get("restaurantId"));
            menuRepository.getByDate(restaurantId, menu.getDate()).ifPresent(dbMenu -> errors.rejectValue("date", "", EXCEPTION_DUPLICATE_DATE));
        }
    }
}