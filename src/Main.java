import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

  private static final int startSceneSize = 400;
  private static final int loginSpacing = 10;
  private static final double defaultWalletBalance = 0.0;
  private static final double loginTextAreaMultiplier = 2.0 / 3.0 ;
  private final XMLParser x1 = new XMLParser();

  @Override
  public void start(Stage primaryStage)  {
    VBox pane = new VBox(loginSpacing);
    pane.setAlignment(Pos.CENTER);

    Label usernameLabel = new Label("Username:");
    TextField username = new TextField();
    username.setMaxWidth(loginTextAreaMultiplier * startSceneSize);
    username.setPromptText("Enter your username");

    Label passwordLabel = new Label("Password:");
    PasswordField password = new PasswordField();
    password.setPromptText("Enter your password");
    password.setMaxWidth(loginTextAreaMultiplier * startSceneSize);

    Button loginButton = new Button("Login");

    Button signUpButton = new Button("SignUp");

    loginButton.setOnAction(e -> {
      if (x1.validPassword(username.getText(), password.getText())) {
        User u1 = new User(username.getText(), x1.balanceGivenValidPassword(username.getText(),
            password.getText()), primaryStage);
      }
      else {
        showError("Invalid Username or Password");
      }
    });

    signUpButton.setOnAction(e -> {
      try {
        newUser(username.getText(), password.getText());
        username.clear();
        password.clear();
      }
      catch (ExistingUsernameException e1) {
        showError(e1.getMessage());
      }
    });

    pane.getChildren().addAll(usernameLabel, username, passwordLabel, password, loginButton, signUpButton);

    Scene scene = new Scene(pane, startSceneSize, startSceneSize);
    primaryStage.setTitle("Login Page");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void showError(String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.setTitle("ERROR");
    Label messageText;
    messageText = new Label(message);
    messageText.setWrapText(true);
    alert.getDialogPane().setContent(messageText);
    alert.show();
  }


  public void newUser(String username, String password) {
    if (!x1.existingUsername(username)) {
      x1.newUser(username, password, defaultWalletBalance);
    }
    else {
      throw new ExistingUsernameException("Username Already Exists");
    }
  }
}