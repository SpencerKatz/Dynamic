import java.util.ArrayList;
import java.util.List;
import javafx.stage.Stage;

public class User {

  private final String username;
  private double balance;

  private final List<Wallet> userWallets;

  public User(String username, double balance, Stage stage) {
    userWallets = new ArrayList<>();
    userWallets.add(new Wallet(balance));
    this.username = username;
    this.balance = balance;
    UI ui = new UI(this);
    ui.setScene(stage);
  }

  public Wallet getCurrentWallet(int i) {
    return userWallets.get(i);
  }
}
