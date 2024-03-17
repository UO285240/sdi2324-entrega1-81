package com.uniovi.sdi2324entrega181.validators;

import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.services.UsersService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EditUserValidator implements Validator{
    private final UsersService usersService;

    public EditUserValidator(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        //Comprobar vacíos
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "Error.empty");

        //Quitar espacios al principio y al final
        if (user.getEmail().startsWith(" ") ||
                user.getEmail().endsWith(" ")) {
            user.setEmail(user.getEmail().trim());
        }
        if (user.getName().startsWith(" ") ||
                user.getName().endsWith(" ")) {
            user.setName(user.getName().trim());
        }
        if (user.getLastName().startsWith(" ") ||
                user.getLastName().endsWith(" ")) {
            user.setLastName(user.getLastName().trim());
        }
        User existingUser = usersService.getUserByEmail(user.getEmail());
        if (existingUser != null && existingUser.getId() != user.getId()) {
            errors.rejectValue("email", "Error.signup.email.duplicate");
        }
        if(!user.getEmail().matches(".*@.*\\..*")){
            errors.rejectValue("email", "Error.signup.email.notCorrectFormat");
        }

    }
}