package xyz.huanju.accounting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.huanju.accounting.converter.SubjectConverter;
import xyz.huanju.accounting.domain.Subject;
import xyz.huanju.accounting.domain.response.CommonResult;
import xyz.huanju.accounting.domain.vo.SubjectVO;
import xyz.huanju.accounting.service.SubjectService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/8/10 0:30
 */
@RestController
@Slf4j
public class SubjectController {

    @Resource(name = "subjectService")
    private SubjectService subjectService;

    private final SubjectConverter subjectConverter=SubjectConverter.INSTANCE;

    /**
     * 创建科目
     *
     * @param subject 科目实体
     */
    @PostMapping("/manage/subject")
    public CommonResult<SubjectVO> createSubject(@RequestBody @Validated Subject subject) {
        Subject temp = subjectService.save(subject);
        return CommonResult.ok(subjectConverter.convertToVo(temp));
    }


    /**
     * 根据ID获得科目
     *
     * @param id 科目Id
     */

    @GetMapping(value = {"/subject/{id}","/admin/subject/{id}"})
    public CommonResult<SubjectVO> getSubjectById(@PathVariable int id) {
        Subject subject = subjectService.find(id);
        return CommonResult.ok(subjectConverter.convertToVo(subject));
    }


    /**
     * 根据科目代码查找科目
     *
     * @param code 代码
     */
    @GetMapping("/subject/code/{code}")
    public CommonResult<SubjectVO> getSubjectByCode(@PathVariable String code){
        Subject subject=subjectService.findByCode(code);
        return CommonResult.ok(subjectConverter.convertToVo(subject));
    }


    /**
     * 获取科目列表
     *
     * @param searchWord 查找关键字 code or name
     * @param parentId   上级科目ID
     * @param valid      科目状态 启用或禁用 true or false
     * @param desc       根据 code倒序 not_null or null
     * @param page       分页当前页 number or null
     * @param pageSize   分页每页显示的条目 number or null
     */

    @GetMapping({"/subject"})
    public CommonResult<List<SubjectVO>> list(String searchWord, String valid, String desc,
                             Integer parentId, Integer page, Integer pageSize
    ) {

        Map<String, Object> map = paramHandle(searchWord, valid, desc, parentId, page, pageSize);
        List<Subject> subjects = subjectService.list(map);
        return CommonResult.ok(subjectConverter.convertToVoList(subjects));
    }


    /**
     * 请求参数处理
     */
    private Map<String, Object> paramHandle(String searchWord,
                                            String valid,
                                            String desc,
                                            Integer parentId,
                                            Integer page,
                                            Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        if (searchWord != null && searchWord.length() > 0) {
            try {
                Integer codeNum = Integer.valueOf(searchWord);
                map.put("codeNum", codeNum);
                map.put("codeSw", searchWord);
            } catch (NumberFormatException e) {
                map.put("nameSw", searchWord);
            }
        }
        if (parentId != null) {
            map.put("parentId", parentId);
        }

        if (valid != null && valid.length() > 0) {
            map.put("valid", Boolean.valueOf(valid));
        }

        if (desc != null && desc.length() > 0) {
            map.put("desc", new Object());
        }

        if (page != null && pageSize != null) {
            map.put("offset", page > 0 ? ((page - 1) * pageSize) : 0);
            map.put("count", pageSize > 1 ? pageSize : 1);
        }

        return map;


    }


    /**
     * 获取科目总条数
     *
     * @param searchWord 查找关键字 code or name
     * @param parentId   上级科目ID
     * @param valid      科目状态 启用或禁用 true or false
     */
    @GetMapping("/subject/count")
    public CommonResult<Integer> count(String searchWord,
                              Integer parentId,
                              String valid) {

        Map<String, Object> map = paramHandle(searchWord, valid, null, parentId, null, null);
        Integer result = subjectService.count(map);
        if (result == null) {
            return CommonResult.ok(0);
        } else {
            return CommonResult.ok(result);
        }
    }


    /**
     * 更新科目
     *
     * @param subject 科目数据
     */
    @PutMapping("/manage/subject")
    public CommonResult<SubjectVO> update(@RequestBody @Validated Subject subject) {
        subjectService.update(subject);
        return CommonResult.ok();
    }


    /**
     * 启用科目
     *
     * @param subjectId 科目ID
     */
    @PutMapping("/manage/subject/lock/{subjectId}")
    public CommonResult<SubjectVO> lock(@PathVariable Integer subjectId) {
        subjectService.lock(subjectId);
        return CommonResult.ok();
    }


    /**
     * 启用科目
     *
     * @param subjectId 科目ID
     */

    @PutMapping("/manage/subject/unlock/{subjectId}")
    public CommonResult<SubjectVO> unlock(@PathVariable Integer subjectId) {
        subjectService.unlock(subjectId);
        return CommonResult.ok();
    }


    /**
     * 删除科目
     */
    @DeleteMapping("/manage/subject/{subjectId}")
    public CommonResult<SubjectVO> delete(@PathVariable Integer subjectId) {
        subjectService.delete(subjectId);
        return CommonResult.ok();
    }


}
