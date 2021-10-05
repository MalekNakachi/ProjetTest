package com.example.gestionAchat.service;


import com.example.gestionAchat.domain.AclSid;
import com.example.gestionAchat.entities.*;
import com.example.gestionAchat.entities.Department;
import com.example.gestionAchat.entities.Group;
import com.example.gestionAchat.entities.Role;
import com.example.gestionAchat.entities.User;
import com.example.gestionAchat.repository.*;
import com.example.gestionAchat.repository.pm.DepRepository;
import com.example.gestionAchat.repository.pm.RoleRepository;
import com.example.gestionAchat.repository.pm.UserRepository;
import com.example.gestionAchat.service.pm.ProfileService;
import com.example.gestionAchat.service.pm.UserAccessService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.bind.annotation.RequestParam;


import javax.persistence.*;
import java.util.*;

@Service
@Transactional
public class DocumentService {
    @Autowired
    JdbcTemplate jdbcTemplate;



    @PersistenceContext(unitName = "externDSEmFactory")
    //@Autowired
    EntityManager em;

    @Autowired
    private UserAccessService userAccessService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private DepRepository depRepository;
    @Autowired
    CustomPermissionEvaluator permissionEvaluator;




    @Autowired
    JdbcMutableAclService aclService;






    @Autowired
    AclEntryRepository aclEntryRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ProfileService profileService;
    @Autowired
    AclSidRepository aclSidRepository;

    @Autowired
    AclClassRepository aclClassRepository;

    @Autowired
    JournalsRepository journalsRepository;

    @Autowired
    JournalDetailsRepository journalDetailsRepository;

    @Autowired
    JournalService journalService;

    @Autowired
    AclObjectIdentityRepository aclObjectIdentityRepository;


    private ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy = new ObjectIdentityRetrievalStrategyImpl();


    public List<AclSid> AllSid() {
        List<AclSid> aclSids = aclSidRepository.findAll();
        return aclSids;
    }


