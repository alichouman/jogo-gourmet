package com.ogisystems;

import com.ogisystems.model.Dish;
import com.ogisystems.model.DishCategory;
import com.ogisystems.service.GameService;
import com.ogisystems.ui.GameInterfaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GameServiceTest {

    @Mock
    private GameInterfaceService gameInterfaceService;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameService.configureInitialCategories();
    }

    @Test
    void testConfigureInitialCategories() {

        gameService.configureInitialCategories();

        DishCategory pastaCategory = gameService.getCategories().stream()
                .filter(c -> c.getName().equalsIgnoreCase("massa"))
                .findFirst()
                .orElse(null);

        assertNotNull(pastaCategory, "A categoria 'massa' deve existir.");
        assertTrue(pastaCategory.getDishes().contains(new Dish("Lasanha")),
                "A categoria 'massa' deve conter o prato 'Lasanha'.");
    }

    @Test
    void testCheckCategoryOrDishWhenDishIsInPastaCategory() {

        when(gameInterfaceService.ask("O prato que você pensou é da categoria massa?"))
                .thenReturn(JOptionPane.YES_OPTION);
        when(gameInterfaceService.ask("O prato que você pensou é Lasanha?"))
                .thenReturn(JOptionPane.YES_OPTION);

        boolean result = gameService.checkCategoryOrDish();

        assertTrue(result, "Deveria retornar verdadeiro quando o prato está na categoria 'massa'.");
        verify(gameInterfaceService).ask("O prato que você pensou é da categoria massa?");
        verify(gameInterfaceService).ask("O prato que você pensou é Lasanha?");
    }

    @Test
    void testCheckCategoryOrDishWhenDishIsNotInAnyCategoryAndAddNewCategoryAndDish() {
        when(gameInterfaceService.ask("O prato que você pensou é da categoria massa?"))
                .thenReturn(JOptionPane.NO_OPTION);
        when(gameInterfaceService.ask("O prato que você pensou é Bolo de Chocolate?"))
                .thenReturn(JOptionPane.NO_OPTION);
        when(gameInterfaceService.requestInput("Qual prato você pensou?"))
                .thenReturn("Feijoada");
        when(gameInterfaceService.requestInput("Feijoada é ______, mas Bolo de Chocolate não."))
                .thenReturn("Brasileira");

        boolean result = gameService.checkCategoryOrDish();

        assertFalse(result, "Deveria retornar falso, pois o prato não estava em nenhuma categoria.");
        DishCategory newCategory = gameService.getCategories().stream()
                .filter(c -> c.getName().equalsIgnoreCase("Brasileira"))
                .findFirst()
                .orElse(null);

        assertNotNull(newCategory, "A nova categoria 'Brasileira' deve ser criada.");
        assertTrue(newCategory.getDishes().contains(new Dish("Feijoada")),
                "A nova categoria deve conter o prato 'Feijoada'.");
    }

    @Test
    void testCheckCategoryOrDishWhenExitDuringDishCheck() {
        when(gameInterfaceService.ask("O prato que você pensou é da categoria massa?"))
                .thenReturn(JOptionPane.NO_OPTION);
        when(gameInterfaceService.ask("O prato que você pensou é Bolo de Chocolate?"))
                .thenReturn(JOptionPane.NO_OPTION);
        when(gameInterfaceService.ask("O prato que você pensou é da categoria Brasileira?"))
                .thenReturn(JOptionPane.NO_OPTION);
        when(gameInterfaceService.ask("O prato que você pensou é Bolo de Chocolate?"))
                .thenReturn(2);

        gameService.checkCategoryOrDish();

        verify(gameInterfaceService).endGame();
    }
}
