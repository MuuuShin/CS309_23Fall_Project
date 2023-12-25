package cse.ooad.project.controller;


import cse.ooad.project.model.*;
import cse.ooad.project.service.RoomService;
import cse.ooad.project.service.SearchService;
import cse.ooad.project.service.StudentService;
import cse.ooad.project.service.TeacherService;
import cse.ooad.project.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
//@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private SearchService searchService;
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

//    @GetMapping("/rooms")
//    public Result<List<Room>> getAllRoomsInfo() {
//        log.info("get all rooms info");
////        List<Room> rooms = roomService.getAllRoomsInfo(); 还没实现
//        List<Room> rooms = null;
//        //TODO: get all rooms info
//        return Result.success("success", rooms);
//    }

    @GetMapping("/{roomId}")
    public Result<Room> getRoomInfo(@PathVariable("roomId") String roomId) {
        log.info("get room info");
        Room room = searchService.searchRoomByRoomId(Long.parseLong(roomId));
        return Result.success("success", room);
    }

//    @GetMapping("/regions/{region}/dormitories/{dormitory}/floors/{floor}/rooms")
//    public Result<List<Room>> getRoomsByRegionAndDormitoryAndFloor(@PathVariable("region") String region, @PathVariable("dormitory") String dormitory, @PathVariable("floor") String floor) {
//        log.info("get rooms by region and dormitory and floor");
//
//        return Result.success("success", rooms);
//    }

//    @GetMapping("/regions/{region}/dormitories/{dormitory}/floors")
//    public Result<List<String>> getFloorsByRegionAndDormitory(@PathVariable("region") String region, @PathVariable("dormitory") String dormitory) {
//        log.info("get floors by region and dormitory");
//        List<String> floors = roomService.getFloorsByRegionAndDormitory(region, dormitory);
//        return Result.success("success", floors);
//    }

//    @GetMapping("/regions/{region}/dormitories")
//    public Result<List<String>> getDormitoriesByRegion(@PathVariable("region") String region) {
//        log.info("get dormitories by region");
//        List<String> dormitories = roomService.getDormitoriesByRegion(region);
//        return Result.success("success", dormitories);
//    }

    @GetMapping("/regions")
    public Result<List<Region>> getRegions() {
        log.info("get regions");
        List<Region> regions = searchService.searchAllRegion();
        return Result.success("success", regions);
    }

    @GetMapping("/regions/{regionid}/dormitories")
    public Result<List<Building>> getDormitoriesByRegion(@PathVariable("regionid") String region) {
        log.info("get dormitories by region");
        List<Building> dormitories = searchService.searchBuildingByRegion(Long.parseLong(region));
        log.info(dormitories.toString());
//        List<Building> dormitories = null; //TODO: 还哦没改完
        return Result.success("success", dormitories);
    }


    @GetMapping("/dormitories/{dormitoryid}/floors")
    public Result<List<Floor>> getFloorsByDormitory(@PathVariable("dormitoryid") String dormitoryid) {
        log.info("get floors by dormitory");
        List<Floor> floors = searchService.searchFloorByFloor(Long.parseLong(dormitoryid));
//        List<Floor> floors = null; //TODO: 还哦没改完
        return Result.success("success", floors);
    }

    @GetMapping("/floors/{floorid}/rooms")
    public Result<List<Room>> getRoomsByFloor(@PathVariable("floorid") String floorid) {
        log.info("get rooms by floor");
        List<Room> rooms = searchService.searchRoomByFloor(Long.parseLong(floorid));
//        List<Room> rooms = null; //TODO: 还哦没改完
        return Result.success("success", rooms);
    }

    @GetMapping("/rooms/{id}")
    public Result<Room> getRoomById(@PathVariable("id") String id) {
        log.info("get room by id");
        Room room = searchService.searchRoomByRoomId(Long.parseLong(id));
        return Result.success("success", room);
    }

    @DeleteMapping("/rooms/{id}")
    public Result<String> deleteRoomById(@PathVariable("id") String id) {
        log.info("delete room by id");
        boolean delete = teacherService.deleteRoom(Long.parseLong(id));
//        boolean delete = false; //TODO: 还哦没改完
        return Result.success("success", null);
    }

    @PutMapping("/rooms/{id}")
    public Result<String> updateRoomById(@PathVariable("id") String id, @RequestBody Room room) {
        log.info("update room by id");
        teacherService.updateRoom(room);
        return Result.success("success", null);
    }

    @PostMapping("/rooms")
    public Result<String> addRoom(@RequestBody Room room, @RequestParam("image") MultipartFile image) {
        log.info("add room");
        teacherService.saveRoom(room);
        return Result.success("success", null);
    }

    @GetMapping("/rooms/{roomId}/comments")
    public Result<List<Comment>> getCommentsByRoomId(@PathVariable("roomId") String roomId) {
        log.info("get comments by room id");
        List<Comment> comments = roomService.getCommentsByRoom(Long.parseLong(roomId));
//        List<Comment> comments = null; //TODO: 还哦没改完
        return Result.success("success", comments);
    }

    @PostMapping("/rooms/comments")
    public Result<String> addComment(@RequestBody Comment comment, @RequestHeader("Authorization") String token) {
        Claims claims;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            return Result.error("token error");
        }
        log.info("add comment");
        comment.setUserId(Long.parseLong(claims.get("id").toString()));
        Comment c = studentService.saveComment(comment);
        c.setDisabled(true);
        return c != null ? Result.success("success", null) : Result.error("fail");
    }

    @DeleteMapping("/rooms/comments/{id}")
    public Result<String> deleteCommentById(@PathVariable("id") String id, @RequestHeader("Authorization") String token) {
        log.info("delete comment by id");
        Claims claims;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            return Result.error("token error");
        }
        Long userId = Long.parseLong(claims.get("id").toString());
        boolean delete = studentService.deleteComment(Long.parseLong(id), userId);
        return delete ? Result.success("success", null) : Result.error("fail");
    }






}
