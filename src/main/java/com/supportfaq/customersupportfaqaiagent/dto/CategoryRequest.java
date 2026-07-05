package com.supportfaq.customersupportfaqaiagent.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryRequest {

    @NotBlank(message = "English category name is required")
    @Size(max = 100, message = "English category name must be at most 100 characters")
    private String nameEnglish;

    @Size(max = 100, message = "Arabic category name must be at most 100 characters")
    private String nameArabic;

    @Size(max = 1000, message = "Description must be at most 1000 characters")
    private String description;

    @Size(max = 40, message = "Status must be at most 40 characters")
    private String status;

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public String getNameArabic() {
        return nameArabic;
    }

    public void setNameArabic(String nameArabic) {
        this.nameArabic = nameArabic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
