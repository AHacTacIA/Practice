import java.io.*;
import java.util.zip.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class Writer {

    public void createZip(String filename, String newFilename, String name){
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(newFilename));
            FileInputStream fis= new FileInputStream(filename);)
        {
            ZipEntry entry1=new ZipEntry(name);
            zout.putNextEntry(entry1);
            // считываем содержимое файла в массив byte
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            // добавляем содержимое к архиву
            zout.write(buffer);
            // закрываем текущую запись для новой записи
            zout.closeEntry();
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    }


    public static void encryptWitEcb(String filenamePlain, String filenameEnc, SecretKey key) throws IOException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        //SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        // cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        try (FileInputStream fis = new FileInputStream(filenamePlain);
             BufferedInputStream in = new BufferedInputStream(fis);
             FileOutputStream out = new FileOutputStream(filenameEnc);
             BufferedOutputStream bos = new BufferedOutputStream(out)) {
            byte[] ibuf = new byte[1024];
            int len;
            while ((len = in.read(ibuf)) != -1) {
                byte[] obuf = cipher.update(ibuf, 0, len);
                if (obuf != null)
                    bos.write(obuf);
            }
            byte[] obuf = cipher.doFinal();
            if (obuf != null)
                bos.write(obuf);
        }
    }


}

