package com.product.api.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "category")
public class Category {
       
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer category_id;
    
    @NotNull
    @Column(name = "category")
    private String category;

    @Column(name = "status")
	@Min(value = 0, message="status must be 0 or 1")
	@Max(value = 1, message="status must be 0 or 1")
	@JsonIgnore
	private Integer status;
	
    public Category(){
 
    }

    public Integer getCategoryId(){
        return this.category_id;
    }

    public String getCategory(){
        return this.category;
    }
    
    public Integer getStatus(){
        return this.status;
    }
    
    public void setCategoryId(Integer category_id) {
    	this.category_id = category_id;
    }
    
    public void setCategory(String category) {
    	this.category = category;
    }
    
    public void setStatus(Integer status) {
    	this.status = status;
    }
    
    @Override
    public String toString() {
    	return "category_id: " + category_id + ", category: " + category + ", status: " + status;
    }
     

}
