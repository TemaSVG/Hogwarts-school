package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.hogwarts.school.service.InfoService;

@RestController
@RequestMapping("/info")
public class InfoController {

    private InfoService infoService;


    @GetMapping("/port")
    public String getPort() {
        return infoService.getPort();
    }

    @GetMapping("/slowSum")
    public int getSlowSum() {
        return infoService.getSlowSum();
    }

    @GetMapping("/fastSum")
    public int getFastSum() {
        return infoService.getFastSum();
    }
}
