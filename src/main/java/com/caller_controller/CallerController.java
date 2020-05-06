package com.caller_controller;

import com.gui.components.CallPageController;
import com.models.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
public class CallerController {

    @Autowired
    private CallPageController controller;

    @PostMapping("/call")
    public String serveIncomingCall(@RequestBody UserTO user) {
        controller.informAboutNewCall(user);
        return "User informed!";
    }
}
