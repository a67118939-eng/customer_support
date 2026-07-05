package com.supportfaq.customersupportfaqaiagent.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class SupportTicketRequest {

    @Size(max = 120, message = "Customer name must be at most 120 characters")
    private String customerName;

    @NotBlank(message = "Customer email is required")
    @Email(message = "Customer email must be valid")
    @Size(max = 254, message = "Customer email must be at most 254 characters")
    private String customerEmail;

    @Size(max = 180, message = "Subject must be at most 180 characters")
    private String subject;

    @NotBlank(message = "Message is required")
    @Size(max = 3000, message = "Message must be at most 3000 characters")
    private String message;

    @Size(max = 40, message = "Priority must be at most 40 characters")
    private String priority;

    @Size(max = 1000, message = "Source question must be at most 1000 characters")
    private String sourceQuestion;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getSourceQuestion() {
        return sourceQuestion;
    }

    public void setSourceQuestion(String sourceQuestion) {
        this.sourceQuestion = sourceQuestion;
    }
}
