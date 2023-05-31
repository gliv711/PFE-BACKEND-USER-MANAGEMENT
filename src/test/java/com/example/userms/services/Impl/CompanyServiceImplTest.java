package com.example.userms.services.Impl;


import com.example.userms.entity.Company;
import com.example.userms.repository.companyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@SpringBootTest
class CompanyServiceImplTest {
    @Mock
    private companyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCompanyByEmail() {
        String email = "example@example.com";
        Company company = new Company();
        company.setEmail(email);

        when(companyRepository.findByEmail(email)).thenReturn(company);

        Optional<Company> result = companyService.getCompanyByEmail(email);

        assertEquals(Optional.of(company), result);
        verify(companyRepository, times(1)).findByEmail(email);
    }
    @Test
    public void testGetAllC() {
        Company company1 = new Company();
        Company company2 = new Company();
        List<Company> companies = Arrays.asList(company1, company2);

        when(companyRepository.findAll()).thenReturn(companies);

        List<Company> result = companyService.getAllC();

        assertEquals(companies, result);
        verify(companyRepository, times(1)).findAll();
    }
    @Test
    public void testGetCompanyById() {
        Long id = 1L;
        Company company = new Company();
        company.setId(id);

        when(companyRepository.findById(id)).thenReturn(Optional.of(company));

        Optional<Company> result = companyService.getCompanyById(id);

        assertEquals(Optional.of(company), result);
        verify(companyRepository, times(1)).findById(id);
    }

}