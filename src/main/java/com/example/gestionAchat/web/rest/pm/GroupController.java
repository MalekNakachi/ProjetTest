
package com.example.gestionAchat.web.rest.pm;

import com.example.gestionAchat.domain.GroupUserPK;
import com.example.gestionAchat.entities.*;
import com.example.gestionAchat.repository.pm.*;
import com.example.gestionAchat.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

@PreAuthorize("isAuthenticated()")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping({"/api"})
public class GroupController {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger(GroupController.class);
    }

    @PersistenceContext(unitName = "externDSEmFactory")
    //@Autowired
    EntityManager em;
    @Autowired
    GroupUserRepository groupUserRepository;

    @Autowired
    GroupService groupService;

    @Autowired
    private GroupRepository Repository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/Groups")
    public List<Group> getAllGroups() {

        List<Group> liste = Repository.findAll();
        if (liste.isEmpty())
            return null;
        else
            return liste;
    }

    @GetMapping("/ListGroups")
    public List<String> getAllGroupsString() {

        List<Group> liste = Repository.findAll();

        List<String> groupstring = new ArrayList<>();

        for (int k = 0; k < liste.size(); k++)
            groupstring.add(liste.get(k).getName());

        if (liste.isEmpty())
            return null;
        else
            return groupstring;
    }


    @Transactional
    @PostMapping("/Group")
    public Group AddGroup(@RequestBody Group group, @RequestParam(name = "Users") String userList) {
        List<User> listeUsers = new ArrayList<>();


        Group result = Repository.save(group);
        List<String> users = Arrays.asList(userList.split(","));


       for (int i = 0; i < users.size(); i++) {

            listeUsers.add(userRepository.findByDisplayname(users.get(i)));
        }
            //    group.getUsers().add(userRepository.findByDisplayname(users.get(i)));

        if (group.getId() != null) {

            List<GroupUser> groupUserList = this.groupUserRepository.findByGroup_Id(group.getId());

            this.groupUserRepository.deleteAll(groupUserList);

        }
for(int j=0; j<listeUsers.size(); j++) {

    GroupUserPK groupUserPK = new GroupUserPK(result.getId(), listeUsers.get(j).getId());
    GroupUser groupUser = new GroupUser(groupUserPK, listeUsers.get(j), result);


    groupUser.setGroupUserPK(groupUserPK);



    groupUser = this.groupUserRepository.save(groupUser);

}


        return result;

    }


    @GetMapping("/groupeByUser")
    public String GroupByUser(@RequestParam(name = "userid") Long userid) {
        List<Group> groupList = groupService.getGroupByUser(userid);
        if (groupList.size() == 0)
            return "";
        else
            return groupList.get(groupList.size()-1).getName();
    }


    @GetMapping("/groupeById")
    public Group GroupById(@RequestParam(name = "id") Long id) {
        Group groupe = Repository.findById(id).get();
        return groupe;
    }



    @Transactional
    @GetMapping("/membersOfGroup")
    public List<User> membersOfGroup(@RequestParam(name = "name") String name) {
        Group group = Repository.findByName(name);
        Set<GroupUser> groupUserSet = null;
        List<User> result = new ArrayList<>();
        groupUserSet = group.getGroupUserSet();
        List<GroupUser> list = new ArrayList<>(groupUserSet);
        for (int i = 0; i < list.size(); i++) {
            result.add(list.get(i).getUser());
          // System.out.println("user::" + list.get(i).getUser().getSamaccountname());
        }


        return result;
    }


    @Transactional
    @DeleteMapping("/groupe/{id}")
    public void deleteGroup(@PathVariable Long id) {
        log.debug("REST request to delete Group : {}", id);

        Optional<Group> groupe = Repository.findById(id);
        if (groupe.isPresent()) {
            Set<GroupUser> groupUserSet = groupe.get().getGroupUserSet();

            Iterator<GroupUser> iterator = groupUserSet.iterator();

            while (iterator.hasNext()) {
                if(iterator.hasNext()) {
                    GroupUser groupUser = iterator.next();
                    if (groupUser != null) {
                        groupUserRepository.delete(groupUser);

                    }
                }else{
                    Repository.deleteById(id);
                }
            }
            Repository.deleteById(id);
        }
    }
}
