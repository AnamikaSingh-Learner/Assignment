package com.assignment.managingrecipes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.assignment.managingrecipes.entities.Ingredients;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredients, Integer> {

	List<Ingredients> findByRecipeId(int recipeId);

	int deleteByRecipeId(int recipeId);

}
