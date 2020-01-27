package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

/**
 *
 * @author Fran
 */
public class EncryptionClass {

    /**
     * Cifra un texto con RSA, modo ECB y padding PKCS1Padding (asimétrica) y lo
     * retorna
     *
     * @param mensaje El mensaje a cifrar
     * @return El mensaje cifrado
     */
    public String encryptText(String mensaje) {
        byte[] encodedMessage = null;
        try {
            // Clave pública
            byte fileKey[] = fileReader("files/Public.key");

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(fileKey);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encodedMessage = cipher.doFinal(mensaje.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(encodedMessage);
    }

    /**
     * Descifra un texto con RSA, modo ECB y padding PKCS1Padding (asimétrica) y
     * lo retorna
     *
     * @param mensaje El mensaje a descifrar
     * @return El mensaje descifrado
     */
    private byte[] decryptText(byte[] mensaje) {
        byte[] decodedMessage = null;
        try {
            // Clave pública
            byte fileKey[] = fileReader("./files/Private.key");

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(fileKey);
            PrivateKey privateKey = keyFactory.generatePrivate(pKCS8EncodedKeySpec);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decodedMessage = cipher.doFinal(mensaje);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decodedMessage;
    }

    /**
     * Aplica SHA al texto pasado por parámetro
     *
     * @param texto
     */
    public String hashingText(String texto) {
        MessageDigest messageDigest = null;
        byte[] hash = null;
        String encoded = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA");
            byte[] dataBytes = texto.getBytes(); // texto a bytes
            messageDigest.update(dataBytes);// se introduce texto en bytes a resumir
            hash = messageDigest.digest();// se calcula el resumen
            encoded = Base64.getEncoder().encodeToString(hash);

            System.out.println("Mensaje original: " + texto);
            System.out.println("Número de Bytes: " + messageDigest.getDigestLength());
            System.out.println("Algoritmo: " + messageDigest.getAlgorithm());
            System.out.println("Mensaje Resumen: " + new String(hash));
            System.out.println("Mensaje en Hexadecimal: " + Hexadecimal(hash));
            System.out.println("Proveedor: " + messageDigest.getProvider().toString());
            System.out.println("encoded: " + encoded);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encoded;
    }

    // Convierte Array de Bytes en hexadecimal
    static String Hexadecimal(byte[] resumen) {
        String HEX = "";
        for (int i = 0; i < resumen.length; i++) {
            String h = Integer.toHexString(resumen[i] & 0xFF);
            if (h.length() == 1) {
                HEX += "0";
            }
            HEX += h;
        }
        return HEX.toUpperCase();
    }

    /**
     * Retorna el contenido de un fichero
     *
     * @param path Path del fichero
     * @return El texto del fichero
     */
    private byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
