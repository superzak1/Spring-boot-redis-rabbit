package ru.rabbit.test.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.rabbit.test.service.RabbitService;
import ru.rabbit.test.service.RedisService;

/**
 * Created by rakhmetov on 23/08/16.
 */
@RestController
public class Controller {

    private RabbitService rabbitService;
    private RedisService redisService;

    @Autowired
    public Controller(RabbitService rabbitService, RedisService redisService) {
        this.rabbitService = rabbitService;
        this.redisService = redisService;
    }

    @RequestMapping(
            value = "/publish/{message}",
            method = RequestMethod.GET
    )
    public String publishMessage(@PathVariable("message") String message) {
        rabbitService.publishMessage(message);
        return "success";
    }

    @RequestMapping(
            value = "/integer/{integer}",
            method = RequestMethod.POST
    )
    public String saveInteger(@PathVariable("integer") int value) {
        return redisService.saveInteger(value);
    }

    @RequestMapping(
            value = "/integer/{key}",
            method = RequestMethod.GET
    )
    public Integer saveInteger(@PathVariable("key") String key) {
        return redisService.getInteger(key);
    }

}
