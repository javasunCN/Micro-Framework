package com.microboot.cn.controller.bbs;


import com.microboot.cn.pojo.BBSNew;
import com.microboot.cn.service.bbs.BBSNewService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/news")
public class BBSNewController {

    @Resource
    BBSNewService bbsNewService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BBSNew> getUserById(@PathVariable("id") long id) {
        System.out.println("Fetching User with id " + id);
        BBSNew bbsNew = bbsNewService.findById(id);
        if (bbsNew == null) {
            return new ResponseEntity<BBSNew>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<BBSNew>(bbsNew, HttpStatus.OK);
    }

    @PostMapping(value="/create",headers="Accept=application/json")
    public ResponseEntity<Void> createUser(@RequestBody BBSNew bbsNew,
                                           UriComponentsBuilder ucBuilder){
        System.out.println("Creating User "+bbsNew.getName());
        bbsNew.setCreateTime(new Date());
        bbsNewService.createBBSNew(bbsNew);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/news/{id}").buildAndExpand(bbsNew.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value="/get", headers="Accept=application/json")
    public List<BBSNew> getAllUser() {
        List<BBSNew> tasks=bbsNewService.getBBSNew();
        return tasks;

    }

    @PutMapping(value="/update", headers="Accept=application/json")
    public ResponseEntity<String> updateUser(@RequestBody BBSNew currentNews)
    {
        System.out.println("sd");
        BBSNew bbsNew = bbsNewService.findById(currentNews.getId());
        if (bbsNew == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        bbsNewService.update(currentNews, currentNews.getId());
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(value="/{id}", headers ="Accept=application/json")
    public ResponseEntity<BBSNew> deleteUser(@PathVariable("id") Long id){
        BBSNew bbsNew = bbsNewService.findById(id);
        if (bbsNew == null) {
            return new ResponseEntity<BBSNew>(HttpStatus.NOT_FOUND);
        }
        bbsNewService.deleteBBSNewById(id);
        return new ResponseEntity<BBSNew>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value="/{id}", headers="Accept=application/json")
    public ResponseEntity<BBSNew> updateUserPartially(@PathVariable("id") long id,
                                                      @RequestBody BBSNew currentUser){
        BBSNew bbsNew = bbsNewService.findById(id);
        if(bbsNew ==null){
            return new ResponseEntity<BBSNew>(HttpStatus.NOT_FOUND);
        }
        BBSNew news = bbsNewService.updatePartially(currentUser, id);
        return new ResponseEntity<BBSNew>(news, HttpStatus.OK);
    }
}
