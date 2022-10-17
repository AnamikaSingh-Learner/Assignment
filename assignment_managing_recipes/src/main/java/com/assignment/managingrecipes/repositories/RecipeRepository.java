package com.assignment.managingrecipes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.assignment.managingrecipes.entities.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer>{
Recipe findByRecipeId(int id);

@Query(value = "select r from Recipe r where (:category is null or r.category =:category) and (:servings=-1 or r.servings = :servings)"
		+ "and(:ing is null or  r.recipeId in (select i.recipeId from Ingredients i where i.inName like CONCAT('%',:ing,'%')))")
List<Recipe> searchINRecipe(String category, int servings, String ing);

@Query("select r from Recipe r where (:category is null or r.category =:category) and (:servings=-1 or r.servings = :servings)"
		+ "and(:ing is null or  r.recipeId not in (select i.recipeId from Ingredients i where i.inName like CONCAT('%',:ing,'%')))")
List<Recipe> searchEXRecipe(String category, int servings, String ing);

}
