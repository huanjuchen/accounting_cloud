package xyz.huanju.accounting.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import xyz.huanju.accounting.domain.vo.SubjectVO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 科目
 * @author HuanJu
 * @date 2020/8/8 17:13
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Subject implements Serializable {

    private Integer id;
    /**
     * 科目代码
     */
    @NotNull(message = "科目代码不能为空")
    @Size(min = 4, max = 8, message = "科目代码的长度为${min}至${max}位")
    private String code;
    /**
     * 科目名
     */
    @NotNull(message = "科目名不可为空")
    @Size(max = 20, min = 1, message = "长度不能超过${max}")
    private String name;
    /**
     * 科目类别
     * 1、资产类
     * 2、负债类
     * 3、共同类
     * 4、所有者权益类
     * 5、成本类
     * 6、损益类-收入
     * 7、损益类-费用
     */
    private Integer category;

    /**
     * 上级科目ID
     */
    private Integer parentId;

    /**
     * 上级科目
     */
    private Subject parent;

    /**
     * 科目备注
     */
    private String remark;
    /**
     * 科目标识，标注科目是否可用
     */
    private Boolean valid;

    public SubjectVO covert() {
        SubjectVO subjectVo = new SubjectVO();
        subjectVo.setId(this.id)
                .setCode(this.code)
                .setName(this.name)
                .setCategory(this.category)
                .setParent(this.parent==null?null:this.parent.covert())
                .setRemark(this.remark)
                .setValid(this.valid);
        return subjectVo;
    }

    public static List<SubjectVO> listCovert(List<Subject> subjects) {
        List<SubjectVO> subjectVos = new ArrayList<>(subjects.size());
        for (Subject subject : subjects) {
            subjectVos.add(subject.covert());
        }
        return subjectVos;
    }

}
