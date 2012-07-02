package com.eliasnogueira.alm.testes;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import com.eliasnogueira.alm.bugzilla.BugzillaUtils;
import com.eliasnogueira.alm.mantis.MantisUtils;
import com.eliasnogueira.alm.redmine.RedmineUtils;
import com.eliasnogueira.alm.selenium.LoginPage;
import com.eliasnogueira.alm.selenium.MainPage;
import com.eliasnogueira.alm.testlink.TestlinkUtils;
import com.eliasnogueira.alm.util.ALMUtils;
import com.eliasnogueira.alm.util.ReportaErro;

/**
 * Classe de execucao dos testes para cada ferramenta
 * 
 * @author Elias Nogueira <elias.nogueira@gmail.com>
 *
 */
public class ExecutaTestesPorFerramenta {

    WebDriver driver = null;
    static ReportaErro erro;
    
    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(ALMUtils.getProperties("test.url"));
    }

    @After
    public void tearDown() throws Exception {
        driver.close();
    }
    
    @Test
    public void erroBugzilla() {
        try {
            // clica em Login, efetua o login com sucesso e efetua logoff
            MainPage mainPage = new MainPage(driver);
            mainPage.acessaLogin();
            
            // efetua o login e garante que esta na pagina de login
            LoginPage login = new LoginPage(driver);
            login.efetuaLogin("tdc2012alm@mailnator.com", "tdc2012");
            
            /**
             * Existe um erro proposital neste assert
             * O texto correto que aparece na tela e 'ALM TDC'
             * 
             * Isso foi inserido para simular o erro na pagina e o report automatico das ferramentas
             */
            assertTrue(login.estaNaPaginaDeLogin("PROVOCA ERRO")); 
            
            mainPage.logout();
            
        } catch (Exception e) {
            erro = new ReportaErro(((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64), e.getMessage());
        } catch (AssertionError e) {
            erro = new ReportaErro(((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64), e.getMessage());
        } finally {
            if (erro != null) {
                String data = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss").format(new Date());

                // reporta um bug no Bugzilla
                int bugID = BugzillaUtils.reportarIssue("Teste-ALM", "interface", "Erro " + data, 
                        "Erro na execucao do script", "unspecified", "Mac OS", "Macintosh", 
                        "High", "blocker", "CONFIRMED");
                
                // anexa o arquivo da tela no momento do erro
                BugzillaUtils.adicionarAnexo(bugID, erro.getEvidencia(), "ErroExecucao.png", "Erro na execucao do teste");
                
                // reporta o resultado do caso de teste no Testlink
                int execucao = TestlinkUtils.reportaResultadoExecucao("ALM TDC2012","Plano Regressão", "Login com Sucesso", "Erro na execução do teste", String.valueOf(bugID));
                TestlinkUtils.anexaEvidenciaExecucao(execucao, erro.getEvidencia(), "Erro", "Erro na execucao do teste", "ErroExecucao.png");
                
                Assert.fail(erro.getStackTrace());

            } else {
                // reporta o resultado do caso de teste no Testlink
                TestlinkUtils.reportaResultadoExecucao("ALM TDC2012","Plano Regressão", "Login com Sucesso", "Teste executado com sucesso", null);               
            }
        }
    }    
    
    @Test
    public void erroRedmine() {
        try {
            // clica em Login, efetua o login com sucesso e efetua logoff
            MainPage mainPage = new MainPage(driver);
            mainPage.acessaLogin();
            
            // efetua o login e garante que esta na pagina de login
            LoginPage login = new LoginPage(driver);
            login.efetuaLogin("tdc2012alm@mailnator.com", "tdc2012");
            
            /**
             * Existe um erro proposital neste assert
             * O texto correto que aparece na tela e 'ALM TDC'
             * 
             * Isso foi inserido para simular o erro na pagina e o report automatico das ferramentas
             */
            assertTrue(login.estaNaPaginaDeLogin("PROVOCA ERRO")); 
            
            mainPage.logout();
            
        } catch (Exception e) {
            erro = new ReportaErro(((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64), e.getMessage());
        } catch (AssertionError e) {
            erro = new ReportaErro(((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64), e.getMessage());
        } finally {
            if (erro != null) {
                String data = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss").format(new Date());

                // reporta uma issue no Redmine
                int bugID = RedmineUtils.abrirIssue("Erro " + data, "Erro na execucao de um teste", "alm");
                
                // reporta o resultado do caso de teste no Testlink
                int execucao = TestlinkUtils.reportaResultadoExecucao("ALM TDC2012","Plano Regressão", "Login com Sucesso", "Erro na execução do teste", String.valueOf(bugID));
                TestlinkUtils.anexaEvidenciaExecucao(execucao, erro.getEvidencia(), "Erro", "Erro na execucao do teste", "ErroExecucao.png");
                
                
                Assert.fail();

            } else {
                // reporta o resultado do caso de teste no Testlink
                TestlinkUtils.reportaResultadoExecucao("ALM TDC2012","Plano Regressão", "Login com Sucesso", "Teste executado com sucesso", null);               
            }
        }
    }   
   
    @Test
    public void erroMantis() {
        try {
            // clica em Login, efetua o login com sucesso e efetua logoff
            MainPage mainPage = new MainPage(driver);
            mainPage.acessaLogin();
            
            // efetua o login e garante que esta na pagina de login
            LoginPage login = new LoginPage(driver);
            login.efetuaLogin("tdc2012alm@mailnator.com", "tdc2012");
            
            /**
             * Existe um erro proposital neste assert
             * O texto correto que aparece na tela e 'ALM TDC'
             * 
             * Isso foi inserido para simular o erro na pagina e o report automatico das ferramentas
             */
            assertTrue(login.estaNaPaginaDeLogin("PROVOCA ERRO")); 
            
            mainPage.logout();
            
        } catch (Exception e) {
            erro = new ReportaErro(((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64), e.getMessage());
        } catch (AssertionError e) {
            erro = new ReportaErro(((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64), e.getMessage());
        } finally {
            if (erro != null) {
                String data = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss").format(new Date());

                long bugID = MantisUtils.reportaBug("ALM TDC-2012", "Erro " + data, "Erro na execucao do teste erroMantis", "new", erro.getStackTrace());
                MantisUtils.adicionaAnexo(bugID, "ErroExecucao.png", erro.getEvidencia());
                
                // reporta o resultado do caso de teste no Testlink
                int execucao = TestlinkUtils.reportaResultadoExecucao("ALM TDC2012","Plano Regressão", "Login com Sucesso", "Erro na execução do teste", String.valueOf(bugID));
                TestlinkUtils.anexaEvidenciaExecucao(execucao, erro.getEvidencia(), "Erro", "Erro na execucao do teste", "ErroExecucao.png");
                
                Assert.fail(erro.getStackTrace());

            } else {
                // reporta o resultado do caso de teste no Testlink
                TestlinkUtils.reportaResultadoExecucao("ALM TDC2012","Plano Regressão", "Login com Sucesso", "Teste executado com sucesso", null);               
            }
        }
    }    
    
}
