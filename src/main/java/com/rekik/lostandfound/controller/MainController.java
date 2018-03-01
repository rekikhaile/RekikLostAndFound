package com.rekik.lostandfound.controller;

import com.rekik.lostandfound.model.AppRole;
import com.rekik.lostandfound.model.AppUser;
import com.rekik.lostandfound.model.LostItem;
import com.rekik.lostandfound.repository.AppRoleRepo;
import com.rekik.lostandfound.repository.AppUserRepo;
import com.rekik.lostandfound.repository.LostItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class MainController {
    @Autowired
    AppRoleRepo roleRepo;

    @Autowired
    AppUserRepo userRepo;

    @Autowired
    LostItemRepo lostRepo;

    @GetMapping("/")
    public String showIndex(Model model){
        model.addAttribute("losts",lostRepo.findAll());
        return "index";

    }

    @RequestMapping("/login")
    public String showLogin(Model model){
        return "login";
    }

    @GetMapping("/register")
    public String registerUser(Model model)
    {
        model.addAttribute("newuser",new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute("newuser")AppUser user,
                           BindingResult result, HttpServletRequest request, Model model)
    {
        if(result.hasErrors()){
            System.out.println(result.toString());
            return "register";
        }

        /*if(request.getParameter("isAdmin")!=null) // where does that come from
            user.addRole(roleRepo.findAppRoleByRoleName("ADMIN"));
        else
            user.addRole(roleRepo.findAppRoleByRoleName("USER"));
        userRepo.save(user);
        return "redirect:/login";*/


        else{
            //Create a new ordinary user
            model.addAttribute(user.getUsername()+" created");
            AppRole r = roleRepo.findAppRoleByRoleName("USER");
            userRepo.save(user);
            user.addRole(r);
            userRepo.save(user);
            return "redirect:/login";
        }
    }

    @GetMapping("/addlost")
    public String addPledge(Model model){
        model.addAttribute("aLost", new LostItem());
        return "lostform";
    }

    @PostMapping("/addlost")
    public String saveAddpledge(@Valid @ModelAttribute("aLost") LostItem lost, BindingResult result, Authentication auth)
    {
        if(result.hasErrors())
        {
            return "lostform";
        }
        lostRepo.save(lost);
        AppUser currentUser =  userRepo.findAppUserByUsername(auth.getName());
        return "redirect:/";
    }

}
