package view;

public class AdminView {

    public void showMenu() {
        String menu = "1) Create mentor\n" +
                      "2) Edit mentor\n" +
                      "3) Show mentors\n" +
                      "4) Create class\n" +
                      "5) Show classes\n" +
                      "0) EXIT";

        System.out.println(menu);
    }


}