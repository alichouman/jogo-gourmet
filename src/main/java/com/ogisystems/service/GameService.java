package com.ogisystems.service;

import com.ogisystems.model.Dish;
import com.ogisystems.model.DishCategory;
import com.ogisystems.ui.GameInterfaceService;

import javax.swing.*;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class GameService {

    private static final int YES = JOptionPane.YES_OPTION;
    private static final int EXIT = 2;

    private static final Set<DishCategory> categories = new LinkedHashSet<>();

    private final GameInterfaceService gameInterfaceService = new GameInterfaceService();

    public void configureInitialCategories() {

        Dish lasagna = new Dish("Lasanha");
        DishCategory pastaCategory = new DishCategory("massa", new LinkedHashSet<>(Set.of(lasagna)));
        categories.add(pastaCategory);
    }

    public boolean checkCategoryOrDish() {

        Optional<DishCategory> pastaCategory = categories.stream()
                .filter(c -> c.getName().equalsIgnoreCase("massa")).findFirst();

        if (pastaCategory.isPresent() && gameInterfaceService.ask("O prato que você pensou é da categoria massa?") == YES) {
            return checkThoughtDish(pastaCategory.get());
        } else {
            for (DishCategory category : categories) {
                if (!category.getName().equalsIgnoreCase("massa")) {
                    int categoryResponse = gameInterfaceService.ask("O prato que você pensou é da categoria " + category.getName() + "?");
                    if (categoryResponse == YES) {
                        return checkThoughtDish(category);
                    } else if (categoryResponse == EXIT) {
                        gameInterfaceService.endGame();
                    }
                }
            }

            int cakeResponse = checkChocolateCakeDish();
            return cakeResponse == YES || (cakeResponse != EXIT && addNewCategoryAndDish());
        }
    }

    private boolean addNewCategoryAndDish() {

        String newDish = gameInterfaceService.requestInput("Qual prato você pensou?");
        if (newDish == null) gameInterfaceService.endGame();

        String newCategory = gameInterfaceService.requestInput(newDish + " é ______, mas Bolo de Chocolate não.");
        if (newCategory == null) gameInterfaceService.endGame();

        DishCategory category = categories.stream()
                .filter(c -> c.getName().equalsIgnoreCase(newCategory))
                .findFirst()
                .orElseGet(() -> createCategory(newCategory));

        category.getDishes().add(new Dish(newDish));
        return false;
    }

    private int checkChocolateCakeDish() {
        int cakeResponse = gameInterfaceService.ask("O prato que você pensou é Bolo de Chocolate?");
        if (cakeResponse == EXIT) {
            gameInterfaceService.endGame();
        }
        return cakeResponse;
    }

    private boolean checkThoughtDish(DishCategory dishCategory) {
        for (Dish dish : dishCategory.getDishes()) {
            int dishResponse = gameInterfaceService.ask("O prato que você pensou é " + dish.getName() + "?");
            if (dishResponse == YES) {
                return true;
            } else if (dishResponse == EXIT) {
                gameInterfaceService.endGame();
            }
        }
        return addNewDish(dishCategory);
    }

    private boolean addNewDish(DishCategory category) {
        String newDish = gameInterfaceService.requestInput("Qual prato você pensou?");
        if (newDish == null) {
            gameInterfaceService.endGame();
        }

        category.getDishes().add(new Dish(newDish));
        return false;
    }

    private DishCategory createCategory(String categoryName) {
        DishCategory newCategory = new DishCategory(categoryName, new LinkedHashSet<>());
        categories.add(newCategory);
        return newCategory;
    }

    public Set<DishCategory> getCategories() {
        return categories;
    }
}
