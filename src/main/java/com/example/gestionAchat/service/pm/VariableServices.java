
package com.example.gestionAchat.service.pm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.gestionAchat.entities.Variable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Transactional
@Service
public class VariableServices {
    @PersistenceContext
    @Autowired
    private EntityManager em;
    @Value("${clientApp.name}")
    private String applicationName;

    public Variable getvariableById(String name, String application) {

            Query q = this.em.createNativeQuery("SELECT * FROM pm_variable WHERE name =? and application =(select id from pm_application where alias =?)", Variable.class).setParameter(1, name).setParameter(2, application);
            return (Variable) q.getResultList().get(0);

    }

    public List<Variable> getVariablesByApplication(String application) {
        Query q = this.em.createNativeQuery("SELECT * FROM pm_variable WHERE application =(select id from pm_application where alias =?)", Variable.class).setParameter(1, application);
        return (List<Variable>) q.getResultList();
    }

    public Object getValeur(String Name, String applicationName) throws IOException, ScriptException {
        Variable var = getvariableById(Name, applicationName);
        String value=var.getValue();
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
        Object values =  nashorn.eval(value);
        if (values!=null) {
            return values;
        }
        return null;
    }

    public Object getValeur(String Name) throws IOException, ScriptException {
        return getValeur(Name,applicationName );
    }

    }
