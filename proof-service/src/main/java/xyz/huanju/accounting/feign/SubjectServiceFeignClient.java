package xyz.huanju.accounting.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.huanju.accounting.domain.Subject;
import xyz.huanju.accounting.domain.response.CommonResult;
import xyz.huanju.accounting.domain.vo.SubjectVO;

/**
 * @author HuanJu
 * @date 2020/8/11 1:36
 */
@FeignClient(value = "${remote.service.subject}")
public interface SubjectServiceFeignClient {

    /**
     * 根据ID获得科目
     *
     * @param id 科目Id
     * @return subject
     */
    @GetMapping(value = {"/subject/{id}"})
    CommonResult<SubjectVO> getSubjectById(@PathVariable(value = "id") Integer id);


    /**
     * 根据科目代码查找科目
     *
     * @param code 代码
     * @return subject
     */
    @GetMapping("/subject/code/{code}")
    CommonResult<SubjectVO> getSubjectByCode(@PathVariable(value = "code") String code);

}
