package com.example.gestionAchat.service;



import org.apache.commons.io.FileUtils;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.ServiceTask;
import org.flowable.bpmn.model.*;
import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.identitylink.api.IdentityLinkType;
import org.flowable.task.api.NativeTaskQuery;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.service.history.NativeHistoricTaskInstanceQuery;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.flowable.variable.api.history.NativeHistoricVariableInstanceQuery;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormatSymbols;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.joda.time.DateTimeConstants.SECONDS_PER_HOUR;
import static org.joda.time.DateTimeConstants.SECONDS_PER_MINUTE;

@Transactional
@Service
public class WorkflowService {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private FormService formService;



  //get statistique des demande sou format list des Json
  public List getInfo() {
    List<Object> result= new ArrayList<>();
    List<ProcessDefinition> listActiveProcess = repositoryService.createProcessDefinitionQuery().latestVersion().list();
    for(ProcessDefinition processDefinition:listActiveProcess) {
      Long nbrProcessDemandeInstances = historyService.createHistoricProcessInstanceQuery().processDefinitionName(processDefinition.getName()).count();

      Long nbrProcessDemandesInstancesActive = historyService.createHistoricProcessInstanceQuery().processDefinitionName(processDefinition.getName()).finished().count();
      Long nbrProcessDemandesInstancesNotActive = nbrProcessDemandeInstances - nbrProcessDemandesInstancesActive;

      List<JSONObject> listProcessDemandesInstancesByMois = new ArrayList<JSONObject>();

      List<JSONObject> listProcessDemandesInstancesByJour = new ArrayList<JSONObject>();

      LocalDate date = LocalDate.now();
      int currentMonth = date.getMonthValue();
      int currentYear = date.getYear();
      int currentDay = date.getDayOfMonth();
      System.out.println(currentMonth);
      for (int i = 0; i < currentMonth; i++) {
        Calendar calendar = Calendar.getInstance();

        calendar.clear();
        calendar.set(Calendar.DAY_OF_MONTH, 01);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        calendar.set(Calendar.MONTH, i);
        calendar.set(Calendar.YEAR, currentYear);
        Date dateDeb = calendar.getTime();

        System.out.println((dateDeb.toString()));
        calendar.clear();
        calendar.set(Calendar.MONTH, i + 1);
        calendar.set(Calendar.YEAR, currentYear);
        Date dateFin = calendar.getTime();
        System.out.println((dateFin.toString()));
        Long nbrProcessDemandesInstancesParMois = historyService.createHistoricProcessInstanceQuery().processDefinitionName(processDefinition.getName()).startedAfter(dateDeb).startedBefore(dateFin).count();

        String month = new DateFormatSymbols().getMonths()[i];
        System.out.println(month);
        JSONObject json = new JSONObject();
        json.put("Actif", month);
        json.put("count", nbrProcessDemandesInstancesParMois);
        listProcessDemandesInstancesByMois.add(json);


      }

      System.out.println("------");

      for (int i = 1; i <= currentDay; i++) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.DAY_OF_MONTH, i);
        calendar.set(Calendar.MONTH, currentMonth - 1);
        calendar.set(Calendar.YEAR, currentYear);
        calendar.set(Calendar.HOUR, 00);
        calendar.set(Calendar.MINUTE, 01);
        Date dateDeb = calendar.getTime();

        System.out.println((dateDeb.toString()));
        calendar.clear();
        calendar.set(Calendar.DAY_OF_MONTH, i);
        calendar.set(Calendar.MONTH, currentMonth - 1);
        calendar.set(Calendar.YEAR, currentYear);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        Date dateFin = calendar.getTime();

        System.out.println((dateFin.toString()));
        Long nbrProcessDemandesInstancesParJour = historyService.createHistoricProcessInstanceQuery().processDefinitionName(processDefinition.getName()).startedAfter(dateDeb).startedBefore(dateFin).count();

        calendar.set(Calendar.DAY_OF_MONTH, i);
        calendar.set(Calendar.MONTH, currentMonth - 1);
        calendar.set(Calendar.YEAR, currentYear);
        String day = calendar.getTime().toString();

        JSONObject json = new JSONObject();
        json.put("Actif", day);
        json.put("count", nbrProcessDemandesInstancesParJour);
        listProcessDemandesInstancesByJour.add(json);

      }


      Long nbrDemandesValide = historyService.createHistoricActivityInstanceQuery().activityType("endEvent").activityName("Validé").count();
      Long nbrDemandesAnnule = historyService.createHistoricActivityInstanceQuery().activityType("endEvent").activityName("Annulé").count();


      System.out.println("------");

      JSONObject jsonDemande = new JSONObject();
      jsonDemande.put("Type", processDefinition.getName());
      jsonDemande.put("nbrInstances", nbrProcessDemandeInstances);
      jsonDemande.put("nbrFinishedInstances", nbrProcessDemandesInstancesActive);
      jsonDemande.put("nbrNotFinishedInstances", nbrProcessDemandesInstancesNotActive);
      jsonDemande.put("listProcessDemandeInstancesByMois", listProcessDemandesInstancesByMois);
      jsonDemande.put("listProcessDemandeInstancesByJour", listProcessDemandesInstancesByJour);
      jsonDemande.put("listProcessDemandeInstancesAnnule", nbrDemandesAnnule);
      jsonDemande.put("listProcessDemandeInstancesValide", nbrDemandesValide);


