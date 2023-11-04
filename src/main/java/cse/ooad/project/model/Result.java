package cse.ooad.project.model;

import lombok.Data;


/**
 * {@link  Result<T>}用于表示发送给前端的信息的实体类，包括信息的基本信息和属性。<br>
 * 属性列表：
 * <ul>
 *   <li>code: 状态代码。</li>
 *   <li>msg: 状态代码对应的消息。</li>
 *   <li>data: 发送的实际内容。</li>
 * </ul>
 */
@Data
public class Result<T> {
    private String code;
    private String msg;
    private T data;
}

