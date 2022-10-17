package com.assignment.managingrecipes.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Ingredients")
public class Ingredients implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 @Column(name = "ID")
	 private int id;
	 @Column(name="recipe_Id",insertable=false, updatable = false)
	 private int recipeId;
	 private String inName;
	 
	 @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	 @JoinColumn(name = "recipe_id" ,  referencedColumnName = "recipeId")
	 @JsonIgnoreProperties("ingredientsList")
	 @OnDelete(action = OnDeleteAction.CASCADE)
    private Recipe recipe;
	
	 public Ingredients() {}
	 
	 public Ingredients(int id, int recipeRId, String inName) {
		super();
		this.id = id;
		this.recipeId = recipeRId;
		this.inName = inName;
	}
	 

	public Ingredients(int id, int recipeRId, String inName, Recipe recipe) {
		super();
		this.id = id;
		this.recipeId = recipeRId;
		this.inName = inName;
		this.recipe = recipe;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	public String getInName() {
		return inName;
	}

	public void setInName(String inName) {
		this.inName = inName;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	
}
