//imports
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.ArrayList;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        //variables to use
        ArrayList<String> itemList = new ArrayList<>();
        ArrayList<Double> priceList = new ArrayList<>();
        ArrayList<Integer> amountList = new ArrayList<>();
        final double taxRate = 0.07;
        String userDiscount = "";

        //create the app interface
        primaryStage.setTitle("Shopping List Calculator");
        
        //labels
        Label label1 = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        Label label4 = new Label();
        label1.setText("Please enter your item here: ");
        label2.setText("Please enter the item price here: ");
        label3.setText("Please enter the amount of the item here: ");
        label4.setText("Enter discount code here (if applicable): ");

        //text entry
        TextField itemEntry = new TextField();
        itemEntry.setMaxWidth(150);
        TextField priceEntry = new TextField();
        priceEntry.setMaxWidth(150);
        TextField amountEntry = new TextField();
        amountEntry.setMaxWidth(150);
        TextField discountEntry = new TextField();
        discountEntry.setMaxWidth(100);

        //Buttons
        Button addToListButton = new Button("Add To Your List");
        Button calculateButton = new Button("Calculate Total");

        //vbox
        VBox vbox1 = new VBox(label1, itemEntry, label2, priceEntry, label3, amountEntry, addToListButton);
        VBox vbox2 = new VBox(label4, discountEntry, calculateButton);
        vbox1.setSpacing(10);
        vbox1.setAlignment(Pos.CENTER);
        vbox2.setSpacing(10);
        vbox2.setAlignment(Pos.CENTER);
        VBox fullvbox = new VBox(vbox1, vbox2);
        fullvbox.setSpacing(40);

        //events for clicking the buttons
        addToListButton.setOnAction(event -> {
            //adding the string/item input
            try
            {
                //item first, check if field is empty
                String textInput = itemEntry.getText();
                if(!textInput.isEmpty())
                {
                    itemList.add(textInput);
                    System.out.println(textInput + " added to shopping list.");
                }

                //price second
                double priceInput = Double.parseDouble(priceEntry.getText());
                priceList.add(priceInput);

                //amount third
                int amountInput = Integer.parseInt(amountEntry.getText());
                amountList.add(amountInput);

                //clear the textfields
                itemEntry.clear();
                priceEntry.clear();
                amountEntry.clear();

            } catch (NumberFormatException e)
            {
                System.out.println("Invalid number entered for price! Please Try again");
            }
        });

        calculateButton.setOnAction(event -> {
            StringBuilder receipt = new StringBuilder();
            receipt.append("---- Shopping Reciept ----\n\n");

            //getting each specific item, price, and amount to add to the list
            double subtotal = 0.0;
            for (int i = 0; i < itemList.size(); i++)
            {
                String item = itemList.get(i);
                double price = priceList.get(i);
                int amount = amountList.get(i);

                double itemTotal = price * amount;
                subtotal += itemTotal;

                receipt.append(String.format("%s x%d @ $%.2f = $%.2f\n", item, amount, price, itemTotal));
            }
            
            //applying any possible discounts
            String discountCode = discountEntry.getText().trim();
            double discount = 0.0;
            if (discountCode.equalsIgnoreCase("SAVE10"))
            {
                discount = subtotal * 0.10;
                receipt.append("\nDiscount (SAVE10): -$" + String.format("%.2f", discount));
            }

            double tax = (subtotal - discount) * taxRate;
            double total = subtotal - discount + tax;

            receipt.append("\nTax: $" + String.format("%.2f", tax));
            receipt.append("\nTotal: $" + String.format("%.2f", total));
            receipt.append("\n============================");
            discountEntry.clear();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Receipt");
            alert.setHeaderText("Your Shopping Receipt");
            alert.setContentText(receipt.toString());
            alert.showAndWait();
        });

        //creating the scene and show it
        Scene scene = new Scene(fullvbox, 300, 425);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}