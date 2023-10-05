package cse.ooad.project.model;

import lombok.Data;
import lombok.Setter;
import java.util.Objects;

@Entity
@Table(name = "room")
@Data
@Setter
public class Room {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "room_id")
  private Long roomId;

  private Long type;


}
