import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;
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
        AlgorithmParameterSpec aps = new IvParameterSpec(initialization_vector);//krijon nje objekt duke perdorur bytes of initialization_vector si IV
        //Cipher  class siguron funksionalitetin e kriptografis per enkriptim dhe dekriptim
        encrypt=Cipher.getInstance("DES/CBC/PKCS5Padding");//me ane te getInstace specifikojm llojin e algoritmit qe perdorim ne rastintone
        //modin CBC dhe padding PKCS5Padding"
        decrypt= Cipher.getInstance("DES/CBC/PKCS5Padding");

        //----------------------------------------TEXT/FILE ENCRYPTION/DECRYPTION----------------------------------------------//
        System.out.print("Zgjedhni text ose file : ");//varet prej userit
        String fromuser=input.nextLine();
        fromuser=fromuser.toLowerCase();
        if(fromuser.equals("text")){
            text();
        }
        else if(fromuser.equals("file")){
            file();
        }
        else
            System.out.print("\nGabim!!!");
    }

    //----------------------------------------TEXT/FILE ENCRYPTION/DECRYPTION-----------------------------------------//
    public static void text() throws BadPaddingException, IllegalBlockSizeException {

        Scanner input=new Scanner(System.in);
        System.out.print("Text:");
        String plain=input.nextLine();

        String encrypted = encrypt(encrypt, plain);
        System.out.println("Encrypted text: " + encrypted);

        String decrypted = decrypt(decrypt, encrypted);
        System.out.println("Decrypted text: " + decrypted);
        input.close();
    }

    public static void file() throws IOException {
        Scanner input=new Scanner(System.in);
        System.out.print("File to encrypt:");
        String textFile=input.nextLine();

        System.out.print("Where to save encrypted file:");
        String encryptedData =input.nextLine();

        System.out.print("Where to save decrypted file:");
        String decryptedData = input.nextLine();

        //calling encrypt() method to encrypt the file
        encryption(new FileInputStream(textFile), new FileOutputStream(encryptedData));//parameter hyres e ka file i cili duhet te enkriptohet dhe file ku duhet me vendos filen e enkriptuar
        //calling decrypt() method to decrypt the file
        decryption(new FileInputStream(encryptedData), new FileOutputStream(decryptedData));//parameter hyres ka filen e enkriptuar dhe filen ku ka me vendos filen e dekriptuar
        //prints the statement if the program runs successfully
        System.out.println("The encrypted and decrypted files have been created successfully.");
        input.close();
    }

    //----------------------------------------TEXT ENCRYPTION/DECRYPTION----------------------------------------------//
    public static String encrypt(Cipher cipher, String plainStr) throws BadPaddingException,IllegalBlockSizeException
    {
        byte[] plainBytes;
        byte[] encryptedBytes;
        plainBytes=plainStr.getBytes(StandardCharsets.UTF_8);//stringun e marr si parameter hyres e kthen ne byte dhe e vendos ne plainbytes
        encryptedBytes=cipher.doFinal(plainBytes);//me ane te do finel enkriptohet ne cipher qe e kemi inicializu kete rast encrypt
        //return new String(encryptedBytes,StandardCharsets.ISO_8859_1);
        return Base64.getEncoder().encodeToString(encryptedBytes);//konverton encrypted text nga byte ne string dhe kthen si rez
    }

    public static String decrypt(Cipher cipher,String encryptedStr) throws BadPaddingException,IllegalBlockSizeException
    {
        byte[] encryptedBytes= Base64.getDecoder().decode(encryptedStr);//dekodon stringun encryptedstr
        //byte[] encryptedBytes=encryptedStr.getBytes(StandardCharsets.ISO_8859_1);
        byte[] decryptedBytes=cipher.doFinal(encryptedBytes);//e dekripton ne siq e kemi inicaializu cipher
        return new String(decryptedBytes, StandardCharsets.UTF_8);//kthen decrypted data ne string dhe e vendos ne rez
    }

    //----------------------------------------FILE ENCRYPTION/DECRYPTION----------------------------------------------//
    private static void encryption(InputStream input, OutputStream output)
            throws IOException
    {
        output = new CipherOutputStream(output, encrypt);//CipherOutputStream kominim mes Cipher dhe OutputStream qe merr si paramtere file ku ka me vendos tekstin e enkriptun dhe parametri tjeter specifikon modin
        //calling the writeBytes() method to write the encrypted bytes to the file
        writeBytes(input, output);//path prej ku kemi me marr filen per enkriptim dhe path ku kemi me vendos filen e enkriptuar
    }

    }
