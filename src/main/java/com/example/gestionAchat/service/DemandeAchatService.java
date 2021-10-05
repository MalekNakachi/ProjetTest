//package com.example.gestionAchat.service;
//
//import com.example.gestionAchat.domain.DemandeAchat;
//import com.example.gestionAchat.domain.Journals;
//import com.example.gestionAchat.repository.DemandeAchatRepository;
//import eu.bitwalker.useragentutils.UserAgent;
//import org.springframework.security.acls.domain.ObjectIdentityImpl;
//import org.springframework.security.acls.model.MutableAcl;
//import org.springframework.security.acls.model.ObjectIdentity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//
//import javax.mail.MessagingException;
//import javax.management.Query;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.text.ParseException;
//import java.time.LocalDate;
//import java.util.Date;
//import java.util.List;
//
//public class DemandeAchatService {
//
//    public DemandeAchat saveDemande(DemandeAchat demandeachat, List<String> auteurs, List<String> lecteurs, int statut, HttpServletRequest request) throws IOException, MessagingException, ParseException {
//
////*********************get Authentication objet***************
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("authentification !!!!!!" + authentication.getPrincipal().toString());
//
//        String typeOp = "";
//        DemandeAchat savedemande = demandeachat;
//
//        //Journalisation/////
//        Date date = new Date();
//        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
//
//        ObjectIdentity oid = null;
//
//        // Correction du retard d'un jour
////        if(DemandeAchat.getDatearrivee()!=null)
////            DemandeAchat.setDatearrivee(LocalDate.parse(DemandeAchat.getDatearrivee().toString()).plusDays(1));
////        DemandeAchat.setDatecourrier(LocalDate.parse(DemandeAchat.getDatecourrier().toString()).plusDays(1));
//        MutableAcl acl = null;
//
//        if (DemandeAchat.getId() == null) {
//
//            typeOp = "Ajout";
//          //  savedemande= DemandeAchatRepository.save(demandeachat);
//            //
//            oid = new ObjectIdentityImpl(DemandeAchat.class, savedemande.getId());
//            System.out.println(oid.getIdentifier());
//            //acl = aclService.createAcl(oid);
//
//            Journals journal = new Journals(authentication.getName(), journalService.getIp(request).get("RemoteAddr"), date, userAgent.getBrowser().getName(), typeOp, userAgent.getOperatingSystem().getDeviceType().toString(), savedemande);
//            System.out.println("Journal " + journal);
//            journalsRepository.save(journal);
//
//
//        } else {
//
//            Query q = null;
//            typeOp = "Mise à jour";
//
//            System.out.println("ID avant sauvegarde ::::!!!!!" + DemandeAchat.getId());
//
//            savedcourrier = DemandeAchatRepository.save(DemandeAchat);
//
//            System.out.println("ID apres sauvegarde ::::!!!!!" + savedcourrier.getId());
//
//
//            Journals journal = new Journals(authentication.getName(), journalService.getIp(request).get("RemoteAddr"), date, userAgent.getBrowser().getName(), typeOp, userAgent.getOperatingSystem().getDeviceType().toString(), savedcourrier);
//
//            journalsRepository.save(journal);
//
//            oid = new ObjectIdentityImpl(DemandeAchat.class, savedcourrier.getId());
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
//        AclClass aclClass = aclClassRepository.findByClasse("com.example.MaiManager.domain.DemandeAchat").get();
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
//            savedcourrier = DemandeAchatRepository.save(DemandeAchat);
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
//
//}
