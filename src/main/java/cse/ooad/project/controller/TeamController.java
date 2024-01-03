package cse.ooad.project.controller;


import cse.ooad.project.model.Group;
import cse.ooad.project.model.Room;
import cse.ooad.project.model.Student;
import cse.ooad.project.service.GroupService;
import cse.ooad.project.service.SearchService;
import cse.ooad.project.service.StudentService;
import cse.ooad.project.service.TeacherService;
import cse.ooad.project.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
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


    @Autowired
    private StudentService studentService;

    @Autowired
    private SearchService searchService;


    @PostMapping("/trans-room")
    public Result<String> transRoom(@RequestBody Map<String, Object> JsonData) {
        String studentId1 = (String) JsonData.get("studentId1");
        String studentId2 = (String) JsonData.get("studentId2");
        boolean success = teacherService.transRoom(Long.parseLong(studentId1), Long.parseLong(studentId2));

        if (success) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }

    }


    @PostMapping("/select-room")
    public Result<String> selectRoom(@RequestBody Map<String, Object> JsonData, @RequestHeader("Authorization") String token) {
        String teamId = (String) JsonData.get("teamId");
        String roomId = (String) JsonData.get("roomId");
        log.info("select room");
        Claims claims;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            return Result.error("fail");
        }


        boolean isLeader = groupService.isLeader(Long.parseLong(teamId), Long.parseLong(claims.get("id").toString()));
        if (!isLeader) {
            return Result.error("fail");
        }
        boolean success = groupService.chooseRoom(Long.parseLong(teamId), Long.parseLong(roomId));
        if (success) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }

    }

    @PostMapping("/{teamId}/transfer-leadership")
    public Result<String> transferLeadership(@PathVariable("teamId") String teamId, @RequestBody Map<String, Object> JsonData, @RequestHeader("Authorization") String token) {
        String newLeaderId = (String) JsonData.get("newLeaderId");
        Claims claims;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            return Result.error("fail");
        }
        boolean isLeader = groupService.isLeader(Long.parseLong(teamId), Long.parseLong(claims.get("id").toString()));
        if (!isLeader) {
            return Result.error("fail");
        }
        boolean success = groupService.changeLeader(Long.parseLong(teamId), Long.parseLong(newLeaderId));
        if (success) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }

    }

    @GetMapping("/{teamId}/members")
    public Result<List<Student>> getMembers(@PathVariable("teamId") String teamId) {

        List<Student> members = groupService.getMemberList(Long.parseLong(teamId));
        if (members != null) {
            return Result.success("success", members);
        }

        return Result.error("fail");
    }

    @PostMapping("/{teamId}/leave")
    public Result<String> leaveTeam(@PathVariable("teamId") String teamId, @RequestHeader("Authorization") String token) {
        Claims claims;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            return Result.error("fail");
        }
        boolean success = studentService.memberLeave(Long.parseLong(claims.get("id").toString()));
        if (success) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }
    }


    @PostMapping("/{teamId}/kick")
    public Result<String> Teamkick(@PathVariable("teamId") String teamId, @RequestHeader("Authorization") String token, @RequestBody Map<String, Object> JsonData) {
        Claims claims;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            return Result.error("fail");
        }

        Long memberId = Long.parseLong((String) JsonData.get("memberId"));


        boolean success = studentService.memberLeave(Long.parseLong(claims.get("id").toString()), memberId);
        if (success) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }
    }


    @PostMapping("/exchange")
    public Result<String> exchangeRoom(@RequestHeader("Authorization") String token, @RequestBody Map<String, Object> JsonData) {
        System.out.println(JsonData);
        String leaderId = JsonData.get("leaderId").toString();
        String message = JsonData.get("message").toString();

        Claims claims;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            System.out.println("jwt error");
            return Result.error("fail");
        }
//        boolean success = studentService.joinGroup(Long.parseLong(claims.get("id").toString()), Long.parseLong(teamId));

        boolean success = studentService.transRoomApply(Long.parseLong(claims.get("id").toString()), Long.parseLong(leaderId), message);
        System.out.println(success);
        if (success) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }
    }



    @PostMapping("/join")
    public Result<String> joinTeam(@RequestHeader("Authorization") String token, @RequestBody Map<String, Object> JsonData) {
        System.out.println(JsonData);
        String leaderId = JsonData.get("leaderId").toString();
        String message = JsonData.get("message").toString();

        Claims claims;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            System.out.println("jwt error");
            return Result.error("fail");
        }
//        boolean success = studentService.joinGroup(Long.parseLong(claims.get("id").toString()), Long.parseLong(teamId));

        boolean success = studentService.sendApply(Long.parseLong(claims.get("id").toString()), Long.parseLong(leaderId), message);
        System.out.println(success);
        if (success) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }
    }

    @DeleteMapping("/{teamId}/favorites")
    public Result<String> deleteFavorite(@PathVariable("teamId") String teamId, @RequestHeader("Authorization") String token, @RequestBody Map<String, Object> JsonData) {
        String roomId = (String) JsonData.get("roomId");
        Claims claims;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            return Result.error("jwt error");
        }
        boolean isLeader = groupService.isLeader(Long.parseLong(teamId), Long.parseLong(claims.get("id").toString()));
        if (!isLeader) {
            return Result.error("not leader");
        }
        //TODO 传入roomId
        boolean success = groupService.unStarRoom(Long.parseLong(teamId), Long.parseLong(roomId));
        if (success) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }
    }

    @GetMapping("/{teamId}/favorites")
    public Result<List<Room>> getFavorites(@PathVariable("teamId") String teamId) {
        log.info("add favorite");
        List<Room> favorites = groupService.getStarList(Long.parseLong(teamId));
        if (favorites != null) {
            return Result.success("success", favorites);
        }

        return Result.error("fail");
    }

    @PostMapping("/{teamId}/favorites")
    public Result<String> addFavorite(@PathVariable("teamId") String teamId, @RequestBody Map<String, Object> JsonData, @RequestHeader("Authorization") String token) {
        String roomId = (String) JsonData.get("roomId");
        Claims claims;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            return Result.error("fail");
        }
        boolean isLeader = groupService.isLeader(Long.parseLong(teamId), Long.parseLong(claims.get("id").toString()));
        if (!isLeader) {
            return Result.error("fail");
        }
        boolean success = groupService.starRoom(Long.parseLong(teamId), Long.parseLong(roomId));
        if (success) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }
    }

