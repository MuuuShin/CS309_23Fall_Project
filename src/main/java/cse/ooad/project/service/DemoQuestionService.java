package cse.java2.project.service;

import cse.java2.project.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DemoQuestionService {
  private final QuestionRepository questionRepository;
  private final TagRepository tagRepository;

  public DemoQuestionService(QuestionRepository questionRepository,
                             TagRepository TagRepository) {
    this.questionRepository = questionRepository;
    this.tagRepository = TagRepository;
  }

  public List<Question> getAllQuestions() {
    return questionRepository.findAll();
  }

  public Map<Long, Integer> getAllQuestionUsers() {
    List<Question> questionList = getAllQuestions();
    Map<Long, Integer> userList = new HashMap<>();
    for (Question question : questionList) {
      if (userList.containsKey(question.getAccountId())) {
        userList.put(question.getAccountId(), userList.get(question.getAccountId()) + 1);
      } else {
        userList.put(question.getAccountId(), 1);
      }
    }
    return userList;
  }

  public Optional<Question> getQuestion(Long questionId) {
    return questionRepository.findById(questionId);
  }

  //used by restapi
  public List<Question> getQuestionUnionByTags(List<String> tags) {
    Set<Question> questions = new HashSet<>();
    for (String tag : tags) {
      Tag t = tagRepository.findByTag(tag);
      if (t != null)
        questions.addAll(t.getQuestionList());
    }
    //distinct
    return new ArrayList<>(questions);
  }

  public double getNoAnswerQuestionPercentage() {
    long noAnswerNum = questionRepository.countByAnswerCount(0);
    long totalNum = questionRepository.count();
    double percentage = (double) noAnswerNum / totalNum;
    return percentage;
  }

  public double getAvgAnswerCount() {
    return questionRepository.findAvgAnswerCount();
  }

  public int getMaxAnswerCount() {
    return questionRepository.findMaxAnswerCount();
  }

  public List<Object[]> getQuestionCountGroupByAnswerCount() {
    return questionRepository.findQuestionCountGroupByAnswerCount();
  }

  //used by restapi
  public List<Question> getQuestionIntersectionByTags(List<Tag> tags) {
    Set<Question> questions1 = new HashSet<>();
    Set<Question> questions2 = new HashSet<>();
    boolean first = true;
    for (Tag tag : tags) {
      if (first) {
        questions1.addAll(tag.getQuestionList());
        first = false;
      } else {
        questions2.addAll(tag.getQuestionList());
        questions1.retainAll(questions2);
      }
    }
    //distinct
    return new ArrayList<>(questions1);
  }

  public List<Integer> getAllQuestionsUserCount() {
    List<Question> questionList = questionRepository.findAll();
    List<Integer> userNum = new ArrayList<>();
    for (Question question : questionList) {
      userNum.add(question.getUserCount());
    }
    return userNum;
  }


  public Integer getUserNumByInterval(List<Integer> userNum, int low, int high) {
    int count = 0;
    for (Integer integer : userNum) {
      if (integer >= low && integer <= high) {
        count++;
      }
    }
    return count;
  }

}
