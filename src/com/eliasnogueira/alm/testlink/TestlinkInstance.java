package com.eliasnogueira.alm.testlink;

import java.net.MalformedURLException;
import java.net.URL;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;

import com.eliasnogueira.alm.util.ALMUtils;

/**
 * Classe de conexao com o Testlink
 * 
 * @author Elias Nogueira <elias.nogueira@gmail.com>
 */
public class TestlinkInstance {

    private static TestLinkAPI instance = null;

    /**
     * Retorna uma nova instancia ou a instancia existente da conexao com o Testlink
     * @return instancia de conexao do Testlink
     */
    public static TestLinkAPI getInstance() {
        if (instance == null) {
            try {
                instance = new TestLinkAPI(new URL(ALMUtils.getProperties("testlink.url")), ALMUtils.getProperties("testlink.devkey"));
            } catch (TestLinkAPIException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
	
}
