package com.rekik.lostandfound.controller;

import com.rekik.lostandfound.model.AppRole;
import com.rekik.lostandfound.model.AppUser;
import com.rekik.lostandfound.model.Category;
import com.rekik.lostandfound.model.LostItem;
import com.rekik.lostandfound.repository.AppRoleRepo;
import com.rekik.lostandfound.repository.AppUserRepo;
import com.rekik.lostandfound.repository.CategoryRepo;
import com.rekik.lostandfound.repository.LostItemRepo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("foundlost",lostRepo.findByStatus(false));
        return "landing";

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
        model.addAttribute("cats",catRepo.findAll());
        model.addAttribute("newlost",new LostItem());
        return "useraddlost";
    }

    @PostMapping("/useraddlost")
    public String userSaveLost(@Valid @ModelAttribute("newlost") LostItem lost, BindingResult result,
                               Model model, Authentication auth)
    {
        if(result.hasErrors())
        {
            return "useraddlost";
        }

        AppUser thisUser = userRepo.findAppUserByUsername(auth.getName());
        lost.addUsertoLost(thisUser);
        lostRepo.save(lost);
        model.addAttribute("lostlistperuser",lostRepo.findLostItemByLusers(thisUser));
        //return "listlosts";
        return "lostlistperuser";

    }

    @GetMapping("/addlost")
    public String addLost(Model model)
    {
        LostItem lost = new LostItem();
        //lost.setStatus("lost");
      //  lostRepo.save(lost);
        model.addAttribute("cats",catRepo.findAll());
        model.addAttribute("newlost",lost);
        model.addAttribute("userList",userRepo.findAll());
        return "addlost";

    }




            @PostMapping("/addlost")
    public String saveLost(@Valid @ModelAttribute("newlost") LostItem lost,
                HttpServletRequest request,Authentication auth, BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            return "addlost";
        }

       String anonymoususer = request.getParameter("anonymoususer");
        //* if(anonymoususer.equals("anony")){*//*
        if(anonymoususer!=null){

            lostRepo.save(lost);
            return "redirect:/";
        }

        String userid = request.getParameter("userid");
        AppUser userpostedonbehalf = userRepo.findOne(new Long(userid));
        lost.addUsertoLost(userpostedonbehalf);
        //lost.setStatus("lost");
        lostRepo.save(lost);
        model.addAttribute("lostlist",lostRepo.findAll());
       /* model.addAttribute("losts",lostRepo.findAll());
        model.addAttribute("foundlost",lostRepo.findByStatus(false));*/
        return "redirect:/";

    }

    @GetMapping("/lostitem/{id}")
    public String changeLostStatus(Model model, @PathVariable("id") String lostId) {

        LostItem lost = lostRepo.findOne(new Long(lostId));
        //hmmm
        lost.setStatus(!lost.isStatus());
        lostRepo.save(lost);
        model.addAttribute("lostlist",lostRepo.findAll());
        model.addAttribute("foundlost",lostRepo.findByStatus(false));
        return "redirect:/";
    }



    @GetMapping("/edititem/{id}")
    public String editLostItem(@PathVariable("id") long id, Model model){
        model.addAttribute("newlost", lostRepo.findOne(id));
        model.addAttribute("cats",catRepo.findAll());
        model.addAttribute("userList",userRepo.findAll());
        return "addlost";
    }

    @GetMapping("/deleteitem/{id}")
    public String deleteLostItem(@PathVariable("id") long id){
        lostRepo.delete(id);
        return "redirect:/";
    }


    @GetMapping("/displayuserlist")
    public String userlistdisplay(Model model, Authentication auth)
    {
        model.addAttribute("lostlistperuser",lostRepo.findLostItemByLusers(userRepo.findAppUserByUsername(auth.getName())));
        return "lostlistperuser";

    }


    @GetMapping("/searchbycategory")
    public String getSearch()
    {
        return "searchbycategory";
    }

    @PostMapping("/searchbycategory")
    public String showSearchResults(HttpServletRequest request, Model model)
    {
        //Get the search string from the result form
        String searchString = request.getParameter("searchval");
        model.addAttribute("searchval",searchString);
        model.addAttribute("lostitems",lostRepo.findAllByCategoryCatNameContainingIgnoreCase(searchString));
        return "list";
    }



   /* @GetMapping("/showlostclothes")
    public String showLostClothes(Model model)
    {
        model.addAttribute("lostlist", lostRepo.findAllByCategoryCatNameContainingIgnoreCase(Category c))
        model.addAttribute("lostList",lostAndFound.findByCategory(lostAndFound.findCategory("Clothes")));
        return "listitems";
    }

    @GetMapping("/showlostothers")
    public String showLostOthers(Model model)
    {
        model.addAttribute("lostList",lostAndFound.findByCategory(lostAndFound.findCategory("Others")));
        return "listitems";
    }

    @GetMapping("/showlostpets")
    public String showLostPets(Model model)
    {
        model.addAttribute("lostList",lostAndFound.findByCategory(lostAndFound.findCategory("Pets")));
        return "listitems";
    }*/







}
