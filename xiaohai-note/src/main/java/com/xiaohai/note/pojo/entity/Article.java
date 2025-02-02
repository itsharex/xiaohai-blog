package com.xiaohai.note.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author xiaohai
 * @since 2023-04-04
 */
@Getter
@Setter
@EqualsAndHashCode()
@TableName("b_article")
@Schema(name = "Article", description = "文章表")
public class Article  implements Serializable  {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户id")
    private Integer userId;

    @Schema(description = "分类id")
    private Integer categoryId;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章简介")
    private String summary;

    @Schema(description = "封面")
    private String cover;

    @Schema(description = "内容")
    private String text;

    @Schema(description = "是否发布(0否，1是)")
    private Integer isPush;

    @Schema(description = "是否顶置(0否，1是)")
    private Integer isTop;

    @Schema(description = "顶置时间")
    /**
     *  设置策略为IGNORED，忽略null值的判断
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime topTime;

    @Schema(description = "是否原创 (0原创，1转载)")
    private Integer isOriginal;

    @Schema(description = "转载地址")
    private String originalUrl;

    @Schema(description = "浏览量")
    private Integer pageView;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    @Schema(description = "更新时间")
    private LocalDateTime updatedTime;
}
