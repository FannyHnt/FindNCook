package jgfx.javagradlefx.controller;

import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import jgfx.javagradlefx.model.GroceryList;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

public class ShoppingListController {

    @FXML
    private TextField ingredientNameField;
    @FXML
    private ListView<String> ingredientListView;
    private GroceryList groceryList = new GroceryList();
    private NavigationHandler navbar = new NavigationHandler();
    private String userView = "/jgfx/javagradlefx/UserView.fxml";
    private String AdvancedSearchView = "/jgfx/javagradlefx/advancedResearchView.fxml";
    private String homeView = "/jgfx/javagradlefx/HomeView.fxml";
    private String favoritesView = "/jgfx/javagradlefx/FavoriteListView.fxml";

    @FXML
    public void initialize() {
        // Charger les ingrédients existants
        uploadIngredients();
        // Configurer la ListView pour utiliser des cellules personnalisées
        ingredientListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new IngredientListCell();
            }
        });
    }

    @FXML
    private void addIngredient() {
        String name = ingredientNameField.getText();
        if (!name.isEmpty()) {
            groceryList.addIngredient(name);
            uploadIngredients();
            ingredientNameField.clear();
        }
    }

    private void uploadIngredients() {
        ingredientListView.getItems().clear();
        for (String ingredient : groceryList.getGroceryList()) {
            ingredientListView.getItems().add(ingredient);
        }
    }

    private class IngredientListCell extends ListCell<String> {
        private HBox hbox = new HBox();
        private TextField textField = new TextField();
        private Button deleteButton = new Button("Remove");

        public IngredientListCell() {
            super();
            textField.setEditable(false);
            HBox.setHgrow(textField, Priority.ALWAYS);
            hbox.getChildren().addAll(textField, deleteButton);
            deleteButton.setOnAction(event -> {
                String item = getItem();
                if (item != null) {
                    groceryList.removeIngredient(item);
                    uploadIngredients();
                }
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                textField.setText(item);
                setGraphic(hbox);
            }
        }
    }

    @FXML
    private void emptyList() {
        ingredientListView.getItems().clear();
        groceryList.clearGroceryList();
    }

    @FXML
    private void goToUser() throws Exception {
        navbar.goToAnotherPage(ingredientNameField, userView);
    }

    @FXML
    private void goToHome() throws Exception {
        navbar.goToAnotherPage(ingredientNameField, homeView);
    }

    @FXML
    private void goToAdvancedSearch() throws Exception {
        navbar.goToAnotherPage(ingredientNameField, AdvancedSearchView);
    }

    @FXML
    private void goToFavorites() throws Exception {
        navbar.goToAnotherPage(ingredientNameField, favoritesView);
    }

    @FXML
    private void printIngredientList() {
        if (ingredientListView.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Impression");
            alert.setHeaderText(null);
            alert.setContentText("La liste d'ingrédients est vide.");
            alert.showAndWait();
            return;
        }

        // Crée une VBox contenant tous les ingrédients en Labels
        VBox printableContent = new VBox(10);
        printableContent.setStyle("-fx-padding: 20;");
        for (String ingredient : ingredientListView.getItems()) {
            Label label = new Label("• " + ingredient);
            label.setFont(new Font(14)); // taille lisible
            printableContent.getChildren().add(label);
        }

        // Crée et configure le PrinterJob
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucune imprimante disponible.");
            alert.showAndWait();
            return;
        }

        boolean proceed = job.showPrintDialog(ingredientListView.getScene().getWindow());
        if (proceed) {
            boolean success = job.printPage(printableContent);
            if (success) {
                job.endJob();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("L'impression a échoué.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void sendListByEmail() {
        if (ingredientListView.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Email");
            alert.setHeaderText(null);
            alert.setContentText("La liste d'ingrédients est vide.");
            alert.showAndWait();
            return;
        }

        String subject = "Ma liste de courses";
        StringBuilder body = new StringBuilder("Voici la liste d'ingrédients :\n\n");
        for (String ingredient : ingredientListView.getItems()) {
            body.append("- ").append(ingredient).append("\n");
        }

        try {
            String mailto = "mailto:?subject=" + encodeForMail(subject) + "&body=" + encodeForMail(body.toString());
            Desktop.getDesktop().browse(new URI(mailto));
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Impossible d'ouvrir le client mail.");
            alert.showAndWait();
        }
    }

    // Méthode personnalisée pour encoder les chaînes avec %20 au lieu de +
    private String encodeForMail(String text) throws UnsupportedEncodingException {
        return URLEncoder.encode(text, "UTF-8").replace("+", "%20");
    }
    
}