      result.add(jsonDemande);
    }
    return result;
  }
    public List<Object> startProcessInstance(String name, String process) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("person", name);
        variables.put("initiator", name);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(process, variables);
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list().get(0);
        List<Object> result = new ArrayList<>();
        taskService.setAssignee(task.getId(), name);
        List<String> listParams = Filter(getListParams(task.getId()));
        result.add(task.getId());
        result.add(processInstance.getId());
        result.add(task.getName());
        result.add(listParams);
        return result;
    }


    public List<String> getListParamsV1(ProcessInstance processInstance){
        Process process = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId()).getMainProcess();

        // extracter la liste de tous les links de tous less activité from l'objet process
        List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class);

        // initialisation de la liste des params de notre retour
        List<String> listParam = new ArrayList<>();

        // parcourir la list des liens et tester si il'ont relier a notre activité courante et qu'il s'agit de type exclusive gateway
        for (SequenceFlow sequenceFlow : sequenceFlows) {

            if (sequenceFlow.getConditionExpression() != null) {

                // recuperation de condition de chaque lien
                String condition = sequenceFlow.getConditionExpression();

                // extracter le paramaitre de la condition

                String param = null;
                if(condition.indexOf("==")>=0) {
                    param = condition.substring(condition.indexOf("${") + 2, condition.indexOf("=="));
                }else if(condition.indexOf("!=")>=0) {
                    param = condition.substring(condition.indexOf("${") + 2, condition.indexOf("!="));
                }else{
                    param = condition.substring(condition.indexOf("${") + 2, condition.indexOf("}"));
                }
                // puis tester qu'il s'agit d'une nouvelle paramaitre diffrent de Decision
                if (!param.equals("Decision")) {

                    // remplir notre liste par ses paramaitres
                    listParam.add(param);
                }

            }


        }

        // envoyer notre liste finale comme retour
        return listParam;

    }

    public List<Object> startProcessInstanceRecieveEvent(String name, String process) {
//        String[] array = {"ficheProcessMM","ficheProcessBTL"};
//        for(String var : array) {
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("person", name);
            variables.put("initiator", name);
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(process, variables);
        //    FicheProcess ficheprocess = processRepository.findByProcessKey(processInstance.getProcessDefinitionKey()).get(0);


        //    Set<ConnexionsExterne> connexionExternes=ficheprocess.getConnexionExternes();



//            List<String> vars = Filter(getListParamsV1(processInstance));
//            System.out.println(vars.toString());
//            String ficheProcessName = (String) vars.get(0);
//            System.out.println(ficheProcessName);
       //     runtimeService.setVariable(processInstance.getProcessInstanceId(), "connexions", connexionExternes);


//        }
        return new ArrayList<>();
    }





    public Process getListProcess(String taskId) {
        Process process = getProcess(taskId);

        return process;
    }


    public String getRelativeProcess(String processtanceId) {
        String relativeProcess = "ProcessCA";


        return relativeProcess;

    }

    public List<Task> getTasks(String assignee) {
        List<Task> tasks = taskService.createTaskQuery().list();
        List<Task> tasks1 = taskService.createTaskQuery().taskAssignee(assignee).list();
        List<Task> tasks2 = taskService.createTaskQuery().taskCandidateOrAssigned(assignee).list();

        Long tasks3 = taskService.createTaskQuery().count();

        return taskService.createTaskQuery().taskCandidateGroup(assignee).list();
    }

    public List<String> getCandidateGroups(String taskId) {

        List<String> Groups = new ArrayList<String>();


        System.out.println(taskId);

            List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(taskId);

            for (IdentityLink identityLink : identityLinks) {
                String type = identityLink.getType();
                String groupId = identityLink.getGroupId();
                if (IdentityLinkType.CANDIDATE.equals(type) && groupId != null) {
                    Groups.add(groupId);
                }
            }

        return Groups;
    }

    public List<String> getCandidateUsers(String taskId) {

        List<String> User = new ArrayList<String>();



            List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(taskId);
          //  System.out.println("===ZZ"+identityLinks);
            for (IdentityLink identityLink : identityLinks) {
                String type = identityLink.getType();
                String userId = identityLink.getUserId();
                if (IdentityLinkType.CANDIDATE.equals(type) && userId != null) {
                    User.add(userId);
                }
            }


        return User;
    }

    public List getDescription(String taskId) {

        NativeTaskQuery query = taskService.createNativeTaskQuery()
                .sql("SELECT * FROM act_hi_taskinst where ID_='" + taskId + "'");
        Task task = query.list().get(0);
        String processInstanceId = task.getProcessInstanceId();

        NativeHistoricVariableInstanceQuery variableQuery = historyService.createNativeHistoricVariableInstanceQuery();
        variableQuery.sql("SELECT TEXT_ FROM act_hi_varinst WHERE PROC_INST_ID_='" + processInstanceId + "' AND NAME_ ='" + taskId + " :description" + "' ");
        HistoricVariableInstance variable = variableQuery.list().get(0);
        List list = new ArrayList<String>();
        list.add(variable.toString().substring(variable.toString().lastIndexOf('=') + 1, variable.toString().lastIndexOf(']')));
        return list;
    }

    public List<String> getAssignee(String taskId) {
        List<String> list = new ArrayList<>();

        NativeTaskQuery query = taskService.createNativeTaskQuery()
                .sql("SELECT * FROM act_hi_taskinst where ID_='" + taskId + "'");
        Task task = query.list().get(0);
        list.add(task.getAssignee());
        return (list);
    }

    public Date getdelaiTask(String taskId) {

        NativeTaskQuery query = taskService.createNativeTaskQuery()
                .sql("SELECT * FROM act_hi_taskinst where ID_='" + taskId + "'");
        Task task = query.list().get(0);
        String processInstanceId = task.getProcessInstanceId();
        List<String> his = new ArrayList<String>();
        String processDefId = getprocessInstanceOftask(taskId);
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefId);
       // System.out.println("ROC_DEF_ID_= " + processDefinition.getId());
        /*List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
        .processInstanceId(processInstanceId).list();
*/

        NativeHistoricTaskInstanceQuery taskQuery = historyService.createNativeHistoricTaskInstanceQuery();
