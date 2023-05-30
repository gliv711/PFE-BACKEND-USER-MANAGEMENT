package com.example.userms.services.Impl;

import com.example.userms.entity.Client;
import com.example.userms.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceImplTest {

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    /*public void shouldSaveUserWithSuccess() {
        // Create a new Client object
        Client expectedClient = new Client();
        expectedClient.setName("Cat test");
        expectedClient.setPassword("Hello");
        expectedClient.setId(1L);
        expectedClient.setLastName("Last Name");
        expectedClient.setDomain("example.com");
        expectedClient.setRegion("Region");
        expectedClient.setBirthDate(new Date());
        expectedClient.setStartofStudy(new Date());
        expectedClient.setEndofStudy(new Date());
        expectedClient.setStartofWork(new Date());
        expectedClient.setEndofWork(new Date());
        expectedClient.setUniversity("University");

        when(userService.SaveUser(expectedClient)).thenReturn(expectedClient);

        // Save the client
        Client savedClient = userService.SaveUser(expectedClient);

        // Assert that the savedClient is not null and has a valid ID
        Assertions.assertNotNull(savedClient);
        Assertions.assertNotNull(savedClient.getId());

        // Assert that the savedClient's properties match the expectedClient's properties
        assertEquals(expectedClient.getName(), savedClient.getName());
        assertEquals(expectedClient.getLastName(), savedClient.getLastName());
        assertEquals(expectedClient.getDomain(), savedClient.getDomain());
        assertEquals(expectedClient.getRegion(), savedClient.getRegion());
        assertEquals(expectedClient.getBirthDate(), savedClient.getBirthDate());
        assertEquals(expectedClient.getStartofStudy(), savedClient.getStartofStudy());
        assertEquals(expectedClient.getEndofStudy(), savedClient.getEndofStudy());
        assertEquals(expectedClient.getStartofWork(), savedClient.getStartofWork());
        assertEquals(expectedClient.getEndofWork(), savedClient.getEndofWork());
        assertEquals(expectedClient.getUniversity(), savedClient.getUniversity());
    }

    @Test
    public void shouldUpdateUserWithSuccess() {
        // Create a new Client object
        Client expectedClient = new Client();
        expectedClient.setName("Cat test");
        expectedClient.setPassword("Hello");
        expectedClient.setId(1L);
        expectedClient.setLastName("Last Name");
        expectedClient.setDomain("example.com");
        expectedClient.setRegion("Region");
        expectedClient.setBirthDate(new Date());
        expectedClient.setStartofStudy(new Date());
        expectedClient.setEndofStudy(new Date());
        expectedClient.setStartofWork(new Date());
        expectedClient.setEndofWork(new Date());
        expectedClient.setUniversity("University");

        UserService userService = Mockito.mock(UserService.class);

        when(userService.SaveUser(any(Client.class))).thenReturn(expectedClient);

        Client savedClient = userService.SaveUser(expectedClient);

        Client ClientToUpdate = savedClient;
        ClientToUpdate.setName("Cat update");

        savedClient = userService.SaveUser(ClientToUpdate);

        Assertions.assertNotNull(savedClient);
        Assertions.assertNotNull(savedClient.getId());

        assertEquals(ClientToUpdate.getName(), savedClient.getName());
        assertEquals(ClientToUpdate.getLastName(), savedClient.getLastName());
        assertEquals(ClientToUpdate.getDomain(), savedClient.getDomain());
        assertEquals(ClientToUpdate.getRegion(), savedClient.getRegion());
        assertEquals(ClientToUpdate.getBirthDate(), savedClient.getBirthDate());
        assertEquals(ClientToUpdate.getStartofStudy(), savedClient.getStartofStudy());
        assertEquals(ClientToUpdate.getEndofStudy(), savedClient.getEndofStudy());
        assertEquals(ClientToUpdate.getStartofWork(), savedClient.getStartofWork());
        assertEquals(ClientToUpdate.getEndofWork(), savedClient.getEndofWork());
        assertEquals(ClientToUpdate.getUniversity(), savedClient.getUniversity());
    }*/

}

