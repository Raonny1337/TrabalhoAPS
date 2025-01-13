package controller;

import model.user.Reader;
import service.ReaderService;

public class ReaderController {
    public static void registerReader(){
        ReaderService.registerReader();
    }

    public static Reader loginReader(String username, String password){
        return ReaderService.loginReader(username, password);
    }
}
