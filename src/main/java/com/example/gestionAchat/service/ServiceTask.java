package com.example.gestionAchat.service;


import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Named("serviceTask")
public class ServiceTask implements JavaDelegate {
//
//    @Autowired
//    private CheckInBoxService checkInBoxService;




//    @Value("${spring.ProfileManagerURL}")
//    private  String ProfileManagerURL;
//
//    @Value("${spring.MailManagerAgentURL}")
//    private  String MailManagerAgentURL;
//
//    @Value("${spring.AdminProfileUsername}")
//    private  String AdminProfileUsername;
//
//    @Value("${spring.AdminProfilePassword}")
//    private  String AdminProfilePassword;
//
//    @Value("${spring.AdminProfileApplication}")
//    private  String AdminProfileApplication;

    @Autowired
    private ServiceTask serviceTask;

    @Autowired
    RuntimeService runtimeService;





    @Override
    public void execute(DelegateExecution execution) {

////        JSONParser jsonParser = new JSONParser();
////        JSONObject jsonObject = null;
////        try {
////            jsonObject = (JSONObject) jsonParser.parse(jsonObjectTalend);
////        } catch (ParseException e) {
////            e.printStackTrace();
////        }
//
//
//        Set<ConnexionsExterne> var = (Set<ConnexionsExterne>) runtimeService.getVariable(execution.getProcessInstanceId(),"connexions");
//     //   System.out.println(execution.getVariables());
//    //    String var = (String) execution.getVariable("connexions");
//        System.out.println(var);
////        execution.getVariable("connexions");
//
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("mailboxhote","srv-mail1.picosoft.biz");
//        jsonObject.put("mailboxport","110");
//        jsonObject.put("mailboxuser","Helmi Test");
//        jsonObject.put("mailboxpassword","PicoSoft2019");
//        jsonObject.put("nomboite","MManagerMB2021");
//
//        LocalDate today = LocalDate.now();
//        LocalDate yeasterday = today.minusDays(1);
//        String dateemail= yeasterday.toString().substring(6,10)+"-"+yeasterday.toString().substring(3,5)+"-"+yeasterday.toString().substring(0,2);
//        System.out.println("dateemail::"+dateemail);
//        jsonObject.put("dateemail",dateemail);
//        jsonObject.put("outputdirectory","C:/TalendTemp");
//        jsonObject.put("urlbackmm","http://192.168.10.161:8088/MPM2021/");
//        jsonObject.put("urlserveur","http://192.168.10.156:8001/mailingjob/");
//        jsonObject.put("targetnoderef","workspace://SpacesStore/f67b462e-956b-46e9-91de-4cf77ab52104");
//        jsonObject.put("useralfresco","admin");
//        jsonObject.put("passwordalfresco","admin@2020");
//        jsonObject.put("urlserveuralfresco","https://gedps.picosoft.biz/");
//        System.out.println("Came in first service task :");
//        try {
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//            HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(jsonObject,headers);
//
//            ConnexionsExterne connexionExternesMailBox = null;
//            ConnexionsExterne connexionExternesAlfresco = null;
//            ConnexionsExterne connexionExternesJob = null;
////            String url = MailManagerAgentURL+"/mailingjob/RunJobMailBox1";
//            Iterator<ConnexionsExterne> iterator = var.iterator();
//            while(iterator.hasNext()) {
//                ConnexionsExterne connexionExternes = iterator.next();
//                if (connexionExternes.getNameConnexion().equals("MailBox")) {
//                    connexionExternesMailBox = connexionExternes;
//                } else if (connexionExternes.getNameConnexion().equals("Alfresco")) {
//                    connexionExternesAlfresco = connexionExternes;
//                } else {
//                    connexionExternesJob = connexionExternes;
//
//                }
//            }
//            String url = connexionExternesJob.getUrl();
//            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, JSONObject.class);
//            JSONObject output = responseEntity.getBody();
//            System.out.println(output);
//
//            execution.setVariable("Decision","not_detected");

//            CustomMailActivityBehavior mailActivityBehavior = new CustomMailActivityBehavior("walid.ayari@picosoft.biz", "walid.ayari@picosoft.biz", "walid.ayari@picosoft.biz", "walid.ayari@picosoft.biz", "Bn:Bn", "Notification Flowable", "url", "<b>Test</b>", execution);
//        }catch (Exception e){
////                        execution.setVariable("Decision","detected");
//
//            execution.setVariable("Decision","not_detected");
//            System.out.println("failed");
//        }
        execution.setVariable("Result","Succés");

    }

}
