package com.supportfaq.customersupportfaqaiagent.service;

import com.supportfaq.customersupportfaqaiagent.dto.CategoryRequest;
import com.supportfaq.customersupportfaqaiagent.entity.Category;
import com.supportfaq.customersupportfaqaiagent.exception.ResourceNotFoundException;
import com.supportfaq.customersupportfaqaiagent.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final InputSanitizer inputSanitizer;

    private static final Set<String> ALLOWED_STATUSES = Set.of("ACTIVE", "INACTIVE");

    public CategoryService(CategoryRepository categoryRepository, InputSanitizer inputSanitizer) {
        this.categoryRepository = categoryRepository;
        this.inputSanitizer = inputSanitizer;
    }

    public Category create(CategoryRequest request) {
        Category category = new Category();
        apply(category, request);
        return categoryRepository.save(category);
    }

    public List<Category> getAll() {
        return categoryRepository.findAllByOrderByCreatedAtDesc();
    }

    public Category update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        apply(category, request);
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found");
        }
        categoryRepository.deleteById(id);
    }

    private void apply(Category category, CategoryRequest request) {
        category.setNameEnglish(inputSanitizer.requiredText(request.getNameEnglish(), 100, "English category name"));
        category.setNameArabic(inputSanitizer.text(request.getNameArabic(), 100));
        category.setDescription(inputSanitizer.text(request.getDescription(), 1000));
        category.setStatus(inputSanitizer.enumValue(request.getStatus(), "ACTIVE", ALLOWED_STATUSES, "status"));
    }

}
