package com.xiaohai.system.pojo.vo;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 字典数据表 VO（View Object）：显示层对象
* </p>
*
* @author xiaohai
* @since 2023-02-03
*/
@Getter
@Setter
@Schema(name = "DictDataVo", description = "字典数据表 VO（View Object）：显示层对象")
public class DictDataVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "字典排序")
    private Integer dictSort;

    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "字典标签")
    private String dictLabel;

    @Schema(description = "字典键值")
    private String dictValue;

    @Schema(description = "状态（0正常 1停用）")
    private String status;
}
