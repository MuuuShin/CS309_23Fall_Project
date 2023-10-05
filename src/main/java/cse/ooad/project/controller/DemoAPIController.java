package cse.java2.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class DemoAPIController {
  private cse.java2.project.repository.DemoQuestionRepository demoQuestionRepository;
  private AnswerRepository answerRepository;
  private TagRepository tagRepository;
  private CommentRepository commentRepository;

  public DemoAPIController(cse.java2.project.repository.DemoQuestionRepository demoQuestionRepository,
                           AnswerRepository answerRepository,
                           TagRepository tagRepository,
                           CommentRepository commentRepository) {
    this.demoQuestionRepository = demoQuestionRepository;
    this.answerRepository = answerRepository;
    this.tagRepository = tagRepository;
    this.commentRepository = commentRepository;
  }

  @RequestMapping("/API")
  public String API(Model model) {
    //java api热度=tag*10 +标题*10 + question_body*5 +answer_body*3+ comment *1

    List<String> questionApi = demoQuestionRepository.findJavaApi();
    List<String> questionTitleApi = demoQuestionRepository.findJavaApiTitle();
    List<String> answerApi = answerRepository.findJavaApi();
    List<String> commentApi = commentRepository.findJavaApi();
    List<Object[]> tagApi = tagRepository.findJavaApi();
    Map<String, Integer> apiMap = new java.util.HashMap<>();
    for (Object[] api : tagApi) {
      if (apiMap.containsKey((String) api[0])) {
        apiMap.put((String) api[0], apiMap.get(api[0]) + Integer.parseInt(Objects.toString(api[1])) * 10);
      } else {
        apiMap.put((String) api[0], Integer.parseInt(Objects.toString(api[1])) * 10);
      }
    }
    for (String api : questionTitleApi) {
      if (apiMap.containsKey(api)) {
        apiMap.put(api, apiMap.get(api) + 10);
      } else {
        apiMap.put(api, 10);
      }
    }
    for (String api : questionApi) {
      if (apiMap.containsKey(api)) {
        apiMap.put(api, apiMap.get(api) + 3);
      } else {
        apiMap.put(api, 3);
      }
    }
    for (String api : answerApi) {
      if (apiMap.containsKey(api)) {
        apiMap.put(api, apiMap.get(api) + 2);
      } else {
        apiMap.put(api, 3);
      }
    }
    for (String api : commentApi) {
      if (apiMap.containsKey(api)) {
        apiMap.put(api, apiMap.get(api) + 1);
      } else {
        apiMap.put(api, 1);
      }
    }


    List<Map<String, Object>> wordcloudData = new ArrayList<>();

    for (Map.Entry<String, Integer> entry : apiMap.entrySet()) {
      Map<String, Object> map = new java.util.HashMap<>();
      map.put("name", entry.getKey());
      map.put("value", entry.getValue());
      wordcloudData.add(map);
    }

    model.addAttribute("wordcloudData", wordcloudData);

    return "api";
  }
}
