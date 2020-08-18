package xyz.huanju.accounting.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.huanju.accounting.converter.ProofConverter;
import xyz.huanju.accounting.domain.Proof;
import xyz.huanju.accounting.domain.ProofItem;
import xyz.huanju.accounting.domain.response.CommonResult;
import xyz.huanju.accounting.domain.vo.ProofVO;
import xyz.huanju.accounting.exception.RemoteCallException;
import xyz.huanju.accounting.feign.ProofFeignClient;
import xyz.huanju.accounting.service.ProofService;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author HuanJu
 * @date 2020/8/19 2:02
 */
@Service
@Slf4j
public class ProofServiceImpl implements ProofService {

    @Resource
    private ProofFeignClient proofFeignClient;

    private final ProofConverter proofConverter=ProofConverter.INSTANCE;

    private final static Integer OK =200;


    @Override
    public Proof getProof(Integer id) {
        CommonResult<ProofVO> commonResult=proofFeignClient.find(id);
        if (!Objects.equals(commonResult.getCode(),OK )){
            log.error("proof service 凭证服务调用错误");
            throw new RemoteCallException(500,"服务器错误");
        }
        return proofConverter.convertToPojo(commonResult.getData());
    }

    @Override
    public void update(Proof proof) {
        CommonResult<ProofVO> commonResult=proofFeignClient.update(proof);
        if (!Objects.equals(commonResult.getCode(),OK )){
            log.error("proof service 凭证服务调用错误");
            throw new RemoteCallException(500,"服务器错误");
        }

    }

    @Override
    public void updateItem(ProofItem proofItem) {
        CommonResult<ProofVO> commonResult=proofFeignClient.updateItem(proofItem);
        if (!Objects.equals(commonResult.getCode(),OK )){
            log.error("proof service 凭证服务调用错误");
            throw new RemoteCallException(500,"服务器错误");
        }
    }
}
