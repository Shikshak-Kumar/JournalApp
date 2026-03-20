package com.example.JournalApp.service;

import com.example.JournalApp.entity.User;
import com.example.JournalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@Disabled
//@SpringBootTest  // without this means we are not playing with spring context
public class UserDetailsServiceImpTests {

    @InjectMocks // use @Autowired is @SpringBootTest is used
    // without @SpringBootTest @InjectMocks initialize the instance of this :-
    private UserDetailsServiceImp userDetailsService;

    @Mock // Use @MockBean if @SpringBootTest
    private UserRepository userRepository;

    @BeforeEach // without this our above Mock will not be initialized
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadByUserNameTest(){
        User mockUser = new User("ram","dfdsdsdgs");
        mockUser.setRoles(new ArrayList<>(List.of("USER","ADMIN")));
        when(userRepository.findByUserName(ArgumentMatchers.anyString()))
                .thenReturn(mockUser);

        UserDetails user  = userDetailsService.loadUserByUsername("ram");

        Assertions.assertNotNull(user);

    }


}
