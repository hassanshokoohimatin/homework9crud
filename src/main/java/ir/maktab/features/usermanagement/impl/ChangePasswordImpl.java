package ir.maktab.features.usermanagement.impl;

import ir.maktab.entities.User;
import ir.maktab.features.usermanagement.usecase.ChangePassword;
import ir.maktab.repositories.UserRepository;

import java.util.Scanner;

public class ChangePasswordImpl implements ChangePassword {

    static UserRepository userRepository = UserRepository.getInstance();
    @Override
    public void changePass(User user) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your old password : ");
        String oldPassword = scanner.next();
        if (user.getPassword().equals(oldPassword)) {
            System.out.println("Enter new password");
            String newPassword = scanner.next();
            System.out.println("Reenter the password");
            String reenteredPassword = scanner.next();
            if (newPassword.equals(reenteredPassword)) {
                user.setPassword(newPassword);
                userRepository.update(user);
                System.out.println("password changed successfully...");
            }else{
                System.out.println("Entered passwords are not the same...try again");
            }

        } else {
            System.out.println("Wrong password...you can not change it");
        }
    }
}
