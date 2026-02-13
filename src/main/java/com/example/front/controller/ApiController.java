package com.example.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ApiController {

    @Value("${api.token}")
    private String apiToken;

    @GetMapping("/api-data")
    @SuppressWarnings("unchecked")
    public String getApiData(@RequestParam(value = "date", required = false) String date, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/backoffice-reservation/api/reservations?token=" + apiToken;

        List<Map<String, Object>> reservations = List.of();
        try {
            // The back API returns a wrapper object: { status, message, data }
            Map<String, Object> resp = restTemplate.getForObject(url, Map.class);
            if (resp != null && resp.get("data") instanceof List) {
                reservations = (List<Map<String, Object>>) resp.get("data");
            }
        } catch (org.springframework.web.client.RestClientException ex) {
            model.addAttribute("error", "Impossible de récupérer les données du backoffice: " + ex.getMessage());
        }

        // Filter by date if provided (dateHeureArrivee may be an object {date, heure})
        if (date != null && !date.isEmpty()) {
            reservations = reservations.stream()
                .filter(res -> {
                    Object d = res.get("dateHeureArrivee");
                    if (d == null) return false;
                    if (d instanceof Map) {
                        Object dateField = ((Map<?,?>) d).get("date");
                        return dateField != null && dateField.toString().startsWith(date);
                    } else {
                        return d.toString().startsWith(date);
                    }
                })
                .collect(Collectors.toList());
        }

        model.addAttribute("reservations", reservations);
        model.addAttribute("filterDate", date);
        return "apiData";
    }
}