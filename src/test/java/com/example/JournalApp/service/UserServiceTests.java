package com.example.JournalApp.service;


import com.example.JournalApp.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

//    @BeforeEach // har ek test chalne se pehle ye chalega
//    @BeforeAll // sabse se phle
//    @AfterAll // sab ke baad
//    @AfterEach // har ek ke baad
//    void setup(){
//
//    }

    // @Disabled // nahi chalana hai to
    @ParameterizedTest // not param pe only @Test
    @ValueSource( strings = {
            "Ram",
            "shikshak",
            "abc"
    })
    public void testFindByUserName(String name){
        assertNotNull(userRepository.findByUserName(name),"test failed for user: "+name);
    }

    // to pass args
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,6"
    })
    @ParameterizedTest
    public void test(int a,int b, int expected){
        assertEquals(expected,a+b);
    }


}