    public void SaveSid(AclSid aclSid) {

        aclSidRepository.save(aclSid);
    }
//
//    public List<CourrierArrive> AllCALazy(int skip, int take, String filter, String orderBy) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String credentiels = getMyArrayListString(authentication.getPrincipal().toString());
//        //   System.out.println("credentiels of grid ==>>> " + credentiels);
//
//        List<CourrierArrive> retour = new ArrayList<CourrierArrive>();
//        Query q1 = null;
//        String allquery = "";
//
//     /* String part0 = "SELECT * FROM doc_courrier_arrive where id IN (" +
//            "select object_id_identity from acl_object_identity where id in ("+
//             "(SELECT acl_entry.acl_object_identity FROM acl_entry WHERE sid IN " +
//             "(SELECT id FROM acl_sid WHERE sid IN (" + credentiels + "))))) ";
//*/
///***********************************************************************/
//        int idclasse = -1;
//        AclClass aclClass = aclClassRepository.findByClasse("com.example.MaiManager.domain.CourrierArrive").get();
//
//        if (aclClass.getId() != null)
//            idclasse = aclClass.getId();
//
//
//        //String viewCa = "SELECT * FROM ca";
//        String viewCa = "SELECT distinct object_id_identity from ca where sid IN (" + credentiels + ")  and  object_id_class=" + idclasse;
//
//        Query q = (Query) em.createNativeQuery(viewCa);
//
//        List<BigInteger> liste = (List<BigInteger>) q.getResultList();
//
//        String listeString = "";
//        //if(listeString.indexOf(liste.get(j).getObject_id_identity())==-1)
//        // listeString+=(liste.get(j).getObject_id_identity());
//        //  listeString+=",";
//
//
//        if (liste.size() == 0)
//
//            return new ArrayList<CourrierArrive>();
//
//        else {
//
//            for (int j = 0; j < liste.size(); j++) {
//                // if(listeString.indexOf(String.valueOf(liste.get(j)))==-1)
//                listeString += (String.valueOf(liste.get(j)));
//                listeString += ",";
//            }
//
//
//            if (listeString.indexOf(",,") != -1)
//                listeString = listeString.replaceAll(",,", ",");
//            //  and sid IN (" + credentiels + ")
//
//            String part0 = "SELECT * FROM doc_courrier_arrive where id IN (" + listeString.substring(0, listeString.length() - 1) + ") ";
//
//            //Process castedDEc = (Process) q.getResultList().get(0);
//
//
//            allquery += part0;
//            String ch = "";
//            String w = "";
//            //   System.out.println("filter " + filter);
//
//            if (!filter.equals("undefined")) {
//                String x = filter;
////            String[] T = x.split(",and,");
//                String[] T = x.split(",XR002,");
//
//                for (int i = 0; i < T.length; i++) {
//                    if (T[i].contains(",or,")) {
//                        w = T[i];
//                        String[] R = w.split(",or,");
//                        String v0 = R[0];
//                        String[] R00 = v0.split(",");
//                        // System.out.println("R00   " + R00);
//                        if (R00[1].contains("XR001")) {
//                            ch += " AND " + "(" + R00[0] + " like '%" + R00[2].trim() + "%'";
//                        } else {
//                            ch += " AND " + "(" + R00[0] + " " + R00[1] + " '" + R00[2] + "'";
//                        }
//
//                        for (int y = 1; y < R.length; y++) {
////                    for (int y = 0; y < R.length; y++) {
//                            String v = R[y];
//                            String[] R0 = v.split(",");
//                            if (y != R.length - 1) {
//
//                                if (R0[1].contains("XR001")) {
//                                    ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'";
//                                } else {
//
//                                    ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'";
//                                }
//
//                            } else {
//                                if (R0[1].contains("XR001")) {
//                                    ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'" + ")";
//                                } else {
//
//                                    ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'" + ")";
//                                }
//                            }
//
//                        }
//                    } else {
//                        w = T[i];
//
//                        String[] R = w.split(",");
//
//                        if (R[1].contains("XR001")) {
//                            ch += " AND " + R[0] + " like '%" + R[2].trim() + "%'";
//                        } else {
//                            ch += " AND " + R[0] + " " + R[1] + " '" + R[2] + "'";
//
//                        }
//                    }
//                }
//            }
//
//            allquery += ch;
//            if (orderBy.equals("undefined")) {
//                allquery += " ORDER BY datearrivee desc ";
//            }
//            if (!orderBy.equals("undefined")) {
//                allquery += " ORDER BY " + orderBy;
//            }
//            allquery += " LIMIT " + take + " OFFSET " + skip;
////    allquery += " OFFSET " + skip + " ROWS FETCH NEXT " + take+" ROWS ONLY";
//
//            // System.out.println("all Query Lazy ==> " + allquery);
//
//            if (allquery.indexOf("activityName") != -1)
//                allquery = allquery.replace("activityName", "activity_name");
//            if (allquery.indexOf("adresseExterneorganisme") != -1)
//                allquery = allquery.replace("adresseExterneorganisme", "adresse_externeorganisme");
//
//            if (allquery.indexOf("adresseExterneorganisme") != -1)
//                allquery = allquery.replace("adresseExterneorganisme", "adresse_externeorganisme");
//
//            if (allquery.indexOf("initiateurName") != -1)
//                allquery = allquery.replace("initiateurName", "initiateur_name");
//
//            if (allquery.indexOf("adresseExterne") != -1)
//                allquery = allquery.replace("adresseExterne", "adresse_externe");
//
//
//            if (allquery.indexOf("adresseExterneorganisme") != -1)
//                allquery = allquery.replace("adresseExterneorganisme", "adresse_externeorganisme");
//
//
//            q1 = (Query) em.createNativeQuery(allquery, CourrierArrive.class);
//            retour = q1.getResultList();
//
//            return retour;
//        }
//
//    }
//
//    public List<CourrierDepart> AllCDLazy(int skip, int take, String filter, String orderBy) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String credentiels = getMyArrayListString(authentication.getPrincipal().toString());
//        //System.out.println("credentiels ==>>> " + credentiels);
//
//
//        List<CourrierDepart> retour = new ArrayList<CourrierDepart>();
//        Query q1 = null;
//        String allquery = "";
//
//     /* String part0 = "SELECT * FROM doc_courrier_arrive where id IN (" +
//            "select object_id_identity from acl_object_identity where id in ("+
//             "(SELECT acl_entry.acl_object_identity FROM acl_entry WHERE sid IN " +
//             "(SELECT id FROM acl_sid WHERE sid IN (" + credentiels + "))))) ";
//*/
///***********************************************************************/
//        int idclasse = -1;
//        AclClass aclClass = aclClassRepository.findByClasse("com.example.MaiManager.domain.CourrierDepart").get();
//
//        if (aclClass.getId() != null)
//            idclasse = aclClass.getId();
//
//
//        //String viewCa = "SELECT * FROM ca";
//        String viewCa = "SELECT distinct object_id_identity from ca where sid IN (" + credentiels + ")  and  object_id_class=" + idclasse;
//
//        Query q = (Query) em.createNativeQuery(viewCa);
//
//        List<BigInteger> liste = (List<BigInteger>) q.getResultList();
//
//        String listeString = "";
//        //if(listeString.indexOf(liste.get(j).getObject_id_identity())==-1)
//        // listeString+=(liste.get(j).getObject_id_identity());
//        //  listeString+=",";
//
//        for (int j = 0; j < liste.size(); j++) {
//            // if(listeString.indexOf(String.valueOf(liste.get(j)))==-1)
//            listeString += (String.valueOf(liste.get(j)));
//            listeString += ",";
//        }
//
//        if (listeString.indexOf(",,") != -1)
//            listeString = listeString.replaceAll(",,", ",");
//
//        //  and sid IN (" + credentiels + ")
//
//        String part0 = "SELECT * FROM doc_courrier_depart where id IN (" + listeString.substring(0, listeString.length() - 1) + ") ";
//
//        //Process castedDEc = (Process) q.getResultList().get(0);
//
//
//        allquery += part0;
//        String ch = "";
//        String w = "";
//        //   System.out.println("filter " + filter);
//
//        if (!filter.equals("undefined")) {
//            String x = filter;
////            String[] T = x.split(",and,");
//            String[] T = x.split(",XR002,");
//
//            for (int i = 0; i < T.length; i++) {
//                if (T[i].contains(",or,")) {
//                    w = T[i];
//                    String[] R = w.split(",or,");
//                    String v0 = R[0];
//                    String[] R00 = v0.split(",");
//                    //   System.out.println("R00   " + R00);
//                    if (R00[1].contains("XR001")) {
//                        ch += " AND " + "(" + R00[0] + " like '%" + R00[2].trim() + "%'";
//                    } else {
//                        ch += " AND " + "(" + R00[0] + " " + R00[1] + " '" + R00[2] + "'";
//                    }
//
//                    for (int y = 1; y < R.length; y++) {
////                    for (int y = 0; y < R.length; y++) {
//                        String v = R[y];
//                        String[] R0 = v.split(",");
//                        if (y != R.length - 1) {
//
//                            if (R0[1].contains("XR001")) {
//                                ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'";
//                            } else {
//
//                                ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'";
//                            }
//
//                        } else {
//                            if (R0[1].contains("XR001")) {
//                                ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'" + ")";
//                            } else {
//
//                                ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'" + ")";
//                            }
//                        }
//
//                    }
//                } else {
//                    w = T[i];
//
//                    String[] R = w.split(",");
//
//                    if (R[1].contains("XR001")) {
//                        ch += " AND " + R[0] + " like '%" + R[2].trim() + "%'";
//                    } else {
//                        ch += " AND " + R[0] + " " + R[1] + " '" + R[2] + "'";
//
//                    }
//                }
//            }
//        }
//
//        allquery += ch;
//        if (orderBy.equals("undefined")) {
//            allquery += " ORDER BY datecourrier desc ";
//        }
//        if (!orderBy.equals("undefined")) {
//            allquery += " ORDER BY " + orderBy;
//        }
//        allquery += " LIMIT " + take + " OFFSET " + skip;
////    allquery += " OFFSET " + skip + " ROWS FETCH NEXT " + take+" ROWS ONLY";
//
//        //      System.out.println("all Query Lazy ==> " + allquery);
//
//
//        if (allquery.indexOf("activityName") != -1)
//            allquery = allquery.replace("activityName", "activity_name");
////        if (allquery.indexOf("adresseExterneorganisme") != -1)
////            allquery = allquery.replace("adresseExterneorganisme", "adresse_externeorganisme");
////
////        if (allquery.indexOf("adresseExterneorganisme") != -1)
////            allquery = allquery.replace("adresseExterneorganisme", "adresse_externeorganisme");
////
////        if (allquery.indexOf("initiateurName") != -1)
////            allquery = allquery.replace("initiateurName", "initiateur_name");
////
////        if (allquery.indexOf("adresseExterne") != -1)
////            allquery = allquery.replace("adresseExterne", "adresse_externe");
////
////
////        if (allquery.indexOf("adresseExterneorganisme") != -1)
////            allquery = allquery.replace("adresseExterneorganisme", "adresse_externeorganisme");
//
//
//        q1 = (Query) em.createNativeQuery(allquery, CourrierDepart.class);
//        retour = q1.getResultList();
//
//        return retour;
//
//    }
//
//    /*
//        public List<CourrierDepart> AllCDLazy(int skip, int take, String filter, String orderBy) {
//
//            String credentiels =ListGroupsRoles();
//            System.out.println("credentiels ==>>> " + credentiels);
//
//            List<CourrierDepart> retour = new ArrayList<CourrierDepart>();
//            Query q1 = null;
//            String allquery = "";
//            String part0 = "SELECT * FROM doc_courrier_depart where id IN ((SELECT acl_entry.acl_object_identity FROM acl_entry WHERE sid IN " +
//                    "(SELECT id FROM acl_sid WHERE sid IN (" + credentiels + ")))) ";
//            allquery += part0;
//            String ch = "";
//            String w = "";
//            System.out.println("filter " + filter);
//
//            if (!filter.equals("undefined")) {
//                String x = filter;
//    //            String[] T = x.split(",and,");
//                String[] T = x.split(",XR002,");
//                for (int i = 0; i < T.length; i++) {
//                    if (T[i].contains(",or,")) {
//                        w = T[i];
//                        String[] R = w.split(",or,");
//                        String v0 = R[0];
//                        String[] R00 = v0.split(",");
//                        System.out.println("R00   " + R00);
//                        if (R00[1].contains("XR001")) {
//                            ch += " AND " + "(" + R00[0] + " like '%" + R00[2].trim() + "%'";
//                        } else {
//                            ch += " AND " + "(" + R00[0] + " " + R00[1] + " '" + R00[2] + "'";
//                        }
//
//                        for (int y = 1; y < R.length; y++) {
//    //                    for (int y = 0; y < R.length; y++) {
//                            String v = R[y];
//                            String[] R0 = v.split(",");
//                            if (y != R.length - 1) {
//
//                                if (R0[1].contains("XR001")) {
//                                    ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'";
//                                } else {
//
//                                    ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'";
//                                }
//
//                            } else {
//                                if (R0[1].contains("XR001")) {
//                                    ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'" + ")";
//                                } else {
//
//                                    ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'" + ")";
//                                }
//                            }
//
//                        }
//                    } else {
//                        w = T[i];
//                        System.out.println("w =>" + w);
//                        String[] R = w.split(",");
//                        System.out.println("R[2]>>>" + R[2]);
//                        if (R[1].contains("XR001")) {
//                            ch += " AND " + R[0] + " like '%" + R[2].trim() + "%'";
//                        } else {
//                            ch += " AND " + R[0] + " " + R[1] + " '" + R[2] + "'";
//
//                        }
//                    }
//                }
//            }
//
//            allquery += ch;
//            if (orderBy.equals("undefined")) {
//                allquery += " ORDER BY datearrivee desc ";
//            }
//            if (!orderBy.equals("undefined")) {
//                allquery += " ORDER BY " + orderBy;
//            }
//            allquery += " LIMIT " + take + " OFFSET " + skip;
//    //    allquery += " OFFSET " + skip + " ROWS FETCH NEXT " + take+" ROWS ONLY";
//            System.out.println("all Query ==> " + allquery);
//            if (allquery.indexOf("activityName") != -1)
//                allquery = allquery.replace("activityName", "activity_name");
//            q1 = (Query) em.createNativeQuery(allquery, CourrierDepart.class);
//            retour = q1.getResultList();
//
//            return retour;
//
//        }
//    */
//    public List<CourrierInterne> AllCILazy(int skip, int take, String filter, String orderBy) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String credentiels = getMyArrayListString(authentication.getPrincipal().toString());
//        // System.out.println("credentiels ==>>> " + credentiels);
//
//        List<CourrierInterne> retour = new ArrayList<CourrierInterne>();
//        Query q1 = null;
//        String allquery = "";
//
//     /* String part0 = "SELECT * FROM doc_courrier_arrive where id IN (" +
//            "select object_id_identity from acl_object_identity where id in ("+
//             "(SELECT acl_entry.acl_object_identity FROM acl_entry WHERE sid IN " +
//             "(SELECT id FROM acl_sid WHERE sid IN (" + credentiels + "))))) ";
//*/
///***********************************************************************/
//        int idclasse = -1;
//        AclClass aclClass = aclClassRepository.findByClasse("com.example.MaiManager.domain.CourrierInterne").get();
//
//        if (aclClass.getId() != null)
//            idclasse = aclClass.getId();
//
//        //String viewCa = "SELECT * FROM ca";
//        String viewCa = "SELECT distinct object_id_identity from ca where sid IN (" + credentiels + ")  and  object_id_class=" + idclasse;
//
//        Query q = (Query) em.createNativeQuery(viewCa);
//
//        List<BigInteger> liste = (List<BigInteger>) q.getResultList();
//
//        String listeString = "";
//        //if(listeString.indexOf(liste.get(j).getObject_id_identity())==-1)
//        // listeString+=(liste.get(j).getObject_id_identity());
//        //  listeString+=",";
//
//        for (int j = 0; j < liste.size(); j++) {
//            //  if(listeString.indexOf(String.valueOf(liste.get(j)))==-1)
//            listeString += (String.valueOf(liste.get(j)));
//            listeString += ",";
//        }
//
//        if (listeString.indexOf(",,") != -1)
//            listeString = listeString.replaceAll(",,", ",");
//
//        //  and sid IN (" + credentiels + ")
//
//        String part0 = "SELECT * FROM doc_courrier_interne where id IN (" + listeString.substring(0, listeString.length() - 1) + ") ";
//
//        //Process castedDEc = (Process) q.getResultList().get(0);
//
//
//        allquery += part0;
//        String ch = "";
//        String w = "";
//        //  System.out.println("filter " + filter);
//
//        if (!filter.equals("undefined")) {
//            String x = filter;
////            String[] T = x.split(",and,");
//            String[] T = x.split(",XR002,");
//            for (int i = 0; i < T.length; i++) {
//                if (T[i].contains(",or,")) {
//                    w = T[i];
//                    String[] R = w.split(",or,");
//                    String v0 = R[0];
//                    String[] R00 = v0.split(",");
//                    //   System.out.println("R00   " + R00);
//                    if (R00[1].contains("XR001")) {
//                        ch += " AND " + "(" + R00[0] + " like '%" + R00[2].trim() + "%'";
//                    } else {
//                        ch += " AND " + "(" + R00[0] + " " + R00[1] + " '" + R00[2] + "'";
//                    }
//
//                    for (int y = 1; y < R.length; y++) {
////                    for (int y = 0; y < R.length; y++) {
//                        String v = R[y];
//                        String[] R0 = v.split(",");
//                        if (y != R.length - 1) {
//
//                            if (R0[1].contains("XR001")) {
//                                ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'";
//                            } else {
//
//                                ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'";
//                            }
//
//                        } else {
//                            if (R0[1].contains("XR001")) {
//                                ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'" + ")";
//                            } else {
//
//                                ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'" + ")";
//                            }
//                        }
//
//                    }
//                } else {
//                    w = T[i];
//                    //  System.out.println("w =>" + w);
//                    String[] R = w.split(",");
//                    //   System.out.println("R[2]>>>" + R[2]);
//                    if (R[1].contains("XR001")) {
//                        ch += " AND " + R[0] + " like '%" + R[2].trim() + "%'";
//                    } else {
//                        ch += " AND " + R[0] + " " + R[1] + " '" + R[2] + "'";
//
//                    }
//                }
//            }
//        }
//
//        allquery += ch;
//        if (orderBy.equals("undefined")) {
//            allquery += " ORDER BY datearrivee desc ";
//        }
//        if (!orderBy.equals("undefined")) {
//            allquery += " ORDER BY " + orderBy;
//        }
//        allquery += " LIMIT " + take + " OFFSET " + skip;
////    allquery += " OFFSET " + skip + " ROWS FETCH NEXT " + take+" ROWS ONLY";
//        // System.out.println("all Query ==> " + allquery);
//        if (allquery.indexOf("activityName") != -1)
//            allquery = allquery.replace("activityName", "activity_name");
//        q1 = (Query) em.createNativeQuery(allquery, CourrierInterne.class);
//        retour = q1.getResultList();
//
//        return retour;
//
//    }
//
//    public List<Contact> ContactLazy(int skip, int take, String filter, String orderBy) {
//
//        List<Contact> retour = new ArrayList<Contact>();
//        Query q1 = null;
//        String allquery = "";
//
//
//        String part0 = "SELECT * FROM metier_carnet_contact where 1  ";
//
//
//        allquery += part0;
//        String ch = "";
//        String w = "";
//        //  System.out.println("filter " + filter);
//
//        if (!filter.equals("undefined")) {
//            String x = filter;
////            String[] T = x.split(",and,");
//            String[] T = x.split(",XR002,");
//            for (int i = 0; i < T.length; i++) {
//                if (T[i].contains(",or,")) {
//                    w = T[i];
//                    String[] R = w.split(",or,");
//                    String v0 = R[0];
//                    String[] R00 = v0.split(",");
//                    //  System.out.println("R00   " + R00);
//                    if (R00[1].contains("XR001")) {
//                        ch += " AND " + "(" + R00[0] + " like '%" + R00[2].trim() + "%'";
//                    } else {
//                        ch += " AND " + "(" + R00[0] + " " + R00[1] + " '" + R00[2] + "'";
//                    }
//
//                    for (int y = 1; y < R.length; y++) {
////                    for (int y = 0; y < R.length; y++) {
//                        String v = R[y];
//                        String[] R0 = v.split(",");
//                        if (y != R.length - 1) {
//
//                            if (R0[1].contains("XR001")) {
//                                ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'";
//                            } else {
//
//                                ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'";
//                            }
//
//                        } else {
//                            if (R0[1].contains("XR001")) {
//                                ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'" + ")";
//                            } else {
//
//                                ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'" + ")";
//                            }
//                        }
//
//                    }
//                } else {
//                    w = T[i];
//                    //  System.out.println("w =>" + w);
//                    String[] R = w.split(",");
//                    //  System.out.println("R[2]>>>" + R[2]);
//                    if (R[1].contains("XR001")) {
//                        ch += " AND " + R[0] + " like '%" + R[2].trim() + "%'";
//                    } else {
//                        ch += " AND " + R[0] + " " + R[1] + " '" + R[2] + "'";
//
//                    }
//                }
//            }
//        }
//
//        allquery += ch;
////        if (orderBy.equals("undefined")) {
////            allquery += " ORDER BY datearrivee desc ";
////        }
//        if (!orderBy.equals("undefined")) {
//            allquery += " ORDER BY " + orderBy;
//        }
//        allquery += " LIMIT " + take + " OFFSET " + skip;
////    allquery += " OFFSET " + skip + " ROWS FETCH NEXT " + take+" ROWS ONLY";
////       System.out.println("all Query ==> " + allquery);
////        if (allquery.indexOf("activityName") != -1)
////            allquery = allquery.replace("activityName", "activity_name");
//        q1 = (Query) em.createNativeQuery(allquery, Contact.class);
//        retour = q1.getResultList();
//
//        return retour;
//
//    }
//
//
//    public List<Organisme> OrganismeLazy(int skip, int take, String filter, String orderBy) {
//
//
//        List<Organisme> retour = new ArrayList<Organisme>();
//        Query q1 = null;
//        String allquery = "";
//
//
//        String part0 = "SELECT * FROM metier_carnet_organisme where 1  ";
//
//
//        allquery += part0;
//        String ch = "";
//        String w = "";
//        //  System.out.println("filter " + filter);
//
//        if (!filter.equals("undefined")) {
//            String x = filter;
////            String[] T = x.split(",and,");
//            String[] T = x.split(",XR002,");
//            for (int i = 0; i < T.length; i++) {
//                if (T[i].contains(",or,")) {
//                    w = T[i];
//                    String[] R = w.split(",or,");
//                    String v0 = R[0];
//                    String[] R00 = v0.split(",");
//                    // System.out.println("R00   " + R00);
//                    if (R00[1].contains("XR001")) {
//                        ch += " AND " + "(" + R00[0] + " like '%" + R00[2].trim() + "%'";
//                    } else {
//                        ch += " AND " + "(" + R00[0] + " " + R00[1] + " '" + R00[2] + "'";
//                    }
//
//                    for (int y = 1; y < R.length; y++) {
////                    for (int y = 0; y < R.length; y++) {
//                        String v = R[y];
//                        String[] R0 = v.split(",");
//                        if (y != R.length - 1) {
//
//                            if (R0[1].contains("XR001")) {
//                                ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'";
//                            } else {
//
//                                ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'";
//                            }
//
//                        } else {
//                            if (R0[1].contains("XR001")) {
//                                ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'" + ")";
//                            } else {
//
//                                ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'" + ")";
//                            }
//                        }
//
//                    }
//                } else {
//                    w = T[i];
//                    //  System.out.println("w =>" + w);
//                    String[] R = w.split(",");
//                    //  System.out.println("R[2]>>>" + R[2]);
//                    if (R[1].contains("XR001")) {
//                        ch += " AND " + R[0] + " like '%" + R[2].trim() + "%'";
//                    } else {
//                        ch += " AND " + R[0] + " " + R[1] + " '" + R[2] + "'";
//
//                    }
//                }
//            }
//        }
//
//        allquery += ch;
////        if (orderBy.equals("undefined")) {
////            allquery += " ORDER BY datearrivee desc ";
////        }
//        if (!orderBy.equals("undefined")) {
//            allquery += " ORDER BY " + orderBy;
//        }
//        allquery += " LIMIT " + take + " OFFSET " + skip;
////    allquery += " OFFSET " + skip + " ROWS FETCH NEXT " + take+" ROWS ONLY";
//        //       System.out.println("all Query ==> " + allquery);
////        if (allquery.indexOf("activityName") != -1)
////            allquery = allquery.replace("activityName", "activity_name");
//        q1 = (Query) em.createNativeQuery(allquery, Organisme.class);
//        retour = q1.getResultList();
//
//        return retour;
//
//    }
//
//
//    public <T> List<T> getFields(@RequestParam String classe)
//            throws ClassNotFoundException, IOException {
//        ArrayList<String> Listfields = new ArrayList<String>();
//        // returns the Class object for this class
//        Class myClass = Class.forName("com.example.MaiManager.domain." + classe);
//
//        Field[] listeFields = myClass.getDeclaredFields();
//        ArrayList<String> ecarter = new ArrayList<>();
//
//        for (int s = 0; s < listeFields.length; s++) {
//            Annotation[] annotations = (Annotation[]) listeFields[s].getAnnotations();
//            // System.out.println("***"+ listeFields[s].getName()+"-------"+listeFields[s].getAnnotations().length);
//
//            for (int r = 0; r < annotations.length; r++) {
//                //  System.out.println(annotations[r].toString());
//                if ((annotations[r].toString().indexOf("One") != -1 || annotations[r].toString().indexOf("Many") != -1)) {
//                    if (ecarter.indexOf(listeFields[s].toString()) == -1)
//                        ecarter.add(listeFields[s].toString().substring(listeFields[s].toString().lastIndexOf(".") + 1));
//                }
//
//            }
//
//        }
////        for (int j = 0; j < ecarter.size(); j++) {
////            System.out.println(ecarter.get(j));
////        }
//
////        System.out.println("Class represented by myClass: "
////                + myClass.toString());
//
//        // Get the fields of myClass
//        // using getDeclaredFields() method
//        String fields = Arrays.toString(myClass.getDeclaredFields());
//        String[] tableauFields = fields.split(",");
//        String field = "";
//        for (int i = 0; i < tableauFields.length; i++) {
//            field = tableauFields[i].substring(tableauFields[i].lastIndexOf(".") + 1);
//            field = field.replace("]", "");
//            if (Listfields.indexOf(field) == -1)
//                Listfields.add(field);
//
//        }
//
//
//        return (List<T>) intersection(Listfields, ecarter);
//    }
//
//
//    public String getCharFromString(String str) {
//        char s;
//        List<Char> liste = new ArrayList<>();
//        for (int i = 0; i < str.length(); i++) {
//            s = str.toCharArray()[i];
//            if (Character.isUpperCase(s))
//                str = str.replace(("" + s), ("_" + Character.toLowerCase(s)));
//
//        }
//        // System.out.println("str"+str);
//        return str;
//
//    }
//
//
//    public BigInteger countOrganismes() {
//
//        String credentiels = ListGroupsRoles();
//        // System.out.println("credentiels ==>>> "+ credentiels);
//        List<Integer> retour = new ArrayList<Integer>();
//        Query q1 = null;
////        String allquery = "SELECT count(acl_entry.acl_object_identity) FROM acl_entry WHERE sid IN " +
////                "(SELECT id FROM acl_sid WHERE sid IN ("+  credentiels + "))"+
////                "AND acl_entry.acl_object_identity IN (SELECT acl_object_identity.object_id_identity FROM acl_object_identity where acl_object_identity.object_id_class=1)";
//
//        String allquery = "SELECT count(*) FROM metier_carnet_organisme ";
//
//        q1 = (Query) em.createNativeQuery(allquery);
//        // System.out.println("query" + q1);
//        return (BigInteger) q1.getResultList().get(0);
//
//    }
//
//    public BigInteger countContacts() {
//
//        String credentiels = ListGroupsRoles();
//        // System.out.println("credentiels ==>>> "+ credentiels);
//        List<Integer> retour = new ArrayList<Integer>();
//        Query q1 = null;
////        String allquery = "SELECT count(acl_entry.acl_object_identity) FROM acl_entry WHERE sid IN " +
////                "(SELECT id FROM acl_sid WHERE sid IN ("+  credentiels + "))"+
////                "AND acl_entry.acl_object_identity IN (SELECT acl_object_identity.object_id_identity FROM acl_object_identity where acl_object_identity.object_id_class=1)";
//
//        String allquery = "SELECT count(*) FROM metier_carnet_contact ";
//
//        q1 = (Query) em.createNativeQuery(allquery);
//        //  System.out.println("query" + q1);
//        return (BigInteger) q1.getResultList().get(0);
//
//    }
//
//    public LocalDate getFirstDay() {
//        Locale locale = Locale.getDefault();
//        ZoneId TZ = ZoneId.of("UTC");
//        DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
//        return LocalDate.now(TZ).with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
//    }
//
//    public LocalDate getLastDay(DayOfWeek firstDayOfWeek) {
//        ZoneId TZ = ZoneId.of("UTC");
//        DayOfWeek lastDayOfWeek = DayOfWeek.of(((firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);
//        return LocalDate.now(TZ).with(TemporalAdjusters.nextOrSame(lastDayOfWeek));
//    }
//
//
//    public static Date getWeekStartDate() {
//        Calendar calendar = Calendar.getInstance();
//        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
//            calendar.add(Calendar.DATE, -1);
//        }
//        return calendar.getTime();
//    }
//
//    public static Date getWeekEndDate() {
//        Calendar calendar = Calendar.getInstance();
//        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
//            calendar.add(Calendar.DATE, 1);
//        }
//        calendar.add(Calendar.DATE, -1);
//        return calendar.getTime();
//    }
//
//    public List<CourrierArrive> FindCent(String paramSearch, String typecourrier, int skip, int take, String filter, String orderBy
//    ) throws IOException, ClassNotFoundException {
//
//
//        Locale locale = Locale.getDefault();
//        ZoneId TZ = ZoneId.of("UTC");
//        DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
//
//        String paramcard = "";
//        switch (paramSearch) {
//            case "day": {
//                LocalDate currentdate = LocalDate.now();
//                //  System.out.println("Current date: " + currentdate);
//                paramcard = " and datearrivee ='" + currentdate + "'";
//
//                break;
//            }
//            case "week": {
//                paramcard = " and  datearrivee <=  '" + getLastDay(firstDayOfWeek) + " ' and  datearrivee >= '" + getFirstDay() + "'";
//
//                break;
//            }
//            case "month": {
//                LocalDate currentdate = LocalDate.now();
//
//                LocalDate monthBegin = currentdate.withDayOfMonth(1);
//                LocalDate monthEnd = currentdate.plusMonths(1).withDayOfMonth(1).minusDays(1);
//
//
//                // System.out.println("begin month: " + monthBegin);
//                // System.out.println("end month: " + monthEnd);
//
//
//                paramcard = " and  datearrivee <=  '" + monthEnd + " ' and  datearrivee >= '" + monthBegin + "'";
//
//                break;
//            }
//            case "tous": {
//
//                paramcard = "";
//
//                break;
//            }
//
//            default:
//                // code block
//        }
//
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String credentiels = getMyArrayListString(authentication.getPrincipal().toString());
//        //   System.out.println("credentiels ==>>> " + credentiels);
//        int idclasse = -1;
//        AclClass aclClass = aclClassRepository.findByClasse("com.example.MaiManager.domain.CourrierArrive").get();
//
//        if (aclClass.getId() != null)
//            idclasse = aclClass.getId();
//
//
//        String allquery = "";
//        String querySelect = "";
//        //  if( typecourrier.equals("CourrierArrive")) {
//
//        List<String> fields = getFields("CourrierArrive");
//
//        //String viewCa = "SELECT * FROM ca";
//        String viewCa = "SELECT distinct object_id_identity from ca where sid IN (" + credentiels + ") and  object_id_class=" + idclasse;
//
//        Query q = (Query) em.createNativeQuery(viewCa);
//
//        List<BigInteger> liste = (List<BigInteger>) q.getResultList();
//
//        String listeString = "";
//        //if(listeString.indexOf(liste.get(j).getObject_id_identity())==-1)
//        // listeString+=(liste.get(j).getObject_id_identity());
//        //  listeString+=",";
//        if (liste.size() == 0)
//
//            return new ArrayList<CourrierArrive>();
//
//        else {
//            for (int j = 0; j < liste.size(); j++) {
////                if(listeString.indexOf(String.valueOf(liste.get(j)))==-1)
//                listeString += (String.valueOf(liste.get(j)));
//                listeString += ",";
//            }
//
//
//            if (listeString.indexOf(",,") != -1)
//                listeString = listeString.replaceAll(",,", ",");
//
//            //  and sid IN (" + credentiels + ")
//            querySelect = "SELECT * FROM doc_courrier_arrive where activity_name!='Enregistrement' ";
//            querySelect += paramcard;
//
//            querySelect += " and id IN (" + listeString.substring(0, listeString.length() - 1) + ") ";
//
//
//            allquery += querySelect;
//            String ch = "";
//            String w = "";
//            //  System.out.println("filter " + filter);
//
//            if (!filter.equals("undefined")) {
//                String x = filter;
////            String[] T = x.split(",and,");
//                String[] T = x.split(",XR002,");
//
//                for (int i = 0; i < T.length; i++) {
//                    if (T[i].contains(",or,")) {
//                        w = T[i];
//                        String[] R = w.split(",or,");
//                        String v0 = R[0];
//                        String[] R00 = v0.split(",");
//                        // System.out.println("R00   " + R00);
//                        if (R00[1].contains("XR001")) {
//                            ch += " AND " + "(" + R00[0] + " like '%" + R00[2].trim() + "%'";
//                        } else {
//                            ch += " AND " + "(" + R00[0] + " " + R00[1] + " '" + R00[2] + "'";
//                        }
//
//                        for (int y = 1; y < R.length; y++) {
////                    for (int y = 0; y < R.length; y++) {
//                            String v = R[y];
//                            String[] R0 = v.split(",");
//                            if (y != R.length - 1) {
//
//                                if (R0[1].contains("XR001")) {
//                                    ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'";
//                                } else {
//
//                                    ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'";
//                                }
//
//                            } else {
//                                if (R0[1].contains("XR001")) {
//                                    ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'" + ")";
//                                } else {
//
//                                    ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'" + ")";
//                                }
//                            }
//
//                        }
//                    } else {
//                        w = T[i];
//                        //    System.out.println("w =>" + w);
//                        String[] R = w.split(",");
//                        //     System.out.println("R[2]>>>" + R[2]);
//                        if (R[1].contains("XR001")) {
//                            ch += " AND " + R[0] + " like '%" + R[2].trim() + "%'";
//                        } else {
//                            ch += " AND " + R[0] + " " + R[1] + " '" + R[2] + "'";
//
//                        }
//                    }
//                }
//            }
//
//            allquery += ch;
//
//
//            if (orderBy.equals("undefined")) {
//                allquery += " ORDER BY datearrivee desc ";
//            }
//            if (!orderBy.equals("undefined")) {
//                allquery += " ORDER BY " + orderBy + " desc";
//            }
//            allquery += " LIMIT " + take + " OFFSET " + skip;
////    allquery += " OFFSET " + skip + " ROWS FETCH NEXT " + take+" ROWS ONLY";
//            //   System.out.println("all Query ==> " + allquery);
//            if (allquery.indexOf("activityName") != -1)
//                allquery = allquery.replace("activityName", "activity_name");
//            if (allquery.indexOf("adresseExterneorganisme") != -1)
//                allquery = allquery.replace("adresseExterneorganisme", "adresse_externeorganisme");
//
//            if (allquery.indexOf("adresseExterneorganisme") != -1)
//                allquery = allquery.replace("adresseExterneorganisme", "adresse_externeorganisme");
//
//            if (allquery.indexOf("initiateurName") != -1)
//                allquery = allquery.replace("initiateurName", "initiateur_name");
//
//            if (allquery.indexOf("adresseExterne") != -1)
//                allquery = allquery.replace("adresseExterne", "adresse_externe");
//
//
//            if (allquery.indexOf("adresseExterneorganisme") != -1)
//                allquery = allquery.replace("adresseExterneorganisme", "adresse_externeorganisme");
//
//            //  }
//
//
//            //  System.out.println("centReq::"+allquery);
//
//            Query q1 = em.createNativeQuery(allquery, CourrierArrive.class);
//
//            List<CourrierArrive> courriers = q1.getResultList();
//
//            return courriers;
//        }
//    }
//
//
//    public List<CourrierDepart> FindCentD(String paramSearch, String typecourrier, int skip, int take, String filter, String orderBy
//    ) throws IOException, ClassNotFoundException {
//
//
//        Locale locale = Locale.getDefault();
//        ZoneId TZ = ZoneId.of("UTC");
//        DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
//
//        String paramcard = "";
//        switch (paramSearch) {
//            case "day": {
//                LocalDate currentdate = LocalDate.now();
//                //  System.out.println("Current date: " + currentdate);
//                paramcard = " and datecourrier ='" + currentdate + "'";
//
//                break;
//            }
//            case "week": {
//                paramcard = " and  datecourrier <=  '" + getLastDay(firstDayOfWeek) + " ' and  datecourrier >= '" + getFirstDay() + "'";
//
//                break;
//            }
//            case "month": {
//                LocalDate currentdate = LocalDate.now();
//
//                LocalDate monthBegin = currentdate.withDayOfMonth(1);
//                LocalDate monthEnd = currentdate.plusMonths(1).withDayOfMonth(1).minusDays(1);
//
//
//                //  System.out.println("begin month: " + monthBegin);
//                //  System.out.println("end month: " + monthEnd);
//
//
//                paramcard = " and  datecourrier <=  '" + monthEnd + " ' and  datecourrier >= '" + monthBegin + "'";
//
//                break;
//            }
//            case "tous": {
//
//                paramcard = "";
//
//                break;
//            }
//
//            default:
//                // code block
//        }
//
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String credentiels = getMyArrayListString(authentication.getPrincipal().toString());
//        //   System.out.println("credentiels ==>>> " + credentiels);
//        int idclasse = -1;
//        AclClass aclClass = aclClassRepository.findByClasse("com.example.MaiManager.domain.CourrierDepart").get();
//
//        if (aclClass.getId() != null)
//            idclasse = aclClass.getId();
//
//
//        String allquery = "";
//        String querySelect = "";
//        //  if( typecourrier.equals("CourrierArrive")) {
//
//        List<String> fields = getFields("CourrierDepart");
//
//        //String viewCa = "SELECT * FROM ca";
//        String viewCa = "SELECT distinct object_id_identity from ca where sid IN (" + credentiels + ") and  object_id_class=" + idclasse;
//
//        Query q = (Query) em.createNativeQuery(viewCa);
//
//        List<BigInteger> liste = (List<BigInteger>) q.getResultList();
//
//        String listeString = "";
//        //if(listeString.indexOf(liste.get(j).getObject_id_identity())==-1)
//        // listeString+=(liste.get(j).getObject_id_identity());
//        //  listeString+=",";
//
//        for (int j = 0; j < liste.size(); j++) {
////                if(listeString.indexOf(String.valueOf(liste.get(j)))==-1)
//            listeString += (String.valueOf(liste.get(j)));
//            listeString += ",";
//        }
//
//
//        if (listeString.indexOf(",,") != -1)
//            listeString = listeString.replaceAll(",,", ",");
//
//        //  and sid IN (" + credentiels + ")
//        querySelect = "SELECT * FROM doc_courrier_depart where 1  ";
//        querySelect += paramcard;
//
//        querySelect += " and id IN (" + listeString.substring(0, listeString.length() - 1) + ") ";
//
//
//        allquery += querySelect;
//        String ch = "";
//        String w = "";
//        // System.out.println("filter " + filter);
//
//        if (!filter.equals("undefined")) {
//            String x = filter;
////            String[] T = x.split(",and,");
//            String[] T = x.split(",XR002,");
//
//            for (int i = 0; i < T.length; i++) {
//                if (T[i].contains(",or,")) {
//                    w = T[i];
//                    String[] R = w.split(",or,");
//                    String v0 = R[0];
//                    String[] R00 = v0.split(",");
//                    //  System.out.println("R00   " + R00);
//                    if (R00[1].contains("XR001")) {
//                        ch += " AND " + "(" + R00[0] + " like '%" + R00[2].trim() + "%'";
//                    } else {
//                        ch += " AND " + "(" + R00[0] + " " + R00[1] + " '" + R00[2] + "'";
//                    }
//
//                    for (int y = 1; y < R.length; y++) {
////                    for (int y = 0; y < R.length; y++) {
//                        String v = R[y];
//                        String[] R0 = v.split(",");
//                        if (y != R.length - 1) {
//
//                            if (R0[1].contains("XR001")) {
//                                ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'";
//                            } else {
//
//                                ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'";
//                            }
//
//                        } else {
//                            if (R0[1].contains("XR001")) {
//                                ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'" + ")";
//                            } else {
//
//                                ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'" + ")";
//                            }
//                        }
//
//                    }
//                } else {
//                    w = T[i];
//                    //    System.out.println("w =>" + w);
//                    String[] R = w.split(",");
//                    //    System.out.println("R[2]>>>" + R[2]);
//                    if (R[1].contains("XR001")) {
//                        ch += " AND " + R[0] + " like '%" + R[2].trim() + "%'";
//                    } else {
//                        ch += " AND " + R[0] + " " + R[1] + " '" + R[2] + "'";
//
//                    }
//                }
//            }
//        }
//
//        allquery += ch;
//
//
//        if (orderBy.equals("undefined")) {
//            allquery += " ORDER BY datecourrier desc ";
//        }
//        if (!orderBy.equals("undefined")) {
//            allquery += " ORDER BY " + orderBy + " desc";
//        }
//        allquery += " LIMIT " + take + " OFFSET " + skip;
////    allquery += " OFFSET " + skip + " ROWS FETCH NEXT " + take+" ROWS ONLY";
//        //   System.out.println("all Query ==> " + allquery);
//        if (allquery.indexOf("activityName") != -1)
//            allquery = allquery.replace("activityName", "activity_name");
//        if (allquery.indexOf("adresseExterneorganisme") != -1)
//            allquery = allquery.replace("adresseExterneorganisme", "adresse_externeorganisme");
//
//        if (allquery.indexOf("adresseExterneorganisme") != -1)
//            allquery = allquery.replace("adresseExterneorganisme", "adresse_externeorganisme");
//
//        if (allquery.indexOf("initiateurName") != -1)
//            allquery = allquery.replace("initiateurName", "initiateur_name");
//
//        if (allquery.indexOf("adresseExterne") != -1)
//            allquery = allquery.replace("adresseExterne", "adresse_externe");
//
//
//        if (allquery.indexOf("adresseExterneorganisme") != -1)
//            allquery = allquery.replace("adresseExterneorganisme", "adresse_externeorganisme");
//
//        //  }
//
//
//        //  System.out.println("centReq::"+allquery);
//
//        Query q1 = em.createNativeQuery(allquery, CourrierDepart.class);
//
//        List<CourrierDepart> courriers = q1.getResultList();
//
//        return courriers;
//    }
//
//
//    public List<CourrierInterne> FindCentI(String paramSearch, String typecourrier, int skip, int take, String filter, String orderBy
//    ) throws IOException, ClassNotFoundException {
//
//
//        Locale locale = Locale.getDefault();
//        ZoneId TZ = ZoneId.of("UTC");
//        DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
//
//        String paramcard = "";
//        switch (paramSearch) {
//            case "day": {
//                LocalDate currentdate = LocalDate.now();
//                //  System.out.println("Current date: " + currentdate);
//                paramcard = " and datecourrier ='" + currentdate + "'";
//
//                break;
//            }
//            case "week": {
//                paramcard = " and  datecourrier <=  '" + getLastDay(firstDayOfWeek) + " ' and  datecourrier >= '" + getFirstDay() + "'";
//
//                break;
//            }
//            case "month": {
//                LocalDate currentdate = LocalDate.now();
//
//                LocalDate monthBegin = currentdate.withDayOfMonth(1);
//                LocalDate monthEnd = currentdate.plusMonths(1).withDayOfMonth(1).minusDays(1);
//
//
//                //   System.out.println("begin month: " + monthBegin);
//                //  System.out.println("end month: " + monthEnd);
//
//
//                paramcard = " and  datecourrier <=  '" + monthEnd + " ' and  datecourrier >= '" + monthBegin + "'";
//
//                break;
//            }
//            case "tous": {
//
//                paramcard = "";
//
//                break;
//            }
//
//            default:
//                // code block
//        }
//
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String credentiels = getMyArrayListString(authentication.getPrincipal().toString());
//        //   System.out.println("credentiels ==>>> " + credentiels);
//        int idclasse = -1;
//        AclClass aclClass = aclClassRepository.findByClasse("com.example.MaiManager.domain.CourrierInterne").get();
//
//        if (aclClass.getId() != null)
//            idclasse = aclClass.getId();
//
//
//        String allquery = "";
//        String querySelect = "";
//        //  if( typecourrier.equals("CourrierArrive")) {
//
//        List<String> fields = getFields("CourrierInterne");
//
//        //String viewCa = "SELECT * FROM ca";
//        String viewCa = "SELECT distinct object_id_identity from ca where sid IN (" + credentiels + ") and  object_id_class=" + idclasse;
//
//        Query q = (Query) em.createNativeQuery(viewCa);
//
//        List<BigInteger> liste = (List<BigInteger>) q.getResultList();
//
//        String listeString = "";
//        //if(listeString.indexOf(liste.get(j).getObject_id_identity())==-1)
//        // listeString+=(liste.get(j).getObject_id_identity());
//        //  listeString+=",";
//
//        for (int j = 0; j < liste.size(); j++) {
////                if(listeString.indexOf(String.valueOf(liste.get(j)))==-1)
//            listeString += (String.valueOf(liste.get(j)));
//            listeString += ",";
//        }
//
//
//        if (listeString.indexOf(",,") != -1)
//            listeString = listeString.replaceAll(",,", ",");
//
//        //  and sid IN (" + credentiels + ")
//        querySelect = "SELECT * FROM doc_courrier_interne where 1  ";
//        querySelect += paramcard;
//
//        querySelect += " and id IN (" + listeString.substring(0, listeString.length() - 1) + ") ";
//
//
//        allquery += querySelect;
//        String ch = "";
//        String w = "";
//        // System.out.println("filter " + filter);
//
//        if (!filter.equals("undefined")) {
//            String x = filter;
////            String[] T = x.split(",and,");
//            String[] T = x.split(",XR002,");
//
//            for (int i = 0; i < T.length; i++) {
//                if (T[i].contains(",or,")) {
//                    w = T[i];
//                    String[] R = w.split(",or,");
//                    String v0 = R[0];
//                    String[] R00 = v0.split(",");
//                    //  System.out.println("R00   " + R00);
//                    if (R00[1].contains("XR001")) {
//                        ch += " AND " + "(" + R00[0] + " like '%" + R00[2].trim() + "%'";
//                    } else {
//                        ch += " AND " + "(" + R00[0] + " " + R00[1] + " '" + R00[2] + "'";
//                    }
//
//                    for (int y = 1; y < R.length; y++) {
////                    for (int y = 0; y < R.length; y++) {
//                        String v = R[y];
//                        String[] R0 = v.split(",");
//                        if (y != R.length - 1) {
//
//                            if (R0[1].contains("XR001")) {
//                                ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'";
//                            } else {
//
//                                ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'";
//                            }
//
//                        } else {
//                            if (R0[1].contains("XR001")) {
//                                ch += " OR " + R0[0] + " like '%" + R0[2].trim() + "%'" + ")";
//                            } else {
//
//                                ch += " OR " + R0[0] + " " + R0[1] + " '" + R0[2] + "'" + ")";
//                            }
//                        }
//
//                    }
//                } else {
//                    w = T[i];
//                    //     System.out.println("w =>" + w);
//                    String[] R = w.split(",");
//                    //     System.out.println("R[2]>>>" + R[2]);
//                    if (R[1].contains("XR001")) {
//                        ch += " AND " + R[0] + " like '%" + R[2].trim() + "%'";
//                    } else {
//                        ch += " AND " + R[0] + " " + R[1] + " '" + R[2] + "'";
//
//                    }
//                }
//            }
//        }
//
//        allquery += ch;
//
//
//        if (orderBy.equals("undefined")) {
//            allquery += " ORDER BY datecourrier desc ";
//        }
//        if (!orderBy.equals("undefined")) {
//            allquery += " ORDER BY " + orderBy + " desc";
//        }
//        allquery += " LIMIT " + take + " OFFSET " + skip;
////    allquery += " OFFSET " + skip + " ROWS FETCH NEXT " + take+" ROWS ONLY";
//        //   System.out.println("all Query ==> " + allquery);
//        if (allquery.indexOf("activityName") != -1)
//            allquery = allquery.replace("activityName", "activity_name");
////        if (allquery.indexOf("adresseExterneorganisme") != -1)
////            allquery = allquery.replace("adresseExterneorganisme", "adresse_externeorganisme");
////
////        if (allquery.indexOf("adresseExterneorganisme") != -1)
////            allquery = allquery.replace("adresseExterneorganisme", "adresse_externeorganisme");
////
////        if (allquery.indexOf("initiateurName") != -1)
////            allquery = allquery.replace("initiateurName", "initiateur_name");
////
////        if (allquery.indexOf("adresseExterne") != -1)
////            allquery = allquery.replace("adresseExterne", "adresse_externe");
////
////
////        if (allquery.indexOf("adresseExterneorganisme") != -1)
////            allquery = allquery.replace("adresseExterneorganisme", "adresse_externeorganisme");
//
//        //  }
//
//
//        //  System.out.println("centReq::"+allquery);
//
//        Query q1 = em.createNativeQuery(allquery, CourrierInterne.class);
//
//        List<CourrierInterne> courriers = q1.getResultList();
//
//        return courriers;
//    }
//
//
//    public BigInteger CountDashboard(String paramSearch) throws IOException, ClassNotFoundException {
//
//
//        Locale locale = Locale.getDefault();
//        ZoneId TZ = ZoneId.of("UTC");
//        DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
//
//        String paramcard = "";
//        switch (paramSearch) {
//            case "day": {
//                LocalDate currentdate = LocalDate.now();
//                // System.out.println("Current date: " + currentdate);
//                paramcard = " and datearrivee ='" + currentdate + "'";
//
//                break;
//            }
//            case "week": {
//                paramcard = " and  datearrivee <=  '" + getLastDay(firstDayOfWeek) + " ' and  datearrivee >= '" + getFirstDay() + "'";
//
//                break;
//            }
//            case "month": {
//                LocalDate currentdate = LocalDate.now();
//
//                LocalDate monthBegin = currentdate.withDayOfMonth(1);
//                LocalDate monthEnd = currentdate.plusMonths(1).withDayOfMonth(1).minusDays(1);
//
//
//                // System.out.println("begin month: " + monthBegin);
//                // System.out.println("end month: " + monthEnd);
//
//
//                paramcard = " and  datearrivee <=  '" + monthEnd + " ' and  datearrivee >= '" + monthBegin + "'";
//
//                break;
//            }
//            case "tous": {
//
//                paramcard = "";
//
//                break;
//            }
//
//            default:
//                // code block
//        }
//
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String credentiels = getMyArrayListString(authentication.getPrincipal().toString());
//        //  System.out.println("credentiels ==>>> " + credentiels);
//
//
//        int idclasse = -1;
//        AclClass aclClass = aclClassRepository.findByClasse("com.example.MaiManager.domain.CourrierArrive").get();
//
//        if (aclClass.getId() != null)
//            idclasse = aclClass.getId();
//
//
//        String allquery = "";
//        String querySelect = "";
//
//
//        List<String> fields = getFields("CourrierArrive");
//
//
//        //String viewCa = "SELECT * FROM ca";
//        String viewCa = "SELECT distinct object_id_identity from ca where sid IN (" + credentiels + ")  and  object_id_class=" + idclasse;
//
//        Query q = (Query) em.createNativeQuery(viewCa);
//
//        List<BigInteger> liste = (List<BigInteger>) q.getResultList();
//
//        String listeString = "";
//        //if(listeString.indexOf(liste.get(j).getObject_id_identity())==-1)
//        // listeString+=(liste.get(j).getObject_id_identity());
//        //  listeString+=",";
//
//        for (int j = 0; j < liste.size(); j++) {
////                if(listeString.indexOf(String.valueOf(liste.get(j)))==-1)
//            listeString += (String.valueOf(liste.get(j)));
//            listeString += ",";
//        }
//
//        if (liste.size() == 0)
//
//            return (BigInteger.valueOf(0));
//
//        else {
//            if (listeString.indexOf(",,") != -1)
//                listeString = listeString.replaceAll(",,", ",");
//
//            //  and sid IN (" + credentiels + ")
//            querySelect = "SELECT count(*) FROM doc_courrier_arrive where activity_name!='Enregistrement' ";
//            querySelect += paramcard;
//
//            querySelect += " and id IN (" + listeString.substring(0, listeString.length() - 1) + ") ";
//
//
//            allquery += querySelect;
//
//
//            // System.out.println("centReq::" + allquery);
//
//            Query q1 = em.createNativeQuery(allquery);
//
//            List<BigInteger> courriers = (List<BigInteger>) q1.getResultList();
//
//            return courriers.get(0);
//        }
//    }
//
//    public BigInteger CountDashboardD(String paramSearch) throws IOException, ClassNotFoundException {
//
//
//        Locale locale = Locale.getDefault();
//        ZoneId TZ = ZoneId.of("UTC");
//        DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
//
//        String paramcard = "";
//        switch (paramSearch) {
//            case "day": {
//                LocalDate currentdate = LocalDate.now();
//                // System.out.println("Current date: " + currentdate);
//                paramcard = " and datecourrier ='" + currentdate + "'";
//
//                break;
//            }
//            case "week": {
//                paramcard = " and  datecourrier <=  '" + getLastDay(firstDayOfWeek) + " ' and  datecourrier >= '" + getFirstDay() + "'";
//
//                break;
//            }
//            case "month": {
//                LocalDate currentdate = LocalDate.now();
//
//                LocalDate monthBegin = currentdate.withDayOfMonth(1);
//                LocalDate monthEnd = currentdate.plusMonths(1).withDayOfMonth(1).minusDays(1);
//
//
//                //  System.out.println("begin month: " + monthBegin);
//                //   System.out.println("end month: " + monthEnd);
//
//
//                paramcard = " and  datecourrier <=  '" + monthEnd + " ' and  datecourrier >= '" + monthBegin + "'";
//
//                break;
//            }
//            case "tous": {
//
//                paramcard = "";
//
//                break;
//            }
//
//            default:
//                // code block
//        }
//
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String credentiels = getMyArrayListString(authentication.getPrincipal().toString());
//        //  System.out.println("credentiels ==>>> " + credentiels);
//
//
//        int idclasse = -1;
//        AclClass aclClass = aclClassRepository.findByClasse("com.example.MaiManager.domain.CourrierDepart").get();
//
//        if (aclClass.getId() != null)
//            idclasse = aclClass.getId();
//
//
//        String allquery = "";
//        String querySelect = "";
//
//
//        List<String> fields = getFields("CourrierDepart");
//
//
//        //String viewCa = "SELECT * FROM ca";
//        String viewCa = "SELECT distinct object_id_identity from ca where sid IN (" + credentiels + ")  and  object_id_class=" + idclasse;
//
//        Query q = (Query) em.createNativeQuery(viewCa);
//
//        List<BigInteger> liste = (List<BigInteger>) q.getResultList();
//
//        String listeString = "";
//        //if(listeString.indexOf(liste.get(j).getObject_id_identity())==-1)
//        // listeString+=(liste.get(j).getObject_id_identity());
//        //  listeString+=",";
//
//        if (liste.size() == 0)
//
//            return (BigInteger.valueOf(0));
//
//        else {
//            for (int j = 0; j < liste.size(); j++) {
////                if(listeString.indexOf(String.valueOf(liste.get(j)))==-1)
//                listeString += (String.valueOf(liste.get(j)));
//                listeString += ",";
//            }
//
//
//            if (listeString.indexOf(",,") != -1)
//                listeString = listeString.replaceAll(",,", ",");
//
//            //  and sid IN (" + credentiels + ")
//            querySelect = "SELECT count(*) FROM doc_courrier_depart where 1 ";
//            querySelect += paramcard;
//
//            querySelect += " and id IN (" + listeString.substring(0, listeString.length() - 1) + ") ";
//
//
//            allquery += querySelect;
//
//
//            // System.out.println("centReq::" + allquery);
//
//            Query q1 = em.createNativeQuery(allquery);
//
//            List<BigInteger> courriers = (List<BigInteger>) q1.getResultList();
//
//            return courriers.get(0);
//        }
//    }
//
//    public BigInteger CountDashboardI(String paramSearch) throws IOException, ClassNotFoundException {
//
//
//        Locale locale = Locale.getDefault();
//        ZoneId TZ = ZoneId.of("UTC");
//        DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
//
//        String paramcard = "";
//        switch (paramSearch) {
//            case "day": {
//                LocalDate currentdate = LocalDate.now();
//                // System.out.println("Current date: " + currentdate);
//                paramcard = " and datecourrier ='" + currentdate + "'";
//
//                break;
//            }
//            case "week": {
//                paramcard = " and  datecourrier <=  '" + getLastDay(firstDayOfWeek) + " ' and  datecourrier >= '" + getFirstDay() + "'";
//
//                break;
//            }
//            case "month": {
//                LocalDate currentdate = LocalDate.now();
//
//                LocalDate monthBegin = currentdate.withDayOfMonth(1);
//                LocalDate monthEnd = currentdate.plusMonths(1).withDayOfMonth(1).minusDays(1);
//
//
//                //  System.out.println("begin month: " + monthBegin);
//                //    System.out.println("end month: " + monthEnd);
//
//
//                paramcard = " and  datecourrier <=  '" + monthEnd + " ' and  datecourrier >= '" + monthBegin + "'";
//
//                break;
//            }
//            case "tous": {
//
//                paramcard = "";
//
//                break;
//            }
//
//            default:
//                // code block
//        }
//
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String credentiels = getMyArrayListString(authentication.getPrincipal().toString());
//        //  System.out.println("credentiels ==>>> " + credentiels);
//
//
//        int idclasse = -1;
//        AclClass aclClass = aclClassRepository.findByClasse("com.example.MaiManager.domain.CourrierInterne").get();
//
//        if (aclClass.getId() != null)
//            idclasse = aclClass.getId();
//
//
//        String allquery = "";
//        String querySelect = "";
//
//
//        List<String> fields = getFields("CourrierInterne");
//
//
//        //String viewCa = "SELECT * FROM ca";
//        String viewCa = "SELECT distinct object_id_identity from ca where sid IN (" + credentiels + ")  and  object_id_class=" + idclasse;
//
//        Query q = (Query) em.createNativeQuery(viewCa);
//
//        List<BigInteger> liste = (List<BigInteger>) q.getResultList();
//
//        String listeString = "";
//        //if(listeString.indexOf(liste.get(j).getObject_id_identity())==-1)
//        // listeString+=(liste.get(j).getObject_id_identity());
//        //  listeString+=",";
//        if (liste.size() == 0)
//
//            return (BigInteger.valueOf(0));
//
//        else {
//            for (int j = 0; j < liste.size(); j++) {
////                if(listeString.indexOf(String.valueOf(liste.get(j)))==-1)
//                listeString += (String.valueOf(liste.get(j)));
//                listeString += ",";
//            }
//
//
//            if (listeString.indexOf(",,") != -1)
//                listeString = listeString.replaceAll(",,", ",");
//
//            //  and sid IN (" + credentiels + ")
//            querySelect = "SELECT count(*) FROM doc_courrier_interne where 1 ";
//            querySelect += paramcard;
//
//            querySelect += " and id IN (" + listeString.substring(0, listeString.length() - 1) + ") ";
//
//
//            allquery += querySelect;
//
//
//            // System.out.println("centReq::" + allquery);
//
//            Query q1 = em.createNativeQuery(allquery);
//
//            List<BigInteger> courriers = (List<BigInteger>) q1.getResultList();
//
//            return courriers.get(0);
//        }
//    }
//
//
//    public String getMail(String nom) {
//
//        List<List> UserDepatement = new ArrayList<List>();
//        String email = "";
//        String nameManger = "";
//        UserDepatement.add(usersList);
//        UserDepatement.add(ListeDepartements);
//        int index_samaccountname, index_name, index_mangedBy;
//        String chainedisplay, name = "", display = "", chainename = "", chainemangedBy = "", mangedBy = "";
//        String myString = "";
//        int index_mail = -1;
//        int size = UserDepatement.size();
//        //   System.out.println("get size " + size);
//
//
//        for (int i = 0; i < UserDepatement.size(); i++) {
//            for (int y = 0; y < UserDepatement.get(i).size(); y++) {
//
//
//                index_samaccountname = (UserDepatement.get(i).get(y).toString().indexOf("samaccountname="));
//                //  System.out.println("say helooo fom index"+index_samaccountname );
//
//                if (index_samaccountname == -1) {      //direction
//                    //     System.out.println(UserDepatement.get(i).get(y).toString());
//                    index_name = (UserDepatement.get(i).get(y).toString().indexOf("name="));
//                    chainename = UserDepatement.get(i).get(y).toString().substring(index_name + 5);
//                    //   System.out.println("chainename==>"+chainename);
//                    name = chainename.substring(0, chainename.indexOf(","));
//                    if (name.equals(nom)) {
//                        //mangedBy
//                        index_mangedBy = (UserDepatement.get(i).get(y).toString().indexOf("managedby="));
//                        chainemangedBy = UserDepatement.get(i).get(y).toString().substring(index_mangedBy + 13);
//                        mangedBy = chainemangedBy.substring(0, chainemangedBy.indexOf(","));
//
//
//                        for (int j = 0; j < usersList.size(); j++) {
//                            index_samaccountname = (usersList.get(j).toString().indexOf("samaccountname="));
//                            chainedisplay = usersList.get(j).toString().substring(index_samaccountname + 15);
//                            nameManger = chainedisplay.substring(0, chainedisplay.indexOf(","));
//                            if (nameManger.equals(mangedBy)) {
//                                index_mail = (usersList.get(j).toString().indexOf("mail="));
//                                String chainemail = usersList.get(j).toString().substring(index_mail + 5);
//                                email = chainemail.substring(0, chainemail.indexOf(","));
//                                //          System.out.println(email);
//                            }
//                        }
//                        break;
//                    }
//
//                } else { //personne
//                    chainedisplay = UserDepatement.get(i).get(y).toString().substring(index_samaccountname + 15);
//                    display = chainedisplay.substring(0, chainedisplay.indexOf(","));
//                    if (display.equals(nom)) {
//                        index_mail = (UserDepatement.get(i).get(y).toString().indexOf("mail="));
//                        String chainemail = UserDepatement.get(i).get(y).toString().substring(index_mail + 5);
//                        email = chainemail.substring(0, chainemail.indexOf(","));
//                        //   System.out.println(email);
//                        break;
//                    }
//                }
//            }
//
//
//        }
//
//
//        return email;
//    }
//
//    public BigInteger countCI() {
//
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String credentiels = getMyArrayListString(authentication.getPrincipal().toString());
//
//        List<Integer> retour = new ArrayList<Integer>();
//        Query q1 = null;
////        String allquery = "SELECT count(acl_entry.acl_object_identity) FROM acl_entry WHERE sid IN " +
////                "(SELECT id FROM acl_sid WHERE sid IN ("+  credentiels + "))"+
////                "AND acl_entry.acl_object_identity IN (SELECT acl_object_identity.object_id_identity FROM acl_object_identity where acl_object_identity.object_id_class=1)";
//        Integer ClassAclid=-1;
//        AclClass idClassAcl = aclClassRepository.findByClasse("com.example.MaiManager.domain.CourrierInterne").get();
//
//
//        if (idClassAcl.getId() != null)
//            ClassAclid = idClassAcl.getId();
//
//        String allquery = "SELECT count(*) FROM doc_courrier_interne where id IN (SELECT object_id_identity from ca where object_id_class="+ClassAclid+" and sid IN " +
//                "(" + credentiels + ")) ";
//
//        System.out.println("allqueryCount"+ allquery);
//        //  System.out.println("AllQuery=>" + allquery);
//        q1 = (Query) em.createNativeQuery(allquery);
//
//        // System.out.println("query" + q1);
//        return (BigInteger) q1.getResultList().get(0);
//    }
//
//    public BigInteger countCD() {
//
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String credentiels = getMyArrayListString(authentication.getPrincipal().toString());
//
//        List<Integer> retour = new ArrayList<Integer>();
//        Query q1 = null;
////        String allquery = "SELECT count(acl_entry.acl_object_identity) FROM acl_entry WHERE sid IN " +
////                "(SELECT id FROM acl_sid WHERE sid IN ("+  credentiels + "))"+
////                "AND acl_entry.acl_object_identity IN (SELECT acl_object_identity.object_id_identity FROM acl_object_identity where acl_object_identity.object_id_class=1)";
//        Integer ClassAclid=-1;
//        AclClass idClassAcl = aclClassRepository.findByClasse("com.example.MaiManager.domain.CourrierDepart").get();
//
//
//        if (idClassAcl.getId() != null)
//            ClassAclid = idClassAcl.getId();
//
//        String allquery = "SELECT count(*) FROM doc_courrier_depart where id IN (SELECT object_id_identity from ca where object_id_class="+ClassAclid+" and sid IN " +
//                "(" + credentiels + ")) ";
//
//        System.out.println("allqueryCount"+ allquery);
//        //  System.out.println("AllQuery=>" + allquery);
//        q1 = (Query) em.createNativeQuery(allquery);
//
//        // System.out.println("query" + q1);
//        return (BigInteger) q1.getResultList().get(0);
//
//    }
//
//    public BigInteger countCA() {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String credentiels = getMyArrayListString(authentication.getPrincipal().toString());
//
//        List<Integer> retour = new ArrayList<Integer>();
//        Query q1 = null;
////        String allquery = "SELECT count(acl_entry.acl_object_identity) FROM acl_entry WHERE sid IN " +
////                "(SELECT id FROM acl_sid WHERE sid IN ("+  credentiels + "))"+
////                "AND acl_entry.acl_object_identity IN (SELECT acl_object_identity.object_id_identity FROM acl_object_identity where acl_object_identity.object_id_class=1)";
//      Integer ClassAclid=-1;
//        AclClass idClassAcl = aclClassRepository.findByClasse("com.example.MaiManager.domain.CourrierArrive").get();
//
//
//        if (idClassAcl.getId() != null)
//            ClassAclid = idClassAcl.getId();
//
//        String allquery = "SELECT count(*) FROM doc_courrier_arrive where id IN (SELECT object_id_identity from ca where object_id_class="+ClassAclid+" and sid IN " +
//                "(" + credentiels + ")) ";
//
//        System.out.println("allqueryCount"+ allquery);
//        //  System.out.println("AllQuery=>" + allquery);
//        q1 = (Query) em.createNativeQuery(allquery);
//
//        // System.out.println("query" + q1);
//        return (BigInteger) q1.getResultList().get(0);
//
//    }
//
//
    public List<Group> getgroups(String samaccountname) {

        User user = userRepository.findBySamaccountname(samaccountname).get();

        String querySelect = "select * from pm_group where id in (select group_id from pm_group_users where user_id = " + user.getId() + ")";
        Query q1 = em.createNativeQuery(querySelect, Group.class);

        List<Group> groupList = q1.getResultList();

        return groupList;
    }
//
    public List<UserAccess> getAccessByUser(@RequestParam String samaccountname) {
        List<UserAccess> privList = this.userAccessService.getAccessByUser(samaccountname);


        return privList;
    }
//
    public List<Role> getRolesByProfile(final String name) {
        Query q = this.em.createNativeQuery("SELECT * FROM pm_role WHERE id in (SELECT pr.roles FROM pm_profile_role pr , pm_profile p WHERE pr.profile=p.id and p.profilename=?)", Role.class).setParameter(1, name);
        return (List<Role>) q.getResultList();
    }

