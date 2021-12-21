import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.zip.*;
import java.security.*;


public class Reader {

    public void openZip(String filename, String newFilename){
        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(filename)))
        {
            ZipEntry entry;
            String name;
            long size;
            while((entry=zin.getNextEntry())!=null){

                name = entry.getName(); // получим название файла
                size=entry.getSize();  // получим его размер в байтах
                System.out.printf("File name: %s \t File size: %d \n", name, size);

                // распаковка
                FileOutputStream fout = new FileOutputStream(newFilename + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    }

    //public static void decryptWithEcb(String filenameEnc, String filenameDec, byte[] key) throws IOException,
    public static void decryptWithEcb(String filenameEnc, String filenameDec, SecretKey key) throws IOException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        try (FileInputStream in = new FileInputStream(filenameEnc);
             FileOutputStream out = new FileOutputStream(filenameDec)) {
            byte[] ibuf = new byte[1024];
            int len;
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            //SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            while ((len = in.read(ibuf)) != -1) {
                byte[] obuf = cipher.update(ibuf, 0, len);
                if (obuf != null)
                    out.write(obuf);
            }
            byte[] obuf = cipher.doFinal();
            if (obuf != null)
                out.write(obuf);
        }
    }









}

