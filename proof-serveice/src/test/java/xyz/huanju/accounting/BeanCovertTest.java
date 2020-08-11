package xyz.huanju.accounting;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.huanju.accounting.converter.ProofConverter;
import xyz.huanju.accounting.domain.Proof;
import xyz.huanju.accounting.domain.ProofItem;
import xyz.huanju.accounting.domain.vo.ProofVO;
import xyz.huanju.accounting.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HuanJu
 * @date 2020/8/11 14:02
 */
@SpringBootTest
public class BeanCovertTest {


    @Test
    void covertTest(){
        ProofItem proofItem=new ProofItem();
        proofItem.setId(1);

        Proof proof=new Proof();
        proof.setId(2);

        List<ProofItem> proofItems=new ArrayList<>();
        proofItems.add(proofItem);
        proofItems.add(proofItem);
        proofItems.add(proofItem);
        proofItems.add(proofItem);
        proofItems.add(proofItem);
        proof.setItems(proofItems);

        ProofVO proofVO= ProofConverter.INSTANCE.convertToVo(proof);

        System.out.println(JsonUtils.toJson(proofVO));



    }


}
