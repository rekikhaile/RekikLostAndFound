package com.rekik.lostandfound.setup;

import com.rekik.lostandfound.model.AppRole;
import com.rekik.lostandfound.model.AppUser;
import com.rekik.lostandfound.model.LostItem;
import com.rekik.lostandfound.repository.AppRoleRepo;
import com.rekik.lostandfound.repository.AppUserRepo;
import com.rekik.lostandfound.repository.LostItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner{
    @Autowired
    AppRoleRepo roleRepo;

    @Autowired
    AppUserRepo userRepo;

    @Autowired
    LostItemRepo lostRepo;

    @Override
    public void run(String... strings) throws Exception {
        //Add all data that should be in the database at the beginning of the application

        AppRole role = new AppRole();
        role.setRoleName("ADMIN");
        roleRepo.save(role);

        role = new AppRole();
        role.setRoleName("USER");
        roleRepo.save(role);

//Add test data for users

        AppUser user = new AppUser();
        user.setUsername("rekik");
        user.setPassword("password");
        user.setFirstName("Rekik");
        user.setLastName("Haile");
        user.setImage("http://www.nurseryrhymes.org/nursery-rhymes-styles/images/john-jacob-jingleheimer-schmidt.jpg");
        user.addRole(roleRepo.findAppRoleByRoleName("USER"));
        userRepo.save(user);


        user = new AppUser();
        user.setUsername("selam");
        user.setPassword("samuel");
        user.setFirstName("Selam");
        user.setLastName("Samuel");
        user.setImage("http://www.nurseryrhymes.org/nursery-rhymes-styles/images/john-jacob-jingleheimer-schmidt.jpg");
        user.addRole(roleRepo.findAppRoleByRoleName("USER"));
        userRepo.save(user);


        user = new AppUser();
        user.setUsername("admin");
        user.setPassword("password");
        user.setFirstName("PotAdmin");
        user.setLastName("CoolGal");
        user.addRole(roleRepo.findAppRoleByRoleName("ADMIN"));
        userRepo.save(user);


        LostItem lost = new LostItem();
        lost.setTitle("Lost Pet");
        lost.setName("Kitty");
        lost.setDesc("black and white");
        lost.setImage("http://www.nurseryrhymes.org/nursery-rhymes-styles/images/john-jacob-jingleheimer-schmidt.jpg");
        lost.setCategory("PET");
        lost.setStatus("lost");
        //lost.addUsertoLost(userRepo.findAppUserByUsername("rekik"));
        //lostRepo.save(lost);



    }

}
