package org.platanus.webboard.controller.front;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(FrontWebController.class)
public class FrontWebControllerTest {
    @Autowired
    private MockMvc mockMvc;

//    @Test
//    public void frontTest() throws Exception {
//        mockMvc.perform(get("/"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }

}