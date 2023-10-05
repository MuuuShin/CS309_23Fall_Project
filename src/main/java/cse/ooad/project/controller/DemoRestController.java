package cse.java2.project.controller;

import cse.java2.project.model.Comment;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1")
public class DemoRestController {
  private final cse.java2.project.service.DemoQuestionService demoQuestionService;
  private final AnswerService answerService;

  private final CommentService commentService;

  private final TagService tagService;

  public DemoRestController(cse.java2.project.service.DemoQuestionService demoQuestionService,
                            AnswerService answerService,
                            CommentService commentService,
                            TagService tagService) {
    this.demoQuestionService = demoQuestionService;
    this.answerService = answerService;
    this.commentService = commentService;
    this.tagService = tagService;
  }

  @GetMapping("/questions")
  public ResponseEntity<?> getQuestions(@RequestParam(value = "tag")
                                        List<String> tags) {
    if (tags.size() == 0) {
      return ResponseEntity.badRequest().build();
    }
    List<Question> questions = demoQuestionService.getQuestionUnionByTags(tags);
    int sum = questions.size();
    Pair<Integer, List<Question>> pair = Pair.of(sum, questions);
    return ResponseEntity.ok(pair);
  }

  @GetMapping("/questions/{questionId}")
  public ResponseEntity<?> getQuestionrById(@PathVariable("questionId") Long questionId) {
    Optional<Question> question = demoQuestionService.getQuestion(questionId);
    if (question.isPresent()) {
      return ResponseEntity.ok(question.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/answers")
  public ResponseEntity<?> getAnswers(@RequestParam("questionId") Long questionId) {
    Optional<Question> question = demoQuestionService.getQuestion(questionId);
    if (question.isPresent()) {
      return ResponseEntity.ok(question.get().getAnswerList());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/answers/{answerId}")
  public ResponseEntity<?> getAnswerById(@PathVariable("answerId") Long answerId) {
    Optional<Answer> answer = answerService.getAnswer(answerId);
    if (answer.isPresent()) {
      return ResponseEntity.ok(answer.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }


  @GetMapping("/comments")
  public ResponseEntity<?> getComments(@RequestParam("answerId") Long answerId) {
    Optional<Answer> answer = answerService.getAnswer(answerId);
    if (answer.isPresent()) {
      return ResponseEntity.ok(answer.get().getCommentList());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/comments/{commentId}")
  public ResponseEntity<?> getCommentById(@PathVariable("commentId") Long commentId) {
    Optional<Comment> comment = commentService.getComment(commentId);
    if (comment.isPresent()) {
      return ResponseEntity.ok(comment.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/top40tag")
  public ResponseEntity<?> getTop40Tag() {
    List<Pair<String, Integer>> top40Tag = tagService.getTop40FrequencyTags();
    return ResponseEntity.ok(top40Tag);
  }
}
