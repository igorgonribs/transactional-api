package com.project.transactional.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.transactional.api.account.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountService service;
    @Test
    void case1() throws Exception {


        mockMvc.perform(post("/bookings")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bookingInfo)))
                .andExpect(status().isOk());


        LocalDate checkIn = string2LocalDate("2020-01-02");
        LocalDate checkOut = string2LocalDate("2020-01-03");

        BookingInfo bookingInfo = new BookingInfo(checkIn, checkOut);

        mockMvc.perform(post("/bookings")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bookingInfo)))
                .andExpect(status().isOk());

        BookingReturn bookReturn = service.getWeeksAndExtraNigths(bookingInfo);

        // metodos JUnit versão anterior
        assertThat(bookReturn.getWeeks()).isEqualTo(0);
        assertThat(bookReturn.getDaysAfter()).isEqualTo(0);
        assertThat(bookReturn.getDaysBefore()).isEqualTo(1);

        // JUnit5
        Assertions.assertEquals(bookReturn.getWeeks(), 0);
        Assertions.assertEquals(bookReturn.getDaysAfter(), 0);
        Assertions.assertEquals(bookReturn.getDaysBefore(), 1);
    }
}
