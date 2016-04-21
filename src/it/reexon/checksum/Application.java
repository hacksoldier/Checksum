/**
 * Copyright (c) 2016 Marco Velluto
 */
package it.reexon.checksum;

import java.io.File;
import java.io.IOException;

import it.reexon.lib.security.checksums.GenerateSecureChecksum;
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

    final Button checksumCalculateButton = new Button("Calculate");
    final Button fileChooserButton = new Button("Load File");

    final Label notification = new Label();
    final TextField selectedFilePath = new TextField("");
    final TextArea text = new TextArea("");

    String address = new String(" ");

    File fileSelected;

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

        final ComboBox<String> algorithms = new ComboBox<String>();
        //@f:off
        algorithms.getItems().addAll(
            "MD2",
            "MD5",
            "SHA-1",
            "SHA-256",
            "SHA-224",
            "SHA-384",
            "SHA-512"
        );   
        //@f:on

        algorithms.setValue("SHA-256");

        fileChooserButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                fileSelected = fileChooser.showOpenDialog(stage);
                text.setText("");
                selectedFilePath.setText("");
                selectedFilePath.setText(fileSelected.getPath());
            }
        });
        checksumCalculateButton.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent event)
            {
                if (fileSelected != null)
                {
                    selectedFilePath.setText(fileSelected.getPath());
                    try
                    {
                        text.setText(GenerateSecureChecksum.getChecksum(fileSelected));
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                System.out.println(event.toString());
            }
        });

        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("To: "), 0, 0);
        grid.add(emailComboBox, 1, 0);
        grid.add(new Label("Priority: "), 2, 0);
        //        grid.add(algorithms, 3, 0);
        grid.add(fileChooserButton, 0, 1);
        grid.add(selectedFilePath, 1, 1, 3, 1);
        grid.add(text, 0, 2, 4, 1);
        grid.add(checksumCalculateButton, 0, 3);
        grid.add(algorithms, 1, 3);
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
