package cse.ooad.project.service;


import cse.ooad.project.model.Comment;
import cse.ooad.project.model.Group;
import cse.ooad.project.model.Room;
import cse.ooad.project.repository.CommentRepository;
import cse.ooad.project.repository.RoomRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoomService {


    @Autowired
    RoomRepository roomRepository;

    @Autowired
    CommentRepository commentRepository;


    @Transactional
    public List<Group>  getGroupStarList(Long id) {
        // todo 这里展示了两种绕过懒加载错误(lazy initialization exception)的方法

        // 第一种方法 Hibernate.initialize 对应的实体,简单粗暴最好理解
//        log.info("getGroupStarListByRoomId: {}", id);
//        Room r = roomRepository.getRoomsByRoomId(id);
//        log.info("get r");
//        List<Group> groupList = r.getGroupStarList();
//        log.info("getGroupStarListByRoomId end");
//        Hibernate.initialize(groupList);
//        return groupList;

        //  这是第二种方法,需要提前在repository里面定义一个投影接口 GroupStarListProjection
        //  本来投影是用来做查询优化的,但是查映射实体的时候还是会加载所有的属性,但是投影的查询可以绕过懒加载
        //  add: 这么写不用加载所有的属性 下面那种会加载room本身
        //  但是这么写有个铸币的问题 要判空 因为有room没有groupList返回的是一个List[null]
        log.info("getGroupStarListByRoomId: {}", id);
        List<RoomRepository.GroupStarListProjection> r=roomRepository.getGroupStarListByRoomId(id);
        if(r.isEmpty() || r.get(0) == null) {
            return new ArrayList<>();
        }
        log.info("get r");
        List<Group> groupList = r.stream().map(RoomRepository.GroupStarListProjection::getGroupStarList).toList();
        log.info("getGroupStarListByRoomId end");
        return groupList;
        // 等于这个
        // return roomRepository.getByRoomId(id).stream().map(RoomRepository.GroupStarListProjection::getGroupStarList).toList();

        //  这是第二种的换个格式 我也不知道为什么这么写的投影反而没有上面这种好,乐
//        log.info("getGroupStarListByRoomId: {}", id);
//        RoomRepository.GroupStarListProjection1 r=  roomRepository.getGroupByRoomId(id);
//        log.info("get r");
//        List<Group> groupList = r.getGroupStarList();
//        log.info("getGroupStarListByRoomId end");
//        return groupList;

        // 其实有第三种方法 也就是根本不管room
        // 直接 groupRepository.getGroupStarListByRoomId(id);
        // 但是这里是多对多 给封死了 sad

    }


    public List<Comment> getCommentsByRoom(Long id) {
        Room room = roomRepository.getRoomsByRoomId(id);
        int index = 0;
        List<Comment> commentList = new ArrayList<>();
        commentList.add(commentRepository.getCommentByCommentId(room.getCommentBaseId()));
        while (index < commentList.size()) {
            //弄一个队列，如果可视就入队
            List<Comment> list =  commentRepository.getCommentsByPostId(commentList.get(index).getPostId());
            list.forEach(t -> {if (t.getDisabled()) {
                commentList.add(t);
            }});

            index++;
        }
        return commentList;
    }


}
