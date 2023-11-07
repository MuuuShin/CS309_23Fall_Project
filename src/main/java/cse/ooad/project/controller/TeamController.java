package cse.ooad.project.controller;


import cse.ooad.project.model.Group;
import cse.ooad.project.model.Room;
import cse.ooad.project.model.Student;
import cse.ooad.project.service.GroupService;
import cse.ooad.project.service.TeacherService;
import cse.ooad.project.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private GroupService groupService;

    @Autowired
    private TeacherService teacherService;


    @PostMapping("/trans-room")
    public Result<String> transRoom(@RequestBody Map<String, Object> JsonData){
        String studentId1 = (String) JsonData.get("studentId1");
        String studentId2 = (String) JsonData.get("studentId2");
//        boolean success = teacherService.transRoom(studentId1, studentId2);

        boolean success = true;
        if (success) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }

    }


    @PostMapping("/select-room")
    public Result<String> selectRoom(@RequestBody Map<String, Object> JsonData, @RequestHeader("Authorization") String token){
        String teamId = (String) JsonData.get("studentId");
        String roomId = (String) JsonData.get("roomId");
        Claims claims = JwtUtils.parseJWT(token);
//        boolean isLeader = GroupService.isLeader(teamId, claims.get("id").toString());
        boolean isLeader = true;
        if (!isLeader) {
            return Result.error("fail");
        }
//        boolean success = groupService.selectRoom(teamId, roomId);
        boolean success = true;
        if (success) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }

    }

    @PostMapping("/{teamId}/transfer-leadership")
    public Result<String> transferLeadership(@PathVariable("teamId") String teamId, @RequestBody Map<String, Object> JsonData, @RequestHeader("Authorization") String token){
        String newLeaderId = (String) JsonData.get("newLeaderId");
        Claims claims = JwtUtils.parseJWT(token);
//        boolean isLeader = GroupService.isLeader(teamId, claims.get("id").toString());
        boolean isLeader = true;
        if (!isLeader) {
            return Result.error("fail");
        }
//        boolean success = groupService.transferLeadership(teamId, newLeaderId);
        boolean success = true;
        if (success) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }

    }

    @GetMapping("/{teamId}/members")
    public Result<List<Student>> getMembers(@PathVariable("teamId") String teamId) {

//        List<Student> members = groupService.getMembers(teamId);
        List<Student> members = null; //TODO: get members
        return Result.success("success", members);
    }

    @PostMapping("/{teamId}/leave")
    public Result<String> leaveTeam(@PathVariable("teamId") String teamId, @RequestHeader("Authorization") String token) {
        Claims claims = JwtUtils.parseJWT(token);
//        boolean success = groupService.leaveTeam(teamId, claims.get("id").toString());
        boolean success = true;
        if (success) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }
    }


    @PostMapping("/{teamId}/join")
    public Result<String> joinTeam(@PathVariable("teamId") String teamId, @RequestHeader("Authorization") String token) {
        Claims claims = JwtUtils.parseJWT(token);
//        boolean success = groupService.joinTeam(teamId, claims.get("id").toString());
        boolean success = true;
        if (success) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }
    }

    @DeleteMapping("/{teamId}/favorites")
    public Result<String> deleteFavorite(@PathVariable("teamId") String teamId, @RequestHeader("Authorization") String token) {
        Claims claims = JwtUtils.parseJWT(token);
//        boolean isLeader = GroupService.isLeader(teamId, claims.get("id").toString());
        boolean isLeader = true;
        if (!isLeader) {
            return Result.error("fail");
        }
//        boolean success = groupService.deleteFavorite(teamId);
        boolean success = true;
        if (success) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }
    }

    @GetMapping("/{teamId}/favorites")
    public Result<List<Room>> getFavorites(@PathVariable("teamId") String teamId) {
//        List<Room> favorites = groupService.getFavorites(teamId);
        List<Room> favorites = null; //TODO: get favorites
        return Result.success("success", favorites);
    }

    @PostMapping("/{teamId}/favorites")
    public Result<String> addFavorite(@PathVariable("teamId") String teamId, @RequestBody Map<String, Object> JsonData, @RequestHeader("Authorization") String token) {
        String roomId = (String) JsonData.get("roomId");
        Claims claims = JwtUtils.parseJWT(token);
//        boolean isLeader = GroupService.isLeader(teamId, claims.get("id").toString());
        boolean isLeader = true;
        if (!isLeader) {
            return Result.error("fail");
        }
//        boolean success = groupService.addFavorite(teamId, roomId);
        boolean success = true;
        if (success) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }
    }

    @DeleteMapping("/{teamId}")
    public Result<String> deleteTeam(@PathVariable("teamId") String teamId, @RequestHeader("Authorization") String token) {
        Claims claims = JwtUtils.parseJWT(token);
//        boolean isLeader = GroupService.isLeader(teamId, claims.get("id").toString());
        boolean isLeader = true;
        if (!isLeader) {
            return Result.error("fail");
        }
//        boolean success = groupService.deleteTeam(teamId);
        boolean success = true;
        if (success) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }
    }

    @GetMapping("/{teamId}")
    public Result<Group> getTeamInfo(@PathVariable("teamId") String teamId) {
//        Group teamInfo = groupService.getTeamInfo(teamId);
        Group teamInfo = null; //TODO: get team info
        return Result.success("success", teamInfo);
    }



    @GetMapping("/")
    public Result<List<Group>> getAllTeamInfo() {
        log.info("get team info");
//        List<Group> allTeamInfo = groupService.getAllTeamInfo();
        List<Group> teamInfo = null; //TODO: get all team info
        return Result.success("success", teamInfo);
    }

    @PostMapping("/")
    public Result<String> createTeam(@RequestBody Map<String, Object> JsonData, @RequestHeader("Authorization") String token) {
        String teamName = (String) JsonData.get("teamName");
        Claims claims = JwtUtils.parseJWT(token);
//        boolean success = groupService.createTeam(teamName, claims.get("id").toString());
        boolean success = true;
        if (success) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }
    }







}
