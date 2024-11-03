package com.ogisystems;

import com.ogisystems.service.GameInterface;
import com.ogisystems.service.GameService;
import com.ogisystems.ui.GameInterfaceService;

public class Main {

    public static void main(String[] args) {

        GameService gameService = new GameService();
        GameInterface gameInterface = new GameInterfaceService();

        gameService.configureInitialCategories();

        while (true) {

            gameInterface.displayMessage("Pense em um prato que você gosta");

            if (gameService.checkCategoryOrDish()) {
                gameInterface.showSuccessMessage();
            }
        }
    }
}
