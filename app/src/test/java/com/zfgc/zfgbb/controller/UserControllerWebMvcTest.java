package com.zfgc.zfgbb.controller;

import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.services.core.AccountDeletionService;
import com.zfgc.zfgbb.services.core.AuthCookieService;
import com.zfgc.zfgbb.services.core.AuthService;
import com.zfgc.zfgbb.services.core.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private AuthCookieService cookieService;

    @MockitoBean
    private AccountDeletionService accountDeletionService;

    @org.springframework.boot.autoconfigure.SpringBootApplication
    static class DummyApp {
    }
    @Test
    void testGetLoggedInUserRequiresAuthOrAllowsAnonymous() throws Exception {
        mockMvc.perform(get("/users/loggedInUser"))
                .andExpect(status().isOk());
    }
}
