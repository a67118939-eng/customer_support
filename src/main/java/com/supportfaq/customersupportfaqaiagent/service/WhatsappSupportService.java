package com.supportfaq.customersupportfaqaiagent.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class WhatsappSupportService {

    @Value("${app.support.whatsapp-number:966554370544}")
    private String whatsappNumber;

    @Value("${app.support.whatsapp-message:Hello, I need help from customer support.}")
    private String whatsappMessage;

    public String buildSupportUrl() {
        String encodedMessage = URLEncoder.encode(whatsappMessage, StandardCharsets.UTF_8).replace("+", "%20");
        return "https://wa.me/" + whatsappNumber + "?text=" + encodedMessage;
    }
}
