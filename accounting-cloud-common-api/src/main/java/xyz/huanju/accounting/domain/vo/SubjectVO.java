package xyz.huanju.accounting.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author HuanJu
 * @date 2020/8/8 17:26
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SubjectVO implements Serializable {

    private Integer id;

    private String code;

    private String name;

    private Integer category;

    private SubjectVO parent;

    private String remark;

    private Boolean valid;

}
