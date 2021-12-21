import javax.crypto.*;
import java.io.*;
import java.security.*;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, IOException, BadPaddingException, InvalidKeyException {

        Scanner in = new Scanner(System.in);
        System.out.println("Введите путь до файла");
        String filename = in.nextLine();
        System.out.println("Введите путь до нового файла");
        String newFilename = in.nextLine();

        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        SecretKey key = kgen.generateKey();

        Writer b = new Writer();
        b.encryptWitEcb(filename,newFilename,key);
        filename=in.nextLine();
        Reader a = new Reader();
        a.decryptWithEcb(newFilename,filename,key);

        // a.openZip(filename, newFilename);

        // System.out.println("Введите путь до файла");
        // filename = in.nextLine();
        /*System.out.println("Введите путь до нового архива");
        newFilename = in.nextLine();
        System.out.println("Введите название файла");
        String name = in.nextLine();*/


        // b.createZip(filename, newFilename, name);

    }

}


