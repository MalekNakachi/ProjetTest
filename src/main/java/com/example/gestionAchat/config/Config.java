//package com.example.MaiManager.config;
//
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.Collections;
//
////import org.elasticsearch.client.Client;
////import org.elasticsearch.client.transport.TransportClient;
////import org.elasticsearch.common.settings.Settings;
////import org.elasticsearch.common.transport.TransportAddress;
////import org.elasticsearch.transport.client.PreBuiltTransportClient;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
////import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
////import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
////import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//@Configuration
//
////@EnableTransactionManagement
////@EnableJpaRepositories("com.example.MaiManager.repository")
////@EnableElasticsearchRepositories("com.example.MaiManager.repository.elastic")
//public class Config {
//
//    @Value("${spring.ProfileManagerURL}")
//    private  String ProfileManagerURL;
//    //@Value("${elasticsearch.host:192.168.10.103}")
//
//    @Value("${spring.AdminProfileUsername}")
//    private  String AdminProfileUsername;
//
//    @Value("${spring.AdminProfilePassword}")
//    private  String AdminProfilePassword;
//
//    @Value("${spring.AdminProfileApplication}")
//    private  String AdminProfileApplication;
//
//    public String host;
//    // @Value("${elasticsearch.port:9300}")
//    public int port;
//
//    public String getHost() {
//        return host;
//    }
//
//    public int getPort() {
//        return port;
//    }
//
//    public void getConfig() {
//
//
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//
//        body.add("username", AdminProfileUsername);
//
//        body.add("password", AdminProfilePassword);
//
//        body.add("application", AdminProfileApplication);
//
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, new HttpHeaders());
//        String url = ProfileManagerURL+"login";
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        ResponseEntity<String> result1 = restTemplate.postForEntity(url, requestEntity, String.class);
//
//        String token = result1.getHeaders().getFirst("authorization");
//
//        RestTemplate restTemplate2 = new RestTemplate();
//
//        String url2 = ProfileManagerURL+"api/ConfigElastic/PM";
//
//        HttpHeaders headers = new HttpHeaders();
//
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//
//        headers.set("Authorization", token);
//
//        HttpEntity<?> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<String> c =restTemplate2.exchange(url2, HttpMethod.GET, entity,String.class);
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        String cc=c.getBody();
//
//        JSONObject jsonObj = new JSONObject(cc);
//
//        host=jsonObj.get("hosturl").toString().split("//")[1].split(":")[0];
//
//        port=Integer.parseInt(jsonObj.get("portnode").toString());
//    }
////
////    @Bean
////    public Client client() {
////        TransportClient client = null;
////        getConfig();
////        try {
////            System.out.println("host:" + host + "port:" + port);
////            client = new PreBuiltTransportClient(Settings.EMPTY)
////                    .addTransportAddress(new TransportAddress(InetAddress.getByName(host), port));
////        } catch (UnknownHostException e) {
////            e.printStackTrace();
////        }
////        return client;
////        //return null;
////    }
//
////   @Bean
////    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
////        return new ElasticsearchTemplate(client());
////    }
//
//}
//
