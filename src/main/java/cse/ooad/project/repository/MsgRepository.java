package cse.ooad.project.repository;



import cse.ooad.project.model.Msg;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MsgRepository extends JpaRepository<Msg, Long> {


    List<Msg> getMsgsBySrcIdAndDstId(Long srcId, Long DistId);

    List<Msg> getMsgsByDstIdAndStatusAndType(Long DistId, Integer status, Integer type);


    Msg getMsgByMsgId(Long msgId);

    List<Msg> getMsgsByDstId(Long DistId);
}
