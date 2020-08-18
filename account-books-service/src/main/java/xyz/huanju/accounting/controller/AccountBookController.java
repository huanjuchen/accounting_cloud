package xyz.huanju.accounting.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.huanju.accounting.converter.BankAccountConverter;
import xyz.huanju.accounting.converter.CashAccountConverter;
import xyz.huanju.accounting.converter.LedgerAccountConverter;
import xyz.huanju.accounting.converter.SubAccountConverter;
import xyz.huanju.accounting.domain.BankAccount;
import xyz.huanju.accounting.domain.CashAccount;
import xyz.huanju.accounting.domain.LedgerAccount;
import xyz.huanju.accounting.domain.SubAccount;
import xyz.huanju.accounting.domain.response.CommonResult;
import xyz.huanju.accounting.domain.vo.BankAccountVO;
import xyz.huanju.accounting.domain.vo.CashAccountVO;
import xyz.huanju.accounting.domain.vo.LedgerAccountVO;
import xyz.huanju.accounting.domain.vo.SubAccountVO;
import xyz.huanju.accounting.service.AccountBookService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author HuanJu
 * @date 2020/8/18 2:19
 */
@RestController
public class AccountBookController {

    private AccountBookService accountBookService;

    private final BankAccountConverter bankAccountConverter = BankAccountConverter.INSTANCE;

    private final CashAccountConverter cashAccountConverter = CashAccountConverter.INSTANCE;

    private final LedgerAccountConverter ledgerAccountConverter = LedgerAccountConverter.INSTANCE;

    private final SubAccountConverter subAccountConverter = SubAccountConverter.INSTANCE;

    @Resource
    public void setAccountBookService(AccountBookService accountBookService) {
        this.accountBookService = accountBookService;
    }

    @GetMapping("/accountBook/bank")
    public CommonResult<List<BankAccountVO>> getBankAccountList(String startDate, String endDate, Integer page) {
        List<BankAccount> bankAccountList = accountBookService.getBankAccount(startDate, endDate, page);
        return CommonResult.ok(bankAccountConverter.convertToVoList(bankAccountList));
    }

    @GetMapping("/accountBook/bank/count")
    public CommonResult<Integer> getBankAccountCount(String startDate, String endDate) {
        Integer count = accountBookService.getBankAccountCount(startDate, endDate);
        return CommonResult.ok(count == null ? 0 : count + 1);
    }


    @GetMapping("/accountBook/cash")
    public CommonResult<List<CashAccountVO>> getCashAccountList(String startDate, String endDate, Integer page) {
        List<CashAccount> cashAccountList = accountBookService.getCashAccount(startDate, endDate, page);
        return CommonResult.ok(cashAccountConverter.convertToVoList(cashAccountList));
    }

    @GetMapping("/accountBook/cash/count")
    public CommonResult<Integer> getCashAccountCount(String startDate, String endDate) {
        Integer count = accountBookService.getCashAccountCount(startDate, endDate);
        return CommonResult.ok(count == null ? 0 : count + 1);
    }


    @GetMapping("/accountBook/ledger")
    public CommonResult<List<LedgerAccountVO>> getLedgerAccountList(Integer subjectId, String startDate, String endDate) {
        List<LedgerAccount> ledgerAccountList = accountBookService.getLedgerAccount(subjectId, startDate, endDate);
        return CommonResult.ok(ledgerAccountConverter.convertToVoList(ledgerAccountList));
    }


    @GetMapping("/accountBook/sub")
    public CommonResult<List<SubAccountVO>> getSubAccountList(Integer subjectId, String startDate, String endDate, Integer page) {
        List<SubAccount> subAccountList = accountBookService.getSubAccount(subjectId, startDate, endDate, page);
        return CommonResult.ok(subAccountConverter.convertToVoList(subAccountList));
    }

    @GetMapping("/accountBook/sub/count")
    public CommonResult<Integer> getSubAccountCount(Integer subjectId, String startDate, String endDate) {
        Integer count = accountBookService.getSubAccountCount(subjectId, startDate, endDate);
        return CommonResult.ok(count == null ? 0 : count + 1);
    }

}
