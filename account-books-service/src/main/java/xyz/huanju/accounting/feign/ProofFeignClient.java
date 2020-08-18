package xyz.huanju.accounting.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.huanju.accounting.domain.Proof;
import xyz.huanju.accounting.domain.ProofItem;
import xyz.huanju.accounting.domain.response.CommonResult;
import xyz.huanju.accounting.domain.vo.ProofVO;

/**
 * @author HuanJu
 * @date 2020/8/19 1:59
 */
@FeignClient(value = "${remote.service.proof}")
public interface ProofFeignClient {

    /**
     * 根据凭证id 获取凭证
     * @param id id
     * @return common result
     */
    @GetMapping("/proof/{id}")
    CommonResult<ProofVO> find(@PathVariable Integer id);


    /**
     * 更新凭证
     * @param proof proof
     * @return common result
     */
    @PutMapping("/proof")
    CommonResult<ProofVO> update(@RequestBody Proof proof);


    /**
     * 更新 proof item
     *
     * @param proofItem proof item
     * @return common result
     */
    @PutMapping("/proof/item")
    CommonResult<ProofVO> updateItem(@RequestBody ProofItem proofItem);

}
