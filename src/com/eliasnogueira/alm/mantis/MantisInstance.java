package com.eliasnogueira.alm.mantis;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mantisbt.connect.IMCSession;
import org.mantisbt.connect.MCException;
import org.mantisbt.connect.axis.MCSession;

import com.eliasnogueira.alm.util.ALMUtils;

/**
 * Classe de conexao com o Mantis
 * 
 * @author Elias Nogueira <elias.nogueira@gmail.com>
 */
public class MantisInstance  {

    private static MantisInstance instance = null;
    private static IMCSession sessao = null;

    /**
     * Cria a instancia de conexao com o Mantis
     * @throws MalformedURLException se a URL da API do Mantis está errada
     * @throws MCException se ocorrer algum erro na conexao com o Mantis
     */
    private MantisInstance() throws MalformedURLException, MCException {
        URL url = new URL(ALMUtils.getProperties("mantis.url"));
        sessao = new MCSession(url, ALMUtils.getProperties("mantis.user"), ALMUtils.getProperties("mantis.password"));
    }

    /**
     * Retorna uma nova instancia ou a instancia existente da conexao com o Mantis
     * @return instancia de conexao do Mantis
     */
    public static MantisInstance getInstance() {
        if (instance == null) {
            try {
                instance = new MantisInstance();
            } catch (MalformedURLException ex) {
                Logger.getLogger(MantisInstance.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MCException ex) {
                Logger.getLogger(MantisInstance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    /**
     * Pega a instancia para a conexao com o Mantis. Se nao existe, cria uma nova instancia
     * @return sessao do Mantis
     * @throws MalformedURLException se a URL da API do Mantis esta errada
     * @throws MCException se ocorrer algum erro na conexao com o Mantis
     */
    public static IMCSession getSessao() throws MalformedURLException, MCException {
        if (sessao == null) {
            getInstance();
        }
        return sessao;
    }	
}
