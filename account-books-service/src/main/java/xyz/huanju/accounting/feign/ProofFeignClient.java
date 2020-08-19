package xyz.huanju.accounting.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
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
    @RequestMapping(value = "/proof/{id}",method = RequestMethod.GET)
    CommonResult<ProofVO> find(@PathVariable("id") Integer id);


    /**
     * 更新凭证
     * @param proof proof
     * @return common result
     */
    @RequestMapping(value = "/proof",method = RequestMethod.PUT)
    CommonResult<ProofVO> update(@RequestBody Proof proof);


    /**
     * 更新 proof item
     *
     * @param proofItem proof item
     * @return common result
     */
    @RequestMapping(value = "/proof/item",method = RequestMethod.PUT)
    CommonResult<ProofVO> updateItem(@RequestBody ProofItem proofItem);

}
