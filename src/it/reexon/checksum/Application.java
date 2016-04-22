/**
 * Copyright (c) 2016 Marco Velluto
 */
package it.reexon.checksum;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import it.reexon.lib.security.checksums.GenerateSecureChecksum;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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

    private final Button checksumCalculateButton = new Button("Calculate");
    private final Button fileChooserButton = new Button("Load File");

    private final Label notification = new Label();
    private final TextField selectedFilePath = new TextField("");
    private final TextArea text = new TextArea("");
    private final ProgressIndicator progressIndicator = new ProgressIndicator();
    private File fileSelected;

    @Override
    public void start(Stage stage) throws InterruptedException
    {
        stage.setTitle("Checksum Calculates");
        Scene scene = new Scene(new VBox(), 450, 350);
        stage.getIcons().add(new Image("file:resources/icon.png"));
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");

        progressIndicator.setVisible(false);

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File..");

        final ComboBox<String> algorithms = new ComboBox<String>();
        //@f:off
        algorithms.getItems().addAll(
            "MD2",
            "MD5",
            "SHA-1",
            "SHA-224",
            "SHA-256",
            "SHA-384",
            "SHA-512"
        );   
        algorithms.setValue("SHA-256");
        //@f:on

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
                        String algoritm = algorithms.getSelectionModel().getSelectedItem();
                        Task<String> generaChecksum = new Task<String>()
                        {
                            @Override
                            protected String call() throws Exception
                            {
                                Date startDate = new Date();
                                String checksum = GenerateSecureChecksum.getChecksum(fileSelected, algoritm);
                                Date endDate = new Date();
                                long different = endDate.getTime() - startDate.getTime();
                                Calendar c = new Calendar.Builder().setInstant(different).build();
                                System.out.println(c.get(Calendar.SECOND) + " s - " + "checksum: " + checksum);
                                return checksum;
                            }
                        };
                        Thread th = new Thread(generaChecksum);
                        th.setDaemon(true);
                        th.start();

                        String checksum = generaChecksum.get();
                        text.setText(checksum);

                        progressIndicator.progressProperty().bind(generaChecksum.progressProperty());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        progressIndicator.setVisible(false);
                    }
                }
                System.out.println(event.toString());
            }
        });

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(15);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(fileChooserButton, 0, 1);
        grid.add(selectedFilePath, 1, 1, 10, 1);
        grid.add(text, 0, 2, 10, 1);
        grid.add(checksumCalculateButton, 0, 3);
        grid.add(algorithms, 1, 3);
        grid.add(notification, 1, 3, 3, 1);

        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar);
        ((VBox) scene.getRoot()).getChildren().add(grid);
        ((VBox) scene.getRoot()).getChildren().add(progressIndicator);

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args)
    {
        launch(args);
    }

}
