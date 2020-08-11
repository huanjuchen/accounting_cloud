package xyz.huanju.accounting.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author HuanJu
 * @date 2020/8/8 18:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ProofVO implements Serializable {

    private Integer id;

    private Date date;

    private Integer invoiceCount;

    private String manager;

    private String collection;

    private UserVO recorder;

    private String cashier;

    private String payer;

    private Integer verify;

    private UserVO verifyUser;

    private Date verifyTime;

    private List<ProofItemVO> items;

    private Integer trash;

}