//    @DeleteMapping("/{teamId}")
//    public Result<String> deleteTeam(@PathVariable("teamId") String teamId, @RequestHeader("Authorization") String token) {
//        Claims claims;
//        try {
//            claims = JwtUtils.parseJWT(token);
//        } catch (Exception e) {
//            return Result.error("fail");
//        }
//        boolean isLeader = GroupService.isLeader(teamId, claims.get("id").toString());
////        boolean isLeader = true;
//        if (!isLeader) {
//            return Result.error("fail");
//        }
//        boolean success = groupService.deleteTeam(teamId);
////        boolean success = true;
//        if (success) {
//            return Result.success("success", null);
//        } else {
//            return Result.error("fail");
//        }
//    }

    @GetMapping("/{teamId}")
    public Result<Group> getTeamInfo(@PathVariable("teamId") String teamId) {
        Group teamInfo = searchService.searchGroupByGroupId(Long.parseLong(teamId));
        if (teamInfo != null) {
            return Result.success("success", teamInfo);
        }

        return Result.error("fail");
    }


    @GetMapping("")
    public Result<List<Group>> getAllTeamInfo(@RequestParam(name = "offset", required = false, defaultValue = "-1") Integer offset,
                                              @RequestParam(name = "size", required = false, defaultValue = "-1") Integer size,
                                              @RequestHeader("Authorization") String token) {
        log.info(offset + " " + size);
        if (offset == null || size == null) {
            return Result.error("fail");
        }
        offset--;
        if (offset < 0 || size < 0) {
            return Result.error("fail");
        }

        List<Group> allTeamInfo = searchService.searchAllGroup(offset, size);
        if (allTeamInfo != null) {
            return Result.success("success", allTeamInfo);
        }

        return Result.error("fail");
    }

    @PostMapping("")
    public Result<String> createTeam(@RequestBody Map<String, Object> JsonData, @RequestHeader("Authorization") String token) {
        String teamName = (String) JsonData.get("name");
        String introduction = (String) JsonData.get("intro");
        Claims claims;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            return Result.error("fail");
        }

        Group group = studentService.createGroup(Long.parseLong(claims.get("id").toString()), teamName, introduction);
        if (group != null) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }
    }


    @GetMapping("/teams/findteam/{sleeptime}/{awaketime}/{query}")
    public Result<List<Group>> findTeam(@PathVariable("sleeptime") String sleeptime, @PathVariable("awaketime") String awaketime, @PathVariable("query") String query, @RequestHeader("Authorization") String token) {
        log.info("find team");
        System.out.println(sleeptime);
        System.out.println(awaketime);
        System.out.println(query);
        if (Objects.equals(query, " ")){
            query="";
        }
        Time awakeTime = Time.valueOf(awaketime);
        Time sleepTime = Time.valueOf(sleeptime);
        Claims claims;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            return Result.error("not login");
        }
        String userId = claims.get("id").toString();
        Student student = searchService.searchStudentByStudentId(Long.parseLong(userId));
        Long gender = Long.parseLong(String.valueOf(student.getGender()));
        Long type = Long.parseLong(String.valueOf(student.getType()));
        List<Group> groups = searchService.searchGroups(gender, awakeTime, sleepTime, type, query);

        if (groups != null) {
            return Result.success("success", groups);
        }

        return Result.error("fail");


    }


    @PostMapping("/accept")
    public Result<String> acceptTeam(@RequestHeader("Authorization") String token, @RequestBody Map<String, Object> JsonData) {
        Claims claims;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            return Result.error("not login");
        }
        String userId = claims.get("id").toString();
        String msgId = (String) JsonData.get("msgId");
        String isAccepted = (String) JsonData.get("isAccepted");
        boolean success = studentService.handleApply(Long.parseLong(msgId), isAccepted.equals("1"), Long.parseLong(userId));
        System.out.println(success);
        if (success) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }
    }
    @PutMapping("/{teamId}")
    public Result<String> updateTeam(@RequestBody Map<String, Object> JsonData, @RequestHeader("Authorization") String token) {
        String introduction = (String) JsonData.get("intro");
        String teamId = (String) JsonData.get("teamId");
        Claims claims;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            return Result.error("not login");
        }
        boolean isLeader = groupService.isLeader(Long.parseLong(teamId), Long.parseLong(claims.get("id").toString()));
        if (!isLeader) {
            return Result.error("not leader");
        }
        boolean success = groupService.updateGroupIntro(Long.parseLong(teamId), introduction);
        if (success) {
            return Result.success("success", null);
        } else {
            return Result.error("fail");
        }
    }



}
