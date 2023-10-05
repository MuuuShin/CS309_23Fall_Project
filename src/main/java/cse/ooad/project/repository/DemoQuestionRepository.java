package cse.java2.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemoQuestionRepository extends JpaRepository<Question, Long> {
  long countByAnswerCount(int answerCount);

  long count();

  long countTop1ByAnswerCount(int answerCount);

  // find the avg of the answer count
  long countByAnswerCountGreaterThanEqual(int answerCount);

  @Query("SELECT MAX(q.answerCount) FROM Question q")
  Integer findMaxAnswerCount();

  @Query("SELECT AVG(q.answerCount) FROM Question q")
  Double findAvgAnswerCount();

  @Query("SELECT q.answerCount, COUNT(q) FROM Question q GROUP BY q.answerCount")
  List<Object[]> findQuestionCountGroupByAnswerCount();

  Question findQuestionByQuestionId(long questionId);

  @Query(value = "SELECT (regexp_match(q.body, 'java\\.[a-zA-Z0-9]+?\\.[a-zA-Z0-9]+(?=[^a-zA-Z0-9])'))[1] FROM Question q WHERE q.body ~ 'java\\.[a-zA-Z0-9]+?\\.[a-zA-Z0-9]+'", nativeQuery = true)
  List<String> findJavaApi();

  @Query(value = "SELECT (regexp_match(q.title, 'java\\.[a-zA-Z0-9]+?\\.[a-zA-Z0-9]+(?=[^a-zA-Z0-9])'))[1] FROM Question q WHERE q.title ~ 'java\\.[a-zA-Z0-9]+?\\.[a-zA-Z0-9]+'", nativeQuery = true)
  List<String> findJavaApiTitle();

}
