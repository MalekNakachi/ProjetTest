package com.example.gestionAchat.web.rest;
import com.example.gestionAchat.service.WorkflowService;
import org.flowable.bpmn.model.Process;

import org.flowable.task.api.Task;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

//@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(path = "/api/workflow")
public class WorkflowRessource {

    @Autowired
    private WorkflowService workflowService;



    ///////////////////////////////////////////////////////////////

    @RequestMapping(value="/startProcessInstance", method= RequestMethod.POST)
    public List<Object> startProcessInstanceWithAuthReader(@RequestBody StartProcessRepresentationWithAuthReader startProcessRepresentation) {

        return workflowService.startProcessInstance(startProcessRepresentation.getName(), startProcessRepresentation.getProcess());
    }
////////////////////////////////////////////////////////////////////

    @RequestMapping(value="/startProcessInstanceRecieveEvent", method= RequestMethod.POST)
    public List<Object> startProcessInstanceRecieveEvent(@RequestBody StartProcessRepresentation startProcessRepresentation) {
        return workflowService.startProcessInstanceRecieveEvent(startProcessRepresentation.getName(), startProcessRepresentation.getProcess());
    }


    @RequestMapping(value="/getdelaiTask", method= RequestMethod.POST)
    public Date getdelaiTask(@RequestBody String taskId) {
        return workflowService.getdelaiTask(taskId);
    }


    @RequestMapping(value="/listAllProcess", method= RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Process> list_All_Process_Instance(@RequestBody String taskId) {
        System.out.println(taskId);
        List<Process> processList= new ArrayList<Process>();
        processList.add(workflowService.getListProcess(taskId));
        return processList;
    }

    @RequestMapping(value="/historic", method= RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<String>  getHistoric(@RequestBody String taskId) {
        return(workflowService.historicaldata(taskId));
    }

    @RequestMapping(value="/getHistoricProcessInstance", method= RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<String>  getHistoricProcessInstance(@RequestBody String processInstanceId) {
        return(workflowService.getHistoricProcessInstance(processInstanceId));
    }

    @RequestMapping(value="/NextAc", method= RequestMethod.POST)
    public List<String>  Nextdata(@RequestBody String taskId) {
        System.out.println("I am here");
        System.out.println(taskId);
        return(workflowService.Nextdata(taskId));
    }

    @RequestMapping(value="/tasks", method= RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
        List<Task> tasks = workflowService.getTasks(assignee);
        List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
        for (Task task : tasks) {
            dtos.add(new TaskRepresentation(task.getId(), task.getName()));
        }
        return dtos;
    }

    @RequestMapping(value="/tasksAssignee", method= RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<TaskRepresentation> getTasksAssingee(@RequestParam String assignee) {
        List<Task> tasks = workflowService.getTasksAssingee(assignee);
        List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
        for (Task task : tasks) {
            dtos.add(new TaskRepresentation(task.getId(), task.getName()));
        }
        return dtos;
    }

    //////////////////walid///////////////////////////////////////////////////////////
    @RequestMapping(value = "/getFirstTask", method= RequestMethod.GET)
    public List<String> getFirstTask(@RequestParam String taskId) {
        return workflowService.firstTask(taskId);
    }

    @RequestMapping(value = "/getRelativeProcess", method= RequestMethod.GET)
    public String getRelativeProcess(@RequestParam String processInstance) {
        return workflowService.getRelativeProcess(processInstance);
    }

    ////////////////////////////////////////////////////////////////////////////////


    @RequestMapping(value = "/decision", method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE,MediaType.TEXT_PLAIN_VALUE})
    public void taskDecision(@RequestParam("taskid") String taskid,@RequestParam("authors") String Authors,@RequestParam("readers") String Readers,@RequestParam("description") String description, @RequestParam("decision") String decision) {
        workflowService.taskDecision(taskid, Authors, decision, Readers, description);
    }

    @RequestMapping(value = "/decisionGW", method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE,MediaType.TEXT_PLAIN_VALUE})
    public void taskDecisionGW(@RequestParam("taskid") String taskId, @RequestParam("decision") String decision, @RequestParam("authors") String Authors,@RequestParam("readers") String Readers,@RequestParam("description") String description,@RequestParam("categorie") String categorie) {
        workflowService.taskDecisionGW(taskId,decision, Authors, Readers, description, categorie);
    }


    @RequestMapping(value = "/nextTask", method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE,MediaType.TEXT_PLAIN_VALUE})
    public List<Object> nextTask(@RequestParam("processInstanceId") String processInstanceId, @RequestParam("taskid") String taskId, @RequestParam("decision") String decision, @RequestParam("description") String description,@RequestParam("authentifier") String authentifier,@RequestParam("listparams") List<String> Params) throws JSONException, ParseException, UnsupportedEncodingException {
        return workflowService.nextTask(processInstanceId,taskId,decision, description, authentifier, Params);
    }
    /////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value="/getInfo", method= RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getInfo() {

      return workflowService.getInfo();
    }

