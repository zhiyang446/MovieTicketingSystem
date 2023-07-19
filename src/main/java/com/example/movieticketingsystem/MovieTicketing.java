package com.example.movieticketingsystem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MovieTicketing extends Application {

    private TextField movieName;
    private ComboBox<String> experience;
    private ToggleGroup time;
    private TextField seats;
    private ToggleGroup popcorn;
    private RadioButton royalMember;
    private RadioButton royalRegular;
    private RadioButton royalCombo;
    private Button submitButton;

    @Override
    public void start(Stage stage) {

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        Label movieLabel = new Label("Movie:");
        movieName = new TextField();
        grid.add(movieLabel, 0, 0);
        grid.add(movieName, 1, 0);

        Label experienceLabel = new Label("Experience:");
        experience = new ComboBox<>();
        experience.getItems().addAll(
                "Beanie", "Classic", "Deluxe", "Family-Friendly",
                "Flexound", "IMAX", "Indulge", "Infinity", "Junior", "Onyx");
        grid.add(experienceLabel, 0, 1);
        grid.add(experience, 1, 1);

        // Showtime radio buttons
        time = new ToggleGroup();
        RadioButton elevenAm = new RadioButton("11:00 AM");
        elevenAm.setToggleGroup(time);
        RadioButton oneThirtyPm = new RadioButton("1:30 PM");
        oneThirtyPm.setToggleGroup(time);
        RadioButton fourPm = new RadioButton("4:00 PM");
        fourPm.setToggleGroup(time);
        RadioButton sixThirtyPm = new RadioButton("6:30 PM");
        sixThirtyPm.setToggleGroup(time);
        RadioButton ninePm = new RadioButton("9:00 PM");
        ninePm.setToggleGroup(time);

        Label timeLabel = new Label("Session:");
        VBox timeBox = new VBox(5);
        timeBox.getChildren().addAll(elevenAm, oneThirtyPm, fourPm, sixThirtyPm, ninePm);

        grid.add(timeLabel, 0, 2);
        grid.add(timeBox, 1, 2);

        // Seat selection text field
        Label seatLabel = new Label("Seats (e.g. F6, F7):");
        seats = new TextField();
        grid.add(seatLabel, 0, 3);
        grid.add(seats, 1, 3);

        Label popcornLabel = new Label("Food & Beverage:");
        HBox popcornBox = new HBox(10);
        popcornBox.setAlignment(Pos.CENTER_LEFT);

        popcorn = new ToggleGroup();

        VBox royalMemberBox = new VBox(5);
        royalMemberBox.setAlignment(Pos.CENTER);
        royalMember = new RadioButton("Royal Popcorn Combo - Member Special");
        royalMember.setToggleGroup(popcorn);
        Label popcornPrice = new Label("RM19.90");
        Image royalMemberImage = new Image(MovieTicketing.class.getResource("popcorn1.png").toString());
        ImageView royalMemberImageView = new ImageView(royalMemberImage);
        royalMemberImageView.setFitWidth(200);
        royalMemberImageView.setFitHeight(200);
        royalMemberBox.getChildren().addAll(royalMemberImageView,royalMember,popcornPrice);

        VBox royalRegularBox = new VBox(5);
        royalRegularBox.setAlignment(Pos.CENTER);
        royalRegular = new RadioButton("Royal Popcorn");
        royalRegular.setToggleGroup(popcorn);
        Label popcornPrice2 = new Label("RM17.90");
        Image royalRegularImage = new Image(MovieTicketing.class.getResource("popcorn2.png").toString());
        ImageView royalRegularImageView = new ImageView(royalRegularImage);
        royalRegularImageView.setFitWidth(200);
        royalRegularImageView.setFitHeight(200);
        royalRegularBox.getChildren().addAll(royalRegularImageView,royalRegular,popcornPrice2);

        VBox royalComboBox = new VBox(5);
        royalComboBox.setAlignment(Pos.CENTER);
        royalCombo = new RadioButton("Royal Popcorn Combo");
        royalCombo.setToggleGroup(popcorn);
        Label popcornPrice3 = new Label("RM21.90");
        Image royalComboImage = new Image(MovieTicketing.class.getResource("popcorn3.png").toString());
        ImageView royalComboImageView = new ImageView(royalComboImage);
        royalComboImageView.setFitWidth(200);
        royalComboImageView.setFitHeight(200);
        royalComboBox.getChildren().addAll(royalComboImageView,royalCombo,popcornPrice3);

        popcornBox.getChildren().addAll(royalMemberBox, royalRegularBox, royalComboBox);

        grid.add(popcornLabel, 0, 5);
        grid.add(popcornBox, 1, 5);


        // Purchase button
        submitButton = new Button("Submit");
        HBox buttonContainer = new HBox(submitButton);
        buttonContainer.setAlignment(Pos.BOTTOM_RIGHT);

        submitButton.setOnAction(e -> {
            try {
                checkInput();
                displayConfirmation();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill out all fields.");
                alert.show();
            }
        });

        grid.add(buttonContainer, 1, 7);


        root.getChildren().add(grid);

        Scene scene = new Scene(root, 1080, 550);
        stage.setScene(scene);
        stage.setTitle("Movie Ticketing");
        stage.show();
    }

    private void checkInput() throws Exception {
        if (movieName.getText().isEmpty() || experience.getValue() == null
                || time.getSelectedToggle() == null || seats.getText().isEmpty()
                || popcorn.getSelectedToggle() == null) {
            throw new Exception("Missing input");
        }
    }

    private void displayConfirmation() {
        String movie = movieName.getText();
        String experienceType = experience.getValue();

        RadioButton selectedTime = (RadioButton) time.getSelectedToggle();
        String showtime = selectedTime.getText();

        String[] seatsArray = seats.getText().split(",");
        int numTickets = seatsArray.length;

        RadioButton selectedPopcorn = (RadioButton) popcorn.getSelectedToggle();
        String popcornChoice = selectedPopcorn.getText();
        double popcornPrice = 0.0;

        if (selectedPopcorn == royalMember) {
            popcornPrice = 19.90;
        } else if (selectedPopcorn == royalRegular) {
            popcornPrice = 17.90;
        } else if (selectedPopcorn == royalCombo) {
            popcornPrice = 21.90;
        }

        double experiencePrice = getExperiencePrice(experienceType);
        double totalPrice = (experiencePrice * numTickets) + popcornPrice;

        String confirmationMessage = "You selected " + movie + " with " + experienceType +
                " at " + showtime + " for " + numTickets + " seat(s) and " + popcornChoice +
                ". \nThe total price is RM" + String.format("%.2f", totalPrice);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Thank You!");
        alert.setHeaderText("Comfirmation");
        alert.setHeight(800);
        alert.setContentText(confirmationMessage);

        alert.showAndWait();
    }


    private double getExperiencePrice(String experienceType) {
        switch (experienceType) {
            case "Beanie":
                return 19.90;
            case "Classic":
                return 15.90;
            case "Deluxe":
                return 23.90;
            case "Family-Friendly":
                return 23.90;
            case "Flexound":
                return 25.90;
            case "IMAX":
                return 25.90;
            case "Indulge":
                return 120.00;
            case "Infinity":
                return 120.00;
            case "Junior":
                return 15.90;
            case "Onyx":
                return 89.90;
            default:
                return 0.00;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
