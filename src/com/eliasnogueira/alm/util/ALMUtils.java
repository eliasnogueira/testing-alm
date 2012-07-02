package com.eliasnogueira.alm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

/**
 * Classe que concentra todas as funcoes utilizadas por todas as classes
 * 
 * @author Elias Nogueira <elias.nogueira@gmail.cm>
 */
public class ALMUtils {

    /**
     * Metodo para pegar o valor de alguma propriedade no arquivo de configuracao do Selenium
     * O caminho e o nome do arquivo pode ser trocados
     */
    public static String getProperties(String name) {
        Properties properties = new Properties();
        String value = null;
        
        try {
            properties.load(new FileInputStream("resources/config.properties"));
            value = properties.getProperty(name);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return value;
    }

    /**
     * Transforma uma imagem de Base64 em um Base64 novamente, pois a api java do Testlink possui um bug
     * na biblioteca commons que somente aceita o envio de um Base64 pela funcao encodeBase64Chunked
     * e n‹o Ž possivel enviar diretamente o Base64 gerado pelo Webdriver
     * 
     * A imagem Ž salva na pasta TEMP do sistema operacional
     * 
     * @param stringBase64 imagem em Base64 gerada pelo Webdriver
     * @return nova string Base64
     */
    public static String decodeImageToContent(String stringBase64)  {
        String fileName = System.getProperty("user.dir") + System.getProperty("file.separator") + "imgTmp.png";
        File file = new File(fileName);
        String fileContent = null;
        
        byte[] byteArray = Base64.decodeBase64(stringBase64);
        
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(byteArray);
            fos.flush();
            
            byte[] fileToByteArray = FileUtils.readFileToByteArray(file);
            fileContent = new String(Base64.encodeBase64Chunked(fileToByteArray));   
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(fileContent);
        return fileContent;
    } 
}
