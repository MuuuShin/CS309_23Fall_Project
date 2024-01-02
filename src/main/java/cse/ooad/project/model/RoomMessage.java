package cse.ooad.project.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomMessage {
    Long roomId;

    String roomName;

    String BuildingName;

    String floorName;

    String regionName;

}
