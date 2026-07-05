package com.supportfaq.customersupportfaqaiagent.controller;

import com.supportfaq.customersupportfaqaiagent.dto.ApiMessageResponse;
import com.supportfaq.customersupportfaqaiagent.dto.FaqRequest;
import com.supportfaq.customersupportfaqaiagent.entity.Faq;
import com.supportfaq.customersupportfaqaiagent.service.FaqService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/faqs")
public class FaqController {

    private final FaqService faqService;

    public FaqController(FaqService faqService) {
        this.faqService = faqService;
    }

    @PostMapping
    public Faq addFaq(@Valid @RequestBody FaqRequest request) {
        return faqService.addFaq(request);
    }

    @GetMapping
    public List<Faq> getAllFaqs() {
        return faqService.getAllFaqs();
    }

    @GetMapping("/generated")
    public List<Faq> getGeneratedFaqs() {
        return faqService.getGeneratedFaqs();
    }

    @GetMapping("/pending-review")
    public List<Faq> getPendingReviewFaqs() {
        return faqService.getPendingReviewFaqs();
    }

    @PutMapping("/{id}/approve")
    public Faq approveFaq(@PathVariable Long id) {
        return faqService.approveFaq(id);
    }

    @PutMapping("/{id}/reject")
    public Faq rejectFaq(@PathVariable Long id) {
        return faqService.rejectFaq(id);
    }

    @GetMapping("/{id}")
    public Faq getFaqById(@PathVariable Long id) {
        return faqService.getFaqById(id);
    }

    @PutMapping("/{id}")
    public Faq updateFaq(@PathVariable Long id, @Valid @RequestBody FaqRequest request) {
        return faqService.updateFaq(id, request);
    }

    @DeleteMapping("/{id}")
    public ApiMessageResponse deleteFaq(@PathVariable Long id) {
        faqService.deleteFaq(id);
        return new ApiMessageResponse("FAQ deleted successfully");
    }

    @GetMapping("/category/{category}")
    public List<Faq> getFaqsByCategory(@PathVariable String category) {
        return faqService.getFaqsByCategory(category);
    }

    @GetMapping("/status/{status}")
    public List<Faq> getFaqsByStatus(@PathVariable String status) {
        return faqService.getFaqsByStatus(status);
    }

    @GetMapping("/language/{language}")
    public List<Faq> getFaqsByLanguage(@PathVariable String language) {
        return faqService.getFaqsByLanguage(language);
    }

    @GetMapping("/search")
    public List<Faq> searchFaqs(@RequestParam String keyword) {
        return faqService.searchFaqs(keyword);
    }
}
