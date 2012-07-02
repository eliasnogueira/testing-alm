package com.eliasnogueira.alm.redmine;

import com.eliasnogueira.alm.util.ALMUtils;
import com.taskadapter.redmineapi.RedmineManager;

/**
 * Classe de conexao com o Redmine
 * 
 * @author Elias Nogueira <elias.nogueira@gmail.com>
 */
public class RedmineInstance {

    private static RedmineManager instance = null;

    /**
     * Retorna uma nova instancia ou a instancia existente da conexao com o Redmine
     * @return instancia de conexao do Redmine
     */
    public static RedmineManager getInstance() {
        if (instance == null) {
            instance = new RedmineManager(ALMUtils.getProperties("redmine.url"), ALMUtils.getProperties("redmine.key"));
        }
        return instance;
    }
	
}

