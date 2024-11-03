package com.ogisystems.service;

public interface GameInterface {

    void displayMessage(String mensagem);

    int ask(String mensagem);

    void showSuccessMessage();

    String requestInput(String mensagem);

    void endGame();

}
