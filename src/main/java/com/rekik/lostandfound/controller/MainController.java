package com.rekik.lostandfound.controller;

import com.rekik.lostandfound.model.AppRole;
import com.rekik.lostandfound.model.AppUser;
import com.rekik.lostandfound.model.LostItem;
import com.rekik.lostandfound.repository.AppRoleRepo;
import com.rekik.lostandfound.repository.AppUserRepo;
import com.rekik.lostandfound.repository.CategoryRepo;
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

    @Autowired
    CategoryRepo catRepo;

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

    @GetMapping("/useraddlost")
    public String userAddLost(Model model)
    {
        LostItem lost = new LostItem();
        lost.setStatus("lost");
        lostRepo.save(lost);
        model.addAttribute("cats",catRepo.findAll());
        model.addAttribute("newlost",lost);
        return "useraddlost";
    }

    @PostMapping("/useraddlost")
    public String userSaveLost(@Valid @ModelAttribute("newlost") LostItem lost, BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            return "useraddlost";
        }

        lostRepo.save(lost);
        model.addAttribute("lostlist",lostRepo.findAll());
        //return "listlosts";
        return "redirect:/";

    }

    @GetMapping("/addlost")
    public String addLost(Model model)
    {
        LostItem lost = new LostItem();
        lost.setStatus("lost");
        lostRepo.save(lost);
        model.addAttribute("cats",catRepo.findAll());
        model.addAttribute("newlost",lost);
        return "addlost";
    }

    @PostMapping("/addlost")
    public String saveLost(@Valid @ModelAttribute("newlost") LostItem lost, BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            return "addlost";
        }

        lostRepo.save(lost);
        model.addAttribute("lostlist",lostRepo.findAll());
        //return "listlosts";
        return "listlosts";

    }

    @RequestMapping("/listlosts")
    public String listLosts(Model model)
    {
        model.addAttribute("lostlist",lostRepo.findAll());
        return "listlosts";
    }


    @PostMapping("/addusertolost")
    public String showUsersForLost(HttpServletRequest request, Model model)
    {
        String lostid = request.getParameter("lostid");
        model.addAttribute("newlost",lostRepo.findOne(new Long(lostid)));

        //Make users disappear from add form when they are already included (Set already makes it impossible to add multiple)
        model.addAttribute("userList",userRepo.findAll());

        return "adduserstolost";
    }


    @PostMapping("/saveusertojob")
    public String addUsertoLost(HttpServletRequest request, @ModelAttribute("newlost") LostItem lost)
    {
        String userid = request.getParameter("userid");
        System.out.println("User id from add user to lost:"+lost.getId()+" User id:"+userid);
        lost.addUsertoLost(userRepo.findOne(new Long(userid)));
        lostRepo.save(lost);
        return "redirect:/listlosts";
    }




}