  public List<String> getUsersByRole(String rolename) {
      List<String> users = new ArrayList<>();
      Optional<Role> role = roleRepository.findByRolename(rolename);
      for(Profile profile: role.get().getProfiles()){
        List<UserAccess> user  = userAccessService.getAllUsersByProfile(profile);
        for(UserAccess userAccess : user){
          if(users.indexOf(userAccess.getUser().getDisplayname()) == -1) {
            users.add(userAccess.getUser().getDisplayname());
          }
        }
      }
      return users;
  }

  public List<String> getAllDepartements() {
      List<String> departements = new ArrayList<>();
      List<Department> departementsTemp =  depRepository.findAll();
      for(Department departementTemp : departementsTemp){
        departements.add(departementTemp.getName());
      }
      return departements;
  }


    public JSONObject getMy(String samaccountname) {
        JSONObject AllmyList = new JSONObject();
        List<String> chefs = new ArrayList<>();

        List<String> orgaPrivilege = new ArrayList<>();
        String departement = "";

        /**************user***********************/
        AllmyList.put("user", samaccountname);
        /*****************dept**********************/
        User user = userRepository.findBySamaccountname(samaccountname).get();
        String s = "";
//        if (user.getAppartenance() != null)
//            departement = user.getAppartenance();
//        else {
//            s = user.getDistinguishedname().substring(user.getDistinguishedname().indexOf("OU=") + 3);
//            departement = s.substring(0, s.indexOf(','));
//        }

        orgaPrivilege.add(user.getDistinguishedname());

        /***************group AD*******************/
        String[] memberOf = null;

//        if (user.getMemberof() != null) {
//            memberOf = user.getMemberof().split(",");
//
//            for (int y = 0; y < memberOf.length; y++)
//                orgaPrivilege.add(memberOf[y]);
//
//
//        }
        /***************group métiers*******************/
        List<Group> groupList = getgroups(samaccountname);
        List<String> profilesOfGroups = new ArrayList<>();
//        if (groupList.size() > 0) {
//            for (int i = 0; i < groupList.size(); i++) {
//
//                profilesOfGroups.add(groupList.get(i).getProfile());
//                orgaPrivilege.add(groupList.get(i).getName());
//            }
//        }

        String distinguishedname = user.getDistinguishedname();

        /***************chef*******************/

//        List<Department> departmentList1 = depRepository.findAllByManagedby(distinguishedname);
//
//        if (departmentList1.size() > 0) {
//            for (int j = 0; j < departmentList1.size(); j++) {
//                if (orgaPrivilege.indexOf(departmentList1.get(j).getName()) == -1)
//                    orgaPrivilege.add(departmentList1.get(j).getName());
//                orgaPrivilege.add(departmentList1.get(j).getName() + "/sup");
//            }
//        }
        /***************Interim*******************/
//        List<Department> departmentList2 = depRepository.findAllByInterim(user.getDisplayname());
//        if (departmentList2.size() > 0) {
//            for (int j = 0; j < departmentList2.size(); j++) {
//                if (orgaPrivilege.indexOf(departmentList2.get(j).getName()) == -1)
//                    orgaPrivilege.add(departmentList2.get(j).getName());
//                orgaPrivilege.add(departmentList2.get(j).getName() + "/sup");
//            }
//        }
        AllmyList.put("orgaPrivilege", orgaPrivilege);
        /***************profile*******************/
        List<UserAccess> userAccessList = getAccessByUser(samaccountname);


        if (userAccessList.size() > 0) {
            AllmyList.put("profiles", userAccessList.get(0).getProfile().getProfilename());


            List<String> roles = new ArrayList<>();
            List<Role> roleList = getRolesByProfile(userAccessList.get(0).getProfile().getProfilename());
            for (int k = 0; k < roleList.size(); k++) {
                roles.add(roleList.get(k).getRolename());
            }
            AllmyList.put("roles", roles);
        } else {

            if (profilesOfGroups.size() > 0) {
                String LastProfile = profilesOfGroups.get(profilesOfGroups.size() - 1);
                AllmyList.put("profiles", LastProfile);
                List<String> roles = new ArrayList<>();
                List<Role> roleList = getRolesByProfile(LastProfile);
                for (int k = 0; k < roleList.size(); k++) {
                    roles.add(roleList.get(k).getRolename());
                }
                AllmyList.put("roles", roles);
            }
            if (profilesOfGroups.size() == 0) {
                AllmyList.put("profiles", "");
                AllmyList.put("roles", "");
            }
        }
        AllmyList.put("application", "mm8");
        return AllmyList;
    }
//
//    public ArrayList<String> getMyArrayList(String samaccountname) {
//
//        ArrayList<String> orgaPrivilege = new ArrayList<>();
//        String departement = "";
//
//
//        /*****************dept**********************/
//        User user = userRepository.findBySamaccountname(samaccountname).get();
//        String s = "";
//        if (user.getAppartenance() != null)
//            departement = user.getAppartenance();
//        else {
//            s = user.getDistinguishedname().substring(user.getDistinguishedname().indexOf("OU=") + 3);
//            departement = s.substring(0, s.indexOf(','));
//        }
//        orgaPrivilege.add(user.getSamaccountname());
//        orgaPrivilege.add(user.getDisplayname());
//        orgaPrivilege.add(departement);
//
//        /***************group AD*******************/
//        String[] memberOf = null;
//
//        if (user.getMemberof() != null) {
//            memberOf = user.getMemberof().split(",");
//
//            for (int y = 0; y < memberOf.length; y++)
//                orgaPrivilege.add(memberOf[y]);
//
//
//        }
//        /***************group métiers*******************/
//        List<Group> groupList = getgroups(samaccountname);
//        List<String> profilesOfGroups = new ArrayList<>();
//        if (groupList.size() > 0) {
//            for (int i = 0; i < groupList.size(); i++) {
//
//                profilesOfGroups.add(groupList.get(i).getProfile());
//                orgaPrivilege.add(groupList.get(i).getName());
//            }
//        }
//
//        String distinguishedname = user.getDistinguishedname();
//
//        /***************chef*******************/
//
//        List<Department> departmentList1 = depRepository.findAllByManagedby(distinguishedname);
//
//        if (departmentList1.size() > 0) {
//            for (int j = 0; j < departmentList1.size(); j++) {
//                if (orgaPrivilege.indexOf(departmentList1.get(j).getName()) == -1)
//                    orgaPrivilege.add(departmentList1.get(j).getName());
//                orgaPrivilege.add(departmentList1.get(j).getName() + "/sup");
//            }
//        }
//        /***************Interim*******************/
//        List<Department> departmentList2 = depRepository.findAllByInterim(user.getDisplayname());
//        if (departmentList2.size() > 0) {
//            for (int j = 0; j < departmentList2.size(); j++) {
//                if (orgaPrivilege.indexOf(departmentList2.get(j).getName()) == -1)
//                    orgaPrivilege.add(departmentList2.get(j).getName());
//                orgaPrivilege.add(departmentList2.get(j).getName() + "/sup");
//            }
//        }
//
//        /***************profile*******************/
//        List<UserAccess> userAccessList = getAccessByUser(samaccountname);
//
//
//        if (userAccessList.size() > 0) {
//            orgaPrivilege.add(userAccessList.get(0).getProfile().getProfilename());
//
//
//            List<Role> roleList = getRolesByProfile(userAccessList.get(0).getProfile().getProfilename());
//            for (int k = 0; k < roleList.size(); k++) {
//
//                orgaPrivilege.add(roleList.get(k).getRolename());
//            }
//
//        } else {
//
//            if (profilesOfGroups.size() > 0) {
//                String LastProfile = profilesOfGroups.get(profilesOfGroups.size() - 1);
//                orgaPrivilege.add(LastProfile);
//                List<String> roles = new ArrayList<>();
//                List<Role> roleList = getRolesByProfile(LastProfile);
//                for (int k = 0; k < roleList.size(); k++) {
//                    orgaPrivilege.add(roleList.get(k).getRolename());
//                }
//
//            }
//        }
//
//        return orgaPrivilege;
//    }
//
//    public String getMyArrayListString(String samaccountname) {
//        String GroupRol = "";
//        ArrayList<String> arrayList = getMyArrayList(samaccountname);
//        for (int k = 0; k < arrayList.size(); k++)
//            GroupRol += "'" + arrayList.get(k) + "', ";
//        GroupRol = GroupRol.substring(0, GroupRol.length() - 2);
//
//        return GroupRol;
//
//    }
//
//    public Connection getDatabaseConnection() throws Exception {
//        Connection con = null;
//        try {
//            ClassLoader loader = Thread.currentThread().getContextClassLoader();
//            Properties properties = new Properties();
//            InputStream is = loader.getResourceAsStream("application.properties");
//            properties.load(is);
//            String dbUrl = properties.getProperty("spring.datasourceextern.url");
//            String dbUser = properties.getProperty("spring.datasourceextern.username");
//            String dbPwd = properties.getProperty("spring.datasourceextern.password");
//            String dbClass = properties.getProperty("spring.datasourceextern.driver-class-name");
//
//
//            Class.forName(dbClass);
//            con = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
//        } catch (Exception e) {
//            throw e;
//        }
//        return con;
//
//    }
//
//    public void InsertAclEntry(String CompteSid, ObjectIdentity oid, Integer idclasse, Integer mask) {
//
//        Integer ACLSID = -1;
//        String InsertAclEntry = "";
//        String objectid = "";
//        AclSid objetSid = null;
//        System.out.println("In SAVE with Satus === 000 ");
//
//        if (aclSidRepository.findBySid(CompteSid) != null) {
//            ACLSID = aclSidRepository.findBySid(CompteSid).getId();
//        } else {
//            objetSid = new AclSid(true, CompteSid);
//            ACLSID = (aclSidRepository.save(objetSid)).getId();
//        }
//
//        Long oidlong = (Long) oid.getIdentifier();
//
//        //List<AclEntry> listeAclentry = aclEntryRepository.findAclEntryByAcl_object_identityAndSidAndMask(aclObjectIdentity, aclSidRepository.findBySid(CompteSid), mask);
//        objectid = "(SELECT id FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier() + " and object_id_class=" + idclasse + ")";
//        String fetchAclEntryByAcl_object_identityAndSidAndMask = "select * from acl_entry where acl_object_identity=" + objectid + " and sid=" + ACLSID + " and mask=" + mask;
//        //System.out.println("fetchAclEntryByAcl_object_identityAndSidAndMask\n"+fetchAclEntryByAcl_object_identityAndSidAndMask);
//        Query query = em.createNativeQuery(fetchAclEntryByAcl_object_identityAndSidAndMask);
//
//        List<AclEntry> listeAclentry = (List<AclEntry>) query.getResultList();
//
//
//        if (listeAclentry.size() == 0) {
//            objectid = "(SELECT id FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier() + " and object_id_class=" + idclasse + ")";
//
//            InsertAclEntry = "INSERT INTO acl_entry(acl_object_identity, ace_order,sid, mask, granting, audit_success, audit_failure) VALUES" +
//                    "(" + objectid + ",1," + ACLSID + "," + mask + ", 1, 1, 1) ";
//        }
//
//
//        // System.out.println("In SAVE with Satus === 000 " + InsertAclEntry);
//        try {
//            Connection conn = getDatabaseConnection();
//            Statement st = conn.createStatement();
//            st.executeUpdate(InsertAclEntry);
//        } catch (Exception e) {
//        }
//
//    }
//
//    public CourrierArrive SaveCourrierA(CourrierArrive courrierArrivee, List<String> auteurs, List<String> lecteurs, int statut, HttpServletRequest request) throws UnknownHostException {
//
////*********************get Authentication objet***************
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("authentification !!!!!!" + authentication.getPrincipal().toString());
//
//        String typeOp = "";
//        CourrierArrive savedcourrier = courrierArrivee;
//
//        //Journalisation/////
//        Date date = new Date();
//        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
//
//        ObjectIdentity oid = null;
//
//        // Correction du retard d'un jour
//        courrierArrivee.setDatearrivee(LocalDate.parse(courrierArrivee.getDatearrivee().toString()).plusDays(1));
//        courrierArrivee.setDatecourrier(LocalDate.parse(courrierArrivee.getDatecourrier().toString()).plusDays(1));
//        MutableAcl acl = null;
//
//        if (courrierArrivee.getId() == null) {
//
//            typeOp = "Ajout";
//            savedcourrier = courrierArriveRepository.save(courrierArrivee);
//            oid = new ObjectIdentityImpl(CourrierArrive.class, savedcourrier.getId());
//            System.out.println(oid.getIdentifier());
//            acl = aclService.createAcl(oid);
//
//            Journals journal = new Journals(authentication.getName(), journalService.getIp(request).get("RemoteAddr"), date, userAgent.getBrowser().getName(), typeOp, userAgent.getOperatingSystem().getDeviceType().toString(), savedcourrier);
//            System.out.println("Journal " + journal);
//            journalsRepository.save(journal);
//
//
//        } else {
//
//            Query q = null;
//            typeOp = "Mise à jour";
//
//            System.out.println("ID avant sauvegarde ::::!!!!!" + courrierArrivee.getId());
//
//            savedcourrier = courrierArriveRepository.save(courrierArrivee);
//
//            System.out.println("ID apres sauvegarde ::::!!!!!" + savedcourrier.getId());
//
//
//            Journals journal = new Journals(authentication.getName(), journalService.getIp(request).get("RemoteAddr"), date, userAgent.getBrowser().getName(), typeOp, userAgent.getOperatingSystem().getDeviceType().toString(), savedcourrier);
//
//            journalsRepository.save(journal);
//
//            oid = new ObjectIdentityImpl(CourrierArrive.class, savedcourrier.getId());
//            System.out.println("object ID  ====" + oid.getIdentifier());
//
//            acl = (MutableAcl) aclService.readAclById(oid);
//            //aclService.updateAcl(acl);
//
//
//            //System.out.println("sizee after =  " + acl.getEntries().size());
//        }
//        int idclasse = -1;
//        String querySelect = "";
//        String queryUpdate = "";
//        AclClass aclClass = aclClassRepository.findByClasse("com.example.MaiManager.domain.CourrierArrive").get();
//
//        if (aclClass.getId() != null)
//            idclasse = aclClass.getId();
//
//        if (statut == 0) {
//            this.InsertAclEntry(authentication.getPrincipal().toString(), oid, idclasse, 2);
//        }
//
//        if (statut == 2) {
//            this.InsertAclEntry("BOC", oid, idclasse, 2);
//            savedcourrier.setAuthors("BOC");
//            savedcourrier = courrierArriveRepository.save(courrierArrivee);
//        }
//        if (statut == 1) {
//            //System.out.println("lecteurs list ===> " + lecteurs.get(0));
//            System.out.println("STATUUTTTTTTTTTTTTTT::::!!!!!" + oid.getIdentifier());
//
//            String objectid = "SELECT id FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier() + " and object_id_class=" + idclasse;
//            // anciens auteurs deviennent des lecteurs
//            querySelect = "SELECT id FROM acl_entry where acl_object_identity in (" + objectid + ") and mask=2";
//
//
//            System.out.println("QuerySelect::::!!!!!" + querySelect);
//
//            Query q1 = em.createNativeQuery(querySelect);
//
//            // List<AclEntry> aclEntries = q1.getResultList();
//
//            List<BigInteger> aclEntries = (List<BigInteger>) q1.getResultList();
//
//            String UpdateQuery = "Update acl_entry set Mask=1 where id in (";
//
//            try {
//
//                Connection conn = getDatabaseConnection();
//
//                System.out.println(" change Mask ");
//                for (int s = 0; s < aclEntries.size(); s++) {
//                    // System.out.println("elem::"+aclEntries.get(s));
//
////                AclEntry aclEntry = aclEntries.get(s);
////                aclEntry.setMask(1);
////                aclEntryRepository.save(aclEntry);
//                    UpdateQuery += aclEntries.get(s) + ",";
//
//                }
//                UpdateQuery = UpdateQuery.substring(0, UpdateQuery.length() - 1) + ")";
//                //   System.out.println("QueryMask::"+UpdateQuery);
//                Statement st = conn.createStatement();
//                st.executeUpdate(UpdateQuery);
//            } catch (Exception e) {
//            }
//
//        }
//
//
//        int returneds = 0;
//        try {
//
//            Connection conn = getDatabaseConnection();
//            //     System.out.println("auteurs size::::::::::::::"+lecteurs.size());
//
//            if (auteurs.size() > 0) {
//
//                // System.out.println("In Auteurs::::::::::::::::::::::::::::::::::::");
//                String ListToInsert = "";
//                String QueryACLEntry = "INSERT INTO acl_entry(acl_object_identity, ace_order,sid, mask, granting, audit_success, audit_failure) VALUES";
//
//                for (int i = 0, s = 0; i < auteurs.size(); i++, s++) {
//                    //   System.out.println("In Auteurs::::::::::::::::::::::::::::::::::::"+auteurs.size());
//
//                    if (aclSidRepository.findBySid(auteurs.get(i)) != null) {
//                        Integer ACLSID = aclSidRepository.findBySid(auteurs.get(i)).getId();
//
//                        // String QueryACLObjectIdentity = "SELECT * FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier()+" and object_id_class="+idclasse;
//
//                        String objectid = "(SELECT id FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier() + " and object_id_class=" + idclasse + ")";
//                        String fetchAclEntryByAcl_object_identityAndSidAndMask = "select * from acl_entry where acl_object_identity=" + objectid + " and sid=" + ACLSID + " and mask=2";
//
//                        //   System.out.println("fetchAclEntryByAcl_object_identityAndSidAndMask\n" + fetchAclEntryByAcl_object_identityAndSidAndMask);
//                        Query query = em.createNativeQuery(fetchAclEntryByAcl_object_identityAndSidAndMask);
//
//                        List<AclEntry> listeAclentry = (List<AclEntry>) query.getResultList();
//
//
//                        if (listeAclentry.size() == 0) {
//                           // Long r = (Long) oid.getIdentifier();
//
//                            Query qq=  em.createNativeQuery(objectid);
//
//                            List<BigInteger> listeidobject = (List<BigInteger>) qq.getResultList();
//
//                            if (i == 0)
//                                s += 1;
//                            // ListToInsert +="(" + (aclEntryRepository.findAll().size() +1) + ", " + retour1.get(0).getId() + "," + s + " ," + ACLSID + ", 1, 1, 1, 1)";
//                            // s += 1;
//                            //    System.out.println("Auteur to Insert == " + acl.getEntries().size() + 1 + i);
//                            ListToInsert += "(" + listeidobject.get(0) + "," + s + " ," + ACLSID + ", 2, 1, 1, 1),";
//                            returneds = s;
//
//
//                        }
//                    } else {
//                        this.InsertAclEntry(auteurs.get(i), oid, idclasse, 2);
//
//                    }
//
//                }
//
//                QueryACLEntry += ListToInsert.substring(0, ListToInsert.length() - 1);
//                // System.out.println("QueryToExecute:::"+QueryACLEntry);
//                Statement st = conn.createStatement();
//                st.executeUpdate(QueryACLEntry);
//            }
//
//            //    System.out.println("lecteurs size::::::::::::::"+lecteurs.size());
//            if (lecteurs.size() > 0) {
//
//                String ListLecteurToInsert = "";
//                String QueryACLEntry = "INSERT INTO acl_entry(acl_object_identity, ace_order,sid, mask, granting, audit_success, audit_failure) VALUES";
//
//                for (int i = 0; i < lecteurs.size(); i++) {
//                    //   System.out.println("In Lecteurs::::::::::::::::::::::::::::::::::::");
//
//
//                    if (aclSidRepository.findBySid(lecteurs.get(i)) != null) {
//                        Integer ACLSID = aclSidRepository.findBySid(lecteurs.get(i)).getId();
//
//                        // String QueryACLObjectIdentity = "SELECT * FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier()+" and object_id_class="+idclasse;
//
//                        String objectid = "(SELECT id FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier() + " and object_id_class=" + idclasse + ")";
//                        String fetchAclEntryByAcl_object_identityAndSidAndMask = "select * from acl_entry where acl_object_identity=" + objectid + " and sid=" + ACLSID + " and mask=1";
//
//                        //   System.out.println("fetchAclEntryByAcl_object_identityAndSidAndMask\n" + fetchAclEntryByAcl_object_identityAndSidAndMask);
//                        Query query = em.createNativeQuery(fetchAclEntryByAcl_object_identityAndSidAndMask);
//
//                        List<AclEntry> listeAclentry = (List<AclEntry>) query.getResultList();
//
//
//                        if (listeAclentry.size() == 0) {
////                            Long r = (Long) oid.getIdentifier();
////
////                            Integer ObjectIdentity = aclObjectIdentityRepository.findByObjectIdentity(r.longValue()).getId();
//
//                            Query qq=  em.createNativeQuery(objectid);
//
//                            List<BigInteger> listeidobject = (List<BigInteger>) qq.getResultList();
//                            returneds= returneds + 1;
//                            // ListToInsert +="(" + (aclEntryRepository.findAll().size() +1) + ", " + retour1.get(0).getId() + "," + s + " ," + ACLSID + ", 1, 1, 1, 1)";
//                            // s += 1;
//                            //     System.out.println("Auteur to Insert == " + acl.getEntries().size() + 1 + i);
//                            ListLecteurToInsert += "(" + listeidobject.get(0) + "," + (returneds) + " ," + ACLSID + ", 1, 1, 1, 1),";
//
//                        }
//                    } else {
//
//                        this.InsertAclEntry(lecteurs.get(i), oid, idclasse, 1);
//                    }
//
//                }
//                QueryACLEntry += ListLecteurToInsert.substring(0, ListLecteurToInsert.length() - 1);
//                Statement st = conn.createStatement();
//                st.executeUpdate(QueryACLEntry);
//            }
//
//            System.out.println("finsaveA::::::::::::::");
//        } catch (Exception e) {
//        }
//        return savedcourrier;
//    }
//
//    public CourrierDepart SaveCourrierD(CourrierDepart courrierDepart, List<String> auteurs, List<String> lecteurs, int statut, HttpServletRequest request) throws IOException, MessagingException, ParseException {
//
//
////*********************get Authentication objet***************
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("authentification !!!!!!" + authentication.getPrincipal().toString());
//
//        String typeOp = "";
//        CourrierDepart savedcourrier = courrierDepart;
//
//        //Journalisation/////
//        Date date = new Date();
//        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
//
//        ObjectIdentity oid = null;
//
//        // Correction du retard d'un jour
//        //courrierDepart.setDatearrivee(LocalDate.parse(courrierDepart.getDatearrivee().toString()).plusDays(1));
//        courrierDepart.setDatecourrier(LocalDate.parse(courrierDepart.getDatecourrier().toString()).plusDays(1));
//        MutableAcl acl = null;
//
//        if (courrierDepart.getId() == null) {
//
//            typeOp = "Ajout";
//            savedcourrier = courrierDepartRepository.save(courrierDepart);
//            oid = new ObjectIdentityImpl(CourrierDepart.class, savedcourrier.getId());
//            System.out.println(oid.getIdentifier());
//            acl = aclService.createAcl(oid);
//
//            Journals journal = new Journals(authentication.getName(), journalService.getIp(request).get("RemoteAddr"), date, userAgent.getBrowser().getName(), typeOp, userAgent.getOperatingSystem().getDeviceType().toString(), savedcourrier);
//            System.out.println("Journal " + journal);
//            journalsRepository.save(journal);
//
//
//        } else {
//
//            Query q = null;
//            typeOp = "Mise à jour";
//
//            System.out.println("ID avant sauvegarde ::::!!!!!" + courrierDepart.getId());
//
//            savedcourrier = courrierDepartRepository.save(courrierDepart);
//
//            System.out.println("ID apres sauvegarde ::::!!!!!" + savedcourrier.getId());
//
//
//            Journals journal = new Journals(authentication.getName(), journalService.getIp(request).get("RemoteAddr"), date, userAgent.getBrowser().getName(), typeOp, userAgent.getOperatingSystem().getDeviceType().toString(), savedcourrier);
//
//            journalsRepository.save(journal);
//
//            oid = new ObjectIdentityImpl(CourrierDepart.class, savedcourrier.getId());
//            System.out.println("object ID  ====" + oid.getIdentifier());
//
//            acl = (MutableAcl) aclService.readAclById(oid);
//            //aclService.updateAcl(acl);
//
//
//            //System.out.println("sizee after =  " + acl.getEntries().size());
//        }
//        int idclasse = -1;
//        String querySelect = "";
//        String queryUpdate = "";
//        AclClass aclClass = aclClassRepository.findByClasse("com.example.MaiManager.domain.CourrierDepart").get();
//
//        if (aclClass.getId() != null)
//            idclasse = aclClass.getId();
//
//        if (statut == 0) {
//            this.InsertAclEntry(authentication.getPrincipal().toString(), oid, idclasse, 2);
//        }
//
//        if (statut == 2) {
//            this.InsertAclEntry("BOC", oid, idclasse, 2);
//            savedcourrier.setAuthors("BOC");
//            savedcourrier = courrierDepartRepository.save(courrierDepart);
//        }
//        if (statut == 1) {
//            //System.out.println("lecteurs list ===> " + lecteurs.get(0));
//            System.out.println("STATUUTTTTTTTTTTTTTT::::!!!!!" + oid.getIdentifier());
//
//            String objectid = "SELECT id FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier() + " and object_id_class=" + idclasse;
//            // anciens auteurs deviennent des lecteurs
//            querySelect = "SELECT id FROM acl_entry where acl_object_identity in (" + objectid + ") and mask=2";
//
//
//            System.out.println("QuerySelect::::!!!!!" + querySelect);
//
//            Query q1 = em.createNativeQuery(querySelect);
//
//            // List<AclEntry> aclEntries = q1.getResultList();
//
//            List<BigInteger> aclEntries = (List<BigInteger>) q1.getResultList();
//
//            String UpdateQuery = "Update acl_entry set Mask=1 where id in (";
//
//            try {
//
//                Connection conn = getDatabaseConnection();
//
//                System.out.println(" change Mask ");
//                for (int s = 0; s < aclEntries.size(); s++) {
//                    // System.out.println("elem::"+aclEntries.get(s));
//
////                AclEntry aclEntry = aclEntries.get(s);
////                aclEntry.setMask(1);
////                aclEntryRepository.save(aclEntry);
//                    UpdateQuery += aclEntries.get(s) + ",";
//
//                }
//                UpdateQuery = UpdateQuery.substring(0, UpdateQuery.length() - 1) + ")";
//                //   System.out.println("QueryMask::"+UpdateQuery);
//                Statement st = conn.createStatement();
//                st.executeUpdate(UpdateQuery);
//            } catch (Exception e) {
//            }
//
//        }
//
//
//        int returneds = 0;
//        try {
//
//            Connection conn = getDatabaseConnection();
//            //     System.out.println("auteurs size::::::::::::::"+lecteurs.size());
//
//            if (auteurs.size() > 0) {
//
//                // System.out.println("In Auteurs::::::::::::::::::::::::::::::::::::");
//                String ListToInsert = "";
//                String QueryACLEntry = "INSERT INTO acl_entry(acl_object_identity, ace_order,sid, mask, granting, audit_success, audit_failure) VALUES";
//
//                for (int i = 0, s = 0; i < auteurs.size(); i++, s++) {
//                    //   System.out.println("In Auteurs::::::::::::::::::::::::::::::::::::"+auteurs.size());
//
//                    if (aclSidRepository.findBySid(auteurs.get(i)) != null) {
//                        Integer ACLSID = aclSidRepository.findBySid(auteurs.get(i)).getId();
//
//                        // String QueryACLObjectIdentity = "SELECT * FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier()+" and object_id_class="+idclasse;
//
//                        String objectid = "(SELECT id FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier() + " and object_id_class=" + idclasse + ")";
//                        String fetchAclEntryByAcl_object_identityAndSidAndMask = "select * from acl_entry where acl_object_identity=" + objectid + " and sid=" + ACLSID + " and mask=2";
//
//                        //   System.out.println("fetchAclEntryByAcl_object_identityAndSidAndMask\n" + fetchAclEntryByAcl_object_identityAndSidAndMask);
//                        Query query = em.createNativeQuery(fetchAclEntryByAcl_object_identityAndSidAndMask);
//
//                        List<AclEntry> listeAclentry = (List<AclEntry>) query.getResultList();
//
//
//                        if (listeAclentry.size() == 0) {
//                            // Long r = (Long) oid.getIdentifier();
//
//                            Query qq=  em.createNativeQuery(objectid);
//
//                            List<BigInteger> listeidobject = (List<BigInteger>) qq.getResultList();
//
//                            if (i == 0)
//                                s += 1;
//                            // ListToInsert +="(" + (aclEntryRepository.findAll().size() +1) + ", " + retour1.get(0).getId() + "," + s + " ," + ACLSID + ", 1, 1, 1, 1)";
//                            // s += 1;
//                            //    System.out.println("Auteur to Insert == " + acl.getEntries().size() + 1 + i);
//                            ListToInsert += "(" + listeidobject.get(0) + "," + s + " ," + ACLSID + ", 2, 1, 1, 1),";
//                            returneds = s;
//
//
//                        }
//                    } else {
//                        this.InsertAclEntry(auteurs.get(i), oid, idclasse, 2);
//
//                    }
//
//                }
//
//                QueryACLEntry += ListToInsert.substring(0, ListToInsert.length() - 1);
//                // System.out.println("QueryToExecute:::"+QueryACLEntry);
//                Statement st = conn.createStatement();
//                st.executeUpdate(QueryACLEntry);
//            }
//
//            //    System.out.println("lecteurs size::::::::::::::"+lecteurs.size());
//            if (lecteurs.size() > 0) {
//
//                String ListLecteurToInsert = "";
//                String QueryACLEntry = "INSERT INTO acl_entry(acl_object_identity, ace_order,sid, mask, granting, audit_success, audit_failure) VALUES";
//
//                for (int i = 0; i < lecteurs.size(); i++) {
//                    //   System.out.println("In Lecteurs::::::::::::::::::::::::::::::::::::");
//
//
//                    if (aclSidRepository.findBySid(lecteurs.get(i)) != null) {
//                        Integer ACLSID = aclSidRepository.findBySid(lecteurs.get(i)).getId();
//
//                        // String QueryACLObjectIdentity = "SELECT * FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier()+" and object_id_class="+idclasse;
//
//                        String objectid = "(SELECT id FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier() + " and object_id_class=" + idclasse + ")";
//                        String fetchAclEntryByAcl_object_identityAndSidAndMask = "select * from acl_entry where acl_object_identity=" + objectid + " and sid=" + ACLSID + " and mask=1";
//
//                        //   System.out.println("fetchAclEntryByAcl_object_identityAndSidAndMask\n" + fetchAclEntryByAcl_object_identityAndSidAndMask);
//                        Query query = em.createNativeQuery(fetchAclEntryByAcl_object_identityAndSidAndMask);
//
//                        List<AclEntry> listeAclentry = (List<AclEntry>) query.getResultList();
//
//
//                        if (listeAclentry.size() == 0) {
////                            Long r = (Long) oid.getIdentifier();
////
////                            Integer ObjectIdentity = aclObjectIdentityRepository.findByObjectIdentity(r.longValue()).getId();
//
//                            Query qq=  em.createNativeQuery(objectid);
//
//                            List<BigInteger> listeidobject = (List<BigInteger>) qq.getResultList();
//                            returneds= returneds + 1;
//                            // ListToInsert +="(" + (aclEntryRepository.findAll().size() +1) + ", " + retour1.get(0).getId() + "," + s + " ," + ACLSID + ", 1, 1, 1, 1)";
//                            // s += 1;
//                            //     System.out.println("Auteur to Insert == " + acl.getEntries().size() + 1 + i);
//                            ListLecteurToInsert += "(" + listeidobject.get(0) + "," + (returneds) + " ," + ACLSID + ", 1, 1, 1, 1),";
//
//                        }
//                    } else {
//
//                        this.InsertAclEntry(lecteurs.get(i), oid, idclasse, 1);
//                    }
//
//                }
//                QueryACLEntry += ListLecteurToInsert.substring(0, ListLecteurToInsert.length() - 1);
//                Statement st = conn.createStatement();
//                st.executeUpdate(QueryACLEntry);
//            }
//
//            System.out.println("finsaveD::::::::::::::");
//        } catch (Exception e) {
//        }
//        return savedcourrier;
//    }
//
//    public CourrierInterne SaveCourrierI(CourrierInterne courrierInterne, List<String> auteurs, List<String> lecteurs, int statut, HttpServletRequest request) throws IOException, MessagingException, ParseException {
//
////*********************get Authentication objet***************
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("authentification !!!!!!" + authentication.getPrincipal().toString());
//
//        String typeOp = "";
//        CourrierInterne savedcourrier = courrierInterne;
//
//        //Journalisation/////
//        Date date = new Date();
//        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
//
//        ObjectIdentity oid = null;
//
//        // Correction du retard d'un jour
//        courrierInterne.setDatearrivee(LocalDate.parse(courrierInterne.getDatearrivee().toString()).plusDays(1));
//        courrierInterne.setDatecourrier(LocalDate.parse(courrierInterne.getDatecourrier().toString()).plusDays(1));
//        MutableAcl acl = null;
//
//        if (courrierInterne.getId() == null) {
//
//            typeOp = "Ajout";
//            savedcourrier = courrierInterneRepository.save(courrierInterne);
//            oid = new ObjectIdentityImpl(CourrierInterne.class, savedcourrier.getId());
//            System.out.println(oid.getIdentifier());
//            acl = aclService.createAcl(oid);
//
//            Journals journal = new Journals(authentication.getName(), journalService.getIp(request).get("RemoteAddr"), date, userAgent.getBrowser().getName(), typeOp, userAgent.getOperatingSystem().getDeviceType().toString(), savedcourrier);
//            System.out.println("Journal " + journal);
//            journalsRepository.save(journal);
//
//
//        } else {
//
//            Query q = null;
//            typeOp = "Mise à jour";
//
//            System.out.println("ID avant sauvegarde ::::!!!!!" + courrierInterne.getId());
//
//            savedcourrier = courrierInterneRepository.save(courrierInterne);
//
//            System.out.println("ID apres sauvegarde ::::!!!!!" + savedcourrier.getId());
//
//
//            Journals journal = new Journals(authentication.getName(), journalService.getIp(request).get("RemoteAddr"), date, userAgent.getBrowser().getName(), typeOp, userAgent.getOperatingSystem().getDeviceType().toString(), savedcourrier);
//
//            journalsRepository.save(journal);
//
//            oid = new ObjectIdentityImpl(CourrierInterne.class, savedcourrier.getId());
//            System.out.println("object ID  ====" + oid.getIdentifier());
//
//            acl = (MutableAcl) aclService.readAclById(oid);
//            //aclService.updateAcl(acl);
//
//
//            //System.out.println("sizee after =  " + acl.getEntries().size());
//        }
//        int idclasse = -1;
//        String querySelect = "";
//        String queryUpdate = "";
//        AclClass aclClass = aclClassRepository.findByClasse("com.example.MaiManager.domain.CourrierInterne").get();
//
//        if (aclClass.getId() != null)
//            idclasse = aclClass.getId();
//
//        if (statut == 0) {
//            this.InsertAclEntry(authentication.getPrincipal().toString(), oid, idclasse, 2);
//        }
//
//        if (statut == 2) {
//            this.InsertAclEntry("BOC", oid, idclasse, 2);
//            savedcourrier.setAuthors("BOC");
//            savedcourrier = courrierInterneRepository.save(courrierInterne);
//        }
//        if (statut == 1) {
//            //System.out.println("lecteurs list ===> " + lecteurs.get(0));
//            System.out.println("STATUUTTTTTTTTTTTTTT::::!!!!!" + oid.getIdentifier());
//
//            String objectid = "SELECT id FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier() + " and object_id_class=" + idclasse;
//            // anciens auteurs deviennent des lecteurs
//            querySelect = "SELECT id FROM acl_entry where acl_object_identity in (" + objectid + ") and mask=2";
//
//
//            System.out.println("QuerySelect::::!!!!!" + querySelect);
//
//            Query q1 = em.createNativeQuery(querySelect);
//
//            // List<AclEntry> aclEntries = q1.getResultList();
//
//            List<BigInteger> aclEntries = (List<BigInteger>) q1.getResultList();
//
//            String UpdateQuery = "Update acl_entry set Mask=1 where id in (";
//
//            try {
//
//                Connection conn = getDatabaseConnection();
//
//                System.out.println(" change Mask ");
//                for (int s = 0; s < aclEntries.size(); s++) {
//                    // System.out.println("elem::"+aclEntries.get(s));
//
////                AclEntry aclEntry = aclEntries.get(s);
////                aclEntry.setMask(1);
////                aclEntryRepository.save(aclEntry);
//                    UpdateQuery += aclEntries.get(s) + ",";
//
//                }
//                UpdateQuery = UpdateQuery.substring(0, UpdateQuery.length() - 1) + ")";
//                //   System.out.println("QueryMask::"+UpdateQuery);
//                Statement st = conn.createStatement();
//                st.executeUpdate(UpdateQuery);
//            } catch (Exception e) {
//            }
//
//        }
//
//
//        int returneds = 0;
//        try {
//
//            Connection conn = getDatabaseConnection();
//            //     System.out.println("auteurs size::::::::::::::"+lecteurs.size());
//
//            if (auteurs.size() > 0) {
//
//                // System.out.println("In Auteurs::::::::::::::::::::::::::::::::::::");
//                String ListToInsert = "";
//                String QueryACLEntry = "INSERT INTO acl_entry(acl_object_identity, ace_order,sid, mask, granting, audit_success, audit_failure) VALUES";
//
//                for (int i = 0, s = 0; i < auteurs.size(); i++, s++) {
//                    //   System.out.println("In Auteurs::::::::::::::::::::::::::::::::::::"+auteurs.size());
//
//                    if (aclSidRepository.findBySid(auteurs.get(i)) != null) {
//                        Integer ACLSID = aclSidRepository.findBySid(auteurs.get(i)).getId();
//
//                        // String QueryACLObjectIdentity = "SELECT * FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier()+" and object_id_class="+idclasse;
//
//                        String objectid = "(SELECT id FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier() + " and object_id_class=" + idclasse + ")";
//                        String fetchAclEntryByAcl_object_identityAndSidAndMask = "select * from acl_entry where acl_object_identity=" + objectid + " and sid=" + ACLSID + " and mask=2";
//
//                        //   System.out.println("fetchAclEntryByAcl_object_identityAndSidAndMask\n" + fetchAclEntryByAcl_object_identityAndSidAndMask);
//                        Query query = em.createNativeQuery(fetchAclEntryByAcl_object_identityAndSidAndMask);
//
//                        List<AclEntry> listeAclentry = (List<AclEntry>) query.getResultList();
//
//
//                        if (listeAclentry.size() == 0) {
//                            // Long r = (Long) oid.getIdentifier();
//
//                            Query qq=  em.createNativeQuery(objectid);
//
//                            List<BigInteger> listeidobject = (List<BigInteger>) qq.getResultList();
//
//                            if (i == 0)
//                                s += 1;
//                            // ListToInsert +="(" + (aclEntryRepository.findAll().size() +1) + ", " + retour1.get(0).getId() + "," + s + " ," + ACLSID + ", 1, 1, 1, 1)";
//                            // s += 1;
//                            //    System.out.println("Auteur to Insert == " + acl.getEntries().size() + 1 + i);
//                            ListToInsert += "(" + listeidobject.get(0) + "," + s + " ," + ACLSID + ", 2, 1, 1, 1),";
//                            returneds = s;
//
//
//                        }
//                    } else {
//                        this.InsertAclEntry(auteurs.get(i), oid, idclasse, 2);
//
//                    }
//
//                }
//
//                QueryACLEntry += ListToInsert.substring(0, ListToInsert.length() - 1);
//                // System.out.println("QueryToExecute:::"+QueryACLEntry);
//                Statement st = conn.createStatement();
//                st.executeUpdate(QueryACLEntry);
//            }
//
//            //    System.out.println("lecteurs size::::::::::::::"+lecteurs.size());
//            if (lecteurs.size() > 0) {
//
//                String ListLecteurToInsert = "";
//                String QueryACLEntry = "INSERT INTO acl_entry(acl_object_identity, ace_order,sid, mask, granting, audit_success, audit_failure) VALUES";
//
//                for (int i = 0; i < lecteurs.size(); i++) {
//                    //   System.out.println("In Lecteurs::::::::::::::::::::::::::::::::::::");
//
//
//                    if (aclSidRepository.findBySid(lecteurs.get(i)) != null) {
//                        Integer ACLSID = aclSidRepository.findBySid(lecteurs.get(i)).getId();
//
//                        // String QueryACLObjectIdentity = "SELECT * FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier()+" and object_id_class="+idclasse;
//
//                        String objectid = "(SELECT id FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier() + " and object_id_class=" + idclasse + ")";
//                        String fetchAclEntryByAcl_object_identityAndSidAndMask = "select * from acl_entry where acl_object_identity=" + objectid + " and sid=" + ACLSID + " and mask=1";
//
//                        //   System.out.println("fetchAclEntryByAcl_object_identityAndSidAndMask\n" + fetchAclEntryByAcl_object_identityAndSidAndMask);
//                        Query query = em.createNativeQuery(fetchAclEntryByAcl_object_identityAndSidAndMask);
//
//                        List<AclEntry> listeAclentry = (List<AclEntry>) query.getResultList();
//
//
//                        if (listeAclentry.size() == 0) {
////                            Long r = (Long) oid.getIdentifier();
////
////                            Integer ObjectIdentity = aclObjectIdentityRepository.findByObjectIdentity(r.longValue()).getId();
//
//                            Query qq=  em.createNativeQuery(objectid);
//
//                            List<BigInteger> listeidobject = (List<BigInteger>) qq.getResultList();
//                            returneds= returneds + 1;
//                            // ListToInsert +="(" + (aclEntryRepository.findAll().size() +1) + ", " + retour1.get(0).getId() + "," + s + " ," + ACLSID + ", 1, 1, 1, 1)";
//                            // s += 1;
//                            //     System.out.println("Auteur to Insert == " + acl.getEntries().size() + 1 + i);
//                            ListLecteurToInsert += "(" + listeidobject.get(0) + "," + (returneds) + " ," + ACLSID + ", 1, 1, 1, 1),";
//
//                        }
//                    } else {
//
//                        this.InsertAclEntry(lecteurs.get(i), oid, idclasse, 1);
//                    }
//
//                }
//                QueryACLEntry += ListLecteurToInsert.substring(0, ListLecteurToInsert.length() - 1);
//                Statement st = conn.createStatement();
//                st.executeUpdate(QueryACLEntry);
//            }
//
//            System.out.println("finsaveI::::::::::::::");
//        } catch (Exception e) {
//        }
//        return savedcourrier;
//
//    }
//
//    public Task SaveTask(Task task, List<String> auteurs, List<String> lecteurs, String CourrierId) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        ObjectIdentity oid = null;
//        Task savedTask = task;
//        MutableAcl acl = null;
//
//
//        if (task.getId() == null) {
//            savedTask = taskRepository.save(task);
//            oid = new ObjectIdentityImpl(Task.class, savedTask.getId());
//            System.out.println(oid.getIdentifier());
//            acl = aclService.createAcl(oid);
////           acl.setEntriesInheriting(true);
////           acl.setParent(acl);
//
//        } else {
//            savedTask = taskRepository.save(task);
//         oid = new ObjectIdentityImpl(Task.class, savedTask.getId());
//            System.out.println("object ID  ====" + oid.getIdentifier());
//            System.out.println(oid.getIdentifier());
//            acl = (MutableAcl) aclService.readAclById(oid);
//            //aclService.updateAcl(acl);
//
//        }
//        int idclasse = -1;
//        AclClass aclClass = aclClassRepository.findByClasse("com.example.MaiManager.domain.Task").get();
//        if (aclClass.getId() != null)
//            idclasse = aclClass.getId();
//
////        acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, acl.getOwner(), true);
////        acl.insertAce(acl.getEntries().size(), BasePermission.READ, acl.getOwner(), true);
//
//        this.InsertAclEntry(authentication.getPrincipal().toString(), oid, idclasse, 2);
//
//        String querySelect, queryUpdate = "";
///***********************************************************************************************************************************/
//
//
//        String objectid = "SELECT id FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier() + " and object_id_class=" + idclasse;
//        // anciens auteurs deviennent des lecteurs
//        querySelect = "SELECT id FROM acl_entry where acl_object_identity in (" + objectid + ") and mask=2";
//
//
//        System.out.println("QuerySelect::::!!!!!" + querySelect);
//
//        Query q1 = em.createNativeQuery(querySelect);
//
//        // List<AclEntry> aclEntries = q1.getResultList();
//
//        List<BigInteger> aclEntries = (List<BigInteger>) q1.getResultList();
//
//        String UpdateQuery = "Update acl_entry set Mask=1 where id in (";
//
//        try {
//
//            Connection conn = getDatabaseConnection();
//
//            System.out.println(" change Mask ");
//            for (int s = 0; s < aclEntries.size(); s++) {
//                //  System.out.println("elem::"+aclEntries.get(s));
//
////                AclEntry aclEntry = aclEntries.get(s);
////                aclEntry.setMask(1);
////                aclEntryRepository.save(aclEntry);
//                UpdateQuery += aclEntries.get(s) + ",";
//
//            }
//            UpdateQuery = UpdateQuery.substring(0, UpdateQuery.length() - 1) + ")";
//            //   System.out.println("QueryMask::"+UpdateQuery);
//            Statement st = conn.createStatement();
//            st.executeUpdate(UpdateQuery);
//        } catch (Exception e) {
//        }
//
//
//        int returneds = 0;
//
//        try {
//
//            Connection conn = getDatabaseConnection();
//     System.out.println("auteurs size::::::::::::::"+auteurs.size());
//     System.out.println("lecteurs size::::::::::::::"+lecteurs.size());
//
//
//
//            if (auteurs.size() > 0) {
//
//                System.out.println("In AuteursTask::::::::::::::::::::::::::::::::::::");
//                String ListToInsert = "";
//                String QueryACLEntry = "INSERT INTO acl_entry(acl_object_identity, ace_order,sid, mask, granting, audit_success, audit_failure) VALUES";
//
//                for (int i = 0, s = 0; i < auteurs.size(); i++, s++) {
//                    //   System.out.println("In Auteurs::::::::::::::::::::::::::::::::::::"+auteurs.size());
//
//                    if (aclSidRepository.findBySid(auteurs.get(i)) != null) {
//                        Integer ACLSID = aclSidRepository.findBySid(auteurs.get(i)).getId();
//
//                        String objectid2 = "(SELECT id FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier() + " and object_id_class=" + idclasse + ")";
//                        String fetchAclEntryByAcl_object_identityAndSidAndMask = "select * from acl_entry where acl_object_identity=" + objectid2 + " and sid=" + ACLSID + " and mask=2";
//
//                         Query query = em.createNativeQuery(fetchAclEntryByAcl_object_identityAndSidAndMask, AclEntry.class);
//
//                         List<AclEntry> listeAclentry = (List<AclEntry>) query.getResultList();
//
//                        if (listeAclentry.size() == 0) {
//
//
//                           // Integer ObjectIdentity = aclObjectIdentityRepository.findByObjectIdentity(r.longValue()).getId();
//                            Query qq=  em.createNativeQuery(objectid2);
//                            int idobject=0;
//                            List<BigInteger> listeidobject = (List<BigInteger>) qq.getResultList();
////                           if(listeidobject.size()>0) {
////                               idobject = listeidobject.get(0).intValue();
////                               System.out.println("idobject"+idobject);
////                           }
//
//                            if (i == 0)
//                                s += 1;
//                            // ListToInsert +="(" + (aclEntryRepository.findAll().size() +1) + ", " + retour1.get(0).getId() + "," + s + " ," + ACLSID + ", 1, 1, 1, 1)";
//                            // s += 1;
//                            //    System.out.println("Auteur to Insert == " + acl.getEntries().size() + 1 + i);
//                            ListToInsert += "(" + listeidobject.get(0) + "," + s + " ," + ACLSID + ", 2, 1, 1, 1),";
//                        // System.out.println("ListToInsert::+"+ListToInsert);
//                            returneds = s;
//
//
//                        }
//                    } else {
//                        this.InsertAclEntry(auteurs.get(i), oid, idclasse, 2);
//
//                    }
//
//                }
//
//                QueryACLEntry += ListToInsert.substring(0, ListToInsert.length() - 1);
//                System.out.println("QueryToExecute:::"+QueryACLEntry);
//                Statement st = conn.createStatement();
//                st.executeUpdate(QueryACLEntry);
//
//            }
//
//              System.out.println("lecteurs size::::::::::::::"+lecteurs.size());
//
//
//                if (lecteurs.size() > 0) {
//                System.out.println("In LecteursTask::::::::::::::::::::::::::::::::::::");
//                String ListLecteurToInsert = "";
//                String QueryACLEntry = "INSERT INTO acl_entry(acl_object_identity, ace_order,sid, mask, granting, audit_success, audit_failure) VALUES";
//
//                for (int i = 0; i < lecteurs.size(); i++) {
//
//
//
//
//                    if (aclSidRepository.findBySid(lecteurs.get(i)) != null) {
//                        Integer ACLSID = aclSidRepository.findBySid(lecteurs.get(i)).getId();
//
//                        // String QueryACLObjectIdentity = "SELECT * FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier()+" and object_id_class="+idclasse;
//
//                        String objectid2 = "(SELECT id FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier() + " and object_id_class=" + idclasse + ")";
//                        String fetchAclEntryByAcl_object_identityAndSidAndMask = "select * from acl_entry where acl_object_identity=" + objectid2 + " and sid=" + ACLSID + " and mask=1";
//
//                        //   System.out.println("fetchAclEntryByAcl_object_identityAndSidAndMask\n" + fetchAclEntryByAcl_object_identityAndSidAndMask);
//                        Query query = em.createNativeQuery(fetchAclEntryByAcl_object_identityAndSidAndMask);
//
//                        List<AclEntry> listeAclentry = (List<AclEntry>) query.getResultList();
//
//
//                        if (listeAclentry.size() == 0) {
////                            Long r = (Long) oid.getIdentifier();
//
////                            Integer ObjectIdentity = aclObjectIdentityRepository.findByObjectIdentity(r.longValue()).getId();
//
//                            Query qq=  em.createNativeQuery(objectid2);
//
//                            List<BigInteger> listeidobject = (List<BigInteger>) qq.getResultList();
////                            if(listeidobject.size()>0)
////                                idobject=listeidobject.get(0).intValue();
//
//                            returneds= (returneds + 1);
//                            ListLecteurToInsert += "(" +  listeidobject.get(0) + "," + (returneds) + " ," + ACLSID + ", 1, 1, 1, 1),";
//
//                        }
//                    } else {
//
//                        this.InsertAclEntry(lecteurs.get(i), oid, idclasse, 1);
//                    }
//
//                }
//                QueryACLEntry += ListLecteurToInsert.substring(0, ListLecteurToInsert.length() - 1);
//                System.out.println("QueryLecteursTask:::" + QueryACLEntry);
//
//                Statement st = conn.createStatement();
//                st.executeUpdate(QueryACLEntry);
//            }
//
//            System.out.println("finsave Task::::::::::::::");
//        } catch (Exception e) {
//        }
//
///******************************************************************************************************************************************/
//        String[] typetask = CourrierId.split("#");
//
//        if (typetask[0].equals("CA")) {
//            int ClassAclid = -1;
//
//            CourrierArrive Parent = courrierArriveRepository.findByIdentifiant(task.getIdDocument()).get(0);
//
//            oid = new ObjectIdentityImpl(CourrierArrive.class, Parent.getId());
//            System.out.println(oid.getIdentifier());
//            //  acl = (MutableAcl) aclService.readAclById(oid);
//
//
//            AclClass ClassAcl = aclClassRepository.findByClasse("com.example.MaiManager.domain.CourrierArrive").get();
//            if (ClassAcl.getId() != null)
//                ClassAclid = ClassAcl.getId();
//
//
//            try {
//
//                Connection conn = getDatabaseConnection();
//                //     System.out.println("auteurs size::::::::::::::"+lecteurs.size());
//
//                if (auteurs.size() > 0) {
//
//                    System.out.println("In AuteursTask forCA::::::::::::::::::::::::::::::::::::");
//                    String ListToInsert = "";
//                    String QueryACLEntry = "INSERT INTO acl_entry(acl_object_identity, ace_order,sid, mask, granting, audit_success, audit_failure) VALUES";
//
//                    for (int i = 0, s = 0; i < auteurs.size(); i++, s++) {
//                        //   System.out.println("In Auteurs::::::::::::::::::::::::::::::::::::"+auteurs.size());
//
//                        if (aclSidRepository.findBySid(auteurs.get(i)) != null) {
//                            Integer ACLSID = aclSidRepository.findBySid(auteurs.get(i)).getId();
//
//                            // String QueryACLObjectIdentity = "SELECT * FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier()+" and object_id_class="+idclasse;
//
//                            String objectid2 = "(SELECT id FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier() + " and object_id_class=" + ClassAclid + ")";
//                            String fetchAclEntryByAcl_object_identityAndSidAndMask = "select * from acl_entry where acl_object_identity=" + objectid2 + " and sid=" + ACLSID + " and mask=2";
//
//                              System.out.println("fetchAclEntryByAcl_object_identityAndSidAndMaskAuttt\n" + fetchAclEntryByAcl_object_identityAndSidAndMask);
//                            Query query = em.createNativeQuery(fetchAclEntryByAcl_object_identityAndSidAndMask);
//
//                            List<AclEntry> listeAclentry = (List<AclEntry>) query.getResultList();
//
//
//                            if (listeAclentry.size() == 0) {
//                                Long r = (Long) oid.getIdentifier();
//
////                                Integer ObjectIdentity = aclObjectIdentityRepository.findByObjectIdentity(r.longValue()).getId();
//                                Query qq=  em.createNativeQuery(objectid2);
//
//                                List<BigInteger> listeidobject = (List<BigInteger>) qq.getResultList();
//
//                                if (i == 0)
//                                    s += 1;
//                                // ListToInsert +="(" + (aclEntryRepository.findAll().size() +1) + ", " + retour1.get(0).getId() + "," + s + " ," + ACLSID + ", 1, 1, 1, 1)";
//                                // s += 1;
//                                //    System.out.println("Auteur to Insert == " + acl.getEntries().size() + 1 + i);
//                                ListToInsert += "(" + listeidobject.get(0) + "," + s + " ," + ACLSID + ", 1, 1, 1, 1),";
//                                returneds = s;
//                                Parent.setReaders(Parent.getReaders() + "," + ArrayToString(auteurs));
//                                courrierArriveRepository.save(Parent);
//                            }
//                        } else {
//                            this.InsertAclEntry(auteurs.get(i), oid, ClassAclid, 1);
//
//                        }
//
//                    }
//
//                    QueryACLEntry += ListToInsert.substring(0, ListToInsert.length() - 1);
//                   System.out.println("QueryToExecuteAut forCA:::"+QueryACLEntry);
//                    Statement st = conn.createStatement();
//                    st.executeUpdate(QueryACLEntry);
//                }
//
//                //  Peut etre enlevé;
//
//                if (lecteurs.size() > 0) {
//
//                    String ListLecteurToInsert = "";
//                    String QueryACLEntry = "INSERT INTO acl_entry(acl_object_identity, ace_order,sid, mask, granting, audit_success, audit_failure) VALUES";
//
//                    for (int i = 0; i < lecteurs.size(); i++) {
//                        //   System.out.println("In Lecteurs::::::::::::::::::::::::::::::::::::");
//
//
//                        if (aclSidRepository.findBySid(lecteurs.get(i)) != null) {
//                            Integer ACLSID = aclSidRepository.findBySid(lecteurs.get(i)).getId();
//
//                            // String QueryACLObjectIdentity = "SELECT * FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier()+" and object_id_class="+idclasse;
//
//                            String objectid2 = "(SELECT id FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier() + " and object_id_class=" + ClassAclid + ")";
//                            String fetchAclEntryByAcl_object_identityAndSidAndMask = "select * from acl_entry where acl_object_identity=" + objectid2 + " and sid=" + ACLSID + " and mask=1";
//
//                             System.out.println("fetchAclEntryByAcl_object_identityAndSidAndMaskLLL\n" + fetchAclEntryByAcl_object_identityAndSidAndMask);
//                            Query query = em.createNativeQuery(fetchAclEntryByAcl_object_identityAndSidAndMask);
//
//                            List<AclEntry> listeAclentry = (List<AclEntry>) query.getResultList();
//
//
//                            if (listeAclentry.size() == 0) {
////                                Long r = (Long) oid.getIdentifier();
//
////                                Integer ObjectIdentity = aclObjectIdentityRepository.findByObjectIdentity(r.longValue()).getId();
//                                Query qq=  em.createNativeQuery(objectid2);
//
//                                List<BigInteger> listeidobject = (List<BigInteger>) qq.getResultList();
//                                // ListToInsert +="(" + (aclEntryRepository.findAll().size() +1) + ", " + retour1.get(0).getId() + "," + s + " ," + ACLSID + ", 1, 1, 1, 1)";
//                                // s += 1;
//                                //     System.out.println("Auteur to Insert == " + acl.getEntries().size() + 1 + i);
//                                ListLecteurToInsert += "(" + listeidobject.get(0) + "," + (returneds + 1) + " ," + ACLSID + ", 1, 1, 1, 1),";
//
//                            }
//                        } else {
//
//                            this.InsertAclEntry(lecteurs.get(i), oid, ClassAclid, 1);
//                        }
//
//                    }
//                    QueryACLEntry += ListLecteurToInsert.substring(0, ListLecteurToInsert.length() - 1);
//                    Statement st = conn.createStatement();
//                    System.out.println("QueryToExecuteLectforCA:::"+QueryACLEntry);
//                    st.executeUpdate(QueryACLEntry);
//                }
//
//                System.out.println("finsave Task::::::::::::::");
//            } catch (Exception e) {
//            }
//
//
//        }
//
//        if (typetask[0].equals("Task")) {
//            //recursiveSearch
//
//            int ClassAclid = -1;
//            AclClass ClassAcl = aclClassRepository.findByClasse("com.example.MaiManager.domain.Task").get();
//            if (ClassAcl.getId() != null)
//                ClassAclid = ClassAcl.getId();
//
//
//            Task Parent = taskRepository.findById(task.getId()).get();
//
//
//            oid = new ObjectIdentityImpl(Task.class, Parent.getId());
//            System.out.println(oid.getIdentifier());
//            //  acl = (MutableAcl) aclService.readAclById(oid);
//
//            List<CourrierArrive> listeidentifiantParent =null;
//            long idCA = -1;
//
////            if (Parent.getCourrierarrive() != null) {
////                idCA = Parent.getCourrierarrive().getId();
////                identifiantParent = courrierArriveRepository.findById(idCA).get().getIdentifiant();
////
////            }
//
//
//            listeidentifiantParent = courrierArriveRepository.findByIdentifiant(typetask[1]);
//            System.out.println(listeidentifiantParent.size());
//
//
//
//            while (listeidentifiantParent.size()==0) {
//                System.out.println("in condi"+listeidentifiantParent.size()+"######"+Parent.getIdDocument());
//                Parent = taskRepository.findByIdentifiant(Parent.getIdDocument());
//                System.out.println("in parent2"+Parent.getIdDocument());
//                listeidentifiantParent = courrierArriveRepository.findByIdentifiant(Parent.getIdDocument());
//                try {
//
//                    Connection conn = getDatabaseConnection();
//                    //     System.out.println("auteurs size::::::::::::::"+lecteurs.size());
//
//                    if (auteurs.size() > 0) {
//
//                        // System.out.println("In Auteurs::::::::::::::::::::::::::::::::::::");
//
//
//                        String ListToInsert = "";
//                        String QueryACLEntry = "INSERT INTO acl_entry(acl_object_identity, ace_order,sid, mask, granting, audit_success, audit_failure) VALUES";
//
//                        for (int i = 0, s = 0; i < auteurs.size(); i++, s++) {
//                            //   System.out.println("In Auteurs::::::::::::::::::::::::::::::::::::"+auteurs.size());
//
//                            if (aclSidRepository.findBySid(auteurs.get(i)) != null) {
//                                Integer ACLSID = aclSidRepository.findBySid(auteurs.get(i)).getId();
//
//                                // String QueryACLObjectIdentity = "SELECT * FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier()+" and object_id_class="+idclasse;
//
//                                String objectid2 = "(SELECT id FROM `acl_object_identity` where object_id_identity=" + Parent.getId() + " and object_id_class=" + ClassAclid + ")";
//                                String fetchAclEntryByAcl_object_identityAndSidAndMask = "select * from acl_entry where acl_object_identity=" + objectid2 + " and sid=" + ACLSID + " and mask=2";
//
//                                //   System.out.println("fetchAclEntryByAcl_object_identityAndSidAndMask\n" + fetchAclEntryByAcl_object_identityAndSidAndMask);
//                                Query query = em.createNativeQuery(fetchAclEntryByAcl_object_identityAndSidAndMask);
//
//                                List<AclEntry> listeAclentry = (List<AclEntry>) query.getResultList();
//
//
//                                if (listeAclentry.size() == 0) {
//                                  //  Long r = (Long) oid.getIdentifier();
//
//                                  //  Integer ObjectIdentity = aclObjectIdentityRepository.findByObjectIdentity(r.longValue()).getId();
//
//                                    Query qq=  em.createNativeQuery(objectid2);
//
//                                    List<BigInteger> listeidobject = (List<BigInteger>) qq.getResultList();
//
//
//
//
//                                    if (i == 0)
//                                        s += 1;
//                                    // ListToInsert +="(" + (aclEntryRepository.findAll().size() +1) + ", " + retour1.get(0).getId() + "," + s + " ," + ACLSID + ", 1, 1, 1, 1)";
//                                    // s += 1;
//                                    //    System.out.println("Auteur to Insert == " + acl.getEntries().size() + 1 + i);
//                                    ListToInsert += "(" + listeidobject.get(0) + "," + s + " ," + ACLSID + ", 1, 1, 1, 1),";
//                                    returneds = s;
//                                    Parent.setReaders(Parent.getReaders() + "," + ArrayToString(auteurs));
//                                    taskRepository.save(Parent);
//                                }
//                            } else {
//                                this.InsertAclEntry(auteurs.get(i), oid, ClassAclid, 1);
//
//                            }
//
//                        }
//
//                        QueryACLEntry += ListToInsert.substring(0, ListToInsert.length() - 1);
//                        // System.out.println("QueryToExecute:::"+QueryACLEntry);
//                        Statement st = conn.createStatement();
//                        st.executeUpdate(QueryACLEntry);
//                    }
//
//                    //    System.out.println("lecteurs size::::::::::::::"+lecteurs.size());
//                    if (lecteurs.size() > 0) {
//
//                        String ListLecteurToInsert = "";
//                        String QueryACLEntry = "INSERT INTO acl_entry(acl_object_identity, ace_order,sid, mask, granting, audit_success, audit_failure) VALUES";
//
//                        for (int i = 0; i < lecteurs.size(); i++) {
//                            //   System.out.println("In Lecteurs::::::::::::::::::::::::::::::::::::");
//
//
//                            if (aclSidRepository.findBySid(lecteurs.get(i)) != null) {
//                                Integer ACLSID = aclSidRepository.findBySid(lecteurs.get(i)).getId();
//
//                                // String QueryACLObjectIdentity = "SELECT * FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier()+" and object_id_class="+idclasse;
//
//                                String objectid2 = "(SELECT id FROM `acl_object_identity` where object_id_identity=" + Parent.getId() + " and object_id_class=" + ClassAclid + ")";
//                                String fetchAclEntryByAcl_object_identityAndSidAndMask = "select * from acl_entry where acl_object_identity=" + objectid2 + " and sid=" + ACLSID + " and mask=1";
//
//                                System.out.println("fetchAclEntryByAcl_object_identityAndSidAndMaskLLL\n" + fetchAclEntryByAcl_object_identityAndSidAndMask);
//                                Query query = em.createNativeQuery(fetchAclEntryByAcl_object_identityAndSidAndMask);
//
//                                List<AclEntry> listeAclentry = (List<AclEntry>) query.getResultList();
//
//
//                                if (listeAclentry.size() == 0) {
////                                Long r = (Long) oid.getIdentifier();
//
////                                Integer ObjectIdentity = aclObjectIdentityRepository.findByObjectIdentity(r.longValue()).getId();
//                                    Query qq=  em.createNativeQuery(objectid2);
//
//                                    List<BigInteger> listeidobject = (List<BigInteger>) qq.getResultList();
//                                    returneds=(returneds + 1);
//                                    // ListToInsert +="(" + (aclEntryRepository.findAll().size() +1) + ", " + retour1.get(0).getId() + "," + s + " ," + ACLSID + ", 1, 1, 1, 1)";
//                                    // s += 1;
//                                    //     System.out.println("Auteur to Insert == " + acl.getEntries().size() + 1 + i);
//                                    ListLecteurToInsert += "(" + listeidobject.get(0) + "," + (returneds) + " ," + ACLSID + ", 1, 1, 1, 1),";
//
//                                }
//                            } else {
//
//                                this.InsertAclEntry(lecteurs.get(i), oid, ClassAclid, 1);
//                            }
//
//                        }
//                        QueryACLEntry += ListLecteurToInsert.substring(0, ListLecteurToInsert.length() - 1);
//                        Statement st = conn.createStatement();
//                        System.out.println("QueryToExecuteLectforCA:::"+QueryACLEntry);
//                        st.executeUpdate(QueryACLEntry);
//                    }
//
//
//
//
//
//                    System.out.println("finsave Task::::::::::::::");
//                } catch (Exception e) {
//                }
//            }
//
//
//
//
//            listeidentifiantParent.get(0).setReaders(listeidentifiantParent.get(0).getReaders() + "," + ArrayToString(lecteurs));
//            courrierArriveRepository.save(listeidentifiantParent.get(0));
//            AclClass idClassAcl = aclClassRepository.findByClasse("com.example.MaiManager.domain.CourrierArrive").get();
//
//
//            if (idClassAcl.getId() != null)
//                ClassAclid = idClassAcl.getId();
//
//
//            try {
//
//                Connection conn = getDatabaseConnection();
//                //     System.out.println("auteurs size::::::::::::::"+lecteurs.size());
//
//                if (auteurs.size() > 0) {
//
//                    System.out.println("In AuteursTask forCA::::::::::::::::::::::::::::::::::::");
//                    String ListToInsert = "";
//                    String QueryACLEntry = "INSERT INTO acl_entry(acl_object_identity, ace_order,sid, mask, granting, audit_success, audit_failure) VALUES";
//
//                    for (int i = 0, s = 0; i < auteurs.size(); i++, s++) {
//                        //   System.out.println("In Auteurs::::::::::::::::::::::::::::::::::::"+auteurs.size());
//
//                        if (aclSidRepository.findBySid(auteurs.get(i)) != null) {
//                            Integer ACLSID = aclSidRepository.findBySid(auteurs.get(i)).getId();
//
//                            // String QueryACLObjectIdentity = "SELECT * FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier()+" and object_id_class="+idclasse;
//
//                            String objectid2 = "(SELECT id FROM `acl_object_identity` where object_id_identity=" + listeidentifiantParent.get(0).getId() + " and object_id_class=" + ClassAclid + ")";
//                            String fetchAclEntryByAcl_object_identityAndSidAndMask = "select * from acl_entry where acl_object_identity=" + objectid2 + " and sid=" + ACLSID + " and mask=2";
//
//                            System.out.println("fetchAclEntryByAcl_object_identityAndSidAndMaskAuttt\n" + fetchAclEntryByAcl_object_identityAndSidAndMask);
//                            Query query = em.createNativeQuery(fetchAclEntryByAcl_object_identityAndSidAndMask);
//
//                            List<AclEntry> listeAclentry = (List<AclEntry>) query.getResultList();
//
//
//                            if (listeAclentry.size() == 0) {
//
//
////                                Integer ObjectIdentity = aclObjectIdentityRepository.findByObjectIdentity(r.longValue()).getId();
//                                Query qq=  em.createNativeQuery(objectid2);
//
//                                List<BigInteger> listeidobject = (List<BigInteger>) qq.getResultList();
//
//                                if (i == 0)
//                                    s += 1;
//                                // ListToInsert +="(" + (aclEntryRepository.findAll().size() +1) + ", " + retour1.get(0).getId() + "," + s + " ," + ACLSID + ", 1, 1, 1, 1)";
//                                // s += 1;
//                                //    System.out.println("Auteur to Insert == " + acl.getEntries().size() + 1 + i);
//                                ListToInsert += "(" + listeidobject.get(0) + "," + s + " ," + ACLSID + ", 1, 1, 1, 1),";
//                                returneds = s;
//
//                            }
//                        } else {
//                            this.InsertAclEntry(auteurs.get(i), oid, ClassAclid, 1);
//
//                        }
//
//                    }
//
//                    QueryACLEntry += ListToInsert.substring(0, ListToInsert.length() - 1);
//                    System.out.println("QueryToExecuteAut forCA:::"+QueryACLEntry);
//                    Statement st = conn.createStatement();
//                    st.executeUpdate(QueryACLEntry);
//                }
//
//                //  Peut etre enlevé;
//
//                if (lecteurs.size() > 0) {
//
//                    String ListLecteurToInsert = "";
//                    String QueryACLEntry = "INSERT INTO acl_entry(acl_object_identity, ace_order,sid, mask, granting, audit_success, audit_failure) VALUES";
//
//                    for (int i = 0; i < lecteurs.size(); i++) {
//                        //   System.out.println("In Lecteurs::::::::::::::::::::::::::::::::::::");
//
//
//                        if (aclSidRepository.findBySid(lecteurs.get(i)) != null) {
//                            Integer ACLSID = aclSidRepository.findBySid(lecteurs.get(i)).getId();
//
//                            // String QueryACLObjectIdentity = "SELECT * FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier()+" and object_id_class="+idclasse;
//
//                            String objectid2 = "(SELECT id FROM `acl_object_identity` where object_id_identity=" + listeidentifiantParent.get(0) + " and object_id_class=" + ClassAclid + ")";
//                            String fetchAclEntryByAcl_object_identityAndSidAndMask = "select * from acl_entry where acl_object_identity=" + objectid2 + " and sid=" + ACLSID + " and mask=1";
//
//                            System.out.println("fetchAclEntryByAcl_object_identityAndSidAndMaskLLL\n" + fetchAclEntryByAcl_object_identityAndSidAndMask);
//                            Query query = em.createNativeQuery(fetchAclEntryByAcl_object_identityAndSidAndMask);
//
//                            List<AclEntry> listeAclentry = (List<AclEntry>) query.getResultList();
//
//
//                            if (listeAclentry.size() == 0) {
////                                Long r = (Long) oid.getIdentifier();
//
////                                Integer ObjectIdentity = aclObjectIdentityRepository.findByObjectIdentity(r.longValue()).getId();
//                                Query qq=  em.createNativeQuery(objectid2);
//
//                                List<BigInteger> listeidobject = (List<BigInteger>) qq.getResultList();
//                                // ListToInsert +="(" + (aclEntryRepository.findAll().size() +1) + ", " + retour1.get(0).getId() + "," + s + " ," + ACLSID + ", 1, 1, 1, 1)";
//                                // s += 1;
//                                //     System.out.println("Auteur to Insert == " + acl.getEntries().size() + 1 + i);
//                                ListLecteurToInsert += "(" + listeidobject.get(0) + "," + (returneds + 1) + " ," + ACLSID + ", 1, 1, 1, 1),";
//
//                            }
//                        } else {
//
//                            this.InsertAclEntry(lecteurs.get(i), oid, ClassAclid, 1);
//                        }
//
//                    }
//                    QueryACLEntry += ListLecteurToInsert.substring(0, ListLecteurToInsert.length() - 1);
//                    Statement st = conn.createStatement();
//                    System.out.println("QueryToExecuteLectforCA:::"+QueryACLEntry);
//                    st.executeUpdate(QueryACLEntry);
//                }
//
//                System.out.println("finsave Task::::::::::::::");
//            } catch (Exception e) {
//            }
//
//
//        }
//
//
//        return savedTask;
//
//    }
//
//    String ArrayToString(List<String> liste) {
//        String chaine = "";
//        for (int k = 0; k < liste.size(); k++)
//            chaine += liste.get(k) + ",";
//        return chaine.substring(0, chaine.length() - 1);
//    }
//
//    public Affaire SaveAffaire(Affaire affaire, List<String> auteurs, List<String> lecteurs, int statut, Long docid, HttpServletRequest request) throws UnknownHostException {
//
//
////*********************get Authentication objet***************
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("authentification !!!!!!" + authentication.getPrincipal().toString());
//
//        String typeOp = "";
//        Affaire savedAffaire = affaire;
//
//        //Journalisation/////
//        Date date = new Date();
//        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
//
//        ObjectIdentity oid = null;
//
//        // Correction du retard d'un jour
//        MutableAcl acl = null;
//
//        if (savedAffaire.getId() == null) {
//
//            typeOp = "Ajout";
//            savedAffaire = affaireRepository.save(affaire);
//            oid = new ObjectIdentityImpl(CourrierArrive.class, savedAffaire.getId());
//            System.out.println(oid.getIdentifier());
//            acl = aclService.createAcl(oid);
//
//            Journals journal = new Journals(authentication.getName(), journalService.getIp(request).get("RemoteAddr"), date, userAgent.getBrowser().getName(), typeOp, userAgent.getOperatingSystem().getDeviceType().toString(), savedAffaire);
//            System.out.println("Journal " + journal);
//            journalsRepository.save(journal);
//
//
//        } else {
//
//            Query q = null;
//            typeOp = "Mise à jour";
//
//            System.out.println("ID avant sauvegarde ::::!!!!!" + savedAffaire.getId());
//
//            savedAffaire = affaireRepository.save(affaire);
//
//            System.out.println("ID apres sauvegarde ::::!!!!!" + savedAffaire.getId());
//
//
//            Journals journal = new Journals(authentication.getName(), journalService.getIp(request).get("RemoteAddr"), date, userAgent.getBrowser().getName(), typeOp, userAgent.getOperatingSystem().getDeviceType().toString(), savedAffaire);
//            journalsRepository.save(journal);
//
//            oid = new ObjectIdentityImpl(Affaire.class, savedAffaire.getId());
//            System.out.println("object ID  ====" + oid.getIdentifier());
//            acl = (MutableAcl) aclService.readAclById(oid);
//        }
//        int idclasse = -1;
//        String querySelect = "";
//        String queryUpdate = "";
//        AclClass aclClass = aclClassRepository.findByClasse("com.example.MaiManager.domain.Affaire").get();
//
//        if (aclClass.getId() != null)
//            idclasse = aclClass.getId();
//
//        if (statut == 0) {
//            this.InsertAclEntry(authentication.getPrincipal().toString(), oid, idclasse, 2);
//        }
//
//
//        if (statut == 1) {
//            //System.out.println("lecteurs list ===> " + lecteurs.get(0));
//            System.out.println("STATUUTTTTTTTTTTTTTT::::!!!!!" + oid.getIdentifier());
//
//            String objectid = "SELECT id FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier() + " and object_id_class=" + idclasse;
//            // anciens auteurs deviennent des lecteurs
//            querySelect = "SELECT id FROM acl_entry where acl_object_identity in (" + objectid + ") and mask=2";
//
//
//            System.out.println("QuerySelect::::!!!!!" + querySelect);
//
//            Query q1 = em.createNativeQuery(querySelect);
//
//            // List<AclEntry> aclEntries = q1.getResultList();
//
//            List<BigInteger> aclEntries = (List<BigInteger>) q1.getResultList();
//
//            String UpdateQuery = "Update acl_entry set Mask=1 where id in (";
//
//            try {
//
//                Connection conn = getDatabaseConnection();
//
//                System.out.println(" change Mask ");
//                for (int s = 0; s < aclEntries.size(); s++) {
//
//                    UpdateQuery += aclEntries.get(s) + ",";
//
//                }
//                UpdateQuery = UpdateQuery.substring(0, UpdateQuery.length() - 1) + ")";
//                //   System.out.println("QueryMask::"+UpdateQuery);
//                Statement st = conn.createStatement();
//                st.executeUpdate(UpdateQuery);
//            } catch (Exception e) {
//            }
//
//        }
//
//
//        int returneds = 0;
//        try {
//
//            Connection conn = getDatabaseConnection();
//            //     System.out.println("auteurs size::::::::::::::"+lecteurs.size());
//
//            if (auteurs.size() > 0) {
//
//                // System.out.println("In Auteurs::::::::::::::::::::::::::::::::::::");
//                String ListToInsert = "";
//                String QueryACLEntry = "INSERT INTO acl_entry(acl_object_identity, ace_order,sid, mask, granting, audit_success, audit_failure) VALUES";
//
//                for (int i = 0, s = 0; i < auteurs.size(); i++, s++) {
//                    //   System.out.println("In Auteurs::::::::::::::::::::::::::::::::::::"+auteurs.size());
//
//                    if (aclSidRepository.findBySid(auteurs.get(i)) != null) {
//                        Integer ACLSID = aclSidRepository.findBySid(auteurs.get(i)).getId();
//
//                        // String QueryACLObjectIdentity = "SELECT * FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier()+" and object_id_class="+idclasse;
//
//                        String objectid = "(SELECT id FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier() + " and object_id_class=" + idclasse + ")";
//                        String fetchAclEntryByAcl_object_identityAndSidAndMask = "select * from acl_entry where acl_object_identity=" + objectid + " and sid=" + ACLSID + " and mask=2";
//
//                        //   System.out.println("fetchAclEntryByAcl_object_identityAndSidAndMask\n" + fetchAclEntryByAcl_object_identityAndSidAndMask);
//                        Query query = em.createNativeQuery(fetchAclEntryByAcl_object_identityAndSidAndMask);
//
//                        List<AclEntry> listeAclentry = (List<AclEntry>) query.getResultList();
//
//
//                        if (listeAclentry.size() == 0) {
//                        //    Long r = (Long) oid.getIdentifier();
//
//                         //   Integer ObjectIdentity = aclObjectIdentityRepository.findByObjectIdentity(r.longValue()).getId();
//                            Query qq=  em.createNativeQuery(objectid);
//
//                            List<BigInteger> listeidobject = (List<BigInteger>) qq.getResultList();
//                            if (i == 0)
//                                s += 1;
//                            // ListToInsert +="(" + (aclEntryRepository.findAll().size() +1) + ", " + retour1.get(0).getId() + "," + s + " ," + ACLSID + ", 1, 1, 1, 1)";
//                            // s += 1;
//                            //    System.out.println("Auteur to Insert == " + acl.getEntries().size() + 1 + i);
//                            ListToInsert += "(" + listeidobject.get(0) + "," + s + " ," + ACLSID + ", 2, 1, 1, 1),";
//                            returneds = s;
//
//
//                        }
//                    } else {
//                        this.InsertAclEntry(auteurs.get(i), oid, idclasse, 2);
//
//                    }
//
//                }
//
//                QueryACLEntry += ListToInsert.substring(0, ListToInsert.length() - 1);
//                // System.out.println("QueryToExecute:::"+QueryACLEntry);
//                Statement st = conn.createStatement();
//                st.executeUpdate(QueryACLEntry);
//            }
//
//            //    System.out.println("lecteurs size::::::::::::::"+lecteurs.size());
//            if (lecteurs.size() > 0) {
//
//                String ListLecteurToInsert = "";
//                String QueryACLEntry = "INSERT INTO acl_entry(acl_object_identity, ace_order,sid, mask, granting, audit_success, audit_failure) VALUES";
//
//                for (int i = 0; i < lecteurs.size(); i++) {
//                    //   System.out.println("In Lecteurs::::::::::::::::::::::::::::::::::::");
//
//
//                    if (aclSidRepository.findBySid(lecteurs.get(i)) != null) {
//                        Integer ACLSID = aclSidRepository.findBySid(lecteurs.get(i)).getId();
//
//                        // String QueryACLObjectIdentity = "SELECT * FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier()+" and object_id_class="+idclasse;
//
//                        String objectid = "(SELECT id FROM `acl_object_identity` where object_id_identity=" + oid.getIdentifier() + " and object_id_class=" + idclasse + ")";
//                        String fetchAclEntryByAcl_object_identityAndSidAndMask = "select * from acl_entry where acl_object_identity=" + objectid + " and sid=" + ACLSID + " and mask=1";
//
//                        //   System.out.println("fetchAclEntryByAcl_object_identityAndSidAndMask\n" + fetchAclEntryByAcl_object_identityAndSidAndMask);
//                        Query query = em.createNativeQuery(fetchAclEntryByAcl_object_identityAndSidAndMask);
//
//                        List<AclEntry> listeAclentry = (List<AclEntry>) query.getResultList();
//
//
//                        if (listeAclentry.size() == 0) {
//                           // Long r = (Long) oid.getIdentifier();
//
//                           // Integer ObjectIdentity = aclObjectIdentityRepository.findByObjectIdentity(r.longValue()).getId();
//                            Query qq=  em.createNativeQuery(objectid);
//
//                            List<BigInteger> listeidobject = (List<BigInteger>) qq.getResultList();
//
//                            // ListToInsert +="(" + (aclEntryRepository.findAll().size() +1) + ", " + retour1.get(0).getId() + "," + s + " ," + ACLSID + ", 1, 1, 1, 1)";
//                            // s += 1;
//                            //     System.out.println("Auteur to Insert == " + acl.getEntries().size() + 1 + i);
//                            ListLecteurToInsert += "(" + listeidobject.get(0) + "," + (returneds + 1) + " ," + ACLSID + ", 1, 1, 1, 1),";
//
//                        }
//                    } else {
//
//                        this.InsertAclEntry(lecteurs.get(i), oid, idclasse, 1);
//                    }
//
//                }
//                QueryACLEntry += ListLecteurToInsert.substring(0, ListLecteurToInsert.length() - 1);
//                Statement st = conn.createStatement();
//                st.executeUpdate(QueryACLEntry);
//            }
//
//            System.out.println("finsave::::::::::::::");
//        } catch (Exception e) {
//        }
//
//
//    //     System.out.println("user to fetch::" + result.get("data").getSamaccountname());
//
//
//        return savedAffaire;
//    }
//
//     public <T> List<T> intersection(List<T> list1, List<T> list2) {
//        List<T> list = new ArrayList<T>();
//
//        for (T t : list1) {
//            if(!list2.contains(t)) {
//                list.add(t);
//            }
//        }
//
//        return list;
//    }
//
//
////    public Boolean getObjectAuthArriv(Long idDocument) {
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
////        ArrayList<GrantedAuthority> newListAutorities = new ArrayList<>(authorities);
////       // ArrayList<String> StringAutorities = new ArrayList<String>();
////        ArrayList<String> StringAutorities =   getMyArrayList(authentication.getPrincipal().toString());
////
////        for (int j = 0; j < newListAutorities.size(); j++) {
////            StringAutorities.add(newListAutorities.get(j).getAuthority());
////           // System.out.println(StringAutorities.get(j));
////        }
////
////        boolean test = true;
////        Query q2 = em.createNativeQuery("SELECT authors FROM  `doc_courrier_arrive` WHERE id=" + idDocument +" and authors is not null");
////
////        List<String> authors = q2.getResultList();
////
////        if(authors.size()==0 || authors==null)
////        {
////           if(courrierArriveRepository.findById(idDocument).get().getInitiateurName().equals(StringAutorities.get(0)))
////
////                 test= true;
////           else
////                 test= false;
////
////        }
////
////        else
////        {
////
////            for (int j = 0; j < StringAutorities.size(); j++) {
////
////                if (authors.get(0).indexOf(StringAutorities.get(j)) !=-1) {
////
////                    test = true;
////                    break;
////                } else
////                    test = false;
////            }
////
////        }
////        return  test;
////    }
//
//    public Integer getObjectAuthArriv(Long idDocument) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        ArrayList<GrantedAuthority> newListAutorities = new ArrayList<>(authorities);
//        // ArrayList<String> StringAutorities = new ArrayList<String>();
//        ArrayList<String> StringAutorities =   getMyArrayList(authentication.getPrincipal().toString());
//
//        for (int j = 0; j < newListAutorities.size(); j++) {
//            if(StringAutorities.indexOf(newListAutorities.get(j).getAuthority())==-1)
//            {
//                StringAutorities.add(newListAutorities.get(j).getAuthority());
//            }
//
//        }
//
//        Integer test = 0;
//        Query q2 = em.createNativeQuery("SELECT authors FROM  `doc_courrier_arrive` WHERE id=" + idDocument +" and authors is not null");
//
//        List<String> authors = q2.getResultList();List<String> readers=null;
//       // System.out.println("authorsA::"+authors.get(0).toUpperCase());
//        if(authors.size()==0 || authors==null)
//        {
//            if (courrierArriveRepository.findById(idDocument).get().getInitiateurName().toUpperCase().equals(StringAutorities.get(0).toUpperCase()))
//                //test= true;
//               {
//             //     System.out.println("cas1");
//                   test = 1;
//               }
//            else
//            //readers!!
//            {
//
//                Query q3 = em.createNativeQuery("SELECT readers FROM  `doc_courrier_arrive` WHERE id=" + idDocument + " and readers is not null");
//                readers = q3.getResultList();
//               // System.out.println("readersA::"+readers.get(0).toUpperCase());
//                if (readers.size() == 0 || readers == null) {
//              //    System.out.println("cas2");
//                    test = 0;
//
//                } else
//                 //  System.out.println("cas3");
//                    test = 2;
//            }
//        }
//        else {
//
//            for (int j = 0; j < StringAutorities.size(); j++) {
//              //  System.out.println("Auth::"+StringAutorities.get(j).toUpperCase());
//                if (authors.get(0).toUpperCase().indexOf(StringAutorities.get(j).toUpperCase()) != -1) {
//                  // System.out.println("cas4");
//                    test = 1;
//                    break;
//                } else {
//                    Query q4 = em.createNativeQuery("SELECT readers FROM  `doc_courrier_arrive` WHERE id=" + idDocument + " and readers is not null");
//                    readers = q4.getResultList();
//                   // System.out.println("readersB::"+readers.get(0).toUpperCase());
//                    if (readers.size() == 0 || readers == null) {
//                   //  System.out.println("cas5");
//                        test = 0;
//                    } else {
//                    //  System.out.println("cas6");
//                        if (readers.get(0).toUpperCase().indexOf(StringAutorities.get(j).toUpperCase()) != -1)
//                            test = 2;
//
//                    }
//                }
//
//            }
//        }
//        return  test;
//    }
//
//
//    public Integer getObjectAuthInterne(Long idDocument) {
//
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        ArrayList<GrantedAuthority> newListAutorities = new ArrayList<>(authorities);
//        // ArrayList<String> StringAutorities = new ArrayList<String>();
//        ArrayList<String> StringAutorities =   getMyArrayList(authentication.getPrincipal().toString());
//
//        for (int j = 0; j < newListAutorities.size(); j++) {
//            if(StringAutorities.indexOf(newListAutorities.get(j).getAuthority())==-1)
//            {
//                StringAutorities.add(newListAutorities.get(j).getAuthority());
//            }
//
//        }
//
//        Integer test = 0;
//        Query q2 = em.createNativeQuery("SELECT authors FROM  `doc_courrier_interne` WHERE id=" + idDocument +" and authors is not null");
//
//        List<String> authors = q2.getResultList();List<String> readers=null;
//        // System.out.println("authorsA::"+authors.get(0).toUpperCase());
//        if(authors.size()==0 || authors==null)
//        {
//            if (courrierInterneRepository.findById(idDocument).get().getInitiateurName().toUpperCase().equals(StringAutorities.get(0).toUpperCase()))
//            //test= true;
//            {
//                //     System.out.println("cas1");
//                test = 1;
//            }
//            else
//            //readers!!
//            {
//
//                Query q3 = em.createNativeQuery("SELECT readers FROM  `doc_courrier_interne` WHERE id=" + idDocument + " and readers is not null");
//                readers = q3.getResultList();
//                // System.out.println("readersA::"+readers.get(0).toUpperCase());
//                if (readers.size() == 0 || readers == null) {
//                    //    System.out.println("cas2");
//                    test = 0;
//
//                } else
//                    //  System.out.println("cas3");
//                    test = 2;
//            }
//        }
//        else {
//
//            for (int j = 0; j < StringAutorities.size(); j++) {
//                //  System.out.println("Auth::"+StringAutorities.get(j).toUpperCase());
//                if (authors.get(0).toUpperCase().indexOf(StringAutorities.get(j).toUpperCase()) != -1) {
//                    // System.out.println("cas4");
//                    test = 1;
//                    break;
//                } else {
//                    Query q4 = em.createNativeQuery("SELECT readers FROM  `doc_courrier_interne` WHERE id=" + idDocument + " and readers is not null");
//                    readers = q4.getResultList();
//                    // System.out.println("readersB::"+readers.get(0).toUpperCase());
//                    if (readers.size() == 0 || readers == null) {
//                        //  System.out.println("cas5");
//                        test = 0;
//                    } else {
//                        //  System.out.println("cas6");
//                        if (readers.get(0).toUpperCase().indexOf(StringAutorities.get(j).toUpperCase()) != -1)
//                            test = 2;
//
//                    }
//                }
//
//            }
//        }
//        return  test;
//
//    }
//
//
//
//    public Integer getObjectAuthDepart(Long idDocument) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        ArrayList<GrantedAuthority> newListAutorities = new ArrayList<>(authorities);
//        // ArrayList<String> StringAutorities = new ArrayList<String>();
//        ArrayList<String> StringAutorities =   getMyArrayList(authentication.getPrincipal().toString());
//
//        for (int j = 0; j < newListAutorities.size(); j++) {
//            if(StringAutorities.indexOf(newListAutorities.get(j).getAuthority())==-1)
//            {
//                StringAutorities.add(newListAutorities.get(j).getAuthority());
//            }
//
//        }
//
//        Integer test = 0;
//        Query q2 = em.createNativeQuery("SELECT authors FROM  `doc_courrier_depart` WHERE id=" + idDocument +" and authors is not null");
//
//        List<String> authors = q2.getResultList();List<String> readers=null;
//        // System.out.println("authorsA::"+authors.get(0).toUpperCase());
//        if(authors.size()==0 || authors==null)
//        {
//            if (courrierArriveRepository.findById(idDocument).get().getInitiateurName().toUpperCase().equals(StringAutorities.get(0).toUpperCase()))
//            //test= true;
//            {
//                //     System.out.println("cas1");
//                test = 1;
//            }
//            else
//            //readers!!
//            {
//
//                Query q3 = em.createNativeQuery("SELECT readers FROM  `doc_courrier_depart` WHERE id=" + idDocument + " and readers is not null");
//                readers = q3.getResultList();
//                // System.out.println("readersA::"+readers.get(0).toUpperCase());
//                if (readers.size() == 0 || readers == null) {
//                    //    System.out.println("cas2");
//                    test = 0;
//
//                } else
//                    //  System.out.println("cas3");
//                    test = 2;
//            }
//        }
//        else {
//
//            for (int j = 0; j < StringAutorities.size(); j++) {
//                //  System.out.println("Auth::"+StringAutorities.get(j).toUpperCase());
//                if (authors.get(0).toUpperCase().indexOf(StringAutorities.get(j).toUpperCase()) != -1) {
//                    // System.out.println("cas4");
//                    test = 1;
//                    break;
//                } else {
//                    Query q4 = em.createNativeQuery("SELECT readers FROM  `doc_courrier_depart` WHERE id=" + idDocument + " and readers is not null");
//                    readers = q4.getResultList();
//                    // System.out.println("readersB::"+readers.get(0).toUpperCase());
//                    if (readers.size() == 0 || readers == null) {
//                        //  System.out.println("cas5");
//                        test = 0;
//                    } else {
//                        //  System.out.println("cas6");
//                        if (readers.get(0).toUpperCase().indexOf(StringAutorities.get(j).toUpperCase()) != -1)
//                            test = 2;
//
//                    }
//                }
//
//            }
//        }
//        return  test;
//    }
//
//
////    public Boolean getObjectAuthTask(Long idDocument) {
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
////        ArrayList<GrantedAuthority> newListAutorities = new ArrayList<>(authorities);
////        // ArrayList<String> StringAutorities = new ArrayList<String>();
////        ArrayList<String> StringAutorities =   getMyArrayList(authentication.getPrincipal().toString());
////
////        for (int j = 0; j < newListAutorities.size(); j++) {
////            StringAutorities.add(newListAutorities.get(j).getAuthority());
////            // System.out.println(StringAutorities.get(j));
////        }
////
////
////
////
////        boolean test = true;
////        Query q2 = em.createNativeQuery("SELECT authors FROM  `doc_task` WHERE id=" + idDocument +" and authors is not null");
////
////        List<String> authors = q2.getResultList();
////
////        if(authors.size()==0 || authors==null)
////        {
////            if(taskRepository.findById(idDocument).get().getOwner().equals(StringAutorities.get(0)))
////
////                test= true;
////            else
////                test= false;
////
////        }
////
////        else
////        {
////
////            for (int j = 0; j < StringAutorities.size(); j++) {
////
////              //  System.out.println("authors.get(0)::"+authors.get(0));
////                // System.out.println("StringAutorities.get(j)::"+StringAutorities.get(j));
////              //  System.out.println(authors.get(0).contains(StringAutorities.get(j)));
////                if (authors.get(0).indexOf(StringAutorities.get(j)) !=-1) {
////
////                    test = true;
////                    break;
////                } else
////                    test = false;
////            }
////
////        }
////        return  test;
////    }
//
//
//
//    public Integer getObjectAuthTask(Long idDocument) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        ArrayList<GrantedAuthority> newListAutorities = new ArrayList<>(authorities);
//        // ArrayList<String> StringAutorities = new ArrayList<String>();
//        ArrayList<String> StringAutorities =   getMyArrayList(authentication.getPrincipal().toString());
//
//        for (int j = 0; j < newListAutorities.size(); j++) {
//            if(StringAutorities.indexOf(newListAutorities.get(j).getAuthority())==-1)
//            {
//                StringAutorities.add(newListAutorities.get(j).getAuthority());
//            }
//
//        }
//
//        Integer test = 0;
//        Query q2 = em.createNativeQuery("SELECT authors FROM `doc_task` WHERE id=" + idDocument +" and authors is not null");
//
//        List<String> authors = q2.getResultList();List<String> readers=null;
//        // System.out.println("authorsA::"+authors.get(0).toUpperCase());
//        if(authors.size()==0 || authors==null)
//        {
//            if (courrierArriveRepository.findById(idDocument).get().getInitiateurName().toUpperCase().equals(StringAutorities.get(0).toUpperCase()))
//            //test= true;
//            {
//                //     System.out.println("cas1");
//                test = 1;
//            }
//            else
//            //readers!!
//            {
//
//                Query q3 = em.createNativeQuery("SELECT readers FROM `doc_task` WHERE id=" + idDocument + " and readers is not null");
//                readers = q3.getResultList();
//                // System.out.println("readersA::"+readers.get(0).toUpperCase());
//                if (readers.size() == 0 || readers == null) {
//                    //    System.out.println("cas2");
//                    test = 0;
//
//                } else
//                    //  System.out.println("cas3");
//                    test = 2;
//            }
//        }
//        else {
//
//            for (int j = 0; j < StringAutorities.size(); j++) {
//                //  System.out.println("Auth::"+StringAutorities.get(j).toUpperCase());
//                if (authors.get(0).toUpperCase().indexOf(StringAutorities.get(j).toUpperCase()) != -1) {
//                    // System.out.println("cas4");
//                    test = 1;
//                    break;
//                } else {
//                    Query q4 = em.createNativeQuery("SELECT readers FROM `doc_task` WHERE id=" + idDocument + " and readers is not null");
//                    readers = q4.getResultList();
//                    // System.out.println("readersB::"+readers.get(0).toUpperCase());
//                    if (readers.size() == 0 || readers == null) {
//                        //  System.out.println("cas5");
//                        test = 0;
//                    } else {
//                        //  System.out.println("cas6");
//                        if (readers.get(0).toUpperCase().indexOf(StringAutorities.get(j).toUpperCase()) != -1)
//                            test = 2;
//
//                    }
//                }
//
//            }
//        }
//        return  test;
//    }
//
//
//
//
//
//
//    public String ListGroupsRoles() {
//        String GroupRol = "";
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        ArrayList<GrantedAuthority> newListAutorities = new ArrayList<>(authorities);
//       // System.out.println("newListAutorities:::::: "+newListAutorities);
//
//        for (int k = 0; k < newListAutorities.size(); k++)
//            GroupRol += "'" + newListAutorities.get(k).getAuthority() + "', ";
//        GroupRol = GroupRol.substring(0, GroupRol.length() - 2);
//        return GroupRol;
//
//    }
//
//
//
//    public List<CourrierArrive> getAllmsg() {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        //   System.out.println(" Authentifié ==>  " + authentication.getName());
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        //  System.out.println(" Authorities ==> " );
//         /*   for (GrantedAuthority grantedAuthority : authorities)
//                System.out.println(grantedAuthority.getAuthority());*/
//
//        return courrierArriveRepository.findAll();
//
//   }
//
//   //        parametrage scanner
//
//   public Profil updateUserProfile(Profil userProfile) {
//       String login = userProfile.getLogin();
//       if (userProfileRepository.findByLogin(login).isPresent()) {
//           return userProfileRepository.save(userProfile);
//       } else throw new NullPointerException("user profile not found");
//
//   }
//
//
//
}
//
//
