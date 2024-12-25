package dat.nx.sanbongdaquoc.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import dat.nx.sanbongdaquoc.models.entities.BookingDTO;

//Assuming ButtonCellFactory is a custom class that creates a button for each row
public class ButtonCellFactory extends TableCell<BookingDTO, String> {
 private final Button button;

 // Constructor takes the button label and the action handler
 public ButtonCellFactory(String buttonLabel, EventHandler<ActionEvent> actionHandler) {
     button = new Button(buttonLabel);
     button.setOnAction(actionHandler);
 }

 // This method will render the button in the table cell
 @Override
 protected void updateItem(String item, boolean empty) {
     super.updateItem(item, empty);

     if (empty) {
         setGraphic(null);
     } else {
         setGraphic(button);
     }
 }
}


