package xyz.huanju.accounting.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.huanju.accounting.converter.SubjectConverter;
import xyz.huanju.accounting.domain.Subject;
import xyz.huanju.accounting.domain.response.CommonResult;
import xyz.huanju.accounting.domain.response.ResultCode;
import xyz.huanju.accounting.domain.vo.SubjectVO;
import xyz.huanju.accounting.exception.RemoteCallException;
import xyz.huanju.accounting.feign.SubjectServiceFeignClient;
import xyz.huanju.accounting.service.SubjectService;

import javax.annotation.Resource;

/**
 * @author HuanJu
 * @date 2020/8/11 20:25
 */
@Service
@Slf4j
public class SubjectServiceImpl implements SubjectService {

    @Resource
    private SubjectServiceFeignClient subjectServiceFeignClient;

    private final SubjectConverter converter = SubjectConverter.INSTANCE;

    @Override
    public Subject getSubjectById(Integer id) {

        CommonResult<SubjectVO> subjectCommonResult = subjectServiceFeignClient.getSubjectById(id);
        return commonResultHandle(subjectCommonResult);
    }

    @Override
    public Subject getSubjectByCode(String code) {
        CommonResult<SubjectVO> subjectCommonResult = subjectServiceFeignClient.getSubjectByCode(code);
        return commonResultHandle(subjectCommonResult);
    }

    private Subject commonResultHandle(CommonResult<SubjectVO> commonResult) {
        if (commonResult == null) {
            return null;
        }
        if (!commonResult.getCode().equals(ResultCode.OK)) {
            log.error("远程服务调用错误");
            throw new RemoteCallException(ResultCode.INTERNAL_SERVER_ERROR, "服务器内部错误");
        }
        SubjectVO subjectVO = commonResult.getData();
        if (subjectVO == null) {
            return null;
        } else {
            return converter.convertToPojo(subjectVO);
        }
    }

}
