/**
 * Copyright (c) 2016 Marco Velluto
 */
package it.reexon.checksum;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author Marco Velluto
 * @since Java 1.8
 */
public class Application extends javafx.application.Application
{

    final Button button = new Button("Send");
    final Label notification = new Label();
    final TextField subject = new TextField("");
    final TextArea text = new TextArea("");

    String address = new String(" ");

    @Override
    public void start(Stage stage)
    {
        stage.setTitle("Menu Sample");
        Scene scene = new Scene(new VBox(), 430, 350);

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
        
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File..");

        final ComboBox<String> emailComboBox = new ComboBox<String>();
        //@f:off
        emailComboBox.getItems().addAll(
            "jacob.smith@example.com",
            "isabella.johnson@example.com",
            "ethan.williams@example.com",
            "emma.jones@example.com",
            "michael.brown@example.com"  
        );
            //@f:on

        final ComboBox<String> priorityComboBox = new ComboBox<String>();
        //@f:off
        priorityComboBox.getItems().addAll(
            "Highest",
            "High",
            "Normal",
            "Low",
            "Lowest" 
        );   
        //@f:on

        priorityComboBox.setValue("Normal");

        EventHandler<ActionEvent> value = new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent event)
            {
                subject.getText();
                System.out.println(event.toString());

            }
        };

        button.setOnAction(value);
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("To: "), 0, 0);
        grid.add(emailComboBox, 1, 0);
        grid.add(new Label("Priority: "), 2, 0);
        grid.add(priorityComboBox, 3, 0);
        grid.add(new Label("Subject: "), 0, 1);
        grid.add(subject, 1, 1, 3, 1);
        grid.add(text, 0, 2, 4, 1);
        grid.add(button, 0, 3);
        grid.add(notification, 1, 3, 3, 1);

        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar);
        ((VBox) scene.getRoot()).getChildren().add(grid);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}
