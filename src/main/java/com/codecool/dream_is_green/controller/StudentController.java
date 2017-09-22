package com.codecool.dream_is_green.controller;

import com.codecool.dream_is_green.model.StudentModel;
import java.util.LinkedList;
import java.lang.IndexOutOfBoundsException;
import java.lang.NumberFormatException;

import com.codecool.dream_is_green.model.*;
import com.codecool.dream_is_green.view.*;
import com.codecool.dream_is_green.dao.*;

public class StudentController {

    private static final int SHOW_QUESTS = 1;
    private static final int BUY_ARTIFACT = 2;
    private static final int TEAM_BUY_ARTIFACT = 3;
    private static final int SHOW_WALLET = 4;
    private static final int EXIT = 0;

    private static UIView uiView = new UIView();
    private static StudentView studentView = new StudentView();
    private static ArtifactView artifactView = new ArtifactView();
    private static QuestView questView = new QuestView();

    private static QuestDAO questDao = DaoStart.getQuestDao();
    private static ArtifactDAO artifactDao = DaoStart.getArtifactDao();


    public void buyArtifact(StudentModel studentModel) {

        artifactDao.clearObjectList();
        artifactDao.loadArtifact();
        LinkedList<ArtifactModel> artifactList = artifactDao.getObjectList();

        String artifactDaoString = artifactDao.toString();
        artifactView.showArtifactList(artifactDaoString);
        System.out.println(studentModel.getWallet().getCoolCoins());

        try {
            Integer index = Integer.parseInt(uiView.getInput("\nEnter index for chosen artifact: "));
            ArtifactModel artifactToBuy = artifactList.get(index - 1);
            System.out.println(artifactToBuy.getPrice());
            Integer artifactPrice = artifactToBuy.getPrice();

            if (artifactPrice < studentModel.getWallet().getCoolCoins()) {

                String title = artifactToBuy.getTitle();
                Integer price = artifactToBuy.getPrice();
                ArtifactCategoryModel artifactCategoryModel = artifactToBuy.getCategory();
                Integer id = studentModel.getUserID();

                artifactDao.insertArtifact("StudentsWithArtifacts", title, price,
                                           artifactCategoryModel.toString(), id);
                ArtifactModel newArtifact = new ArtifactModel(title, price, artifactCategoryModel);
                studentModel.getWallet().addBoughtArtifact(newArtifact);
                studentModel.getWallet().removeCoolCoins(artifactPrice);
                artifactDao.clearObjectList();

            }
            else {
                uiView.printMessage("You don't have enough cool coins");
            }

        } catch (NumberFormatException e) {
            uiView.printMessage("Wrong input");
        }
    }

    public void showWallet(StudentModel student) {

        WalletController walletController = new WalletController();
        walletController.showWalletContent(student.getWallet());
    }

    public void startMenu(int operation, StudentModel student) {

        switch(operation) {

            case SHOW_QUESTS :
                this.showQuestList();
                uiView.pressToContinue();
                break;

            case BUY_ARTIFACT :
                try {
                    this.buyArtifact(student);
                } catch (IndexOutOfBoundsException e) {
                    uiView.printMessage("Wrong index.");
                } catch (NumberFormatException e) {
                    uiView.printMessage("This is not number");
                }
                uiView.pressToContinue();
                break;

            case TEAM_BUY_ARTIFACT :
                System.out.println("Coming soon ...");
                uiView.pressToContinue();
                break;

            case SHOW_WALLET :
                this.showWallet(student);
                uiView.pressToContinue();
                break;

            case EXIT:
                break;

             default :
                uiView.printMessage("No option! Try Again!\n");
                uiView.pressToContinue();
        }

    }

    public void startStudentController(StudentModel student) {

        int operation;

        do {
            uiView.clearScreen();
            System.out.println("Hello " + student.getName() + "\n");
            studentView.showMenu();
            operation = uiView.getInputInt("Choice option: ");
            uiView.clearScreen();
            this.startMenu(operation, student);
        } while (operation != EXIT);

    }

    public void showQuestList() {

        questDao.loadQuest();
        String questDaoString = questDao.toString();
        questView.showQuestList(questDaoString);
        questDao.clearObjectList();
    }
}