//        taskQuery.sql("SELECT * FROM act_hi_taskinst WHERE PROC_DEF_ID_='"+processDefinition.getId()+"' and END_TIME_ IS  NOT NULL ORDER BY END_TIME_ desc");
        taskQuery.sql("SELECT * FROM act_hi_taskinst WHERE PROC_INST_ID_='" + processInstanceId + "' AND END_TIME_ IS NULL");


        HistoricTaskInstance tasks = taskQuery.list().get(0);
        Calendar c = Calendar.getInstance();
        c.setTime(tasks.getStartTime());

        c.add(Calendar.DATE, 2); //same with c.add(Calendar.DAY_OF_MONTH, 1);

        Date delaiDate = c.getTime();
        System.out.println("delai d activite");
        System.out.println(delaiDate);
        return delaiDate;

    }

    public void setAssignee(String taskId, String authentifier) {
        taskService.setAssignee(taskId, authentifier);
    }

    ///////////////////////////////////////////////////////////////////////
    public List<Task> getTasksAssingee(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    public Map<String, String> getGAut(String taskId) {
        Map<String, String> myMap = new HashMap<String, String>();
        Process processe = getProcess(taskId);
        List<String> CandidateStarterGroups = processe.getCandidateStarterGroups();
        myMap.put("AutProcess", CandidateStarterGroups.get(0));
        return myMap;
    }

    public Map<String, String> getGLect(String taskId) {
        Map<String, String> myMap = new HashMap<String, String>();
        Process processe = getProcess(taskId);
        List<String> CandidateStarterUsers = processe.getCandidateStarterUsers();
        myMap.put("LectProcess", CandidateStarterUsers.get(0));
        return myMap;
    }

    public List<String> historicaldata(String taskId) {
        NativeTaskQuery query = taskService.createNativeTaskQuery()
                .sql("SELECT * FROM act_hi_taskinst where ID_='" + taskId + "'");
        Task task = query.list().get(0);
        String processInstanceId = task.getProcessInstanceId();
        List<String> his = new ArrayList<String>();
        String processDefId = getprocessInstanceOftask(taskId);
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefId);
       // System.out.println("ROC_DEF_ID_= " + processDefinition.getId());

        NativeHistoricTaskInstanceQuery taskQuery = historyService.createNativeHistoricTaskInstanceQuery();
        taskQuery.sql("SELECT * FROM act_hi_taskinst WHERE PROC_INST_ID_='" + processInstanceId + "' AND END_TIME_ IS NOT NULL ORDER BY START_TIME_ ASC");
        List<HistoricTaskInstance> copietasks = new ArrayList<HistoricTaskInstance>();
        for (int d = 0; d < taskQuery.list().size(); d++)
            copietasks.add(taskQuery.list().get(d));


        List<HistoricTaskInstance> tasks = copietasks;

        for (HistoricTaskInstance taskk : tasks) {
            NativeHistoricVariableInstanceQuery historicQuery = historyService.createNativeHistoricVariableInstanceQuery();
            HistoricVariableInstance decisionVariable = historicQuery.sql("SELECT TEXT_ FROM act_hi_varinst WHERE NAME_ = '" + taskk.getId() + " :decision" + "'").list().get(0);
            String decision = String.valueOf(decisionVariable).substring(String.valueOf(decisionVariable).lastIndexOf("=") + 1, String.valueOf(decisionVariable).lastIndexOf("]"));
            HistoricVariableInstance descriptionVariable = historicQuery.sql("SELECT TEXT_ FROM act_hi_varinst WHERE NAME_ = '" + taskk.getId() + " :description" + "'").list().get(0);
            String description = String.valueOf(descriptionVariable).substring(String.valueOf(descriptionVariable).lastIndexOf("=") + 1, String.valueOf(descriptionVariable).lastIndexOf("]"));
            LocalDateTime today = LocalDateTime.now();
            String duree = getTime(LocalDateTime.ofInstant(taskk.getStartTime().toInstant(), ZoneId.systemDefault()), today);
            his.add(taskk.getName() + " * " + decision + " * " + taskk.getAssignee() + " * " + taskk.getStartTime() + " * " + taskk.getEndTime() + " * " + duree + " * " + description);
        }
        return his;
    }

    private String getTime(LocalDateTime dob, LocalDateTime now) {
        LocalDateTime today = LocalDateTime.of(now.getYear(),
                now.getMonthValue(), now.getDayOfMonth(), dob.getHour(), dob.getMinute(), dob.getSecond());
        Duration duration = Duration.between(today, now);

        long seconds = duration.getSeconds();

        long hours = seconds / SECONDS_PER_HOUR;
        long minutes = ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        long secs = (seconds % SECONDS_PER_MINUTE);

        return hours + "H " + minutes + "M " + secs + "S";
    }

    public List<String> Nextdata(String taskId) {
        List<String> ndata = new ArrayList<>();
        NativeTaskQuery query = taskService.createNativeTaskQuery()
                .sql("SELECT * FROM act_hi_taskinst where ID_='" + taskId + "'");
        Task task = query.list().get(0);
        String processInstanceId = task.getProcessInstanceId();
        List<String> his = new ArrayList<String>();
        try {
            String processDefId = getprocessInstanceOftask(taskId);
            ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefId);
          //  System.out.println("ROC_DEF_ID_= " + processDefinition.getId());

            NativeHistoricTaskInstanceQuery taskQuery = historyService.createNativeHistoricTaskInstanceQuery();
            taskQuery.sql("SELECT * FROM act_hi_taskinst WHERE PROC_INST_ID_='" + processInstanceId + "' ORDER BY START_TIME_ DESC");

            // taskQueryNext.sql("SELECT * FROM act_hi_taskinst WHERE PROC_INST_ID_='"+processInstanceId+"'");
            for (int i = 0; i < taskQuery.list().size(); i++) {
             ///   System.out.println("i " + i);
            ///    System.out.println(taskQuery.list().get(0));
            }
            String tasksNext = taskQuery.list().get(0).getName();
            ndata.add(tasksNext);
            ndata.add(taskQuery.list().get(0).getId());
            //   his.add(tasksNext);
            return ndata;
        } catch (Exception e) {
            List<String> data = new ArrayList<>();
            data.add("end");
            return data;
        }

    }

    public void taskDecision(String taskId, String authors, String decision, String readers, String description) {
        taskService.setVariable(taskId, taskId + " :description", description);
        taskService.setVariable(taskId, taskId + " :decision", decision);
        Map<String, Object> variable = new HashMap<String, Object>();

        variable.put("ResponsablePS", authors);
        variable.put("EnCopiePS", readers);
        taskService.complete(taskId, variable);
    }

    public void taskDecisionGW(String taskId, String decision, String authors, String readers, String description, String categorie) {

        taskService.setVariable(taskId, taskId + " :decision", decision);
        taskService.setVariable(taskId, taskId + " :description", description);

        Map<String, Object> variable = new HashMap<String, Object>();
        variable.put("Categorie", categorie);
        variable.put("Decision", decision);
        variable.put("ResponsablePS", authors);
        variable.put("EnCopiePS", readers);
        taskService.complete(taskId, variable);

    }

    public List<String> currentTask(String taskId) {
        NativeTaskQuery query = taskService.createNativeTaskQuery().sql("SELECT * FROM act_hi_taskinst where ID_='" + taskId + "'");
        Task task = query.list().get(0);
        System.out.println("==> " + task.getName());
        if (task != null) {
            String processInstanceId = task.getProcessInstanceId();
            List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
            Task taskkk = taskService.createTaskQuery().taskId(tasks.get(tasks.size() - 1).getId()).singleResult();
            System.out.println("task name: " + taskkk.getName());
            String id = taskkk.getId();
            String formKey = formService.getTaskFormData(id).getFormKey();
            List<String> data = new ArrayList<>();
            data.add(id);
            data.add(formKey);
            data.add(taskkk.getName());
            data.add(taskkk.getAssignee());


            return data;


        } else {
            List<String> data = new ArrayList<>();
            data.add("");
            return data;
        }
    }

    public List<String> firstTask(String taskId) {
        List listelast = new ArrayList<String>();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Process processe = getProcess(taskId);

        Collection<FlowElement> collection = processe.getFlowElements();
        ArrayList<FlowElement> flowElementArrayList = (ArrayList<FlowElement>) collection;
        System.out.println(flowElementArrayList.size());

        if (flowElementArrayList.get(2).getClass().getName().equals("org.flowable.bpmn.model.UserTask")) {
            listelast.add(flowElementArrayList.get(2).getName());
        }

        return listelast;
    }

    public List<String> lastTask(String taskId) {
        List listelast = new ArrayList<String>();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Process processe = getProcess(taskId);
        Collection<FlowElement> collection = processe.getFlowElements();
        ArrayList<FlowElement> flowElementArrayList = (ArrayList<FlowElement>) collection;

        for (int i = 0; i < flowElementArrayList.size(); i++) {

            if (flowElementArrayList.get(i).getClass().getName().equals("org.flowable.bpmn.model.EndEvent")) {
                listelast.add(flowElementArrayList.get(i).getName());

            }
        }

        return listelast;
    }

    public List<String> lastUserTask(String taskId) {
        List listelast = new ArrayList<String>();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Process processe = getProcess(taskId);

        Collection<FlowElement> collection = processe.getFlowElements();
        ArrayList<FlowElement> flowElementArrayList = (ArrayList<FlowElement>) collection;

        for (int i = 0; i < flowElementArrayList.size(); i++) {

            if (flowElementArrayList.get(i).getClass().getName().equals("org.flowable.bpmn.model.UserTask") & (
                    flowElementArrayList.get(i + 2).getClass().getName().equals("org.flowable.bpmn.model.EndEvent")))

            // if (flowElementArrayList.get(i).getClass().getName().equals("org.flowable.bpmn.model.EndEvent"))
            {
                listelast.add(flowElementArrayList.get(i).getName());
                break;
            } else {
                if (flowElementArrayList.get(i).getClass().getName().equals("org.flowable.bpmn.model.UserTask") & (
                        flowElementArrayList.get(i + 2).getClass().getName().equals("org.flowable.bpmn.model.ExclusiveGateway") &
                                flowElementArrayList.get(i + 5).getClass().getName() != null))

                    if (flowElementArrayList.get(i + 5).getClass().getName().equals("org.flowable.bpmn.model.EndEvent")) {
                        listelast.add(flowElementArrayList.get(i).getName());
                        break;
                    }
                if (flowElementArrayList.get(i + 4).getClass().getName().equals("org.flowable.bpmn.model.EndEvent")) {
                    listelast.add(flowElementArrayList.get(i).getName());
                    break;
                }

            }
        }

        return listelast;
    }

    public void takeTask(String userId, String id) {
        taskService.claim(id, userId);
    }

    public String getprocessInstanceOftask(String taskid) {
        NativeTaskQuery query = taskService.createNativeTaskQuery().sql("SELECT * FROM act_hi_taskinst where ID_='" + taskid + "'");
        Task task = query.list().get(0);
        String processInstanceId = task.getProcessInstanceId();
        ProcessInstance processInstance =
                runtimeService.createProcessInstanceQuery()
                        .processInstanceId(processInstanceId).singleResult();

        return processInstance.getProcessDefinitionId();

    }

    public List getGatewayDecision(String taskId) {

        List liste = new ArrayList<String>();
        NativeTaskQuery query = taskService.createNativeTaskQuery().sql("SELECT * FROM act_hi_taskinst WHERE ID_='" + taskId + "' ORDER BY START_TIME_ DESC");


        Task task = query.list().get(0);

       // System.out.println("Flowelement NAME    ====== " + task.getName());


        String task_name = query.list().get(0).getName();
        Process process = getProcess(taskId);

        List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class);

        List<String> decisionlist = new ArrayList<>();

        for (SequenceFlow sequenceFlow : sequenceFlows) {
            if (sequenceFlow.getSourceRef().equals(task.getTaskDefinitionKey())) {
                if (sequenceFlow.getTargetFlowElement().getClass().equals(UserTask.class) || sequenceFlow.getTargetFlowElement().getClass().equals(EndEvent.class) || sequenceFlow.getTargetFlowElement().getClass().equals(ServiceTask.class)) {
                    if (sequenceFlow.getName().indexOf("sys_") == -1) {
                        decisionlist.add(sequenceFlow.getName());
                    }
                } else {
                    for (SequenceFlow sequenceFlow2 : sequenceFlows) {
                        if (sequenceFlow2.getSourceRef().equals(sequenceFlow.getTargetFlowElement().getId())) {
                            if (sequenceFlow2.getName().indexOf("sys_") == -1) {
                                decisionlist.add(sequenceFlow2.getName());
                            }
                        }
                    }
                }

            }
        }

        return decisionlist;
    }

    public List<String> ExclusiveGateway(ExclusiveGateway exclusiveGateway, Process process) {
        List<String> flows = new ArrayList<String>();
        String conditionExpression;

        for (SequenceFlow flow : exclusiveGateway.getOutgoingFlows()) {
            conditionExpression = flow.getConditionExpression().substring(2, flow.getConditionExpression().indexOf("}"));
            String targetRef = flow.getTargetRef();

            FlowElement targetElement = process.getFlowElement(targetRef);

            if (targetElement.getClass().getName().equals("org.flowable.bpmn.model.EndEvent")) {
                flows.add(flow.getName() + "," + flow.getTargetRef() + "/" + conditionExpression + "#end");
            } else {
                System.out.println(flow.getName() + "," + flow.getTargetRef() + "/" + conditionExpression);
                flows.add(flow.getName() + "," + flow.getTargetRef() + "/" + conditionExpression);
            }
        }
        return flows;
    }

    public Process getProcess(String taskId) {
        Process process = repositoryService.getBpmnModel(getprocessInstanceOftask(taskId)).getMainProcess();
        return process;
    }

    public List<String> getHistoricProcessInstance(String processInstanceId) {

        List<String> his = new ArrayList<String>();

        NativeHistoricTaskInstanceQuery taskQuery = historyService.createNativeHistoricTaskInstanceQuery();
        taskQuery.sql("SELECT * FROM act_hi_taskinst WHERE PROC_INST_ID_='" + processInstanceId + "' AND END_TIME_ IS NOT NULL ORDER BY START_TIME_ ASC");
        List<HistoricTaskInstance> copietasks = new ArrayList<HistoricTaskInstance>();
        for (int d = 0; d < taskQuery.list().size(); d++)
            copietasks.add(taskQuery.list().get(d));


        List<HistoricTaskInstance> tasks = copietasks;

        for (HistoricTaskInstance taskk : tasks) {
            NativeHistoricVariableInstanceQuery historicQuery = historyService.createNativeHistoricVariableInstanceQuery();
            HistoricVariableInstance decisionVariable = historicQuery.sql("SELECT TEXT_ FROM act_hi_varinst WHERE NAME_ = '" + taskk.getId() + " :decision" + "'").list().get(0);
            String decision = String.valueOf(decisionVariable).substring(String.valueOf(decisionVariable).lastIndexOf("=") + 1, String.valueOf(decisionVariable).lastIndexOf("]"));
            HistoricVariableInstance descriptionVariable = historicQuery.sql("SELECT TEXT_ FROM act_hi_varinst WHERE NAME_ = '" + taskk.getId() + " :description" + "'").list().get(0);
            String description = String.valueOf(descriptionVariable).substring(String.valueOf(descriptionVariable).lastIndexOf("=") + 1, String.valueOf(descriptionVariable).lastIndexOf("]"));
            LocalDateTime today = LocalDateTime.now();
            String duree = getTime(LocalDateTime.ofInstant(taskk.getStartTime().toInstant(), ZoneId.systemDefault()), today);
            his.add(taskk.getName() + " * " + decision + " * " + taskk.getAssignee() + " * " + taskk.getStartTime() + " * " + taskk.getEndTime() + " * " + duree + " * " + description);
        }

        return his;
    }


    public List<Object> nextTask(String processInstanceId, String taskId, String decision, String description, String authentifier, List<String> Params) throws JSONException, ParseException, UnsupportedEncodingException {
        System.out.println("taskid::::"+taskId);
        taskService.setVariable(taskId, "authentifie", authentifier);

        //initialisation de notre resultat
        List<Object> result = new ArrayList<Object>();

        //ajouter l'assignee de l'activitée
        taskService.setAssignee(taskId, authentifier);
       // String decisionutf= new String(decision.getBytes("UTF-8"),"ISO-8859-1");
        //fermer l'activitée
        //decision=decisionutf ;
        finishTask(taskId, decision, description, Params);

        //récuperer l'id de la nouvelle activitée
        String taskid = nextTaskId(taskId);

        if (!taskid.equals("")) {

            //recuperer la liste des auteurs de la nouvelle activitée
            List<String> listAuthors = Filter(getCandidateGroups(taskid));

            //recuperer la liste des lecteurs de la nouvelle activitée
            List<String> listReaders = Filter(getCandidateUsers(taskid));

            //ajouter l'authetifier ou l'initiator au list des lecteurs
          //  listReaders.add(authentifier);

            //recuperer le nom de l'activitée
            String activityName = getActivityName(taskid);

            List<String> listParams = Filter(getListParams(taskId));

            //construction de notre retour de type ArrayJSON qui regroupe tous ces information
            result.add(taskid);
            result.add(listAuthors);
            result.add(listReaders);
            result.add(activityName);
            result.add(listParams);

        } else {
            //recuperer le nom de l'activitée
            String activityName = getEndActivityName(taskId);

            //recuperer l'id de l'activité
            String activityId = "EndEvent";

            //construction de notre retour de type ArrayJSON qui regroupe tous ces information
            result.add(activityId);
            result.add(activityName);
        }


        //envoyer notre result comme retour
        System.out.println("yyyy "+result);
        return result;

    }

    public String getEndActivityName(String taskId) {

        NativeTaskQuery query = taskService.createNativeTaskQuery().sql("SELECT * FROM act_hi_taskinst WHERE ID_='" + taskId + "' ORDER BY START_TIME_ DESC");

        Task task = query.list().get(0);

        String processDefId = task.getProcessDefinitionId();

        Process process = repositoryService.getBpmnModel(processDefId).getMainProcess();

        List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class);

        String activityName = "";

        for (SequenceFlow sequenceFlow : sequenceFlows) {
            if (sequenceFlow.getSourceRef().equals(task.getTaskDefinitionKey())) {
                if ((sequenceFlow.getSourceFlowElement().getClass().equals(TaskService.class) || sequenceFlow.getSourceFlowElement().getClass().equals(UserTask.class)) && sequenceFlow.getTargetFlowElement().getClass().equals(EndEvent.class)) {
                    activityName = sequenceFlow.getTargetFlowElement().getName();
                } else {
                    for (SequenceFlow sequenceFlow2 : sequenceFlows) {
                        if (sequenceFlow2.getSourceRef().equals(sequenceFlow.getTargetFlowElement().getId()) && sequenceFlow2.getTargetFlowElement().getClass().equals(EndEvent.class)) {

                            activityName = sequenceFlow2.getTargetFlowElement().getName();
                        }
                    }
                }

            }
        }

        return activityName;
    }

    //methode pour fermer l'activié actuelle
    public void finishTask(String taskId, String decision, String description, List<String> Params) throws JSONException, ParseException {
        taskService.setVariable(taskId, taskId + " :decision", decision);
        taskService.setVariable(taskId, taskId + " :description", description);
//        System.out.println("===>"+decision);
//        System.out.println("===>"+taskId);
//        System.out.println("===>"+Params);
        Map<String, Object> variable = new HashMap<String, Object>();
        variable.put("Decision", decision);
        for (String param : Params) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(param);
            for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext(); ) {
                String key = (String) iterator.next();
                variable.put(key, jsonObject.get(key));
            }
        }
        taskService.complete(taskId, variable);
    }

    // methode pour recuperer l'id de la nouvelle activité
    public String nextTaskId(String taskId) {
        List<String> ndata = new ArrayList<>();
        NativeTaskQuery query = taskService.createNativeTaskQuery().sql("SELECT * FROM act_hi_taskinst where ID_='" + taskId + "'");

        Task task = query.list().get(0);
        String processInstanceId = task.getProcessInstanceId();
        try {
            String processDefId = getprocessInstanceOftask(taskId);
            ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefId);
            //System.out.println("ROC_DEF_ID_= " + processDefinition.getId());

            Task Currenttask = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);

            return Currenttask.getId();


        } catch (Exception e) {
            return "";
        }

    }

    // methode pour recuperer le nom de l'activité
    public String getActivityName(String taskId) {
        try {
            NativeTaskQuery query = taskService.createNativeTaskQuery().sql("SELECT * FROM act_hi_taskinst WHERE ID_='" + taskId + "' ORDER BY START_TIME_ DESC");
            Task task = query.list().get(0);
            String task_name = query.list().get(0).getName();
            return task_name;
        } catch (Exception e) {
            List<String> data = new ArrayList<>();
            return "EndEvent";

        }
    }

    //methode pour filtrer une liste des auteur ou des lecteurs et tester sont existance avant les enregistrer dans l'activiter
    public List<String> Filter(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if ((list.get(i).equals(list.get(j))) && (i != j)) {
                    list.remove(i);
                }
            }
        }
        System.out.println(list);
        return list;
    }

    //ajouter la list des auteurs et des lecteurs dans la liste des condidates groups et users dans lidentityLinks
    public void setCandidates(String taskid, List<String> authors, List<String> readers) {
        for (String author : authors) {
            taskService.deleteCandidateGroup(taskid, author);
            taskService.addCandidateGroup(taskid, author);
        }
        for (String reader : readers) {
            taskService.deleteCandidateGroup(taskid, reader);
            taskService.addCandidateUser(taskid, reader);
        }

    }


    public List<JSONObject> getListVersionsProcess(String key) {

        List<JSONObject> listProcessActive = new ArrayList<>();

        List<ProcessDefinition> listActiveProcess = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).list();

        for (ProcessDefinition activeDefinition : listActiveProcess) {
            try {
                System.out.println(activeDefinition.getDeploymentId());
                Deployment Deployement = repositoryService.createDeploymentQuery().deploymentId(activeDefinition.getDeploymentId()).list().get(0);
                Long countProcInst =  historyService.createHistoricProcessInstanceQuery().processDefinitionId(activeDefinition.getId()).count();
                Long countProcInstEnCours =  historyService.createHistoricProcessInstanceQuery().processDefinitionId(activeDefinition.getId()).unfinished().count();
                Long countProcInstClosed =  historyService.createHistoricProcessInstanceQuery().processDefinitionId(activeDefinition.getId()).finished().count();

                JSONObject jsonResult = new JSONObject();
                jsonResult.put("id", activeDefinition.getId());
                jsonResult.put("name", activeDefinition.getName());
                jsonResult.put("key", activeDefinition.getKey());
                jsonResult.put("deployement_time", Deployement.getDeploymentTime());
                jsonResult.put("totalProcesses", countProcInst);
                jsonResult.put("nbreEnCours", countProcInstEnCours);
                jsonResult.put("nbreFermer", countProcInstClosed);
                jsonResult.put("version", activeDefinition.getVersion());


                listProcessActive.add(jsonResult);
            }catch (Exception e){
                System.out.println("Erreur process");
            }
        }

        return listProcessActive;
    }


    public List<JSONObject> getListFinalProcess() {


        List<JSONObject> listProcessActive = new ArrayList<>();

        List<ProcessDefinition> listActiveProcess = repositoryService.createProcessDefinitionQuery().latestVersion().list();

        for (ProcessDefinition activeDefinition : listActiveProcess) {
            try {
                System.out.println(activeDefinition.getDeploymentId());
                Deployment Deployement = repositoryService.createDeploymentQuery().deploymentId(activeDefinition.getDeploymentId()).list().get(0);
                Long countProcInst =  historyService.createHistoricProcessInstanceQuery().processDefinitionId(activeDefinition.getId()).count();
                Long countProcInstEnCours =  historyService.createHistoricProcessInstanceQuery().processDefinitionId(activeDefinition.getId()).unfinished().count();
                Long countProcInstClosed =  historyService.createHistoricProcessInstanceQuery().processDefinitionId(activeDefinition.getId()).finished().count();

                JSONObject jsonResult = new JSONObject();
                jsonResult.put("id", activeDefinition.getId());
                jsonResult.put("name", activeDefinition.getName());
                jsonResult.put("key", activeDefinition.getKey());
                jsonResult.put("deployement_time", Deployement.getDeploymentTime());
                jsonResult.put("totalProcesses", countProcInst);
                jsonResult.put("nbreEnCours", countProcInstEnCours);
                jsonResult.put("nbreFermer", countProcInstClosed);
                jsonResult.put("version", activeDefinition.getVersion());


                listProcessActive.add(jsonResult);
            }catch (Exception e){
                System.out.println("Erreur process");
            }
        }

        return listProcessActive;
    }

    //recuperer la liste des paramaitres necessaire pour router a la prochain activité
    public List getListParams(String taskId) {

        // recuperation de l'activité actuelle avec un id prenand en paramaitre
        NativeTaskQuery query = taskService.createNativeTaskQuery().sql("SELECT * FROM act_hi_taskinst WHERE ID_='" + taskId + "' ORDER BY START_TIME_ DESC");
        Task task = query.list().get(0);

        // extracter du nom de l'activité
        String task_name = query.list().get(0).getName();

        // recuperation du l'objet process avec l'Id de l'activité
        Process process = getProcess(taskId);

        // extracter la liste de tous les links de tous less activité from l'objet process
        List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class);

        // initialisation de la liste des params de notre retour
        List<String> listParam = new ArrayList<>();

        // parcourir la list des liens et tester si il'ont relier a notre activité courante et qu'il s'agit de type exclusive gateway
        for (SequenceFlow sequenceFlow : sequenceFlows) {

            if (sequenceFlow.getConditionExpression() != null) {

                // recuperation de condition de chaque lien
                String condition = sequenceFlow.getConditionExpression();

                // extracter le paramaitre de la condition

                String param = null;
                if (condition.indexOf("==") >= 0) {
                    param = condition.substring(condition.indexOf("${") + 2, condition.indexOf("=="));
                }
                if (condition.indexOf("!=") >= 0) {
                    param = condition.substring(condition.indexOf("${") + 2, condition.indexOf("!="));
                }
                // puis tester qu'il s'agit d'une nouvelle paramaitre diffrent de Decision
                if (!param.equals("Decision")) {

                    // remplir notre liste par ses paramaitres
                    listParam.add(param);
                }

            }


        }

        // envoyer notre liste finale comme retour
        return listParam;
    }

    public List getDerivatorsListParams(String taskId) {

        // recuperation de l'activité actuelle avec un id prenand en paramaitre
        NativeTaskQuery query = taskService.createNativeTaskQuery().sql("SELECT * FROM act_hi_taskinst WHERE ID_='" + taskId + "' ORDER BY START_TIME_ DESC");
        Task task = query.list().get(0);

        // extracter du nom de l'activité
        String task_name = query.list().get(0).getName();

        // recuperation du l'objet process avec l'Id de l'activité
        Process process = getProcess(taskId);

        // extracter la liste de tous les links de tous less activité from l'objet process
        List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class);

        // initialisation de la liste des params de notre retour
        List<String> listParam = new ArrayList<>();

        // parcourir la list des liens et tester si il'ont relier a notre activité courante et qu'il s'agit de type exclusive gateway
        for (SequenceFlow sequenceFlow : sequenceFlows) {
            if (sequenceFlow.getSourceRef().equals(task.getTaskDefinitionKey())) {

                // recuperation de l'activité courante
                FlowElement currentTask = sequenceFlow.getTargetFlowElement();
                if (currentTask.getClass().equals(ExclusiveGateway.class)) {

                    // parcourir la list des liens et tester si il'ont relier au prochain activité et qu'il s'agit de type exclusive gateway
                    for (SequenceFlow sequenceFlow2 : sequenceFlows) {
                        if (sequenceFlow2.getSourceRef().equals(currentTask.getId())) {

                            //boucle les meme traitement et incrémenter a chaque fois les deux variabler currentTask et nextTask on testant qu'il son de type exclusive gateway
                            FlowElement nextTask = sequenceFlow2.getTargetFlowElement();
                            if (nextTask.getClass().equals(ExclusiveGateway.class)) {
                                while (nextTask.getClass().equals(ExclusiveGateway.class)) {

                                    // parcourir la list des liens et tester si il'ont relier au prochain activité et qu'il s'agit de type exclusive gateway puis extracter les decision on l'ajoutant a notre resultat
                                    for (SequenceFlow sequenceFlow3 : sequenceFlows) {
                                        if (sequenceFlow3.getSourceRef().equals(nextTask.getId())) {
                                            if (currentTask.getClass().equals(ExclusiveGateway.class)) {
                                                if (nextTask.getClass().equals(ExclusiveGateway.class)) {
                                                    if (sequenceFlow3.getConditionExpression() != null) {

                                                        // recuperation de condition de chaque lien
                                                        String condition = sequenceFlow3.getConditionExpression();

                                                        // extracter le paramaitre de la condition
                                                        String param = condition.substring(condition.indexOf("${") + 2, condition.indexOf("=="));

                                                        // puis tester qu'il s'agit d'une nouvelle paramaitre diffrent de Decision
                                                        if (!param.equals("Decision")) {
                                                            // remplir notre liste par ses paramaitres
                                                            listParam.add(param);
                                                        }

                                                    }
                                                }
                                            }
                                            currentTask = nextTask;
                                            nextTask = sequenceFlow3.getTargetFlowElement();
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // envoyer notre liste finale comme retour
        return listParam;
    }


    public Boolean checkProcessToUndeploy(String processDefinitionId) throws IOException {
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().processDefinitionId(processDefinitionId).list();
        if(processInstances.size()>0){
            return false;
        }else{
            return true;
        }
    }
    public List<JSONObject> getActivitiesByProcesKey(String processId) throws FileNotFoundException {

        List<JSONObject> global = new ArrayList<>();
        ProcessDefinitionQuery listprocess = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processId);


        BpmnModel bpmnModel = repositoryService.getBpmnModel(listprocess.list().get(0).getId());

        Process process = bpmnModel.getProcessById(processId);

        List<UserTask> activities = process.findFlowElementsOfType(UserTask.class);


        for (UserTask activity : activities) {

            JSONObject temp = new JSONObject();
            temp.put("name",activity.getName());
            temp.put("authors",activity.getCandidateGroups());
            temp.put("readers",activity.getCandidateUsers());
            temp.put("key",activity.getId());

            global.add(temp);

        }

        return global;
    }

    public List<JSONObject> getActivityByProcesKey(String processId, String activite) throws FileNotFoundException {

        List<JSONObject> global = new ArrayList<>();
        ProcessDefinitionQuery listprocess = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processId);


        BpmnModel bpmnModel = repositoryService.getBpmnModel(listprocess.list().get(0).getId());

        Process process = bpmnModel.getProcessById(processId);

        List<UserTask> activities = process.findFlowElementsOfType(UserTask.class);


        for (UserTask activity : activities) {
            if(activite.equals(activite)) {
                JSONObject temp = new JSONObject();
                temp.put("name", activity.getName());
                temp.put("authors", activity.getCandidateGroups());
                temp.put("readers", activity.getCandidateUsers());
                temp.put("key", activity.getId());

                global.add(temp);
            }

        }

        return global;
    }

    public ArrayList getAllActivitiesByProcesKey(String processId) throws FileNotFoundException {

        ArrayList global = new ArrayList<>();
        ProcessDefinitionQuery listprocess = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processId);


        BpmnModel bpmnModel = repositoryService.getBpmnModel(listprocess.list().get(0).getId());

        Process process = bpmnModel.getProcessById(processId);

        List<UserTask> activities = process.findFlowElementsOfType(UserTask.class);


        for (UserTask activity : activities) {

            if(global.indexOf(activity.getName())==-1)
             global.add(activity.getName());

        }

        return global;
    }

    public Boolean undeployeProcess(String processDefinitionId, Boolean force) throws IOException {

        try {
            String deployementId =  repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).list().get(0).getDeploymentId();
            repositoryService.deleteDeployment(deployementId, force);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public List<String> get_activities_by_process(String processDefinitionKey) {
        List<String> activitys = new ArrayList<>();

        List liste = new ArrayList<String>();

        ProcessDefinitionQuery listprocess = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey);


        BpmnModel bpmnModel = repositoryService.getBpmnModel(listprocess.list().get(0).getId());

        Process process = bpmnModel.getProcessById(processDefinitionKey);

        List<UserTask> tasks = process.findFlowElementsOfType(UserTask.class);

        for(UserTask userTask : tasks){
            activitys.add(userTask.getName());
        }

        return  activitys;
    }

    public List<String> calculate_decisions(String[] activitys,String taskId) {

        List<String> decisions = new ArrayList<>();

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        String processDefId = task.getProcessDefinitionId();

        Process process = repositoryService.getBpmnModel(processDefId).getMainProcess();

        List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class);

        String taskDefinitionKey = task.getTaskDefinitionKey();
        for(String activity : activitys) {
            for (SequenceFlow sequenceFlow : sequenceFlows) {
                if (sequenceFlow.getSourceRef().equals(taskDefinitionKey)) {
                    if ((sequenceFlow.getTargetFlowElement().getClass().equals(TaskService.class) || sequenceFlow.getSourceFlowElement().getClass().equals(UserTask.class)) && sequenceFlow.getTargetFlowElement().getClass().equals(EndEvent.class)) {
                        if (sequenceFlow.getTargetFlowElement().getName().equals(activity)) {
                            if(sequenceFlow.getName().indexOf("sys_") == -1) {
                                decisions.add(sequenceFlow.getName());
                                taskDefinitionKey = sequenceFlow.getTargetRef();
                            }
                        }
                    }else if(sequenceFlow.getTargetFlowElement().getClass().equals(ExclusiveGateway.class)){
                        for (SequenceFlow sequenceFlow2 : sequenceFlows) {
                            if (sequenceFlow2.getSourceRef().equals(sequenceFlow.getTargetFlowElement().getId())) {
                                if (sequenceFlow2.getTargetFlowElement().getName().equals(activity)) {
                                    if(sequenceFlow2.getName().indexOf("sys_") == -1) {
                                        decisions.add(sequenceFlow2.getName());
                                        taskDefinitionKey = sequenceFlow2.getTargetRef();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return decisions;
    }

    public List getGatewayDecisionWithoutTaskId(String processId) throws FileNotFoundException {

        List liste = new ArrayList<String>();

        ProcessDefinitionQuery listprocess = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processId);


        BpmnModel bpmnModel = repositoryService.getBpmnModel(listprocess.list().get(0).getId());

        Process process = bpmnModel.getProcessById(processId);

        List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class);

        List<String> decisionlist = new ArrayList<>();


        for (SequenceFlow sequenceFlow : sequenceFlows) {
            for (SequenceFlow sequenceFlow1 : sequenceFlows) {
                if (sequenceFlow1.getTargetFlowElement().getId().equals(sequenceFlow.getSourceFlowElement().getId()) && sequenceFlow1.getSourceFlowElement().getClass().equals(StartEvent.class)) {
                    if (sequenceFlow.getTargetFlowElement().getClass().equals(UserTask.class) || sequenceFlow.getTargetFlowElement().getClass().equals(EndEvent.class) || sequenceFlow.getTargetFlowElement().getClass().equals(ServiceTask.class)) {
                        if (sequenceFlow.getName().indexOf("sys_") == -1) {
                            decisionlist.add(sequenceFlow.getName());
                        }
                    } else {
                        for (SequenceFlow sequenceFlow2 : sequenceFlows) {
                            if (sequenceFlow2.getSourceRef().equals(sequenceFlow.getTargetFlowElement().getId())) {
                                if (sequenceFlow2.getName().indexOf("sys_") == -1) {
                                    decisionlist.add(sequenceFlow2.getName());
                                }
                            }
                        }
                    }
                }

            }
        }

        return decisionlist;
    }

    public List getFirstActivityNameWithoutProcessInstance(String processId) throws FileNotFoundException {

        ProcessDefinitionQuery listprocess = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processId);


        BpmnModel bpmnModel = repositoryService.getBpmnModel(listprocess.list().get(0).getId());

        Process process = bpmnModel.getProcessById(processId);

        List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class);

        List<String> activity = new ArrayList<>();


        for (SequenceFlow sequenceFlow : sequenceFlows) {
            if (sequenceFlow.getSourceFlowElement().getClass().equals(StartEvent.class)) {
                activity.add(sequenceFlow.getTargetFlowElement().getName());
            }
        }

        return activity;
    }


    public List<Object> deployeProcessold(@RequestBody MultipartFile BpmnFile) throws IOException, URISyntaxException {
        List<Object> result = new ArrayList<>();

        try {
            URI uriRessource = getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
            System.out.println(uriRessource.getPath());
            System.out.println(BpmnFile.getOriginalFilename());

            File initialFile = new File(uriRessource.getPath() + "processes/" + BpmnFile.getOriginalFilename());
            if (initialFile.exists()) {

                initialFile = new File(uriRessource.getPath() + "processes/" + BpmnFile.getOriginalFilename().substring(0, BpmnFile.getOriginalFilename().indexOf(".bpmn20")) + "(1)" + ".bpmn20.xml");

                //Definir le contenu du nouveau fichier bpmn
                FileOutputStream DocOut = new FileOutputStream(initialFile);
                DocOut.write(BpmnFile.getBytes());
                DocOut.close();

                //Deployer le processus a partir du fichier bpmn
                Deployment deployment = repositoryService.createDeployment()
                        .addClasspathResource("processes/" + initialFile.getName())
                        .deploy();
                System.out.println(deployment.getName());

            } else {

                //Creation d'un nouveau fichier bpmn
                initialFile.createNewFile();

                //Definir le contenu du nouveau fichier bpmn
                FileOutputStream DocOut = new FileOutputStream(initialFile);
                DocOut.write(BpmnFile.getBytes());
                DocOut.close();

                //Deployer le processus a partir du fichier bpmn
                Deployment deployment = repositoryService.createDeployment()
                        .addClasspathResource("processes/" + BpmnFile.getOriginalFilename())
                        .deploy();
                System.out.println(deployment.getName());

                result.add(true);
                result.add("succes de deployement du processus");
            }
        } catch (Exception e) {
            result.add(false);
            result.add("echec de deployement du processus");
            result.add(e.getMessage());
        }
        return result;
    }



    public List<JSONObject> getListProcess() throws FileNotFoundException {


        List<JSONObject> listProcessActive = new ArrayList<>();

        List<ProcessDefinition> listActiveProcess = repositoryService.createProcessDefinitionQuery().latestVersion().list();

        for (ProcessDefinition activeDefinition : listActiveProcess) {
            System.out.println(activeDefinition.getKey());
            System.out.println(activeDefinition.getId());
            Deployment Deployement = repositoryService.createDeploymentQuery().deploymentId(activeDefinition.getDeploymentId()).list().get(0);
            Process Mpdel = repositoryService.getBpmnModel(activeDefinition.getId()).getMainProcess();


            List<JSONObject> listActivityProcess = new ArrayList<>();

            JSONObject jsonResult = new JSONObject();
            jsonResult.put("id", activeDefinition.getId());
            jsonResult.put("name", activeDefinition.getName());
            jsonResult.put("key", activeDefinition.getKey());
            jsonResult.put("deployement_time", Deployement.getDeploymentTime());
            jsonResult.put("condidate_starter_groups", Mpdel.getCandidateStarterGroups());
            jsonResult.put("condidate_starter_users", Mpdel.getCandidateStarterUsers());
            jsonResult.put("condidate_starter_users", Mpdel.getCandidateStarterUsers());
            jsonResult.put("version", activeDefinition.getVersion());


            List<ProcessDefinition> listUnactiveProcess = repositoryService.createProcessDefinitionQuery().processDefinitionKey(activeDefinition.getKey()).list();
            for (ProcessDefinition unactiveDefinition : listUnactiveProcess) {
                Deployment unactiveDeployement = repositoryService.createDeploymentQuery().deploymentId(unactiveDefinition.getDeploymentId()).list().get(0);
                Process unactiveMpdel = repositoryService.getBpmnModel(unactiveDefinition.getId()).getMainProcess();
                System.out.println(unactiveDefinition.getKey());
                JSONObject jsonUnactiveProcesses = new JSONObject();
                jsonUnactiveProcesses.put("id", unactiveDefinition.getId());
                jsonUnactiveProcesses.put("name", unactiveDefinition.getName());
                jsonUnactiveProcesses.put("key", unactiveDefinition.getKey());
                jsonUnactiveProcesses.put("deployement_time", unactiveDeployement.getDeploymentTime());
                jsonUnactiveProcesses.put("condidate_starter_groups", unactiveMpdel.getCandidateStarterGroups());
                jsonUnactiveProcesses.put("condidate_starter_users", unactiveMpdel.getCandidateStarterUsers());
                jsonUnactiveProcesses.put("condidate_starter_users", unactiveMpdel.getCandidateStarterUsers());
                jsonUnactiveProcesses.put("version", unactiveDefinition.getVersion());
                listActivityProcess.add(jsonUnactiveProcesses);

            }
            jsonResult.put("activitys", listActivityProcess);

//            List<UserTask> listActivitys = Mpdel.findFlowElementsOfType(UserTask.class);
//            for (UserTask userTask : listActivitys) {
//                JSONObject jsonActivitys = new JSONObject();
//                jsonActivitys.put("id",userTask.getId());
//                jsonActivitys.put("name",userTask.getName());
//                jsonActivitys.put("assigne",userTask.getAssignee());
//                jsonActivitys.put("condidate_groups",userTask.getCandidateGroups());
//                jsonActivitys.put("condidate_users",userTask.getCandidateUsers());
//                listActivityProcess.add(jsonActivitys);
//            }
//            jsonResult.put("activitys" , listActivityProcess);
            listProcessActive.add(jsonResult);
        }
return listProcessActive;

    }

    //    ***********************************************Adpatation Workflow************************************************



    public List<JSONObject> getListProcessNonDeploye() throws URISyntaxException, IOException {

        URI uriRessource = getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
        new File(uriRessource.getPath() + "processes_undeployed/").mkdirs();
        File directory = new File(uriRessource.getPath() + "processes_undeployed");


        List<JSONObject> listProcessNonDeploye = new ArrayList<>();
        // get all the files from a directory
        File[] fList = directory.listFiles();

        for (File file : fList) {
            if (file.isFile()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",file.getName());
                jsonObject.put("name", file.getName());
                String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

                Document doc = convertStringToXMLDocument(content);
                NodeList nodeList = doc.getElementsByTagName("process");
                Node node  = nodeList.item(0);
                NamedNodeMap Attribute = node.getAttributes();

                jsonObject.put("id", Attribute.getNamedItem("id").getNodeValue());
                jsonObject.put("name", Attribute.getNamedItem("name").getNodeValue());
                jsonObject.put("key", Attribute.getNamedItem("id").getNodeValue());
                BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                jsonObject.put("createdDate", attr.creationTime().toString());
                jsonObject.put("updatedDate", attr.lastModifiedTime().toString());
                jsonObject.put("path", file.getPath());

                listProcessNonDeploye.add(jsonObject);

            }
        }

        return listProcessNonDeploye;


    }

    public List<JSONObject> getListProcesses() {

        List<JSONObject> listProcessActive = new ArrayList<>();

        List<ProcessDefinition> listActiveProcess = repositoryService.createProcessDefinitionQuery().latestVersion().list();

        for (ProcessDefinition activeDefinition : listActiveProcess) {
            try {

                Deployment Deployement = repositoryService.createDeploymentQuery().deploymentId(activeDefinition.getDeploymentId()).list().get(0);
                Process Mpdel = repositoryService.getBpmnModel(activeDefinition.getId()).getMainProcess();
                Long countProcInst =  historyService.createHistoricProcessInstanceQuery().processDefinitionId(activeDefinition.getId()).count();
                Long countProcInstEnCours =  historyService.createHistoricProcessInstanceQuery().processDefinitionId(activeDefinition.getId()).unfinished().count();
                Long countProcInstClosed =  historyService.createHistoricProcessInstanceQuery().processDefinitionId(activeDefinition.getId()).finished().count();
                List<JSONObject> listActivityProcess = new ArrayList<>();

                JSONObject jsonResult = new JSONObject();
                jsonResult.put("id", activeDefinition.getId());
                jsonResult.put("name", activeDefinition.getName());
                jsonResult.put("key", activeDefinition.getKey());
                jsonResult.put("deployement_time", Deployement.getDeploymentTime());
                jsonResult.put("condidate_starter_groups", Mpdel.getCandidateStarterGroups());
                jsonResult.put("condidate_starter_users", Mpdel.getCandidateStarterUsers());
                jsonResult.put("condidate_starter_users", Mpdel.getCandidateStarterUsers());
                jsonResult.put("totalProcesses", countProcInst);
                jsonResult.put("nbreEnCours", countProcInstEnCours);
                jsonResult.put("nbreFermer", countProcInstClosed);
                jsonResult.put("version", activeDefinition.getVersion());


                List<ProcessDefinition> listUnactiveProcess = repositoryService.createProcessDefinitionQuery().processDefinitionKey(activeDefinition.getKey()).orderByProcessDefinitionVersion().desc().list();
                ProcessDefinition unactiveDefinition = listUnactiveProcess.get(0);
                Deployment unactiveDeployement = repositoryService.createDeploymentQuery().deploymentId(unactiveDefinition.getDeploymentId()).list().get(0);
                Process unactiveMpdel = repositoryService.getBpmnModel(unactiveDefinition.getId()).getMainProcess();

                JSONObject jsonUnactiveProcesses = new JSONObject();
                jsonUnactiveProcesses.put("id", unactiveDefinition.getId());
                jsonUnactiveProcesses.put("name", unactiveDefinition.getName());
                jsonUnactiveProcesses.put("key", unactiveDefinition.getKey());
                jsonUnactiveProcesses.put("deployement_time", unactiveDeployement.getDeploymentTime());
                jsonUnactiveProcesses.put("condidate_starter_groups", unactiveMpdel.getCandidateStarterGroups());
                jsonUnactiveProcesses.put("condidate_starter_users", unactiveMpdel.getCandidateStarterUsers());
                jsonUnactiveProcesses.put("condidate_starter_users", unactiveMpdel.getCandidateStarterUsers());
                jsonUnactiveProcesses.put("version", unactiveDefinition.getVersion());
                listActivityProcess.add(jsonUnactiveProcesses);

                jsonResult.put("activitys", listActivityProcess);

                listProcessActive.add(jsonResult);
            }catch (Exception e){
                System.out.println("Erreur process");
            }
        }

        return listProcessActive;
    }


    public Boolean saveBpmnModel(String xml, String name) throws IOException {
        try {
            URI uriRessource = getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
            new File(uriRessource.getPath() + "processes_undeployed/").mkdirs();
            File initialFile = new File(uriRessource.getPath() + "processes_undeployed/" + name);
            if (initialFile.exists()) {
                if(name.contains("-v")){
                    initialFile = new File(uriRessource.getPath() + "processes_undeployed/" + name.substring(0, name.indexOf("-v")) + "-v"+  (Integer.parseInt(name.substring(name.indexOf("-v")+2,name.indexOf(".bpmn20.xml")))+1) + ".bpmn20.xml");

                }else if(name.contains("-V")){
                    initialFile = new File(uriRessource.getPath() + "processes_undeployed/" + name.substring(0, name.indexOf("-V")) + "-v"+  (Integer.parseInt(name.substring(name.indexOf("-V")+2,name.indexOf(".bpmn20.xml")))+1) + ".bpmn20.xml");
                }else{
                    initialFile = new File(uriRessource.getPath() + "processes_undeployed/" + name.substring(0, name.indexOf(".bpmn20.xml")) + "-v1" + ".bpmn20.xml");
                }
            }
            initialFile.createNewFile();
            //Definir le contenu du nouveau fichier bpmn
            FileOutputStream DocOut = new FileOutputStream(initialFile);
            DocOut.write(xml.getBytes());
            DocOut.close();

            return true;

        }catch (Exception e){
            return false;
        }


    }


    public List<String> getListParamsV1_1(ProcessInstance processInstance){
        Process process = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId()).getMainProcess();

        // extracter la liste de tous les links de tous less activité from l'objet process
        List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class);

        // initialisation de la liste des params de notre retour
        List<String> listParam = new ArrayList<>();

        // parcourir la list des liens et tester si il'ont relier a notre activité courante et qu'il s'agit de type exclusive gateway
        for (SequenceFlow sequenceFlow : sequenceFlows) {

            if (sequenceFlow.getConditionExpression() != null) {

                // recuperation de condition de chaque lien
                String condition = sequenceFlow.getConditionExpression();

                // extracter le paramaitre de la condition

                String param = null;
                if(condition.indexOf("==")>=0) {
                    param = condition.substring(condition.indexOf("${") + 2, condition.indexOf("=="));
                }else if(condition.indexOf("!=")>=0) {
                    param = condition.substring(condition.indexOf("${") + 2, condition.indexOf("!="));
                }else{
                    param = condition.substring(condition.indexOf("${") + 2, condition.indexOf("}"));
                }
                // puis tester qu'il s'agit d'une nouvelle paramaitre diffrent de Decision
                if (!param.equals("Decision")) {

                    // remplir notre liste par ses paramaitres
                    listParam.add(param);
                }

            }


        }

        // envoyer notre liste finale comme retour
        return listParam;

    }


    public Boolean deployeBpmnModel(String xml, String name) throws IOException {
        try {
            URI uriRessource = getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
            File initialFile = new File(uriRessource.getPath() + "processes/" + name);
            if (initialFile.exists()) {
                if(name.contains("-v")){
                    initialFile = new File(uriRessource.getPath() + "processes/" + name.substring(0, name.indexOf("-v")) + "-v"+  (Integer.parseInt(name.substring(name.indexOf("-v")+2,name.indexOf(".bpmn20.xml")))+1) + ".bpmn20.xml");

                }else if(name.contains("-V")){
                    initialFile = new File(uriRessource.getPath() + "processes/" + name.substring(0, name.indexOf("-V")) + "-v"+  (Integer.parseInt(name.substring(name.indexOf("-V")+2,name.indexOf(".bpmn20.xml")))+1) + ".bpmn20.xml");
                }else{
                    initialFile = new File(uriRessource.getPath() + "processes/" + name.substring(0, name.indexOf(".bpmn20.xml")) + "-v1" + ".bpmn20.xml");
                }
            }
            initialFile.createNewFile();
            //Definir le contenu du nouveau fichier bpmn
            FileOutputStream DocOut = new FileOutputStream(initialFile);
            DocOut.write(xml.getBytes());
            DocOut.close();

            //Deployer le processus a partir du fichier bpmn
            Deployment deployment = repositoryService.createDeployment()
                    .addClasspathResource("processes/" + initialFile.getName())
                    .deploy();
            System.out.println(deployment.getName());
            return true;

        }catch (Exception e){
            return false;
        }


    }




    public String getBpmnModelXmlByPath(String path) throws IOException {
        return  StreamUtils.copyToString(new FileInputStream(new File(path)), StandardCharsets.UTF_8);

    }

    public String getBpmnModelXml(String processDefinitionId) throws IOException {
        return  StreamUtils.copyToString(repositoryService.getProcessModel(processDefinitionId), StandardCharsets.UTF_8);

    }

    public List<Object> deployeProcess(@RequestBody MultipartFile BpmnFile) throws IOException, URISyntaxException {
        List<Object> result= new ArrayList<>();

        try {
            URI uriRessource = getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
            System.out.println(uriRessource.getPath());
            System.out.println(BpmnFile.getOriginalFilename());

            File initialFile = new File((uriRessource.getPath()).substring(1) + "processes/" + BpmnFile.getOriginalFilename());
            if (initialFile.exists()) {

                initialFile = new File((uriRessource.getPath()).substring(1) + "processes/" + BpmnFile.getOriginalFilename().substring(0, BpmnFile.getOriginalFilename().indexOf(".bpmn20")) + "(1)" + ".bpmn20.xml");

                //Definir le contenu du nouveau fichier bpmn
                FileOutputStream DocOut = new FileOutputStream(initialFile);
                DocOut.write(BpmnFile.getBytes());
                DocOut.close();

                //Deployer le processus a partir du fichier bpmn
                Deployment deployment = repositoryService.createDeployment()
                        .addClasspathResource("processes/" + initialFile.getName())
                        .deploy();
                System.out.println(deployment.getName());
            }

            else {

                //Creation d'un nouveau fichier bpmn
                initialFile.createNewFile();

                //Definir le contenu du nouveau fichier bpmn
                FileOutputStream DocOut = new FileOutputStream(initialFile);
                DocOut.write(BpmnFile.getBytes());
                DocOut.close();

                //Deployer le processus a partir du fichier bpmn
                Deployment deployment = repositoryService.createDeployment()
                        .addClasspathResource("processes/" + BpmnFile.getOriginalFilename())
                        .deploy();
                System.out.println(deployment.getName());

                result.add(true);
                result.add("succes de deployement du processus");
            }
        }catch (Exception e){
            result.add(false);
            result.add("echec de deployement du processus");
            result.add(e.getMessage());
        }
        return result;
    }

    public Boolean deleteSavedBpmnModel(String path) throws IOException {
        try {
            new File(path).delete();
            return true;
        }catch (Exception e){
            return false;
        }

    }

    private static Document convertStringToXMLDocument(String xmlString)
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }




}

