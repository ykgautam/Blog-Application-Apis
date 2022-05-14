package com.yash.blog.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.yash.blog.entities.Category;
import com.yash.blog.exceptions.ResourceNotFoundException;
import com.yash.blog.payloads.CategoryDto;
import com.yash.blog.repositories.CategoryRepo;
import com.yash.blog.services.CategoryService;

public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelmapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelmapper.map(categoryDto, Category.class);
		Category savedCategory = this.categoryRepo.save(category);
		return this.modelmapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", " Category Id", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDecription(categoryDto.getCategoryDecription());
		Category updatedCategory = this.categoryRepo.save(cat);
		return this.modelmapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", " Category Id", categoryId));
		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", " Category Id", categoryId));
		return this.modelmapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> categoryDtos = categories.stream().map(category -> this.modelmapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		
		return categoryDtos;
	}

}
