package com.eliasnogueira.alm.redmine;

import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.Project;

/**
 * Reescrita de funcoes da API do Redmine
 * 
 * @author Elias Nogueira <elias.nogueira@gmail.com>
 *
 */
public class RedmineUtils {

    /**
     * Abre uma issue no Redmine
     * 
     * @param titulo sumario da issue
     * @param descricao descricao da issue
     * @param projeto key do projeto (campo Identifier)
     * @return id da issue
     */
    public static int abrirIssue(String titulo, String descricao, String projeto) {
        RedmineManager redmine = RedmineInstance.getInstance();
        int issueID = 0;
        
        Issue issue = new Issue();
        issue.setSubject(titulo);
        issue.setDescription(descricao);
        
        try {
            Project project = redmine.getProjectByKey(projeto);
            
            Issue issuee = redmine.createIssue(project.getIdentifier(), issue);
            issueID = issuee.getId();
        } catch (RedmineException e) {
            e.printStackTrace();
        }
        
        return issueID;
    }
}
