package xyz.huanju.accounting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.huanju.accounting.converter.ProofConverter;
import xyz.huanju.accounting.domain.Proof;
import xyz.huanju.accounting.domain.ProofItem;
import xyz.huanju.accounting.domain.response.CommonResult;
import xyz.huanju.accounting.domain.vo.ProofVO;
import xyz.huanju.accounting.service.ProofService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/8/11 23:54
 */
@RestController
@Slf4j
public class ProofController {

    @Resource
    private ProofService proofService;

    private final Object object = new Object();
    private static final String ID_DESC = "idDESC";
    private static final String ID_ASC = "idASC";
    private static final String DATE_DESC = "dateDESC";
    private static final String DATE_ASC = "dateASC";
    private static final String RID_DESC = "ridDESC";
    private static final String RID_ASC = "ridASC";

    @PostMapping("/proof")
    public CommonResult<Object> createProof(@Validated @RequestBody Proof proof, HttpServletRequest request) {
        String tokenId = request.getHeader("token_id");
        proofService.save(proof, tokenId);
        return CommonResult.ok();
    }

    /**
     * 查询会计凭证
     *
     * @param rid       记账人用户编号
     * @param startDate 筛选开始日期
     * @param endDate   筛选结束日期
     * @param verify    审核类型
     * @param orderType 排序类型
     * @param page      页数
     * @param pageSize  每页数量
     */
    @GetMapping("/proof")
    public CommonResult<List<ProofVO>> list(Integer rid, String startDate, String endDate, Integer verify, String orderType, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        //包装查询条件
        if (rid != null) {
            map.put("rid", rid);
        }
        if (startDate != null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                map.put("startDate", format.parse(startDate));
            } catch (ParseException e) {
                log.info("日期转换失败,已忽略");
                log.debug("调试日志" + e.getMessage());
            }
            if (endDate != null) {
                try {
                    map.put("endDate", format.parse(endDate));
                } catch (ParseException e) {
                    log.info("日期转换失败,已忽略");
                    log.debug("调试日志" + e.getMessage());
                }

            } else {
                map.put("endDate", new Date());
            }
        }
        if (verify != null) {
            if (verify == 0 || verify == 1 || verify == -1) {
                map.put("verify", verify);
            }
        }
        if (orderType != null) {
            switch (orderType) {
                case ID_ASC:
                    map.put(ID_ASC, object);
                    break;
                case ID_DESC:
                    map.put(ID_DESC, object);
                    break;
                case DATE_ASC:
                    map.put(DATE_ASC, object);
                    break;
                case DATE_DESC:
                    map.put(DATE_DESC, object);
                    break;
                case RID_ASC:
                    map.put(RID_ASC, object);
                    break;
                case RID_DESC:
                    map.put(RID_DESC, object);
                    break;
                default:
                    break;
            }

        }
        if (page != null && pageSize != null) {
            map.put("offset", page > 0 ? ((page - 1) * pageSize) : 0);
            map.put("count", pageSize > 1 ? pageSize : 1);
        } else {
            map.put("offset", 0);
            map.put("count", 10);
        }

        //调用Service层获取数据
        List<Proof> proofs = proofService.list(map);
        return CommonResult.ok(ProofConverter.INSTANCE.convertToVoList(proofs));
    }

    @GetMapping("/proof/count")
    public CommonResult<Integer> count(Integer rid, String startDate, String endDate, Integer verify) {
        Map<String, Object> map = new HashMap<>();
        //包装查询条件
        if (rid != null) {
            map.put("rid", rid);
        }
        if (startDate != null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                map.put("startDate", format.parse(startDate));
            } catch (ParseException e) {
                log.info("日期转换失败,已忽略");
                log.debug("调试日志" + e.getMessage());
            }
            if (endDate != null) {
                try {
                    map.put("endDate", format.parse(endDate));
                } catch (ParseException e) {
                    log.info("日期转换失败,已忽略");
                    log.debug("调试日志" + e.getMessage());
                }

            } else {
                map.put("endDate", new Date());
            }
        }
        if (verify != null) {
            if (verify == 0 || verify == 1 || verify == -1) {
                map.put("verify", verify);
            }
        }

        Integer count = proofService.count(map);
        if (count != null) {
            return CommonResult.ok(count);
        } else {
            return CommonResult.ok(0);
        }
    }

    @GetMapping("/proof/{id}")
    public CommonResult<ProofVO> find(@PathVariable Integer id) {
        Proof proof = proofService.find(id);
        return CommonResult.ok(ProofConverter.INSTANCE.convertToVo(proof));
    }

    @PutMapping("/proof")
    public CommonResult<ProofVO> update(@RequestBody Proof proof){
        proofService.update(proof);
        return CommonResult.ok();
    }

    @PutMapping("/proof/item")
    public CommonResult<ProofVO> updateItem(@RequestBody ProofItem proofItem){
        return CommonResult.ok();
    }


}
