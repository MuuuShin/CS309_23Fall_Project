package cse.java2.project.controller;


import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class DemoUsersController {

  cse.java2.project.service.DemoQuestionService demoQuestionService;
  CommentService commentService;
  AnswerService answerService;

  DemoUsersController(cse.java2.project.service.DemoQuestionService demoQuestionService, CommentService commentService, AnswerService answerService) {
    this.demoQuestionService = demoQuestionService;
    this.commentService = commentService;
    this.answerService = answerService;
  }

  @RequestMapping("/users")
  public String users(Model model) {

    //1
    List<Integer> userNum = demoQuestionService.getAllQuestionsUserCount();
    List<Integer> userNumByInterval = new ArrayList<>();
    for (int i = 1; i < 11; i++) {
      userNumByInterval.add(demoQuestionService.getUserNumByInterval(userNum, i, i));
    }
    userNumByInterval.add(demoQuestionService.getUserNumByInterval(userNum, 11, 100));
    userNumByInterval.add(demoQuestionService.getUserNumByInterval(userNum, 101, 1000));

    List<String> xData1 = new ArrayList<>();
    for (int i = 1; i < 11; i++) {
      xData1.add(i + "");
    }
    xData1.add("11-100");
    xData1.add("101-1000");
    List<Integer> yData1 = userNumByInterval;
    List<Map<String, Object>> Data1 = new ArrayList<>();
    for (int i = 0; i < xData1.size(); i++) {
      Map<String, Object> map = new HashMap<>();
      map.put("name", xData1.get(i));
      map.put("value", yData1.get(i));
      Data1.add(map);
    }
    model.addAttribute("pieData1", Data1);

    //2
    Map<Long, Integer> commentUserList = commentService.getAllCommentUsers();
    Map<Long, Integer> answerUserList = answerService.getAllAnswerUsers();
    List<String> xData2 = new ArrayList<>();
    xData2.add("CommentUser");
    xData2.add("AnswerUser");
    List<Integer> yData2 = new ArrayList<>();
    yData2.add(commentUserList.size());
    yData2.add(answerUserList.size());
    List<Map<String, Object>> Data2 = new ArrayList<>();
    for (int i = 0; i < xData2.size(); i++) {
      Map<String, Object> map = new HashMap<>();
      map.put("name", xData2.get(i));
      map.put("value", yData2.get(i));
      Data2.add(map);
    }
    model.addAttribute("pieData2", Data2);


    //3
    Map<Long, Integer> questionUserList = demoQuestionService.getAllQuestionUsers();
    Set<Long> userList = new HashSet<>();
    userList.addAll(questionUserList.keySet());
    userList.addAll(commentUserList.keySet());
    userList.addAll(answerUserList.keySet());
    List<Pair<Long, Integer>> userScoreList = new ArrayList<>();
    for (Long userId : userList) {
      if (userId == null || userId == -1) continue;
      //question*5+answer*3+comment*2
      int score = 0;
      if (questionUserList.containsKey(userId)) {
        score += questionUserList.get(userId) * 5;
      }
      if (answerUserList.containsKey(userId)) {
        score += answerUserList.get(userId) * 3;
      }
      if (commentUserList.containsKey(userId)) {
        score += commentUserList.get(userId) * 2;
      }
      userScoreList.add(Pair.of(userId, score));
    }
    //降序
    userScoreList.sort((o1, o2) -> o2.getSecond().compareTo(o1.getSecond()));


    List<Map<String, Object>> Data3 = new ArrayList<>();
    for (int i = 0; i < 40; i++) {
      Map<String, Object> map = new HashMap<>();
      map.put("name", userScoreList.get(i).getFirst());
      map.put("value", userScoreList.get(i).getSecond());
      Data3.add(map);
    }
    model.addAttribute("wordcloudData", Data3);

    return "users";
  }

}