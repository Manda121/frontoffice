package com.example.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ApiController {

    @GetMapping("/api-data")
    public String getApiData(@RequestParam(value = "date", required = false) String date, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/reservations";
        List<Map<String, Object>> reservations = restTemplate.getForObject(url, List.class);

        // Filter by date if provided (assuming dateHeureArrive is in format YYYY-MM-DDTHH:MM:SS)
        if (date != null && !date.isEmpty()) {
            reservations = reservations.stream()
                .filter(res -> res.get("dateHeureArrive").toString().startsWith(date))
                .collect(Collectors.toList());
        }

        model.addAttribute("reservations", reservations);
        model.addAttribute("filterDate", date);
        return "apiData";
    }
}