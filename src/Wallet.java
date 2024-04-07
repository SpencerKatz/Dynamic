import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

public class Wallet {

  private double balance;

  public Wallet(double balance) {
    this.balance = balance;
  }

  public double getBalance() {
    return balance;
  }

  public String signMessage(String message)
      throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("EC");
    keyPairGen.initialize(256);
    KeyPair pair = keyPairGen.generateKeyPair();
    PrivateKey privateKey = pair.getPrivate();

    Signature signature = Signature.getInstance("SHA256withECDSA");
    signature.initSign(privateKey);
    signature.update(message.getBytes());

    byte[] digitalSignature = signature.sign();

    return Base64.getEncoder().encodeToString(digitalSignature);

  }

}
