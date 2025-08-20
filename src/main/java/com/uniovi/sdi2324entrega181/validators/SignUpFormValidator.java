package com.uniovi.sdi2324entrega181.validators;


import com.uniovi.sdi2324entrega181.entities.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;
import com.uniovi.sdi2324entrega181.services.UsersService;

@Component
public class SignUpFormValidator implements Validator {

    private final UsersService usersService;

    public SignUpFormValidator(UsersService usersService) {
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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "Error.empty");

        //Quitar espacios al principio y al final
        if (user.getEmail().startsWith(" ")||
                user.getEmail().endsWith(" ")) {
            user.setEmail(user.getEmail().trim());
        }
        if (user.getName().startsWith(" ")||
                user.getName().endsWith(" ")) {
            user.setName(user.getName().trim());
        }
        if (user.getLastName().startsWith(" ")||
                user.getLastName().endsWith(" ")) {
            user.setLastName(user.getLastName().trim());
        }
        if (user.getPassword().startsWith(" ")||
                user.getPassword().endsWith(" ")) {
            user.setPassword(user.getPassword().trim());
        }
        if (user.getPasswordConfirm().startsWith(" ")||
                user.getPasswordConfirm().endsWith(" ")) {
            user.setPasswordConfirm(user.getPasswordConfirm().trim());
        }


        if(usersService.getUserByEmail(user.getEmail())!=null){
            errors.rejectValue("email", "Error.signup.email.duplicate");
        }


        if(!user.getEmail().matches(".*@.*\\..*")){
            errors.rejectValue("email", "Error.signup.email.notCorrectFormat");
        }


        if(!esContraseñaFuerte(user.getPassword())){
            errors.rejectValue("password", "Error.signup.password.notHard");
        }


        if (user.getPassword().length() < 12 ) {
            errors.rejectValue("password", "Error.signup.password.length");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Error.signup.passwordConfirm.coincidence");
        }






    }
    /**
     * Método para comprobar que un String cumple con las caracterísiticas de ser una contraseña fuerte
     * @param contraseña String que se va a comprobar si es fuerte
     * @return devuelve true si la contraseña es fuerte, false en caso contrario
     */
    private static boolean esContraseñaFuerte(String contraseña) {
        // Al menos una letra mayúscula, una minúscula, un número y un carácter especial
        return contieneLetraMayuscula(contraseña) &&
                contieneLetraMinuscula(contraseña) &&
                contieneNumero(contraseña) &&
                contieneCaracterEspecial(contraseña);
    }
    /**
     * Método para comprobar que un String tiene letras mayúsculas
     * @param str el string a comprobar
     * @return devuelve true si tiene letras mayúsculas, false en caso contrario
     */
    private static boolean contieneLetraMayuscula(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Método para comprobar que un String tiene letras minúsculas
     * @param str el string a comprobar
     * @return devuelve true si tiene letras minúsculas, false en caso contrario
     */
    private static boolean contieneLetraMinuscula(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Método para comprobar que un String tiene números
     * @param str el string a comprobar
     * @return devuelve true si tiene números, false en caso contrario
     */
    private static boolean contieneNumero(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método para comprobar que un String tiene carácteres especiales
     * @param str el string a comprobar
     * @return devuelve true si tiene carcateres especiales, false en caso contrario
     */
    private static boolean contieneCaracterEspecial(String str) {
        String caracteresEspeciales = "@$!%*?&";
        for (char c : str.toCharArray()) {
            if (caracteresEspeciales.indexOf(c) != -1) {
                return true;
            }
        }
        return false;
    }
}