    @RequestMapping(value = "/getGeneralAut", produces=MediaType.APPLICATION_JSON_VALUE)
    public Map<String,String> getGeneralAut(@RequestBody String TaskId) {
        return workflowService.getGAut(TaskId);
    }

    @RequestMapping(value = "/getGeneralLect", produces=MediaType.APPLICATION_JSON_VALUE)
    public Map<String,String> getGeneralLect(@RequestBody String TaskId) {
        return workflowService.getGLect(TaskId);
    }

    @RequestMapping(value = "/getCandidateGroups", method= RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<String> getCandidateGroups(@RequestBody String taskId) {
        System.out.println("just for test");
        System.out.println(taskId);
        return workflowService.getCandidateGroups(taskId);
    }
    @RequestMapping(value = "/getCandidateUsers", method= RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<String> getCandidateUsers(@RequestBody String taskId) {
        System.out.println("just for test");
        System.out.println(taskId);
        return workflowService.getCandidateUsers(taskId);
    }

    @RequestMapping(value = "/setCandidates")
    public void setCandidateGroups(@RequestBody CandidatesTask candidatesTask) {
        System.out.println("taskid : "+ candidatesTask.getTaskId());
        System.out.println("authors : "+ candidatesTask.getAuthors());
        System.out.println("readers : "+ candidatesTask.getReaders());
        workflowService.setCandidates(candidatesTask.getTaskId(), candidatesTask.getAuthors(),candidatesTask.getReaders());
    }

    @RequestMapping(value = "/getFirstActivityNameWithoutProcessInstance", method = RequestMethod.POST)
    public List getFirstActivityNameWithoutProcessInstance(@RequestParam("processName") String processName) throws FileNotFoundException {
        System.out.println(processName);
        return workflowService.getFirstActivityNameWithoutProcessInstance(processName);
    }



    @RequestMapping(value = "/getDescription", method= RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public List getDescription(@RequestBody String taskId) {
        System.out.println("just for test");
        System.out.println(taskId);
        return workflowService.getDescription(taskId);
    }

    @RequestMapping(value = "/setAssignee", method= RequestMethod.POST)
    public void setAssignee(@RequestBody AssignneTask assignneTask) {
        workflowService.setAssignee(assignneTask.getTaskid(), assignneTask.getAuthentifier());
    }

    @RequestMapping(value = "/getAssignee", method= RequestMethod.POST)
    public List<String> getAssignee(@RequestParam("taskid") String taskId) {
        return workflowService.getAssignee(taskId);
    }



    @RequestMapping(value = "/getListFinalVersionProcess", method= RequestMethod.GET,produces={MediaType.APPLICATION_JSON_VALUE,MediaType.TEXT_PLAIN_VALUE})
    public List<JSONObject> getListFinalProcess() {

        return workflowService.getListFinalProcess();
    }


    @RequestMapping(value = "/getListProcessVersions", method= RequestMethod.GET,produces={MediaType.APPLICATION_JSON_VALUE,MediaType.TEXT_PLAIN_VALUE})
    public List<JSONObject> getListVersionsProcess(@RequestParam("key") String key) {

        return workflowService.getListVersionsProcess(key);
    }

    /////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "/getCurrentTask", method= RequestMethod.POST)
    public List<String> getCurrentTask(@RequestBody String taskId) {
        return workflowService.currentTask(taskId);
    }

    @RequestMapping(value = "/getLastTask", method= RequestMethod.POST)
    public List<String> getLastTask(@RequestBody String taskId) {
        return workflowService.lastTask(taskId);
    }

    @RequestMapping(value = "/getLastUserTask", method= RequestMethod.GET)
    public List<String> getLastUserTask(@RequestParam String TaskId) {
        return workflowService.lastUserTask(TaskId);
    }

    @RequestMapping(value = "/takeTask", method = RequestMethod.GET)
    public void takeTask(@RequestParam String userId, String taskId) {
        workflowService.takeTask(userId, taskId);
    }

    @RequestMapping(value = "/gatewayDecision", method = RequestMethod.POST)
    public List gatewayDecision(@RequestParam("taskid") String taskid) {
        System.out.println(taskid);
        return workflowService.getGatewayDecision(taskid);
    }

    @RequestMapping(value = "/getGatewayDecisionWithoutTaskId", method = RequestMethod.POST)
    public List gatewayDecisionTest(@RequestParam("processId") String processId) throws FileNotFoundException {
        System.out.println("processId :: "+processId);
        return workflowService.getGatewayDecisionWithoutTaskId(processId);
    }
    @RequestMapping(value = "/calculate_decisions", method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE,MediaType.TEXT_PLAIN_VALUE})
    public List<String> calculate_decisions(@RequestBody String[] activitys, @RequestParam("taskid") String taskId) throws JSONException, ParseException, UnsupportedEncodingException {
        System.out.println(activitys);
        System.out.println(taskId);
        return workflowService.calculate_decisions(activitys, taskId);
    }

    @RequestMapping(value = "/getActivitiesByProcess", method = RequestMethod.GET)
    public List<String> get_activities_by_process(@RequestParam("processDefinitionKey") String processDefinitionKey) throws JSONException {
        return workflowService.get_activities_by_process(processDefinitionKey);
    }

    @RequestMapping(value="/checkProcessToUndeploy/{processDefinitionId}", method= RequestMethod.GET)
    public Boolean checkProcessToUndeploy(@PathVariable("processDefinitionId") String processDefinitionId) throws IOException {
        return workflowService.checkProcessToUndeploy(processDefinitionId);
    }

    @RequestMapping(value="/undeployeBpmnModel/{processDefinitionId}/{force}", method= RequestMethod.DELETE)
    public Boolean undeployeBpmnModel(@PathVariable("processDefinitionId") String processDefinitionId,@PathVariable("force") Boolean force) throws IOException {
        return workflowService.undeployeProcess(processDefinitionId, force);
    }



    @RequestMapping(value = "/getActivitiesByProcesKey", method= RequestMethod.GET)
    public List<JSONObject> getActivitiesByProcesKey(@RequestParam("processDefinitionKey") String processDefinitionKey) throws IOException, URISyntaxException {

        return workflowService.getActivitiesByProcesKey(processDefinitionKey);
    }


    @RequestMapping(value = "/getActivityByProcesKey", method= RequestMethod.GET)
    public List<JSONObject> getActivityByProcesKey(@RequestParam("processDefinitionKey") String processDefinitionKey, @RequestParam("activity") String activity) throws IOException, URISyntaxException {

        return workflowService.getActivityByProcesKey(processDefinitionKey, activity);
    }

    @RequestMapping(value = "/getAllActivitiesByProces", method= RequestMethod.GET)
    public List<JSONObject> getAllActivitiesByProcesKey(@RequestParam("processDefinitionKey") String processDefinitionKey) throws IOException, URISyntaxException {

        return workflowService.getAllActivitiesByProcesKey(processDefinitionKey);
    }




    static class StartProcessRepresentation {

        private String name;
        private String process;

        public String getName() {
            return name;
        }
        public String getProcess() {
            return process;
        }

        public void setName(String name) {
            this.name = name;
        }
        public void setProcess(String process) {
            this.process = process;
        }
    }

    static class StartProcessRepresentationWithoutInitioator {

        private String process;

        public String getProcess() {
            return process;
        }

        public void setProcess(String process) {
            this.process = process;
        }
    }

    static class StartProcessRepresentationWithAuthReader {

        private String name;
        private String process;



        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public String getProcess() {
            return process;
        }
        public void setProcess(String process) {
            this.process = process;
        }

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    static class  CandidatesTask {

        private String taskid;
        private List<String> authors;
        private List<String> readers;

        public  CandidatesTask(String taskid, List<String> authors, List<String> readers) {
            this.taskid = taskid;
            this.authors = authors;
            this.readers = readers;
        }

        public String getTaskId() {
            return taskid;
        }
        public void setTaskId(String taskid) {
            this.taskid = taskid;
        }
        public List<String> getAuthors() {
            return authors;
        }
        public void setAuthors(List<String> authors) {
            this.authors=authors;
        }
        public List<String> getReaders() {
            return readers;
        }
        public void setReaders(List<String> readers) {
            this.readers=readers;
        }

    }

    static class  AssignneTask {

        private String taskid;
        private String authentifier;

        public  AssignneTask(String taskid, String authentifier) {
            this.taskid = taskid;
            this.authentifier = authentifier;
        }

        public String getTaskid() {
            return taskid;
        }
        public void setTaskid(String taskid) {
            this.taskid = taskid;
        }
        public String getAuthentifier() {
            return authentifier;
        }
        public void setAuthentifier(String authentifier) {
            this.authentifier = authentifier;
        }

    }

    static class TaskRepresentation{

        private String id;
        private String name;

        public TaskRepresentation(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

    }

    @CrossOrigin
    @PostMapping(
            value = "/deployeProcessold",
            consumes ="multipart/form-data"

    )
    public List<Object> deployeProcessold(@RequestParam("file") MultipartFile BpmnFile) throws Exception {
        System.out.println(BpmnFile.getContentType());
        return workflowService.deployeProcess(BpmnFile);


    }


    @RequestMapping(value = "/getListProcess", method = RequestMethod.GET)
    public List<JSONObject> getListProcess() throws FileNotFoundException {
        return workflowService.getListProcess();
    }



    //***************************************Adaptation Workflow new******************************


    @RequestMapping(value="/getBpmnModelXml", method= RequestMethod.GET)
    public String getBpmnModelXml(@RequestParam("processDefinitionId") String processDefinitionId) throws IOException, IOException {
        return workflowService.getBpmnModelXml(processDefinitionId);
    }


    @RequestMapping(value="/deleteSavedBpmnModel", method= RequestMethod.GET)
    public Boolean deleteSavedBpmnModel(@RequestParam("path") String path) throws IOException {

        return workflowService.deleteSavedBpmnModel(path);
    }


    @RequestMapping(value="/getBpmnModelXmlByPath", method= RequestMethod.GET)
    public String getBpmnModelXmlByPath(@RequestParam("path") String path) throws IOException {
        return workflowService.getBpmnModelXmlByPath(path);
    }

    @RequestMapping(value="/deployeBpmnModel", method= RequestMethod.POST)
    public Boolean deployeBpmnModel(@RequestBody String xml, @RequestParam("name") String name) throws IOException {
        return workflowService.deployeBpmnModel(xml, name);
    }


    @RequestMapping(value="/saveBpmnModel", method= RequestMethod.POST)
    public Boolean saveBpmnModel(@RequestBody String xml, @RequestParam("name") String name) throws IOException {
        return workflowService.saveBpmnModel(xml, name);
    }


    @CrossOrigin
    @RequestMapping(value = "/getListProcessNonDeploye", method= RequestMethod.GET,produces={MediaType.APPLICATION_JSON_VALUE,MediaType.TEXT_PLAIN_VALUE})
    public List<JSONObject> getListProcessNonDeploye() throws IOException, URISyntaxException {

        return workflowService.getListProcessNonDeploye();
    }

    @CrossOrigin
    @RequestMapping(value = "/getListProcessFinalVersion", method= RequestMethod.GET,produces={MediaType.APPLICATION_JSON_VALUE,MediaType.TEXT_PLAIN_VALUE})
    public List<JSONObject> getListProcesses() {

        return workflowService.getListProcesses();
    }

    @CrossOrigin
    @PostMapping(
            value = "/deployeProcess",
            consumes ="multipart/form-data"

    )
    public List<Object> deployeProcess(@RequestParam("file") MultipartFile BpmnFile) throws Exception {
        System.out.println(BpmnFile.getContentType());
        return workflowService.deployeProcess(BpmnFile);


    }


}
