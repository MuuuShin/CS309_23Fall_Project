package cse.ooad.project.service;


import cse.ooad.project.model.Building;
import cse.ooad.project.model.Comment;
import cse.ooad.project.model.Floor;
import cse.ooad.project.model.Group;
import cse.ooad.project.model.Region;
import cse.ooad.project.model.Room;
import cse.ooad.project.repository.CommentRepository;
import cse.ooad.project.repository.RoomRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoomService {


    @Autowired
    RoomRepository roomRepository;

    @Autowired
    CommentRepository commentRepository;

    /**
     * 获取收藏了该房间的队伍
     * @param id 房间id
     * @return 队伍列表
     */
    @Transactional
    public List<Group>  getGroupStarList(Long id) {

        log.info("getGroupStarListByRoomId: {}", id);
        List<RoomRepository.GroupStarListProjection> r=roomRepository.getGroupStarListByRoomId(id);
        if(r.isEmpty() || r.get(0) == null) {
            return new ArrayList<>();
        }
        log.info("get r");
        List<Group> groupList = r.stream().map(RoomRepository.GroupStarListProjection::getGroupStarList).toList();
        log.info("getGroupStarListByRoomId end");
        return groupList;


    }

    /**
     * 获取房间的所有评论，包括楼中楼，同时将楼中楼的评论放在对应的评论的children中，false表示不可视
     * @param id 房间id
     * @return 房间评论列表
     */

    public List<Comment> getCommentsByRoom(Long id) {
        //todo 可能得把评论列表改的带有层级
        Room room = roomRepository.getRoomsByRoomId(id);
        int index = 0;
        List<Comment> commentList = new ArrayList<>();
        commentList.add(commentRepository.getCommentByCommentId(room.getCommentBaseId()));
        while (index < commentList.size()) {
            //弄一个队列，如果可视就入队
            List<Comment> list =  commentRepository.getCommentsByPostId(commentList.get(index).getCommentId());
            list.forEach(t -> {if (!t.getDisabled()) {
                commentList.add(t);
            }});
            index++;
        }
        return commentList;
    }

        @Transactional
        public Map<String,String> getFullRoomInfo(Long id){
            Room room = roomRepository.getRoomsByRoomId(id);
            Floor floor = room.getFloor();
            Building building = floor.getBuilding();
            Region region = building.getRegion();
            Map<String,String> map = new HashMap<>();
            map.put("region",region.getName());
            map.put("building",building.getName());
            map.put("floor",floor.getName());
            map.put("room",room.getName());
            map.put("roomid",room.getRoomId()+"");
            return map;
        }

}
