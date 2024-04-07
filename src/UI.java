import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UI {

  private final User user;
  private int currentWallet;
  private final Label infoLabel;

  public UI(User user) {
    this.user = user;
    this.currentWallet = 0;
    this.infoLabel = new Label("");
  }

  public void setScene(Stage stage) {
    Pane pane = new VBox(10);
    List<Button> buttons = makeButtons();

    HBox buttonPane = new HBox(10);
    buttonPane.getChildren().addAll(buttons);

    pane.getChildren().addAll(buttonPane, infoLabel);

    Scene scene = new Scene(pane, 400, 400);
    stage.setScene(scene);
    stage.show();
  }

  private List<Button> makeButtons() {
    List<Button> buttons = new ArrayList<>();

    Button button1 = new Button("Get Balance");
    buttons.add(button1);
    button1.setOnAction(e -> {
      double balance = user.getCurrentWallet(currentWallet).getBalance();
      infoLabel.setText("Balance: " + balance);
    });

    Button button2 = new Button("Send Message");
    buttons.add(button2);
    button2.setOnAction(e -> {
      try {
        String signedMessage = user.getCurrentWallet(currentWallet).signMessage("Message");
        // will allow one to sign a different message.
        infoLabel.setText("Signed Message: " + signedMessage);
      } catch (Exception e1) {
        infoLabel.setText("Error: " + e1.getMessage());
      }
    });

    Button button3 = new Button("Send Transaction");
    buttons.add(button3);
    button3.setOnAction(e -> {
      // will add handling
    });

    return buttons;
  }

}


