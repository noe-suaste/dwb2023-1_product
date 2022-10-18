package com.product.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.product.api.dto.ApiResponse;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;

@Service
public class SvcCategoryImp implements SvcCategory {

	@Autowired
	RepoCategory repo;
	
	@Override
	public List<Category> getCategories() {
		return repo.findByStatus(1);
	}

	@Override
	public Category getCategory(Integer category_id) {
		Category category = repo.findByCategoryId(category_id);
		if(category == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Category does not exist");
		}
		return category;
	}

	@Override
	public ApiResponse createCategory(Category category) {
		Category categorySaved = (Category) repo.findByCategory(category.getCategory());
		if(categorySaved != null) { 
			if(categorySaved.getStatus() == 0) {
				repo.activateCategory(categorySaved.getCategoryId());
				return new ApiResponse("Category has been activated");
			}
			else
				throw new ApiException(HttpStatus.BAD_REQUEST, "Category already exists");
		}
		repo.createCategory(category.getCategory());
		return new ApiResponse("Category created");
	}

	@Override
	public ApiResponse updateCategory(Integer category_id, Category category) {
		Category categorySaved = (Category) repo.findByCategoryId(category_id);
		if(categorySaved == null)
			throw new ApiException(HttpStatus.BAD_REQUEST, "Category does not exist");
		else {
			if(categorySaved.getStatus() == 0)
				throw new ApiException(HttpStatus.BAD_REQUEST, "Category is not active");// No funciona porque el findByCategoryId solo regresa categoria si esta activa
			else {
				categorySaved = (Category) repo.findByCategory(category.getCategory());
				if(categorySaved != null)
					throw new ApiException(HttpStatus.BAD_REQUEST, "Category already exists");
				repo.updateCategory(category_id, category.getCategory());
				return new ApiResponse("Category updated");
			}
		}
	}

	@Override
	public ApiResponse deleteCategory(Integer category_id) {
		Category categorySaved = (Category) repo.findByCategoryId(category_id);
		if(categorySaved == null)
			throw new ApiException(HttpStatus.NOT_FOUND, "Category does not exist");
		else {
			repo.deleteById(category_id);
			return new ApiResponse("Category removed");
		}
	}

}
