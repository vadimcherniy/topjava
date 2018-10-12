package ru.javawebinar.topjava.web;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.javawebinar.topjava.service.UserService;

import javax.annotation.PostConstruct;

@ContextConfiguration({"classpath:spring/spring-app.xml","classpath:spring/spring-mvc.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
abstract public class WebTest {

    protected MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    void postConstruct() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
}
