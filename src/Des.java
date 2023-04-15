import javax.crypto.*;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Des {
    //creating an instance of the Cipher class for encryption
    private static Cipher encrypt;
    //creating an instance of the Cipher class for decryption
    private static Cipher decrypt;
    //initializing vector
    private static final byte[] initialization_vector = { 22, 33, 11, 44, 55, 99, 66, 77 };
    //main() method
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException,
            IllegalBlockSizeException, InvalidKeyException, InvalidAlgorithmParameterException, IOException {
        //path of the file that we want to encrypt
        Scanner input = new Scanner(System.in);
        SecretKey scrtkey = KeyGenerator.getInstance("DES").generateKey();//Gjeneron nje secret(symetric)key me ane te implenetimit te libraris javax.crypto
        //getInstance("DES") specifikon se qelsi i gjeneruar eshte des key
    }
}
