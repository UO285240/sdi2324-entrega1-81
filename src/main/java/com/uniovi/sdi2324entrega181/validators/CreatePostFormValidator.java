package com.uniovi.sdi2324entrega181.validators;


import com.uniovi.sdi2324entrega181.entities.Post;
import com.uniovi.sdi2324entrega181.services.PostsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CreatePostFormValidator implements Validator {

    private final PostsService postsService;

    public CreatePostFormValidator(PostsService postsService) {
        this.postsService = postsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Post.class.equals(aClass);
    }


    @Override
    public void validate(Object target, Errors errors) {
        Post post = (Post) target;

        //Comprobar vacíos
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "Error.empty");


        if (post.getTitle().length() < 5 || post.getTitle().length() > 24) {
            errors.rejectValue("title", "Error.addPost.title.length");
        }

        if (post.getText().length() < 5 || post.getText().length() > 255) {
            errors.rejectValue("text", "Error.addPost.text.length");
        }

    }


}
