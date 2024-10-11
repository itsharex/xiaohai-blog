package com.xiaohai.note.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 评论表 DTO 数据传输对象
 * </p>
 *
 * @author xiaohai
 * @since 2023-05-24
 */
@Getter
@Setter
@Schema(name = "CommentDto", description = "评论表 DTO 数据传输对象")
public class CommentDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "父id")
    private Integer parentId;

    @Schema(description = "文章id(0 代表留言)")
    private Integer articleId;

    @Schema(description = "评论人id")
    private Integer userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "头像地址")
    private String avatar;

    @Schema(description = "回复人id")
    private Integer replyUserId;

    @Schema(description = "回复人用户名")
    private String replyUsername;

    @Schema(description = "回复人头像地址")
    private String replyAvatar;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;
    
    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "回复列表")
    private List<CommentDto> children;
}
