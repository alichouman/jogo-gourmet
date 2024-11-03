package com.ogisystems.ui;

import com.ogisystems.service.GameInterface;

import javax.swing.*;

public class GameInterfaceService implements GameInterface {

    private static final Object[] OPTIONS = {"Sim", "Não", "Sair"};

    private static final String TITULO_JOGO = "Jogo Gourmet";

    @Override
    public void displayMessage(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, TITULO_JOGO, JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showSuccessMessage() {
        displayMessage("Acertei de novo!");
    }


    @Override
    public int ask(String mensagem) {
        return JOptionPane.showOptionDialog(null, mensagem, TITULO_JOGO, JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, OPTIONS, OPTIONS[0]);
    }

    @Override
    public String requestInput(String mensagem) {
        return JOptionPane.showInputDialog(null, mensagem, TITULO_JOGO, JOptionPane.QUESTION_MESSAGE);
    }


    @Override
    public void endGame() {
        this.displayMessage("Jogo encerrado. Obrigado por jogar!");
        System.exit(0);
    }
}
