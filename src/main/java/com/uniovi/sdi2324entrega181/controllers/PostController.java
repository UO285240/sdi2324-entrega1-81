package com.uniovi.sdi2324entrega181.controllers;

import com.uniovi.sdi2324entrega181.entities.Post;
import com.uniovi.sdi2324entrega181.entities.User;
import com.uniovi.sdi2324entrega181.services.*;
import com.uniovi.sdi2324entrega181.validators.CreatePostFormValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class PostController {
    private final PostsService postsService;
    private final UsersService usersService;
    private final RecommendationService recommendationService;
    private final CreatePostFormValidator createPostFormValidator;



    public PostController(PostsService postsService, UsersService usersService, RecommendationService recommendationService, CreatePostFormValidator createPostFormValidator) {
        this.postsService = postsService;
        this.usersService = usersService;
        this.recommendationService = recommendationService;
        this.createPostFormValidator = createPostFormValidator;
    }


    @RequestMapping(value = "/post/add", method = RequestMethod.GET)
    public String addPost(Model model) {
        model.addAttribute("post", new Post());
        return "post/add";
    }

    @RequestMapping(value = "/post/add", method = RequestMethod.POST)
    public String createPost(@Validated Post post, BindingResult result, @RequestParam String title, @RequestParam String text, @RequestParam(name = "image", required = false) MultipartFile image, Principal principal) {


        post.setTitle(title);
        post.setText(text);
        createPostFormValidator.validate(post,result);

        if (result.hasErrors()){
            return "post/add";
        }

        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        LocalDate date = LocalDate.now();


        // comprobar si tiene imagen
        if (image != null && !image.isEmpty()){
            try{
                String fileName = post.getId() + ".png";
                InputStream is = image.getInputStream();
                Files.copy(is, Paths.get("src/main/resources/static/images/posts/" + fileName), StandardCopyOption.REPLACE_EXISTING);
                // añadir imagen al post
                post.setImagePath("images/posts/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/home";
            }
        }
        // si no tiene imagen, poner una por defecto
        else{
            post.setImagePath("images/posts/default-image-SDI.png");
        }


        post.setUser(user);
        post.setDate(date);
        post.setState(Post.PostState.ACEPTADA);
        postsService.addPost(post);

        return "redirect:/post/list";
    }


    @RequestMapping("/post/list")
    public String getList(Model model, Pageable pageable, Principal principal){
        String email = principal.getName(); // email del usuario autenticado
        User user = usersService.getUserByEmail(email);


        Page<Post> posts = postsService.getPostsByUser(pageable, user);
        Map<Post,Long> recommendationsNumber = recommendationService.getNumberOfRecommendations(posts.getContent());
        model.addAttribute("recommendationsNumber",recommendationsNumber);

        model.addAttribute("postsList", posts.getContent());
        model.addAttribute("page", posts);


        return "post/list";
    }


    /**
     * Listado de todas las publicaciones del sistema que permite cambiar su estado
     * @param searchText opcional, texto que permite buscar publicaciones por diferentes campos (email, título...)
     * @return la vista con la lista de todas las publicaciones
     */
    @RequestMapping("/post/adminList")
    public String getAdminList(Model model, Pageable pageable, Principal principal, @RequestParam(value="", required=false) String searchText){
        String email = principal.getName(); // email del usuario autenticado
        User user = usersService.getUserByEmail(email);

        // comprobar que el usuario es administrador
        if(!user.getRole().equals(new RolesService().getRoles()[1])){
            return "redirect:/login";
        }

        Page<Post> posts = postsService.getAllPosts(pageable);
        if (searchText != null && !searchText.isEmpty()){
            posts = postsService.searchByPostFields(searchText, pageable);
        }

        model.addAttribute("postsList", posts.getContent());
        model.addAttribute("page", posts);
        if (searchText != null)
            model.addAttribute("searchText", searchText);
        else
            model.addAttribute("searchText","");

        // valores de estado de una publicación
        List<Post.PostState> postStates = Arrays.asList(Post.PostState.values());
        model.addAttribute("postStates", postStates);


        return "post/adminList";
    }

    /**
     * Método get que devuelve los detalles de una publicación
     * @param model modelo para añadir datos a la vista
     * @param id id del usuario del post para encontrar su último post
     * @return devuelve la vista con los detalles del post
     */

    @RequestMapping("/post/details/{id}")
    public String getDetails(Model model, @PathVariable Long id){
        User user = usersService.getUser(id);
        List<Post> posts = postsService.getLastPostByUser(user);
        model.addAttribute("post", posts.get(0));
        return "/post/details";
    }

    /**
     * Actualiza el estado de la publicación
     * @param id la id del post a actualizar
     * @param newState el nuevo estado para la publicación
     */
    @RequestMapping(value = "/post/updateState/{id}", method = RequestMethod.POST)
    public String updatePostState(HttpServletRequest request, HttpServletResponse response, Principal principal, @PathVariable Long id, @RequestParam("state") Post.PostState newState) {
        String email = principal.getName(); // email del usuario autenticado
        User user = usersService.getUserByEmail(email);

        Post post = postsService.getPost(id);
        post.setState(newState);
        postsService.updatePost(post);
        return "redirect:/post/adminList";
    }
}
